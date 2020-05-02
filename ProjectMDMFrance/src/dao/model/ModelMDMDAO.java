package dao.model;

import java.util.ArrayList;

import model.DataMDM;
import model.ModelMDM;
import model.Role;

public abstract class ModelMDMDAO {

	/****************************************************************
	 * Membres de la classe *
	 ****************************************************************/

	/****************************************************************
	 * M�thodes de la classe *
	 ****************************************************************/

	/**
	 * Permet de sauvegarder un modelMDM dans la base de donnees, et creer le
	 * lien avec la nature associe.
	 * 
	 * @param modelMdm
	 *            le model a sauvegarder dans la base.
	 * @return Object correspondant a l'id du noeud dans la base de donnees.
	 * @author Gaetan
	 */
	public abstract Object save(ModelMDM modelMdm);

	/**
	 * Permet charger un modelMDM de la base de donnees a partir de son
	 * identifiant.
	 * 
	 * @param id
	 *            : identifiant du modelMDM dans la base de donnees.
	 * 
	 * @return le modelMDM correspondant a l'id passe en parametre.
	 * @author Gaetan
	 */
	public abstract ModelMDM load(Object id);

	/**
	 * Permet de supprimer un modelMDM de la base de donnees. supprime donc tous
	 * les modelValue et dataMDM associ� a ce modelMDM
	 * 
	 * @param model
	 *            le model a supprimer
	 * @return True si la suppression a bien eu lieu, false sinon.
	 * @author Gaetan
	 */
	public abstract boolean delete(ModelMDM model);

	/**
	 * Permet de mettre a jour un modelMDM enregistre dans la base de donnees.
	 * 
	 * @param model
	 *            le model a mettre a jour.
	 * @return True si l'update s'est bien deroulee, false sinon.
	 * @author Gaetan
	 */
	public abstract Boolean update(ModelMDM model);

	/****************************************************************
	 * Accesseurs de la classe *
	 ****************************************************************/

	/**
	 * Ajoute un utilisateur dans la communaut� model donn�e avec le role
	 * demand�
	 * 
	 * @param user
	 *            Utilisateur � ajouter
	 * @param idCom
	 *            identifiant de la communaut� model
	 * @param role
	 *            Role que va avoir l'utilisateur ajout�
	 * 
	 * @author Lydia
	 */
	public abstract void addMember(Object idCom, DataMDM user, Role role);
	
	
	/** Accesseur pour r�cup�rer le tuteur d'une communaut�
	 * 
	 * @return le tuteur du ModelMDM donn�
	 *  
	 * @author Tom Kuhnen
	 */
	public abstract DataMDM getTuteur();
	
	/** Accesseur pour r�cup�rer le responsable d'une communaut�
	 * 
	 * @return le responsable du ModelMDM donn�
	 *	
	 * @author Tom Kuhnen
	 */
	public abstract DataMDM getResponsable();
	
	/** M�thode utilis�e uniquement lors de l'installation 
	 *  pour modifier le responsable en dur
	 * 
	 * @param com
	 * 		La communaut� de type Model pour laquelle on veut r�cup�rer le responsable
	 * @author Gaston Guy
	 */
	 public abstract void setResponsable();
	
	/** M�thode utilis�e uniquement lors de l'installation 
	 *  pour modifier le tuteur en dur
	 * 
	 * @param com
	 * 		La communaut� de type Model pour laquelle on veut r�cup�rer le tuteur
	 * @author Gaston Guy
	 */
	 public abstract void setTuteur();
	

	 /** Modifie le role de l�utilisateur dans une communaut� model de la BD
	  * 
	  * @param id
	  * 		Identifiant du modelMDM dans la base
	  * @param user
	  * 		Utilisateur dont on va modifier le role
	  * @param role
	  * 		Role que va avoir l'utilisateur
	  * 
	  * @author Aur�lie :)
	  * 	 
	  */
	public abstract void updateRole(Object id, DataMDM user, Role role);
	
	public abstract ArrayList<Object>getAllModelMDMId();
	
	public abstract ArrayList<Object>getAllModelMDMFilsId();
}
