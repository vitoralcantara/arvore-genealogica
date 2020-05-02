package model;

public class Arbitrage 
{
	// D�claration des variables

	private String etat; // Cha�ne de caract�re pouvant prendre les valeurs "Accepte" (si accept� apr�s arbitrage), "En attente" (si en attente d'arbitrage) ou "Refuse" (si refus apr�s arbitrage).
	private DataMDM validateur; // DataMdm correspondant au tuteur de la
								// communaut� li�e � ce Workflow.
	private Wf workflow; // Le Workflow concern� par le besoin d'arbitrage.

	/**
	 * @author Thomas et Bastien F.
	 * @param etat :
	 *            Cha�ne de caract�re pouvant prendre les valeurs "Accepte", "En
	 *            attente" ou "Refuse".
	 * @param validateur :
	 *            DataMdm correspondant au tuteur de la communaut� li�e � ce
	 *            Workflow.
	 * @param workflow :
	 *            Le Workflow concern� par le besoin d'arbitrage.
	 */
	public Arbitrage(String etat, DataMDM validateur, Wf workflow) {
		this.etat = etat;
		this.validateur = validateur;
		this.workflow = workflow;
	}
	
	
	// M�thodes
	
	
	
	//accesseurs
	
	/**
	 * Permet de r�cup�rer l'�tat actuel de l'arbitrage. Celui-ci peut prendre trois valeurs : "Accepte", "En attente" ou "Refuse".
	 * @author Thomas et Bastien F.
	 */
	public String getState() {
		return etat;
	}

	/**
	 * Permet de modifier l'�tat actuel de l'arbitrage.
	 * @author Thomas et Bastien F.
	 * @param etat :
	 *            Cha�ne de caract�re pouvant prendre les valeurs "Accepte", "En
	 *            attente" ou "Refuse".
	 */
	public void setState(String etat) {
		this.etat = etat;
	}

	/**
	 * Permet de r�cup�rer le validateur concern� par cet arbitrage.
	 * @author Thomas et Bastien F.
	 */
	public DataMDM getValidateur() {
		return validateur;
	}

	/**
	 * Permet d'entrer un nouveau validateur pour cet arbitrage de Workflow. Ce sera � lui � arbitrer.
	 * @author Thomas et Bastien F.
	 * @param validateur :
	 *            DataMdm correspondant au tuteur de la communaut� li�e � ce
	 *            Workflow.
	 */
	public void setValidateur(DataMDM validateur) {
		this.validateur = validateur;
	}

	/**
	 * Retourne le Workflow concern� par cet arbitrage.
	 * @author Thomas et Bastien F.
	 */
	public Wf getWorkflow() {
		return workflow;
	}

	/**
	 * Permet d'entrer un nouveau Workflow qui aura besoin d'arbitrage.
	 * @author Thomas et Bastien F.
	 * @param workflow :
	 *            Le Workflow concern� par le besoin d'arbitrage.
	 */
	public void setWorkflow(Wf workflow) {
		this.workflow = workflow;
	}

}
