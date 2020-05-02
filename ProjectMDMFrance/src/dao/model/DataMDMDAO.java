package dao.model;

import java.util.ArrayList;
import java.util.Collection;

import model.DataMDM;
import model.Role;

public abstract class DataMDMDAO {

	/*----------Variables-----------*/

	/*----------Constructeur-----------*/

	/*----------Getters and setters-----------*/

	/*----------Methodes-----------*/

	/**
	 * Permet de charger une dataMDM a partir de son identifiant depuis la base
	 * de donnees.
	 * 
	 * @return DataMDM correspondant dans la base de donnees. return null si
	 *         l'object n'est pas trouve.
	 */
	public abstract DataMDM load(Object id);

	/**
	 * Permet de sauvegarder une DataMDM dans la base de donnees.
	 * 
	 * @param dataMdm
	 *            l'objet DataMDM a sauvegarder dans la base de donnees.
	 * @return Object correspondant a l'identifiant de l'object dans la base de
	 *         donnees.
	 */
	public abstract Object save(DataMDM dataMdm);
	/**
	 * Permet de mettre a jour une dataMDM dans la base de donnees.
	 * 
	 * @param dataMdm
	 *            la data a mettre a jour dans la base de donnees.
	 * @return Boolean signifiant que la mise a jour a ete effectuee dans la
	 *         base de donnees.
	 */
	public abstract Boolean update(DataMDM dataMdm);
	/**
	 * Permet de supprimer une DataMDM de la base de donnees. ainsi que chaque
	 * relation associ� a la dataMDM et les dataValue associe.
	 * 
	 * @param dataMdm
	 *            a supprimer de la base.
	 * @return True si la suppression a ete effectue, False sinon.
	 */
	public abstract Boolean delete(DataMDM dataMdm);

	
	/** Ajoute un utilisateur dans la communaut� data donn�e avec le role demand�
	 * @param user	
	 * 		Utilisateur � ajouter
	 * @param idCom
	 *            identifiant de la communaut� data
	 * @param role
	 * 		Role que va avoir l'utilisateur ajout�
	 * @author Lydia
	 */
	public abstract void addMember(Object idCom, DataMDM user, Role role);
	
	
	/** Accesseur pour r�cup�rer le responsable d'une communaut�
	 * 
	 * @return le responsable de la DataMDM donn�e
	 * 
	 * @author Tom Kuhnen
	 */
	public abstract DataMDM getResponsable();
	
	
	/** Accesseur pour r�cup�rer le tuteur d'une communaut�
	 * 
	 * @return le tuteur de la DataMDM donn�e
	 * 
	 * @author Tom Kuhnen
	 */
	public abstract DataMDM getTuteur();
	
	/** M�thode utilis�e uniquement lors de l'installation 
	 *  pour modifier le responsable en dur
	 * 
	 * @param com
	 * 		La communaut� de type Data pour laquelle on veut r�cup�rer le responsable
	 * @author Gaston Guy
	 */
	 public abstract void setResponsable();
	
	/** M�thode utilis�e uniquement lors de l'installation 
	 *  pour modifier le tuteur en dur
	 * 
	 * @param com
	 * 		La communaut� de type Data pour laquelle on veut r�cup�rer le tuteur
	 * @author Gaston Guy
	 */
	 public abstract void setTuteur();
	 
	 
	 
	 /**
		 * @author edwin
		 */
	 public abstract ArrayList<Object> getAllDataMDMId();
	 
	 public abstract ArrayList<Object> getAllDataValueId(DataMDM dataMDM);
	 
	 public abstract ArrayList<Object> getAllDataMDMFilsId();
	 

	 /** Modifie le role de l�utilisateur dans une communaut� data de la BD
	  * 
	  * @param id
	  * 		Identifiant du data dans la base
	  * @param user
	  * 		Utilisateur dont on va modifier le role
	  * @param role
	  * 		Role que va avoir l'utilisateur
	  * 
	  * @author Aur�lie :)
	  * 	 
	  */
	 public abstract void updateRole(Object id, DataMDM user, Role role);
}
