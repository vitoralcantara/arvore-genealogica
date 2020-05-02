package model;

public class Request {
	
	// Variables de classe
	
	private Wf workflow;
	private String action; //est ce que action est un String ? Correspond au type de Request (Create, Update...)
	private DataMDM demandeur;
		
	
	// Constructeurs 
	
	/**
	 * @author Thomas
	 * @param workflow
	 *            Le Workflow concern� par la requ�te
	 * @param etat
	 *            Correspond � l'action qui est demand�e (pouvant prendre les valeurs "READ", "CREATE", "UPDATE" ou "DELETE").
	 * @param demandeur
	 * 			  Correspond � l'utilisateur ayant demand� la cr�ation du Workflow (c'est un DataMDM).
	 */
	public Request(Wf workflow, String action, DataMDM demandeur)
	{
		this.workflow = workflow;
		this.action = action;
		this.demandeur = demandeur;
	}
	
	
	// M�thodes d'instances
	
	/**
	 * Permet d'obtenir le demandeur de cr�ation du Workflow associ� � cette instance Request.
	 * @author Bastien F. et Thomas
	 * @return Demandeur de cr�ation du Workflow associ� � cette Request.
	 */
	public DataMDM getAsker()
	{
		return(demandeur) ;
	}
	
	/**
	 * @author Killian, Ludo, Vinh
	 * Permet de r�cup�rer le type de la Request
	 * Permet entres autres de filtrer les Workflows en fonction du type (Create, Update, ...)
	 * 
	 * @return String du type de la Request
	 */
	
	public String getAction(){
		return this.action;
	}


	public Wf getWorkflow() {
		return workflow;
	}


	public void setWorkflow(Wf workflow) {
		this.workflow = workflow;
	}


	public DataMDM getDemandeur() {
		return demandeur;
	}


	public void setDemandeur(DataMDM demandeur) {
		this.demandeur = demandeur;
	}


	public void setAction(String action) {
		this.action = action;
	}
	
	
}
