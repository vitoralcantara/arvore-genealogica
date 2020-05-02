package model;

public class RelationshipWf extends Wf
{

	/****************************************************************
	 *                    Variables de la classe                      *
	 ****************************************************************/

	String typeRelation ;   
		// Si ajout d'un nouveau r�le : contient le nom du r�le.
		// Si ajout d'un rattachement : contient le type de ce rattachement "principal", "normal" et "marginal".
		// VOIR POUR CI-DESSUS, PEUT-ETRE PLUTOT CREER DEUX VARIABLES DIFFERENTES : ROLE (car la classe va exister) ET RATTACHEMENT (� cr�er �ventuellement).

	DataMDM start ;	//le demandeur associ� au workflow
	
	Role role; 	//role que l'on va r�cup�rer de la fa�ade car en interne, nous n'en avons pas besoin
				//on va donc le stocker ici pour le renvoyer plus tard si besoin
	
	DataMDM userPropose; 	//l'utilisateur qu'un membre de la communaut� pourra propos�
							//on le stocke ici pour pouvoir le r�utiliser plus tard si besoin car en interne 
							//nous n'en avons pas besoin pour l'instant
	
	
	
	/****************************************************************
	 *                 Constructeurs de la classe                    *
	 ****************************************************************/

	/**
	 * Constructeur pour l'inscription ou le changement
	 * de role d'un user dans une commaunut� portant sur un objet DataMDM
	 * @author Fanny
	 * @param identifiant
	 * 		Identifiant du noeud associ� au workflow Relationship
	 * @param demandeur
	 * 		le demandeur associ� au workflow relationship
	 * @param nomCom
	 * 		la communaut� DataMDM concern�e par ce workflow
	 * @param _role
	 * 		le role associ� au demandeur (qu'il veut ou qu'on doit modifier)
	 * @param description
	 * 		la description associ�e au workflow
	 * @param type
	 * 		le type de la demande (CREATE, DELETE ou UPDATE)
	 */
	public RelationshipWf (Object identifiant, DataMDM demandeur, DataMDM nomCom, Role _role, String description, String type)
	{
		super(identifiant,description, demandeur, nomCom, type);
		role = _role;
		userPropose = null;
		typeRelation = null;
		start = null;
	}
	
	
	/**
	 * Constructeur pour l'inscription ou le changement
	 * de role d'un user dans une communaut� portant sur un objet ModelMDM 
	 * @author Fanny
	 * @param identifiant
	 * 		identifiant du noeud associ� au workflow relationship
	 * @param demandeur
	 * 		le demandeur associ� au workflow relationship
	 * @param nomCom
	 * 		la communaut� ModelMDM concern�e par ce workflow
	 * @param _role
	 * 		le role associ� au demandeur (qu'il veut ou qu'on doit modifier)
	 * @param description
	 * 		la description associ�e au workflow
	 * @param type 
	 * 		le type de la demande (CREATE, DELETE ou UPDATE)
	 */
	public RelationshipWf (Object identifiant, DataMDM demandeur, ModelMDM nomCom, Role _role, String description, String type){
		super(identifiant, description, demandeur, nomCom, type);
		
		role = _role;
		userPropose = null;
		typeRelation = null;
		start = null;
	}
	
	/**
	 * Constructeur pour l'inscription ou le changement 
	 * de role d'un user dans une communaut� portant sur une nature
	 * @author Fanny
	 * @param identifiant
	 * 		identifiant du noeud associ� au workflow relationship
	 * @param demandeur
	 * 		le demandeur associ� au workflow relationship
	 * @param nomCom
	 * 		la communaut� portant sur une nature concern�e par ce workflow
	 * @param _role
	 * 		le role associ� au demandeur (qu'il veut ou qu'on doit modifier)
	 * @param description
	 * 		la description associ�e au workflow
	 * @param type
	 * 		le type de la demande (CREATE, DELETE ou UPDATE)
	 */
	public RelationshipWf (Object identifiant, DataMDM demandeur, Nature nomCom, Role _role, String description, String type){
		super(identifiant, description, demandeur, nomCom, type);
		
		role = _role;
		userPropose = null;
		typeRelation = null;
		start = null;
	}
	
	/**
	 * Constructeur pour la proposition d'un membre par une autre personne dans une communaut� portant sur une DataMDM
	 * pour un role donn�
	 * @author Fanny
	 * @param identifiant
	 * 		identifiant du noeud associ� au workflow relationship
	 * @param demandeur
	 * 		le demandeur associ� au workflow relationship
	 * @param user_proposed
	 * 		l'user que le demandeur aura propos�
	 * @param nomCom
	 * 		la communaut� DataMDM concern�e par ce workflow
	 * @param _role
	 * 		le role que l'on veut donner � l'user_proposed dans la communaut�
	 * @param description
	 * 		la description associ�e au workflow
	 * @param type
	 * 		le type de la demande (CREATE, DELETE ou UPDATE)
	 */
	public RelationshipWf (Object identifiant, DataMDM demandeur, DataMDM user_proposed, DataMDM nomCom, Role _role, String description, String type){
		super(identifiant, description, demandeur, nomCom, type);
		
		role = _role;
		userPropose = user_proposed;
		typeRelation = null;
		start = null;
	}
	
	
	/**
	 * Constructeur pour la proposition d'un membre par une autre personne dans une communaut� portant sur une ModelMDM
	 * pour un role donn�
	 * @author Fanny
	 * @param identifiant
	 * 		identifiant du noeud associ� au workflow relationship
	 * @param demandeur
	 * 		le demandeur associ� au workflow relationship
	 * @param user_proposed
	 * 		l'user que le demandeur aura propos�
	 * @param nomCom
	 * 		la communaut� ModelMDM concern�e par ce workflow
	 * @param _role
	 * 		le role que l'on veut donner � l'user_proposed dans la communaut�
	 * @param description
	 * 		la description associ�e au workflow
	 * @param type
	 * 		le type de la demande (CREATE, DELETE ou UPDATE)
	 */
	public RelationshipWf (Object identifiant, DataMDM demandeur, DataMDM user_proposed, ModelMDM nomCom, Role _role, String description, String type){
		super(identifiant, description, demandeur, nomCom, type);
		
		role = _role;
		userPropose = user_proposed;
		typeRelation = null;
		start = null;
	}
	
	
	/**
	 * Constructeur pour la proposition d'un membre par une autre personne dans une communaut� portant sur une Nature
	 * pour un role donn�
	 * @author Fanny
	 * @param identifiant
	 * 		identifiant du noeud associ� au workflow relationship
	 * @param demandeur
	 * 		le demandeur associ� au workflow relationship
	 * @param user_proposed
	 * 		l'user que le demandeur aura propos�
	 * @param nomCom
	 * 		la communaut� portant sur une Nature concern�e par ce workflow
	 * @param role
	 * 		le role que l'on veut donner � l'user_proposed dans la communaut�
	 * @param description
	 * 		la description associ�e au workflow
	 * @param type
	 * 		le type de la demande (CREATE, DELETE ou UPDATE)
	 */
	public RelationshipWf (Object identifiant, DataMDM demandeur, DataMDM user_proposed, Nature nomCom, Role _role, String description, String type){
		super(identifiant,description, demandeur, nomCom, type);
		
		role = _role;
		userPropose = user_proposed;
		typeRelation = null;
		start = null;
	}
	
	/**
	 * Constructeur pour la d�sinscription d'un membre dans une communaut�
	 * portant sur un DataMDM
	 * @author Fanny
	 * @param identifiant
	 * 		identifiant du noeud associ� au workflow relationship
	 * @param demandeur
	 * 		le demandeur associ� au workflow relationship (qui veut ici se d�sincrire)
	 * @param nomCom
	 * 		la communaut� DataMDM dont le demandeur veut se d�sinscrire
	 * @param description
	 * 		la description associ�e au workflow
	 * @param type
	 * 		le type de la demande (CREATE, DELETE ou UPDATE)
	 */
	public RelationshipWf(Object identifiant, DataMDM demandeur, DataMDM nomCom, String description, String type){
		super(identifiant, description, demandeur, nomCom, type);
		
		role = null;
		userPropose = null;
		typeRelation = null;
		start = null;
	
	}
	
	
	/**
	 * Constructeur pour la d�sinscription d'un membre dans une communaut� 
	 * portant sur un ModelMDM
	 * @author Fanny
	 * @param identifiant
	 * 		identifiant du noeud associ� au workflow relationship
	 * @param demandeur
	 * 		le demandeur associ� au workflow relationship (qui veut ici se d�sinscrire)
	 * @param nomCom
	 * 		la communaut� ModelMDM dont le demandeur veut se d�sinscrire
	 * @param description
	 * 		la description associ�e au workflow
	 * @param type
	 * 		le type de la demande (CREATE, DELETE ou UPDATE)
	 */
	public RelationshipWf(Object identifiant, DataMDM demandeur, ModelMDM nomCom, String description, String type){
		super(identifiant, description, demandeur, nomCom, type);
		
		role = null;
		userPropose = null;
		typeRelation = null;
		start = null;
	
	}
	
	/**
	 * Constructeur pour la d�sinscription d'un membre dans une communaut�
	 * portant sur une Nature
	 * @param identifiant
	 * 		identifiant du noeud associ� au workflow relationship
	 * @param demandeur
	 * 		le demandeur associ� au workflow relationship (qui veut ici se d�sinscrire)
	 * @param nomCom
	 * 		la communaut� portant sur une Nature dont le demandeur veut se d�sinscrire
	 * @param description
	 * 		la description associ�e au workflow
	 * @param type
	 * 		le type de la demande (CREATE, DELETE ou UPDATE)
	 */
	public RelationshipWf(Object identifiant, DataMDM demandeur, Nature nomCom, String description, String type){
		super(identifiant, description, demandeur, nomCom, type);
		
		role = null;
		userPropose = null;
		typeRelation = null;
		start = null;
	
	}
	
	
	/****************************************************************
	 *              Getters/Setters de la classe                      *
	 ****************************************************************/

	
	/**
	 * Permet d'obtenir le type relation li� au workflow
	 * @author Fanny
	 * @return typeRelation
	 * 		Le typeRelation du workflow
	 */
	public String getTypeRelation() {
		return typeRelation;
	}
	
	/**
	 * Permet d'entrer un nouveau type relation
	 * @author Fanny
	 * @param _typeRelation
	 * 		Le nouveau type relation
	 */
	public void setTypeRelation(String _typeRelation){
		typeRelation = _typeRelation;
	}
	
	/**
	 * Permet d'obtenir le role que le demandeur veut, que l'userPropose voudrait dans la communaut�
	 * @author Fanny
	 * @return Role
	 * 		Le role de l'userPropose ou du demandeur
	 */
	public Role getRole(){
		return this.role;
	}
	
	/**
	 * Permet d'entrer un nouveau role pour le workflow
	 * @author Fanny
	 * @param _role
	 * 		Le nouveau role voulu (pour changer celui que voulait l'userPropose ou le demandeur)
	 */
	public void setRole(Role _role){
		role = _role;
	}
	
	/**
	 * Permet d'obtenir le demandeur associ� au workflow
	 * @author Fanny
	 * @return start
	 * 		Le demandeur associ� au workflow
	 */
	public DataMDM getStart(){
		return this.start;
	}
	
	/**
	 * Permet d'entrer un nouveau demandeur pour le workflow
	 * @author Fanny
	 * @param _start
	 * 		Le nouveau demandeur associ� au workflow
	 */
	public void setStart(DataMDM _start){
		this.start = _start;
	}
	
	/**
	 * Permet d'obtenir l'userPropose pour le workflow
	 * @author Fanny
	 * @return userPropose
	 * 		L'user propos� par le demandeur pour le workflow
	 */
	public DataMDM getUserPropose(){
		return this.userPropose;
	}
	
	/**
	 * Permet d'entrer un nouveau userPorpose pour le workflow
	 * @author Fanny
	 * @param _userPropose
	 * 		Le nouvel userPropose pour le workflow
	 */
	public void setUserPropose(DataMDM _userPropose){
		userPropose = _userPropose;
	}
	
}