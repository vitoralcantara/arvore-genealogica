package model;

import dao.factory.DAOFactory;
import dao.factory.Neo4jDAOFactory;
import dao.model.DataMDMDAO;
import dao.model.ModelMDMDAO;
import dao.neo4j.Neo4jDataMDMDAO;

public class DataMDM {

	/*----------Variables----------*/
	// identifiant du data dans la base de donnees
	Object identifiant;
	ModelMDM modelMdm;
	String label;
	DataMDM dataPere;


	/**
	 * Constructeur L'identifiant est initialise a -1, il faudra ajouter
	 * l'identifiant lors de l'enregistrement de la dataMDM dans la base de
	 * donnees
	 * 
	 * @param label
	 *            : string le label correspondant au dataMDM
	 * @author Gaetan
	 */
	public DataMDM(String label) {
		this.label = label;
		this.identifiant = null;
	}
	
	/**
	 * Constructeur L'identifiant est initialise a -1, il faudra ajouter
	 * l'identifiant lors de l'enregistrement de la dataMDM dans la base de
	 * donnees
	 * 
	 * @param label
	 *            : string le label correspondant au dataMDM
	 * @param modelMdm
	 * 				: ModelMDM correspondant au modelMDM associe au dataMDM
	 * @author Gaetan
	 */
	public DataMDM(ModelMDM modelMdm, String label) {
		this.label = label;
		this.identifiant = null;
		this.modelMdm = modelMdm;
	}
	

	/**
	 * Permet de recuperer l'identifiant de la data qui a ete enregistr� dans la
	 * base.
	 * 
	 * @return identifiant, -1 si la dataMDM n'a pas ete enregistre dans la
	 *         base.
	 * @author Gaetan
	 */
	public Object getIdentifiant() {
		return identifiant;
	}

	/**
	 * Permet de mettre a jour l'identifiant de la dataMDM
	 * 
	 * @param identifiant
	 *            : Object correspondant au nouvel identifiant de la data dans la
	 *            base.
	 * @author Gaetan
	 */
	public void setIdentifiant(Object identifiant) {
		this.identifiant = identifiant;
	}

	/**
	 * Permet de recuperer le label de la dataMDM
	 * 
	 * @return label : String label de la dataMDM
	 * @author Gaetan
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Permet de modifier le label de la dataMDM
	 * 
	 * @param label
	 *            : String le nouveau label
	 * @author Gaetan
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**Permet d'obtenir le modelMDM associe a la dataMDM
	 * @return the modelMdm
	 */
	public ModelMDM getModelMdm() {
		return modelMdm;
	}

	/**
	 * @param modelMdm : le modelMdm a associe a la dataMDM
	 */
	public void setModelMdm(ModelMDM modelMdm) {
		this.modelMdm = modelMdm;
	}

	/** Accesseur pour r�cup�rer le responsable d'une communaut�
	 * 
	 * @param com
	 * 		La communaut� de type Data pour laquelle on veut r�cup�rer le responsable
	 * @author Tom Kuhnen
	 */
	public DataMDM getResponsable() {
		DAOFactory df = new Neo4jDAOFactory();
		DataMDMDAO d = df.createDataMDMDAO();
		return d.getResponsable();
	}
	
	/** Accesseur pour r�cup�rer le tuteur d'une communaut�
	 * 
	 * @param com
	 * 		La communaut� de type Data pour laquelle on veut r�cup�rer le tuteur
	 * @author Tom Kuhnen
	 */
	public DataMDM getTuteur() {
		DAOFactory df = new Neo4jDAOFactory();
		DataMDMDAO d= df.createDataMDMDAO();
		return d.getTuteur();
	}
	
	/** M�thode utilis�e uniquement lors de l'installation 
	 *  pour modifier le responsable en dur
	 * 
	 * @param com
	 * 		La communaut� de type Data pour laquelle on veut r�cup�rer le responsable
	 * @author Gaston Guy
	 */
	public void setResponsable() {
		DAOFactory df = new Neo4jDAOFactory();
		DataMDMDAO d = df.createDataMDMDAO();
		setResponsable();
	}
	
	/** M�thode utilis�e uniquement lors de l'installation 
	 *  pour modifier le tuteur en dur
	 * 
	 * @param com
	 * 		La communaut� de type Data pour laquelle on veut r�cup�rer le tuteur
	 * @author Gaston Guy
	 */
	public void setTuteur() {
		DAOFactory df = new Neo4jDAOFactory();
		DataMDMDAO d = df.createDataMDMDAO();
		setTuteur();
	}
	
	public void setDataPere(DataMDM dataPere) {
		this.dataPere=dataPere;
	}
	
	
	/** Ajoute un utilisateur dans la communaut� nature donn�e avec le role demand�
	 * 
	 * @param leWfAnotifier
	 * le workflow qui doit etre ferm� apres l'ajout	
	 * 
	 * @param user	
	 * 		Utilisateur � ajouter
	 * @param objet
	 * 		Communaut� nature dans laquelle l'utilisateur va �tre ajout�
	 * @param role
	 * 		Role que va avoir l'utilisateur ajout�
	 * 
	 * @author Lydia
	 */
	public void addMember(DataMDM user, Role role) 
	{
		DAOFactory df = new Neo4jDataMDMDAO;
		DataMDMDAO m= df.addMember(user,role);  	
	}
	
	
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
	public void updateRole(DataMDM user, Role role)
	{
		Object id = this.getIdentifiant();
		DAOFactory df = new Neo4jDAOFactory();
		DataMDMDAO data = df.createDataMDMDAO();
		data.updateRole(id,user,role);
	}

	
}