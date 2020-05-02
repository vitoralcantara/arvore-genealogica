package facade;

import model.*;
import java.util.*;

/**
 * 
 * @author Groupe Gestion de communaut� : Lydia RAHMANI, Aur�lie ARNOLIN, Gaston GUY, Vincent PHULPIN, Tom KUHNEN, Jonathan RINALDI
 *
 */
public class GCFacade {

	/** 
	 * Cette m�thode permet de demander l�inscription d�un nouvel utilisateur dans une communaut� data ou model
	 *
	 *@param idCom
	 *	L'identifiant de la communaut�
	 *
	 *@param comName
	 *	Le nom de la communaut�
	 *
	 *@param typeCom
	 *	Le type de la communaut�
	 *
	 *@param roleName
	 *	Role auquel on veut souscrire dans la communaut�
	 *
	 *@param description
	 *	description de la demande
	 *
	 *@author Vincent & Jonathan R.
	 */
	public static void subscribeCommunity(Object idComm, String comName, String typeCom, String roleName, String description) {
		
		Role role = new Role(roleName);
		DataMDM user = SecuriteFacade.getDataMDMAssociateToUser();
		
		if (typeCom.equals("ModelMDM")){
			ModelMDM model = CRUDFacade.loadModelMDM(idComm);
			WfFacade.requestApplicationCommunity(user, model, role, description);
		}
		
		if (typeCom.equals("DataMDM")){
			DataMDM data = CRUDFacade.loadDataMDM(idComm);
			WfFacade.requestApplicationCommunity(user, data, role, description);
		}
		
		if (typeCom.equals("Nature")){
			Nature nature = CRUDFacade.loadNature(idComm);
			WfFacade.requestApplicationCommunity(user, nature, role, description);
		}
	}

	/** demande d�ajout d�un utilisateur, autre que l�utilisateur de la session, dans une communaut� data ou model
	  * @param leWfAnotifier
	 * le workflow qui doit etre ferm� apres l'ajout	
	 * 
	 * @param idCOm
	 * 		Identifiant de la communaut�
	 * @param idUser
	 * 		Identifiant de l'utilisateur � ajouter
	 * @param comName
	 * 		Nom de la communaut� dans laquelle on veut ajouter l'utilisateur
	 * @param typeCom
	 * 		Le type de la communaut�
	 * @param roleName
	 * 		Le role de l'utilisateur que l'on veut ajouter
	 *@param description
	 *		description de la demande
	 *
	 * @author Vincent & Jonathan R.
	 */
	public static void proposeMember (Object idComm, Object idUser, String comName, String typeCom, String roleName, String description) {
		
		Role role = new Role(roleName);
		DataMDM user = CRUDFacade.loadDataMDM(idUser);
		DataMDM demandeur = SecuriteFacade.getDataMDMAssociateToUser();
		
		if (typeCom.equals("ModelMDM")){
			ModelMDM model = CRUDFacade.loadModelMDM(idComm);
			WfFacade.requestProposeMember(demandeur, user, model, role, description);
		}
		
		if (typeCom.equals("DataMDM")){
			DataMDM data = CRUDFacade.loadDataMDM(idComm);
			WfFacade.requestProposeMember(demandeur, user, data, role, description);
		}
		
		if (typeCom.equals("Nature")){
			Nature nature = CRUDFacade.loadNature(idComm);
			WfFacade.requestProposeMember(demandeur, user, nature, role, description);
		}

	}

	/** Ajoute un utilisateur dans la communaut� model donn�e avec le role demand�
	 * 
	 * @param leWfAnotifier
	 * 		le workflow qui doit etre ferm� apres l'ajout	
	 * @param user	
	 * 		Utilisateur � ajouter
	 * @param com
	 * 		Communaut� Model dans laquelle l'utilisateur va �tre ajout�
	 * @param role
	 * 		Role que va avoir l'utilisateur ajout�
	 * 
	 * @author Lydia
	 */
	public static void addMember(Wf leWfAnotifier, DataMDM user, ModelMDM com, Role role) 
	{ 
		com.addMember( user, role);
		WfFacade.closeWf(leWfAnotifier);
	}

	/** Ajoute un utilisateur dans la communaut� data donn�e avec le role demand�
	 * 
	 * @param leWfAnotifier
	 * le workflow qui doit etre ferm� apres l'ajout	
	 * 
	 * @param user	
	 * 		Utilisateur � ajouter
	 * @param com
	 * 		Communaut� data dans laquelle l'utilisateur va �tre ajout�
	 * @param role
	 * 		Role que va avoir l'utilisateur ajout�
	 * 
	 * @author Lydia
	 */
	public static void addMember(Wf leWfAnotifier, DataMDM user, DataMDM com, Role role)  		
	{
		com.addMember( user, role);
		WfFacade.closeWf(leWfAnotifier);
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
	public static void addMember(Wf leWfAnotifier, DataMDM user, Nature obj, Role role) 
	{
		obj.addMember( user, role);
		WfFacade.closeWf(leWfAnotifier);
	}

	/** Ajoute un utilisateur dans la communaut� model donn�e mais avec le role refus�
	 * @param leWfAnotifier
	 * le workflow qui doit etre ferm� apres l'ajout	
	 *  
	 * @param user	
	 * 		Utilisateur � ajouter
	 * @param com
	 * 		Communaut� Model dans laquelle l'utilisateur est ajout�
	 * @param role
	 * 		Role qui est refus�
	 * @author Lydia
	 */		
	public static void refuseMember(Wf leWfAnotifier, DataMDM user, ModelMDM com, Role role) {
		Role refuse= new Role("refuse");
		GCFacade.addMember(leWfAnotifier, user, com, refuse);
		
	}
	
	/** Ajoute un utilisateur dans la communaut� data donn�e mais avec le role refus�
	 * @param leWfAnotifier
	 * le workflow qui doit etre ferm� apres l'ajout	
	 *  
	 * @param user	
	 * 		Utilisateur � ajouter
	 * @param com
	 * 		Communaut� data dans laquelle l'utilisateur est ajout�
	 * @param role
	 * 		Role qui est refus�
	 * @author Lydia
	 */	
	public static void refuseMember(Wf leWfAnotifier, DataMDM user, DataMDM com, Role role) {
		Role refuse= new Role("refuse");
		GCFacade.addMember(leWfAnotifier, user, com, refuse);
		
	}
	
	/** Ajoute un utilisateur dans la communaut� nature donn�e mais avec le role refus�
	 * @param leWfAnotifier
	 * le workflow qui doit etre ferm� apres l'ajout	
	 *  
	 * @param user	
	 * 		Utilisateur � ajouter
	 * @param com
	 * 		Communaut� nature dans laquelle l'utilisateur est ajout�
	 * @param role
	 * 		Role qui est refus�
	 * @author Lydia
	 */
	public static void refuseMember(Wf leWfAnotifier, DataMDM user, Nature obj, Role role) 
	{
		Role refuse= new Role("refuse");
		GCFacade.addMember(leWfAnotifier, user, obj, refuse);
		
	}

	/** Demande de modification du role de l�utilisateur dans une communaut� data ou model de la BD
	 * @param idCOm
	 * 		Identifiant de la communaut�
	 * @param comName
	 * 		Nom de la communaut� dans laquelle on veut modifier un role
	 * @param typeCom
	 * 		Type de la communaut� dans laquelle on veut modifier un role
	 * @param newRole
	 * 		Le nouveau role que l'on veut
	 *@param description
	 *	description de la demande
	 *
	 * @author Aur�lie :)
	 */	
	public static void demandUpdatingRole(Object idComm, String comName, String typeCom, Role newRole, String description) {
		DataMDM user = SecuriteFacade.getDataMDMAssociateToUser();

		if (typeCom.equals("ModelMDM"))
		{ 	ModelMDM modeleMDM = CRUDFacade.loadModelMDM(idComm);
		WfFacade.requestModificationRole(user, modeleMDM, newRole, description);
		}
		else if (typeCom.equals("DataMDM"))
		{	DataMDM dataMDM = CRUDFacade.loadDataMDM(idComm);
		WfFacade.requestModificationRole(user, dataMDM, newRole, description);
		}
		else if (typeCom.equals("Nature"))
		{	Nature nature = CRUDFacade.loadNature(idComm);
		WfFacade.requestModificationRole(user, nature, newRole, description);
		}

	}
	
	/** Modification du role de l�utilisateur dans une communaut� model de la BD
	 * @param leWfAnotifier
	 * le workflow qui doit etre ferm� apres l'ajout	
	 *  
	 * @param user	
	 * 		Utilisateur � ajouter
	 * @param com
	 * 		Communaut� nature dans laquelle l'utilisateur est ajout�
	 * @param role
	 * 		Role qui est refus�
	 *
	 * @author Aur�lie :)
	 */	
	public static void updateRole (Wf leWfAnotifier, DataMDM user, ModelMDM com, Role role) 
	{
		com.updateRole(user, role);
		WfFacade.closeWf(leWfAnotifier);
	}
		
	/** Modification du role de l�utilisateur dans une communaut� data de la BD
	 * @param leWfAnotifier
	 * le workflow qui doit etre ferm� apres l'ajout	
	 *  
	 * @param user	
	 * 		Utilisateur � ajouter
	 * @param com
	 * 		Communaut� nature dans laquelle l'utilisateur est ajout�
	 * @param role
	 * 		Role qui est refus�
	 *
	 * @author Aur�lie :)
	 */	
	public static void updateRole (Wf leWfAnotifier, DataMDM user, DataMDM com, Role role) 
	{
		com.updateRole(user,role);
		WfFacade.closeWf(leWfAnotifier);
	}
	
	/** Modification du role de l�utilisateur dans une communaut� nature de la BD
	 * @param leWfAnotifier
	 * le workflow qui doit etre ferm� apres l'ajout	
	 *  
	 * @param user	
	 * 		Utilisateur � ajouter
	 * @param obj
	 * 		Communaut� nature dans laquelle l'utilisateur est ajout�
	 * @param role
	 * 		Role qui est refus�
	 *
	 * @author Aur�lie :)
	 */	
	public static void updateRole (Wf leWfAnotifier, DataMDM user, Nature obj, Role role) 
	{
		obj.updateRole(user,role);
		WfFacade.closeWf(leWfAnotifier);
	}

	/** Affiche la liste de l�ensemble des membres d�une communaut�
	 * 
	 * @param comName
	 * 		Nom de la communaut� pour laquelle on veut afficher la liste des membres
	 * @param typeCom
	 * 		Type de la communaut�
	 * @return
	 * 		La liste des membres d'une communaut� 
	 * @author Gaston Guy
	 */
	public static List<String> displayListMember(String comName, String typeCom) {
			
		List<String> memberList = new ArrayList<String>();
		
		
		
		if (typeCom.equals("Model")) // Si de type Model
		{
			
		}
		else if (typeCom.equals("Data")) // Si de type Data
		{
			
		}
		else if (typeCom.equals("Nature")) // Si de type Nature
		{
			
		}
		
		return memberList;
	}

	/** Demande la d�sinscription de l�utilisateur dans la communaut� donn�e
	 * 
	 * @param idCOm
	 * 		Identifiant de la communaut�
	 * @param comName
	 * 		Nom de la communaut� dans laquelle on veut modifier un role
	 * @param typeCom
	 * 		Type de la communaut� dans laquelle on veut modifier un role
	 *@param description
	 *	description de la demande
	 *
	 * @author Jonathan R. & Vincent
	 */
	public static void unsubscribeCommunity(Object idComm,String comName, String typeCom, String description) 

	{
		DataMDM user = SecuriteFacade.getDataMDMAssociateToUser();
		
		if (typeCom.equals("ModelMDM")){
			ModelMDM model = CRUDFacade.loadModelMDM(idComm);
			WfFacade.requestUnsubscribe(user, model, description);
		}
		
		if (typeCom.equals("DataMDM")){
			DataMDM data = CRUDFacade.loadDataMDM(idComm);
			WfFacade.requestUnsubscribe(user, data, description);
		}
		
		if (typeCom.equals("Nature")){
			Nature nature = CRUDFacade.loadNature(idComm);
			WfFacade.requestUnsubscribe(user, nature, description);
		}
		
	}
	
	/** Supprime le noeud role de l�utilisateur reli� � la communaut� model donn�e
	 * @param NotifyWf
	 * 	le workflow qui doit etre ferm� apres la suppression	
	 * @param user
	 * 		Le Membre � supprimer
	 * @param com
	 * 		La Communaut� Model dans laquelle on supprime le membre
	 * 
	 * @author Jonathan R. & Vincent
	 */
	public static void deleteMember(Wf NotifyWF, DataMDM user, ModelMDM com) 
	{
		Role role = new Role();
		role.deleteRole(user, com);
		
	} 
		
	/** Supprime le noeud role de l�utilisateur reli� � la communaut� data donn�e
	 * @param NotifyWf
	 * 	le workflow qui doit etre ferm� apres la suppression	
	 * @param user
	 * 		Le Membre � supprimer
	 * @param com
	 * 		La Communaut� Model dans laquelle on supprime le membre
	 * 
	 * @author Jonathan R. & Vincent
	 */
	public static void deleteMember(Wf NotifyWF, DataMDM user, DataMDM com) { 
		
	}

	/** Supprime le noeud role de l�utilisateur reli� � la communaut� nature donn�e
	 * @param NotifyWf
	 * 	le workflow qui doit etre ferm� apres la suppression	
	 * @param user
	 * 		Le Membre � supprimer
	 * @param com
	 * 		La Communaut� Model dans laquelle on supprime le membre
	 * 
	 * @author Jonathan R. & Vincent
	 */
	public static void deleteMember(Wf NotifyWF, DataMDM user, Nature obj) {

	}


	// ACCESSEURS 

	/** Accesseur pour r�cup�rer le responsable d'une communaut�
	 * 
	 * @param nature
	 * 		La communaut� de type Nature pour laquelle on veut r�cup�rer le responsable
	 * 
	 * @return le responsable de la Nature donn�e
	 * 
	 * @author Tom Kuhnen
	 */
	public static DataMDM getResponsable(Nature nature) {
		// TODO Auto-generated method stub
		return nature.getResponsable();
	}
	
	/** Accesseur pour r�cup�rer le responsable d'une communaut�
	 * 
	 * @param com
	 * 		La communaut� de type Data pour laquelle on veut r�cup�rer le responsable
	 * 
	 * @return le responsable de la DataMDM donn�e
	 * 
	 * @author Tom Kuhnen
	 */
	public static DataMDM getResponsable(DataMDM com) {
		// TODO Auto-generated method stub
		return com.getResponsable();
	}
	
	/** Accesseur pour r�cup�rer le responsable d'une communaut�
	 * 
	 * @param com
	 * 		La communaut� de type Model pour laquelle on veut r�cup�rer le responsable
	 * 
	 * @return le responsable du ModelMDM donn�
	 * 
	 * @author Tom Kuhnen
	 */
	public static DataMDM getResponsable(ModelMDM com) {
		// TODO Auto-generated method stub
		return com.getResponsable();
	}

	/** Accesseur pour r�cup�rer le tuteur d'une communaut� Nature
	 * 
	 * @param nature
	 * 		La communaut� de type Nature pour laquelle on veut r�cup�rer le tuteur
	 * 
	 * @return le tuteur de la Nature donn�e
	 * 
	 * @author Tom Kuhnen
	 */
	public static DataMDM getTuteur(Nature nature) {
		// TODO Auto-generated method stub
		return nature.getTuteur();
	}
	
	/** Accesseur pour r�cup�rer le tuteur d'une communaut�
	 * 
	 * @param com
	 * 		La communaut� de type Data pour laquelle on veut r�cup�rer le tuteur
	 * 
	 * @return le tuteur de la DataMDM donn�
	 * 
	 * @author Tom Kuhnen
	 */
	public static DataMDM getTuteur(DataMDM com) {
		// TODO Auto-generated method stub
		return com.getTuteur();
	}
	
	/** Accesseur pour r�cup�rer le tuteur d'une communaut�
	 * 
	 * @param com
	 * 		La communaut� de type Model pour laquelle on veut r�cup�rer le tuteur
	 * 
	 * @return le tuteur du ModelMDM donn�
	 * 
	 * @author Tom Kuhnen
	 */
	public static DataMDM getTuteur(ModelMDM com) {
		// TODO Auto-generated method stub
		return com.getTuteur();
	}


	// MODIFICATEURS
	
	/** M�thode utilis�e uniquement lors de l'installation 
	 *  pour modifier le responsable en dur
	 * 
	 * @param nature
	 * 		La communaut� de type Nature pour laquelle on veut r�cup�rer le responsable
	 * @author Gaston Guy
	 */
	public static void setResponsable(Nature nature) {
		nature.setResponsable();
	}
	
	/** M�thode utilis�e uniquement lors de l'installation 
	 *  pour modifier le responsable en dur
	 * 
	 * @param com
	 * 		La communaut� de type Data pour laquelle on veut r�cup�rer le responsable
	 * @author Gaston Guy
	 */
	public static void setResponsable(DataMDM com) {
		com.setResponsable();
	}
	
	/** M�thode utilis�e uniquement lors de l'installation 
	 *  pour modifier le responsable en dur
	 * 
	 * @param com
	 * 		La communaut� de type Model pour laquelle on veut r�cup�rer le responsable
	 * @author Gaston Guy
	 */
	public static void setResponsable(ModelMDM com) {
		com.setResponsable();
	}

	/** M�thode utilis�e uniquement lors de l'installation 
	 *  pour modifier le tuteur en dur
	 * 
	 * @param nature
	 * 		La communaut� de type Nature pour laquelle on veut r�cup�rer le tuteur
	 * @author Gaston Guy
	 */
	public static void setTuteur(Nature nature) {
		nature.setTuteur();
	}
	
	/** M�thode utilis�e uniquement lors de l'installation 
	 *  pour modifier le tuteur en dur
	 * 
	 * @param com
	 * 		La communaut� de type Data pour laquelle on veut r�cup�rer le tuteur
	 * @author Gaston Guy
	 */
	public static void setTuteur(DataMDM com) {
		com.setTuteur();
	}
	
	/** M�thode utilis�e uniquement lors de l'installation 
	 *  pour modifier le tuteur en dur
	 * 
	 * @param nature
	 * 		La communaut� de type Model pour laquelle on veut r�cup�rer le tuteur
	 * @author Gaston Guy
	 */
	public static void setTuteur(ModelMDM com) {
		com.setTuteur();
	}
}
