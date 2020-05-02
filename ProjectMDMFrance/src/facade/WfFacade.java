package facade;

import model.*;

import java.util.*;

import dao.factory.DAOFactory;
import dao.factory.Neo4jDAOFactory;
import dao.model.WFDataDAO;
import dao.neo4j.Neo4jWFDataDAO;

public class WfFacade {

	
	/****************************************************************
	 *                    M�thodes de la classe                      *
	 ****************************************************************/

	/**
	 * Requ�te capitale correspondant � la demande d'ajout d'une brique SI au sein du syst�me.
	 * Une fois que cette m�thode aura �t� appel�e, le Workflow sera lanc�. Suivant l'issue de celui-ci, la brique pourra soit �tre ajout�e, soit ne pas l'�tre (si ajout refus�).
	 * @author Bastien F. et Thomas
	 * @param laNature
	 *            Correspond � l'objet Nature BriqueSI (qui va nous servir pour
	 *            retrouver les tuteur et responsable de cette communaut�.
	 * @param demandeur
	 *            Correspond au demandeur de la cr�ation du Workflow.
	 * @param description
	 *            Correspond � la description du Workflow.
	 * @param connecteur
	 *            Correspond aux informations de connecteur de la brique SI.
	 * @param schema
	 *            Correspond � une liste des ModelValue que l'on veut r�cup�rer
	 *            dans cette brique SI. Concr�tement, cela peut �tre par exemple
	 *            des noms de colonnes d'une base de donn�es.
	 */
	// Demande la cr�ation d'une nouvelle brique SI
	public static void requestCreationBrique(Nature laNature, DataMDM demandeur, String description, Connector connecteur, Map<ModelMDM, List<ModelValue>> schema) 
	{
		// 1ere �tape : Cr�ation du Workflow
		// Utilisation du polymorphisme
		Wf newWf = new BriqueSIWf(null, laNature, demandeur, description, connecteur, schema, "CREATE");

		// IL FAUDRA ICI ENREGISTRER CE WORKFLOW AVEC LE DAO.
	}
	

	/**
	 * Demande la cr�ation d'un mod�le value en renseignant le type d'attribut,
	 * le ModelMDM sur lequel on ajoute l'attribut, la valeur, et la valeur par
	 * d�faut
	 * 
	 * @author Solene, Fanny, Bastien F. et Thomas
	 * @param nomObjet
	 * @param typeAttribut
	 * @param demandeur
	 * @param modeleAssocie
	 * @param modelValue
	 * @param leSocle
	 * @param description
	 */
	 public static void requestCreateModelValue(String nomObjet, String typeAttribut, DataMDM demandeur, ModelMDM modeleAssocie, ModelValue modelValue, Socle leSocle, String description)
	 { 
		// 1ere �tape : Cr�ation du Workflow
		// Utilisation du polymorphisme
		
		 Wf newWf = new ModelData(null, nomObjet, typeAttribut, demandeur, modeleAssocie, modelValue, leSocle, description, "CREATE");
		// IL FAUDRA ICI ENREGISTRER CE WORKFLOW AVEC LE DAO.
	 }
	 

	/**
	 * Demande la cr�ation d'un dataValue en renseignant la valeur, la DataMDM
	 * sur laquelle on ajoute la valeur, et le ModelValue qu'on remplit par
	 * cette valeur
	 * 
	 * @author Solene, Fanny, Bastien F. et Thomas
	 * @param nomObjet
	 * @param dataMDMAssocie
	 * @param demandeur
	 * @param valeur
	 * @param description
	 */
	public static void requestCreateDataValue(String nomObjet, DataMDM dataMDMAssocie, DataMDM demandeur, String valeur, String description) 
	 { 
		// 1ere �tape : Cr�ation du Workflow
		// Utilisation du polymorphisme
		Wf newWf = new ModelData(null, nomObjet, dataMDMAssocie, demandeur, valeur, description, "CREATE");
		
		// IL FAUDRA ICI ENREGISTRER CE WORKFLOW AVEC LE DAO.
	 }

	
	/**
	 * M�thode permettant de valider un Workflow. Suivant s'il s'agit de la premi�re ou la deuxi�me validation, et suivant les avis pris par les validateurs, celle-ci pourra soit �tre mise "en attente d'un second avis", soit valid�e (2 avis OK), soit refus�e (2 avis NOK), soit transform�e en demande d'arbitrage pour ce Workflow (les deux avis sont diff�rents).
	 * @author Bastien F. & Thomas
	 * @param leWf
	 * 			Correspond au Workflow que l'on veut valider.
	 * @param decision 
	 * 			Correspond � la d�cision qui a �t� prise par l'utilisateur validateur. Cette validation peut prendre les valeurs "Accepte" ou "Refuse".
	 * @param personne 
	 * 			Correspond � la personne qui a fait la validation.
	 */
	public static void validateWorkflow (Wf leWf, Boolean decision, DataMDM personne)
	{
		// Premi�rement, nous recherchons les deux instances de Validation qui nous int�ressent.
		// N'oublions pas que l'attribut etat de cette classe peut prendre les valeurs : "Accepte", "En attente" ou "Refuse".
		
		//UNE FONCTION DE KIKI ET WENSHENG DEVRA FAIRE CELA ! A PARTIR DU WF CELA SUFFIT NORMALEMENT.
		Validation validateTuteur = leWf.getValidateTuteur(); 
		Validation validateResponsable = leWf.getValidateResponsable(); 
		LinkWf etatGeneralDuWf = leWf.getLinkWf();
		
		// Nous recherchons l'instance de Validation correspondant � la personne venant de valider le Workflow.
		if (validateTuteur.getValidator() == personne)		
		{
			// Si l'autre validation a d�j� �t� effectu�e, et qu'elle est positive, il va falloir r�agir en fonction.
			if (validateResponsable.getState().equals("Accepte"))	
			{
				// Il va ici falloir valider d�finitivement le Workflow car les deux d�cisions sont positives, et appeler la m�thode effectuant les modifications ad�quates.
				if (decision == true)
				{
					// Validation de l'�tat g�n�ral du Workflow.
					etatGeneralDuWf.setState("Accepte") ;
					// save()
					
					// Validation de l'�tat de l'instance de validation de la personne venant de faire une validation
					validateTuteur.setState("Accepte") ;
					// save()
					
					// Appel de la fonction cl� qui va effectuer la modification dans le MDM.
					// Cette fonction est plac�e dans le Workflow, elle est abstraite dans la super classe et impl�ment�e dans les sous-classes. L'exploitation du polymorphisme permettra d'appeler ici de mani�re g�n�rique la bonne m�thode associ�e � la bonne sous-classe du Workflow.
					leWf.executer() ;
					
					// Eventuellement ici une notification ?
					//ICI LA FONCTION ;
				}
				else
				// Ici il va y avoir une contradiction entre les avis des deux validateurs.
				{
					// On passe l'�tat g�n�ral du Workflow en "arbitrage".
					etatGeneralDuWf.setState("Arbitrage") ;
					// save()
					
					// Invalidation de l'�tat de l'instance de validation de la personne venant de la faire.
					validateTuteur.setState("Refuse") ;
					// save()
					
					// Cr�ation d'une instance d'arbitrage.
					Arbitrage arbitration = new Arbitrage ("En attente",validateTuteur.getValidator(),leWf) ; //Initialement getValidateurs mais il me semble que Lydia a mis getValidator
					leWf.setTutorArbitration(arbitration) ;
					// save()	// ATTENTION, ON SAUVE SEULEMENT LE WF ? VU QUE L'INSTANCE D'ARBITRAGE SERA ENTRE A L'INTERIEUR ? OU ON SAUVA AUSSI L'INSTANCE D'ARBITRAGE A PART ?
					
					// Eventuellement ici une notification ?
					//ICI LA FONCTION ;
				}
			}
			else
			{
				// Si l'autre validation a d�j� �t� effectu�e et est n�gative.
				if (validateResponsable.getState().equals("Refuse")) 
				{
					// Ici il va y avoir une contradiction entre les avis des deux validateurs.
					if (decision == true)
					{
						// On passe l'�tat g�n�ral du Workflow en "arbitrage".
						etatGeneralDuWf.setState("Arbitrage") ;
						
						// Invalidation de l'�tat de l'instance de validation de la personne venant de la faire.
						validateTuteur.setState("Accepte") ;
						// save()
						
						// Cr�ation d'une instance d'arbitrage.
						Arbitrage arbitration = new Arbitrage ("En attente",validateTuteur.getValidator(),leWf) ;
						leWf.setTutorArbitration(arbitration) ;
						// save()	// ATTENTION, ON SAUVE SEULEMENT LE WF ? VU QUE L'INSTANCE D'ARBITRAGE SERA ENTRE A L'INTERIEUR ? OU ON SAUVA AUSSI L'INSTANCE D'ARBITRAGE A PART ?
						
						// Eventuellement ici une notification ?
						//ICI LA FONCTION ;
					}
					else
					// Il va ici falloir valider d�finitivement le Workflow car les deux d�cisions sont positives, et appeler la m�thode effectuant les modifications ad�quates.
					{
						// Validation de l'�tat g�n�ral du Workflow.
						etatGeneralDuWf.setState("Refuse") ;
						// save()
						
						// Validation de l'�tat de l'instance de validation de la personne venant de faire une validation
						validateTuteur.setState("Refuse") ;
						// save()
						
						// Eventuellement ici une notification ?
						//ICI LA FONCTION ;
					}
				}
				else
				// Dans cet ultime cas, nous sommes en pr�sence de la premi�re validation. Il n'y aura alors que l'objet Validation � modifier (et � entrer en base de donn�es).
				{
					// Il suffit dans ce cas de valider l'instance de Validation adapt�e.
					if (decision == true)
					{
						validateTuteur.setState("Accepte") ;
					}
					else
					{
						validateTuteur.setState("Refuse") ;
					}
					// save()
				}
			}
		}
		else
		// Dans ce cas, c'est l'autre instance de Validation qui est concern�e. Nous allons faire les m�mes actions, mais dans l'ordre invers�.
		{
			if (validateTuteur.getState().equals("Accepte"))	
			{
				// Il va ici falloir valider d�finitivement le Workflow car les deux d�cisions sont positives, et appeler la m�thode effectuant les modifications ad�quates.
				if (decision.equals("Accepte"))
				{
					// Validation de l'�tat g�n�ral du Workflow.
					etatGeneralDuWf.setState("Accepte") ;
					// save()
					
					// Validation de l'�tat de l'instance de validation de la personne venant de faire une validation
					validateResponsable.setState("Accepte") ;
					// save()
					
					// Appel de la fonction cl� qui va effectuer la modification dans le MDM.
					leWf.executer() ;
					
					// Eventuellement ici une notification ?
					//ICI LA FONCTION ;
				}
				else
				// Ici il va y avoir une contradiction entre les avis des deux validateurs.
				{
					// On passe l'�tat g�n�ral du Workflow en "arbitrage".
					etatGeneralDuWf.setState("Arbitrage") ;
					// save()
					
					// Invalidation de l'�tat de l'instance de validation de la personne venant de la faire.
					validateResponsable.setState("Refuse") ;
					// save()
					
					// Cr�ation d'une instance d'arbitrage.
					Arbitrage arbitration = new Arbitrage ("En attente",validateTuteur.getValidator(),leWf) ;
					leWf.setTutorArbitration(arbitration) ;
					// save()	// ATTENTION, ON SAUVE SEULEMENT LE WF ? VU QUE L'INSTANCE D'ARBITRAGE SERA ENTRE A L'INTERIEUR ? OU ON SAUVA AUSSI L'INSTANCE D'ARBITRAGE A PART ?
					
					// Eventuellement ici une notification ?
					//ICI LA FONCTION ;
				}
			}
			else
			{
				// Si l'autre validation a d�j� �t� effectu�e et est n�gative.
				if (validateTuteur.getState().equals("Refuse")) 
				{
					// Il va ici falloir valider d�finitivement le Workflow car les deux d�cisions sont positives, et appeler la m�thode effectuant les modifications ad�quates.
					if (decision.equals("Accepte"))
					{
						// On passe l'�tat g�n�ral du Workflow en "arbitrage".
						etatGeneralDuWf.setState("Arbitrage") ;
						// save()
						
						// Invalidation de l'�tat de l'instance de validation de la personne venant de la faire.
						validateResponsable.setState("Accepte") ;
						// save()
						
						// Cr�ation d'une instance d'arbitrage.
						Arbitrage arbitration = new Arbitrage ("En attente",validateTuteur.getValidator(),leWf) ;
						leWf.setTutorArbitration(arbitration) ;
						// save()	// ATTENTION, ON SAUVE SEULEMENT LE WF ? VU QUE L'INSTANCE D'ARBITRAGE SERA ENTRE A L'INTERIEUR ? OU ON SAUVA AUSSI L'INSTANCE D'ARBITRAGE A PART ?						
						
						// Eventuellement ici une notification ?
						//ICI LA FONCTION ;
					}
					else
					// Ici il va y avoir une contradiction entre les avis des deux validateurs.
					{
						// Validation de l'�tat g�n�ral du Workflow.
						etatGeneralDuWf.setState("Refuse") ;
						// save()
						
						// Validation de l'�tat de l'instance de validation de la personne venant de faire une validation
						validateResponsable.setState("Refuse") ;
						// save()
						
						// Eventuellement ici une notification ?
						//ICI LA FONCTION ;
					}
				}
				else
				// Dans cet ultime cas, nous sommes en pr�sence de la premi�re validation. Il n'y aura alors que l'objet Validation � modifier (et � entrer en base de donn�es).
				{
					if (decision == true)
					{
						validateResponsable.setState("Accepte") ;
					}
					else
					{
						validateResponsable.setState("Refuse") ;
					}
					// save()
				}
			}
		}
	}
	
	/**
	 * M�thode permettant d'arbitrer un Workflow.
	 * L'arbitrage survient apr�s deux validations diff�rente
	 * C'est au tuteur de la communaut� de trancher
	 * @author Bastien F. & Thomas
	 * @param leWf
	 * 			Correspond au Workflow que l'on veut valider.
	 * @param decision 
	 * 			Correspond � la d�cision qui a �t� prise par l'utilisateur arbitre.
	 * @param arbitre 
	 * 			Correspond au tuteur qui fait l'arbitrage.
	 */
	public static void ArbitrateWorkflow(Wf leWf, boolean decision, DataMDM arbitre)
	{
		Arbitrage arbitration = leWf.getTutorArbitration();
		LinkWf etatGeneralDuWorkflow = leWf.getLinkWf();
		//si on 
		if(decision == true)
		{
			arbitration.setState("Accepte");
			//save
			
			//le workflow est indiqu� comme accept�
			etatGeneralDuWorkflow.setState("Accepte");
			
			//on ex�cute la demande
			leWf.executer();
		}
		else
		{
			//le worflow est indiqu� comme refus�.
			etatGeneralDuWorkflow.setState("Refuse");
		}
	}
	
	
	//Cette partie l� est � voir si on garde selon les fonctions cod�es en tout premier par Bastien et Thomas
	
	/**
	 * Demande la cr�ation d'un ModelMDM fils d'un ModelMDM p�re, avec un label
	 * et une description
	 * 
	 * @author Solene et Fanny
	 * @param modeleMDMPere
	 * @param label
	 * @param description
	 */
	// Il nous faut le demandeur
	public static void requestCreateModelMDM(ModelMDM modeleMDMPere, String label, String description) {
		
	}

	/**
	 * Demande la cr�ation d'un ModelMDM de nature indiqu�e, avec le label et la
	 * description
	 * 
	 * @author Solene et Fanny
	 * @param nature
	 * @param label
	 * @param description
	 */
	// Il nous faut le demandeur
	/*
	 * public void requestCreateModelMDM(Nature nature, String label, String
	 * description){ }
	 */
	/**
	 * Demande la cr�ation d'un DataMDM instance d'un ModelMDM fils d'un autre
	 * ModelMDM, et il indique donc le dataMDMPere associ�
	 * 
	 * @author Solene et Fanny
	 * @param modelAssocie
	 * @param dataMDMPere
	 * @param description
	 */
	// Il nous faut le demandeur
	public static void requestCreateDataMDM(ModelMDM modelAssocie, DataMDM dataMDMPere, String description) {

	}

	/**
	 * Demande la cr�ation d'un DataMDM
	 * 
	 * @author Solene et Fanny
	 * @param modelAssocie
	 */
	public static void requestCreateDataMDM(ModelMDM modelAssocie) {

	}
	
	
	//Fin de la partie � consid�rer � garder 
	
	
	/**
	 * Demande d'une inscription d'un demandeur au sein d'une communaut� DataMDM nomCom pour un role indiqu�
	 * RelationshipWf concernera les propositions d��criture relatives aux relations entre objets (r�le et niveau de rattachement). 
	 * Elle contiendra les propri�t�s Start et End correspondant au sens du niveau de rattachement (�voqu� � plusieurs reprises plus haut dans le rapport) et relationName 
	 * correspondant au nom de la relation (dans le cas d�un ajout de r�le : le nom du r�le, dans le cas d�un ajout de rattachement : principal, normal ou marginal)
	 * @author Fanny
	 * @param demandeur
	 * 		Le demandeur � l'origine du Workflow
	 * @param nomCom
	 * 		La Communaut� DataMDM concern�e par ce workflow
	 * @param role
	 * 		Le role demand� par le demandeur dans la communit� nomCom
	 * @param description
	 * 		La description associ�e � ce workflow
	 */
	// Il nous faut le demandeur
	public static void requestApplicationCommunity(DataMDM demandeur, DataMDM nomCom, Role role, String description) 
	{
		// Cr�ation du nouveau Workflow
		Wf newWf = new RelationshipWf (null,demandeur, nomCom, role, description, "CREATE");
		
		// Sauvegarde de ce nouveau Workflow dans la base
		//newWf.save() ;
	}
	
	/**
	 * Demande d'une inscription d'un demandeur au sein d'une communaut� ModelMDM nomCom pour un role indiqu�
	 * @author Fanny
	 * @param demandeur
	 * 		Le demandeur � l'origine du workflow
	 * @param nomCom
	 * 		La communaut� ModelMDM concern�e par ce workflow
	 * @param role
	 * 		Le role demand� par le demandeur dans la communaut� nomCom
	 * @param description
	 * 		La description associ�e � ce workflow
	 */
	// Il nous faut le demandeur
	public static void requestApplicationCommunity(DataMDM demandeur, ModelMDM nomCom, Role role, String description) 
	{
		// Cr�ation du nouveau Workflow
		Wf newWf = new RelationshipWf(null, demandeur, nomCom, role, description, "CREATE");
		
		// Sauvegarde de ce nouveau Workflow dans la base
		//newWf.save() ;
	}
	
	/**
	 * Demande d'une inscription d'un user au sein d'une communaut� sur une Nature nomCom pour un role indiqu�
	 * @author Fanny
	 * @param demandeur
	 * 		Le demandeur � l'origine du workflow
	 * @param nomCom
	 * 		La communaut� portant sur une nature concern�e par ce workflow
	 * @param role
	 * 		Le role demand� par le demandeur dans la communaut� nomCom
	 * @param description
	 * 		La description associ�e � ce workflow
	 */
	// Il nous faut le demandeur
	public static void requestApplicationCommunity(DataMDM demandeur, Nature nomCom, Role role, String description) 
	{
		// Cr�ation du nouveau Workflow
		Wf newWf = new RelationshipWf(null, demandeur, nomCom, role, description, "CREATE");
		
		// Sauvegarde de ce nouveau Workflow dans la base
		//newWf.save() ;
	}

	/**
	 * un utilisateur fait la demande pour int�grer un autre utilisateur au sein
	 * d'une communaut� portant sur un DataMDM pour un r�le donn�
	 * @author Fanny
	 * @param demandeur
	 * 		Le demandeur � l'origine du workflow
	 * @param user_proposed
	 * 		L'utilisateur que le demandeur veut int�grer au sein de la communaut�
	 * @param nomCom
	 * 		La communaut� DataMDM dans laquelle le user_proposed serait int�gr�
	 * @param role
	 * 		Le role avec lequel le user_proposed serait int�gr� dans la communaut�
	 * @param description
	 * 		La description associ�e � ce workflow
	 */
	public static void requestProposeMember(DataMDM demandeur, DataMDM user_proposed, DataMDM nomCom, Role role, String description) 
	{
		// demande d�ajout d�un autre utilisateur dans une communaut� data ou model
		Wf newWf = new RelationshipWf(null,demandeur, user_proposed, nomCom, role, description, "CREATE");
		
		// Sauvegarde de ce nouveau Workflow dans la base
		//newWf.save() ;
	}
	
	/**
	 * Un utilisateur fait la demande pour int�grer un autre utilisateur au sein d'une
	 * communaut� portant sur un ModelMDM pour un r�le donn�
	 * @author Fanny
	 * @param demandeur
	 * 		Le demandeur � l'origine du workflow
	 * @param user_proposed
	 * 		L'utilisateur que le demandeur veut int�grer au sein de la communaut�
	 * @param nomCom
	 * 		La communaut� ModelMDM dans laquelle le user_proposed serait int�gr�
	 * @param role
	 * 		Le role avec lequel le user_proposed serait int�gr� dans la communaut�
	 * @param description
	 * 		La description associ�e � ce workflow
	 */
	public static void requestProposeMember(DataMDM demandeur, DataMDM user_proposed, ModelMDM nomCom, Role role, String description) {
		// demande d�ajout d�un autre utilisateur dans une communaut� data ou model
		Wf newWf = new RelationshipWf(null,demandeur, user_proposed, nomCom, role, description, "CREATE");
		
		// Sauvegarde de ce nouveau Workflow dans la base
		//newWf.save() ;
	}

	/**
	 * Un utilisateur fait la demande pour int�grer un autre utilisateur au sein d'une
	 * communaut� portant sur une Nature pour un r�le donn�
	 * @author Fanny
	 * @param demandeur
	 * 		Le demandeur � l'origine du workflow
	 * @param user_proposed
	 * 		L'utilisateur que le demandeur veut int�grer au sein de la communaut�
	 * @param nomCom
	 * 		La communaut� portant sur une Nature dans laquelle le user_proposed serait int�gr�
	 * @param role
	 * 		Le role avec lequel le user_proposed serait int�gr� dans la communaut�
	 * @param description
	 * 		La description associ�e � ce workflow
	 */
	public static void requestProposeMember(DataMDM demandeur, DataMDM user_proposed, Nature nomCom, Role role, String description) {
		// demande d�ajout d�un autre utilisateur dans une communaut� data ou model
		Wf newWf = new RelationshipWf(null,demandeur, user_proposed, nomCom, role, description, "CREATE");
		
		// Sauvegarde de ce nouveau Workflow dans la base
		//newWf.save() ;
	}
	
	
	/*
	 * public void requestAjouterMembre (DataMDM user, ModelMDM com, Role role,
	 * String description){ //ajoute un utilisateur dans la communaut� avec le
	 * role demand� //� mettre dans DataMDM et ModelMDM }
	 * 
	 * public void requestAjouterMembre (DataMDM user, DataMDM com, Role role,
	 * String description){ //ajoute un utilisateur dans la communaut� avec le
	 * role demand� //� mettre dans DataMDM et ModelMDM }
	 * 
	 * public void requestRefuserMembre(DataMDM user, ModelMDM com, Role role,
	 * String description){ //ajoute un utilisateur dans la communaut� avec le
	 * role refus� //� mettre dans DataMDM et ModelMDM }
	 * 
	 * 
	 * public void requestRefuserMembre(DataMDM user, DataMDM com, Role role,
	 * String description){ //ajoute un utilisateur dans la communaut� avec le
	 * role refus� //� mettre dans DataMDM et ModelMDM }
	 */
	
	
	/**
	 *  Demande de d�sinscription de l'utilisateur dans la communaut� portant sur un DataMDM
	 * @author Fanny
	 * @param demandeur
	 * 		Le demandeur � l'origine du workflow qui veut ici se d�sinscrire
	 * @param nomCom
	 * 		La communaut� DataMDM que le demandeur veut quitter
	 * @param description
	 * 		La description associ�e � ce workflow
	 */
	public static void requestUnsubscribe(DataMDM demandeur, DataMDM nomCom, String description) 
	{
		// demande la d�sinscription de l�utilisateur dans la communaut� donn�e
		Wf newWf = new RelationshipWf(null,demandeur, nomCom, description,"UPDATE");
		
		// Sauvegarde de ce nouveau Workflow dans la base
		//newWf.save() ;
	}

	/**
	 * Demande de d�sincription de l'utilisateur dans la communaut� portant sur un ModelMDM
	 * Le demandeur passe donc de son r�le actuel au r�le "refus�". On fait donc un update
	 * @author Fanny
	 * @param demandeur
	 * 		Le demandeur � l'origine du workflow qui veut ici se d�sinscrire
	 * @param nomCom
	 * 		La communaut� ModelMDM que le demandeur veut quitter
	 * @param description
	 * 		La description associ�e � ce workflow
	 */
	public static void requestUnsubscribe(DataMDM demandeur, ModelMDM nomCom, String description) 
	{
		// demande la d�sinscription de l�utilisateur dans la communaut� donn�e
		Wf newWf = new RelationshipWf(null,demandeur, nomCom, description, "UPDATE");
		
		// Sauvegarde de ce nouveau Workflow dans la base
		//newWf.save() ;
	}
	
	
	/**
	 * Demande de d�sincription de l'utilisateur dans la communaut� portant sur une Nature
	 * @author Fanny
	 * @param demandeur
	 * 		Le demandeur � l'origine du workflow qui veut ici se d�sinscrire
	 * @param nomCom
	 * 		La communaut� portant sur une nature que le demandeur veut quitter
	 * @param description
	 * 		La description associ�e � ce workflow
	 */
	public static void requestUnsubscribe(DataMDM demandeur, Nature nomCom, String description) 
	{
		// demande la d�sinscription de l�utilisateur dans la communaut� donn�e
		Wf newWf = new RelationshipWf(null,demandeur, nomCom,  description, "UPDATE");
		
		// Sauvegarde de ce nouveau Workflow dans la base
		//newWf.save() ;
	}


	
	/**
	 * Demande de modification de role dans une communaut� portant sur un DataMDM
	 * @author Fanny
	 * @param demandeur
	 * 		Le demandeur � l'origine du workflow qui veut ici modifier son role
	 * @param nomCom
	 * 		La communaut� DataMDM dans laquelle le demandeur veut changer son role
	 * @param role
	 * 		Le role que le demandeur veut dans la communaut�
	 * @param description
	 * 		La description associ�e � ce workflow
	 */
	// Il manque le demandeur
	public static void requestModificationRole(DataMDM demandeur, DataMDM nomCom, Role role, String description) 
	{	
		// demande de
		// modification du role de l�utilisateur dans la communaut� donn�e
		Wf newWf = new RelationshipWf(null,demandeur, nomCom, role, description, "UPDATE");
		
		// Sauvegarde de ce nouveau Workflow dans la base
		//newWf.save() ;
	}
	
	/**
	 * Demande de modification de role dans une communaut� portant sur un ModelMDM
	 * @param demandeur
	 * 		Le demandeur � l'origine du workflow qui veut ici modifier son role
	 * @param nomCom
	 * 		La communaut� ModelMDM dans laquelle le demandeur veut changer son role
	 * @param role
	 * 		Le role que le demandeur veut dans la communaut�
	 * @param description
	 * 		La description associ�e � ce workflow
	 */
	public static void requestModificationRole(DataMDM demandeur, ModelMDM nomCom, Role role, String description) {// demande de
		// modification du role de l�utilisateur dans la communaut� donn�e
		Wf newWf = new RelationshipWf(null,demandeur, nomCom, role, description, "UPDATE");
		
		// Sauvegarde de ce nouveau Workflow dans la base
		//newWf.save() ;
	}
	
	
	/**
	 * Demande de modification de role dans une communaut� portant sur une Nature
	 * @author Fanny
	 * @param demandeur
	 * 		Le demandeur � l'origine du workflow qui veut ici modifier son role
	 * @param nomCom
	 * 		La communaut� portant sur une nature dans laquelle le demandeur veut changer son role
	 * @param role
	 * 		Le role que le demandeur veut dans la communaut�
	 * @param description
	 * 		La description associ�e � ce workflow
	 */
	public static void requestModificationRole(DataMDM demandeur, Nature nomCom, Role role, String description) {// demande de
		// modification du role de l�utilisateur dans la communaut� donn�e
		Wf newWf = new RelationshipWf(null,demandeur, nomCom, role, description, "UPDATE");
		
		// Sauvegarde de ce nouveau Workflow dans la base
		//newWf.save() ;
	}
	

	/**
	 * Demande de cr�ation d'une hi�rarchie entre deux mod�leMDM, le p�re et le
	 * fils
	 * 
	 * @author Solene et Fanny
	 * @param demandeur
	 * @param modelPere
	 * @param fils
	 * @param description
	 */
	// Il nous manque le demandeur
	/*public static void requestCreateHierarchy(DataMDM demandeur, ModelMDM modelPere, ModelMDM fils, String description) {
		
	}
*/
	/**
	 * Demande de cr�ation d'un rattachement � un DataMDM sur un autre DataMDM
	 * 
	 * @author Solene et Fanny
	 * @param donneeAyantUnRattachement
	 * @param aUneDonne
	 * @param description
	 */
	// Il nous manque le demandeur
/*	public static void requestCreateAttachementDataMDM(DataMDM donneeAyantUnRattachement, DataMDM aUneDonne,String description) {

	}
	
*/
	/**
	 * Demande d'affichage de l'ensemble des workflow associ� � un DataMDM (utilisateur)
	 * Ne n�cessite pas le type, nous ne sommes pas en charge de faire le filtrage.
	 * Nous leur offrons juste de r�cup�rer tous les Wf d'un DataMDM, et les getters
	 * afin qu'ils puissent faire eux-m�me le filtrage.
	 *
	 * @author Vinhou et Ludo
	 * @param User
	 * 		L'utilisateur dont on veut cherche les workflows associ�s
	 * @return setWorkflow
	 * 		L'ensemble des workflows associ�s � l'user
	 */
	public static Set readSetWorkflowType (DataMDM user){
		Set setWorkflow = new HashSet();
		DAOFactory df = new Neo4jDAOFactory();
		WFDataDAO dao = df.createWFDataDAO();
		setWorkflow = dao.getWorkflow(user, "ModelData");
		return setWorkflow;
	}
	
	
	/**
	 * Retourne l'ensemble des commentaires li�s au Workflow pass� en param�tre.
	 * @author Bastien F. et Thomas
	 * @param leWf
	 * 		Le workflow pour lequel on souhaite les commentaires.
	 */
	public static List<Comments> askForComments(Wf _leWf)
	{
		// ALLER CHERCHER DANS LA BASE TOUS LES COMMENTAIRES LIES AU WF PASSE EN PARAMETRE.
		
		return null;
	}
	
	
	/**
	 * Demande de fermeture d'un workflow car celui ci est termin�
	 * @author Fanny
	 * @param wf
	 * 		Le workflow que l'on souhaite fermer
	 */
	public static void closeWf(Wf wf){
		wf.closeWf();
	}
	
	/**
	 * @author Vinhou, Killian , Ludo
	 * 
	 * M�thode de la fa�ade permettant de r�cup�rer le type de la requ�te
	 * Utile par exemple pour l'IHM afin d'effectuer le filtrage.
	 * @param wf
	 * @return Le String correspondant au type du WF (Create, Update, Delete ...)
	 */
	
	public static String getRequestType(Wf wf){
		return wf.getRequestType();
	}
}
