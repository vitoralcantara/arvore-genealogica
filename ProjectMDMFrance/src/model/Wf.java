package model;

import java.util.ArrayList;
import java.util.List;
import facade.*;


public class Wf 
{
	/****************************************************************
	 *                    Variables de la classe                      *
	 ****************************************************************/

	private Object identifiant;					//identifiant du workflow en base
	private int timestamp ;						// Date � laquelle le Workflow a �t� cr��.
	private String description ;				// Description du Workflow
	private LinkWf leLinkWf ;					// Le linkWf li� au Workflow contenant la cible du workflow
	private Request request ;					// Objet contenant le demandeur du Workflow
	private Validation tuteurValidation ;		// Objet correspondant � la validation du tuteur
	private Validation respoValidation ;		// Objet correspondant � la validation du responsable
	private Arbitrage tutorArbitration ;		// Objet correspondant � l'arbitrage du tuteur (si celui-ci est n�cessaire)
	
	
	/****************************************************************
	 *                   Constructeurs de la classe                  *
	 ****************************************************************/

	
	/**
	 * Constructeur utilis� pour cr�er un nouveau workflow en rapport avec un objet Nature.
	 * Ces nouveaux Workflow seront plac�s, � l'origine, � l'�tat d'en attente.
	 * Ils seront alors soumis au jugement de la communaut� associ�e (ici celle de l'objet Nature).
	 * Suivant la d�cision prise, l'issue du Workflow pourra prendre deux formes : accept� ou refus�.
	 * @author Bastien F. et Thomas
	 * @param identifiant
	 * 			L'identifiant permet de faire le lien entre le workflow en base et l'objet,
	 * 			de sorte � pouvoir le retrouver par la suite.
	 * 			Si il s'agit d'un nouveau workflow, il faut envoyer NULL comme identifiant
	 * @param _description
	 * 			Correspond � la description du Workflow.
	 * @param demandeur 
	 * 			Correspond au demandeur de la cr�ation du Workflow.
	 * @param nature
	 * 			Correspond � la nature sur laquelle on veut effectuer une demande.
	 * @param type
	 * 			Correspond au type de la demande. Cha�ne de caract�res pouvant prendre les valeurs "CREATE", "UPDATE" ou "DELETE".
	 */
	public Wf(Object identifiant, String _description, DataMDM demandeur, Nature nature, String type)
	{
		// Initialisation de l'identifiant (permettant de faire le lien avec la base, quelle qu'elle soit)
		this.identifiant = identifiant;
		
		// Initialisation de la description
		description = _description ;
		
		// Cr�ation du LinkWf associ�
		leLinkWf = new LinkWf(this,"En attente", (Object) nature);
		
		// R�cup�ration des tuteur et responsable de la communaut� associ�e � l'objet nature
		if (nature != null)
		{
			DataMDM tuteur = GCFacade.getTuteur(nature);
			DataMDM responsable = GCFacade.getResponsable(nature);
		
			//Apr�s avoir r�cup�rer tuteur et responsable, il faut cr�er les validations associ�es
			this.tuteurValidation = new Validation("En attente", tuteur, this);
			this.respoValidation = new Validation("En attente", responsable, this);
		}
		else 
		{
			System.out.println("nature vide lors de la cr�ation du workflow");
		}
	
		// Cr�ation de l'objet contenant le demandeur du Workflow
		// On passe donc le demandeur, et le type de demande ici : CREATE
		request = new Request (this,type,demandeur) ;
	}
	
	
	/**
	 * Constructeur utilis� pour cr�er un nouveau workflow en rapport avec un objet ModelMDM.
	 * Ces nouveaux Workflow seront plac�s, � l'origine, � l'�tat d'en attente.
	 * Ils seront alors soumis au jugement de la communaut� associ�e (ici celle de l'objet ModelMDM).
	 * Suivant la d�cision prise, l'issue du Workflow pourra prendre deux formes : accept� ou refus�.
	 * @author Bastien F. et Thomas
	 * @param identifiant
	 * 			L'identifiant permet de faire le lien entre le workflow en base et l'objet,
	 * 			de sorte � pouvoir le retrouver par la suite.
	 * 			Si il s'agit d'un nouveau workflow, il faut envoyer NULL comme identifiant
	 * @param _description
	 * 			Correspond � la description du Workflow.
	 * @param demandeur 
	 * 			Correspond au demandeur de la cr�ation du Workflow.
	 * @param objetAssocie
	 * 			Correspond � l'objet de type ModelMDM sur lequel on demande le Workflow.
	 * @param type
	 * 			Correspond au type de la demande. Cha�ne de caract�res pouvant prendre les valeurs "CREATE", "UPDATE" ou "DELETE".
	 */
	public Wf(Object identifiant, String _description, DataMDM demandeur, ModelMDM objetAssocie, String type)
	{
		// Initialisation de l'identifiant (permettant de faire le lien avec la base, quelle qu'elle soit)
		this.identifiant = identifiant;
		
		// Initialisation de la description
		description = _description ;
		
		// Cr�ation du LinkWf associ�
		leLinkWf = new LinkWf(this,"En attente", (Object) objetAssocie) ;
		
		// R�cup�ration des tuteur et responsable de la communaut� associ�e � l'objet ModelMDM
		DataMDM tuteur = GCFacade.getTuteur(objetAssocie);
		DataMDM responsable = GCFacade.getResponsable(objetAssocie);
		
		//Apr�s avoir r�cup�rer tuteur et responsable, il faut cr�er les validations associ�es
		this.tuteurValidation = new Validation("En attente", tuteur, this);
		this.respoValidation = new Validation("En attente", responsable, this);
		
		// Cr�ation de l'objet contenant le demandeur du Workflow
		// On passe donc le demandeur, et le type de demande ici : CREATE
		request = new Request (this,type,demandeur) ;
	}
	
	
	/**
	 * Constructeur utilis� pour cr�er un nouveau workflow en rapport avec un objet DataMDM.
	 * Ces nouveaux Workflow seront plac�s, � l'origine, � l'�tat d'en attente.
	 * Ils seront alors soumis au jugement de la communaut� associ�e (ici celle de l'objet DataMDM).
	 * Suivant la d�cision prise, l'issue du Workflow pourra prendre deux formes : accept� ou refus�.
	 * @author Bastien F. et Thomas
	 * @param identifiant
	 * 			L'identifiant permet de faire le lien entre le workflow en base et l'objet,
	 * 			de sorte � pouvoir le retrouver par la suite.
	 * 			Si il s'agit d'un nouveau workflow, il faut envoyer NULL comme identifiant
	 * @param _description
	 * 			Correspond � la description du Workflow.
	 * @param demandeur 
	 * 			Correspond au demandeur de la cr�ation du Workflow.
	 * @param objetAssocie
	 * 			Correspond � l'objet de type ModelMDM sur lequel on demande le Workflow.
	 * @param type
	 * 			Correspond au type de la demande. Cha�ne de caract�res pouvant prendre les valeurs "CREATE", "UPDATE" ou "DELETE".
	 */
	public Wf(Object identifiant, String _description, DataMDM demandeur, DataMDM objetAssocie, String type)
	{
		// Initialisation de l'identifiant (permettant de faire le lien avec la base, quelle qu'elle soit)
		this.identifiant = identifiant;
		
		// Initialisation de la description
		description = _description ;
		
		// Cr�ation du LinkWf associ�
		leLinkWf = new LinkWf(this,"En attente", (Object) objetAssocie) ;
		
		// R�cup�ration des tuteur et responsable de la communaut� associ�e � l'objet DataMDM
		DataMDM tuteur = GCFacade.getTuteur(objetAssocie);
		DataMDM responsable = GCFacade.getResponsable(objetAssocie);
		
		//Apr�s avoir r�cup�rer tuteur et responsable, il faut cr�er les validations associ�es
		this.tuteurValidation = new Validation("En attente", tuteur, this);
		this.respoValidation = new Validation("En attente", responsable, this);
		
		// Cr�ation de l'objet contenant le demandeur du Workflow
		// On passe donc le demandeur, et le type de demande ici : CREATE
		request = new Request (this,type,demandeur);
	}
	
	
	
	
	
	/****************************************************************
	 *                    M�thodes de la classe                      *
	 ****************************************************************/

	
	/**
	 * Permet d'obtenir l'instance de la Validation du tuteur sur ce Workflow.
	 * @author Bastien F. et Thomas
	 * @return Instance d'une Validation.
	 */
	public Validation getValidateTuteur() 
	{
		return tuteurValidation ;
	}
	
	
	/**
	 * Cette m�thode est une notification de la bonne ex�cution de la modification du syst�me apr�s qu'un Workflow
	 * ait �t� accept� et que nous ayons appel� la m�thode cens�e ex�cuter l'action r�ellement.
	 * Cette m�thode permet ainsi de cl�turer fermement le Workflow, car celui-ci est termin� de mani�re s�re.
	 * Dans ce cas, nous allons supprimer l'instance de Workflow dans la base de donn�es (afin de soulager le syst�me).
	 * @author Bastien F. et Thomas
	 */
	public void closeWf() 
	{
		// SOIT ON LE SUPPRIME DE LA BASE DE DONNEES
		// SOIT ON PASSE LE LINKWF A "TERMINE" PAR EXEMPLE. 
	}
	
	
	/**
	 * Methode qui sera impl�ment�e dans les sous-classes.
	 * Le polymorphisme sera exploit� pour cette m�thode.
	 * @author Bastien F. et Thomas
	 * @return Instance d'une Validation.
	 */
	public void executer()
	{ }

	
	/**
	 * Permet d'obtenir l'instance de la Validation du responsable sur ce Workflow.
	 * @author Bastien F. et Thomas
	 * @return Instance d'une Validation.
	 */
	public Validation getValidateResponsable() 
	{
		return respoValidation ;
	}
	
	
	/**
	 * Permet d'obtenir l'ensemble des personnes au sein de la Communaut� sur la 
	 * Nature en question qui ont les droits pour Valider une demande
	 * @author Solene et Fanny
	 * @param nature
	 * 	La nature dont on veut les validateurs
	 * @return une liste ici des validateurs de la nature
	 */
	public List<DataMDM> getValidateurs(Nature nature){
		//creation de la liste
		List<DataMDM> validateurs = new ArrayList<DataMDM>();
		
		//recuperation du tuteur de la nature donnee
		DataMDM tuteurNature = GCFacade.getTuteur(nature);
		
		if(tuteurNature!= null){
			validateurs.add(tuteurNature);
		}
		
		//recuperation du responsable de la nature donne
		DataMDM respoNature = GCFacade.getResponsable(nature);
		
		if(respoNature!= null){
			if(!respoNature.equals(tuteurNature))
			validateurs.add(respoNature);
		}
		
		//renvoi de la liste des validateurs => penser � v�rifier qu'elle n'est pas nulle
		return validateurs;
	}
	
	
	/**
	 * Permet d'obtenir l'ensemble des personnes au sein de la Communaut� portant sur un objet DataMDM
	 * qui ont les droits pour Valider une demande
	 * @author Solene et Fanny
	 * @param com
	 * Communaute de type DataMDM
	 * @return
	 * Liste des validateurs de la communaut� indiqu�e
	 */
	public List<DataMDM> getValidateurs(DataMDM com){
		//creation de la liste
				List<DataMDM> validateurs = new ArrayList<DataMDM>();
				
				//recuperation du tuteur de la communaute donnee
				DataMDM tuteurCom = GCFacade.getTuteur(com);
				
				if(tuteurCom!= null){
					validateurs.add(tuteurCom);
				}
				
				//recuperation du responsable de la communaute donne
				DataMDM respoCom = GCFacade.getResponsable(com);
				
				if(respoCom!= null){
					if(!respoCom.equals(tuteurCom))
					validateurs.add(respoCom);
				}
				
				//renvoi de la liste des validateurs => penser � v�rifier qu'elle n'est pas nulle
				return validateurs;
	}
	
	
	/**
	 * Permet d'obtenir l'ensemble des personnes au sein de la Communaut� portant sur un objet ModelMDM
	 * qui ont les droits pour Valider une demande
	 * @author Solene et Fanny
	 * @param com
	 * Communaute de type ModelMDM
	 * @return
	 * Liste des validateurs de la communaut� indiqu�e
	 */
	public List<DataMDM> getValidateurs(ModelMDM com){
		//creation de la liste
		List<DataMDM> validateurs = new ArrayList<DataMDM>();
		
		//recuperation du tuteur de la communaute donnee
		DataMDM tuteurCom = GCFacade.getTuteur(com);
		
		if(tuteurCom!= null){
			validateurs.add(tuteurCom);
		}
		
		//recuperation du responsable de la communaute donne
		DataMDM respoCom = GCFacade.getResponsable(com);
		
		if(respoCom!= null){
			if(!respoCom.equals(tuteurCom))
			validateurs.add(respoCom);
		}
		
		//renvoi de la liste des validateurs => penser � v�rifier qu'elle n'est pas nulle
		return validateurs;
		
	}
	
	
	/****************************************************************
	 *                    Getters/Setters de la classe               *
	 ****************************************************************/

	
	/**
	 * Permet d'obtenir le TimeStamp correspondant � la date de cr�ation du Workflow.
	 * @author Solene et Fanny
	 * @return TimeStamp
	 */
	public int getTimestamp() {
		return timestamp;
	}

	/**
	 * Permet d'entrer un TimeStamp correspondant � la date de cr�ation du Workflow.
	 * @author Solene et Fanny
	 * @param timestamp
	 * 			Nouvelle date 
	 */
	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Permet d'obtenir la description correspondant au workflow en question
	 * @author Killian, Ludo, Vinh
	 * @return String contenant la description du workflow
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Permet d'entrer une description correspondant au nouveau workflow cr��
	 * @author Killian, Ludo, Vinh
	 * @param description
	 * 	La description du workflow en question
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Permet d'obtenir l'instance du LinkWf li� � ce Workflow.
	 * @author Bastien F. et Thomas
	 * @return Instance du LinkWf.
	 */
	public LinkWf getLinkWf() 
	{
		return leLinkWf ;
	}

	/**
	 * Permet d'entrer une instance du LinkWf li� � ce Workflow
	 * @author Killian, Ludo, Vinh
	 * @param leLinkWf
	 * 	le LinkWf que l'on veut associer au workflow
	 */
	public void setLinkWf(LinkWf leLinkWf) {
		this.leLinkWf = leLinkWf;
	}
	
	/**
	 * Permet d'obtenir le demandeur de cr�ation de ce Workflow.
	 * @author Bastien F. et Thomas
	 * @return Demandeur de cr�ation du Workflow.
	 */
	public DataMDM getAsker()
	{
		return(request.getAsker()) ;
	}
	
	/**
	 * @author Killian, Ludo, Vinh
	 * Permet de r�cup�rer la Request au sein d'un Workflow
	 * Utilisation par la suite d'un getter dans la classe Request
	 * pour r�cup�rer le Type de la Request
	 * 
	 * @return Request 
	 */
	public Request getRequest(){
		return this.request;
	}
	
	
	
	/**
	 * Permet d'entrer une instance de Request li� � ce Workflow
	 * @param demande qui contient le demandeur, l'action � faire ainsi qu'un lien vers le Workflow en question
	 */
	public void setRequest(Request demande) {
		this.request = demande;
	}

	/**
	 * Permet d'obtenir l'objet correspondant � la validation du tuteur
	 * @return tuteurValidation de type Validation qui va nous retourner l'�tat, le workflow ainsi que le tuteur
	 */
	public Validation getTuteurValidation() {
		return tuteurValidation;
	}

	/**
	 * Permet d'entrer une instance de Validation pour un tuteur li� � ce workflow
	 * @param tuteurValidation qui nous fournit l'�tat (l'avis) ainsi que le tuteur pour le workflow en question
	 */
	public void setTuteurValidation(Validation tuteurValidation) {
		this.tuteurValidation = tuteurValidation;
	}

	
	/**
	 * Permet d'obtenir l'objet correspondant � la validation du responsable
	 * @return respoValidation de type Validation qui nous retourne l'�tat (avis) du responsable vis � vis du workflow
	 */
	public Validation getRespoValidation() {
		return respoValidation;
	}

	/**
	 * Permet d'entrer une instance de Validation pour un responsable li� � ce workflow
	 * @param respoValidation qui nous fournit l'�tat (avis) ainsi que le responsable pour le workflow en question
	 */
	public void setRespoValidation(Validation respoValidation) {
		this.respoValidation = respoValidation;
	}

	/**
	 * Permet d'obtenir l'objet correspondant � l'arbitrage du tuteur li� � ce workflow
	 * @return tutorArbitration de type Arbitrage qui nous retourne l'avis de l'arbitrage sur le workflow
	 */
	public Arbitrage getTutorArbitration() {
		return tutorArbitration;
	}


	/**
	 * Permet d'entrer une instance d'Arbitrage li� � ce workflow
	 * @param tutorArbitration qui nous fournit l'�tat de l'arbitrage ainsi que le responsable pour le workflow en question
	 */
	public void setTutorArbitration(Arbitrage tutorArbitration) {
		this.tutorArbitration = tutorArbitration;
	}

	
	/**
	 * Permet d'obtenir l'identifiant du workflow en base
	 * @return identifiant de type Object pour nous permettre de retrouver le Workflow en base
	 */
	public Object getIdentifiant() {
		return identifiant;
	}


	/**
	 * Permet d'entrer une instance d'un objet pour l'identifiant li� � ce workflow
	 * @param identifiant qui nous permettra d'identifier le workflow dans la base
	 */
	public void setIdentifiant(Object identifiant) {
		this.identifiant = identifiant;
	}
	

	/**
	 * @author Killian, Ludo, Vinh
	 * R�cup�re le type de la requete au sein d'un WF
	 * 
	 * @return String du type de la Requete
	 */
	
	public String getRequestType(){
		return this.request.getAction();
	}
	
	/**
	 * @author Thomas, Bastien F
	 * change le type de la requete au sein d'un WF
	 * 
	 * @param action Le nouveau type de requ�te
	 */
	
	public void setRequestType(String action)
	{
		this.request.setAction(action);
	}
}
