package model;

import dao.factory.DAOFactory;
import dao.factory.Neo4jDAOFactory;
import dao.model.ModelMDMDAO;
import dao.model.NatureDAO;


public class Nature {

	/*----------Variables----------*/
	// contenant le type de la nature (Personne, Structure,...)
	String type;
	Object identifiant;  //id du noeud en base
	DataMDM tuteur;
	
	/*----------Constructeur----------*/
	/**
	 * Constructeur de la classe
	 * 
	 * @param valeur
	 *            : String contenant la valeur de la nature de la classe
	 * @author Gaetan
	 */
	public Nature(String valeur) {
		this.type = valeur;
		this.identifiant = null; // Solene => Les gars; On a pas besoin de l'instancier.
	}

	/*----------Getters and setters----------*/
	
	/**
	 * Permet de retourner le type de la nature.
	 * 
	 * @return String contenant la valeur de la variable type.
	 * @author Gaetan
	 */
	public String getType() {
		return type;
	}

	/**
	 * Permet de modifier le type de la nature
	 * 
	 * @param type
	 *            : String nouvelle valeur a donner a la variable type.
	 * @author Gaetan
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 
	 * @return the identifiant
	 * @author Gaetan
	 */
	public Object getIdentifiant() {
		return identifiant;
	}

	/**
	 * 
	 * @param identifiant the identifiant to set
	 * @author Gaetan
	 */
	public void setIdentifiant(Object identifiant) {
		this.identifiant = identifiant;
	}

	
	/** Accesseur pour r�cup�rer le responsable d'une communaut� Nature
	 * 
	 * @return le responsable de la Nature donn�e
	 * 
	 * @author Tom Kuhnen
	 */
	public DataMDM getResponsable() {
		DAOFactory df = new Neo4jDAOFactory();
		NatureDAO n= df.createNatureDAO();
		return n.getResponsable();
	}


	/** Accesseur pour r�cup�rer le tuteur d'une communaut� Nature
	 * 
	 * @return le tuteur de la Nature donn�e
	 * 
	 * @author Tom Kuhnen
	 */
	public DataMDM getTuteur() {
		DAOFactory df = new Neo4jDAOFactory();
		NatureDAO n= df.createNatureDAO();
		return n.getTuteur();
	}
	
	/** M�thode utilis�e uniquement lors de l'installation 
	 *  pour modifier le responsable en dur
	 * 
	 * @param com
	 * 		La communaut� de type Nature pour laquelle on veut r�cup�rer le responsable
	 * @author Gaston Guy
	 */
	public void setResponsable() {
		DAOFactory df = new Neo4jDAOFactory();
		NatureDAO n= df.createNatureDAO();
		setResponsable();		
	}
	
	/** M�thode utilis�e uniquement lors de l'installation 
	 *  pour modifier le tuteur en dur
	 * 
	 * @param com
	 * 		La communaut� de type Nature pour laquelle on veut r�cup�rer le tuteur
	 * @author Gaston Guy
	 */
	public void setTuteur() {
		DAOFactory df = new Neo4jDAOFactory();
		NatureDAO n= df.createNatureDAO();
		setTuteur();
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
		Object id= this.getIdentifiant();
		DAOFactory df = new Neo4jDAOFactory();
		NatureDAO n= df.createNatureDAO();
		n.addMember(id, user,  role); 
	}
	
	/** Modifie le role de l�utilisateur dans une Communaut� Nature de la BD
	 * 
	 * @param user
	 * 		Utilisateur dont on va modifier le role
	 *
	 * @param role
	 * 		Le role que l'on va appliquer � l'utilisateur
	 */
	public void updateRole(DataMDM user, Role role)
	{
		Object id = this.getIdentifiant();  //on recupere l'id du noeud en base
		DAOFactory df = new Neo4jDAOFactory();
		NatureDAO nature = df.createNatureDAO();
		nature.updateRole(id, user, role);
	}
	
}
