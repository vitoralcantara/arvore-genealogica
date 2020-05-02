package model;

public class LinkWf {
	/****************************************************************
	 *                    Variables de la classe                     *
	 ****************************************************************/


	private String etat; // Correspond � l'�tat du Workflow (cha�ne de
							// caract�res pouvant prendre les valeurs "Valide",
							// "En attente", "Refuse", "Arbitrage".
	private Wf workflow; // Le Workflow concern� par l'�tat
	private Object cible; //il s'agit de la communaut� cible du workflow : nature, dataMDM, modelMDM...

	
	
	/****************************************************************
	 *                    Constructeurs de la classe                 *
	 ****************************************************************/


	/**
	 * Constructeur de la classe LinkWf.
	 * 
	 * @author Thomas & Bastien F.
	 * @param workflow
	 *            Le Workflow concern� par l'�tat
	 * @param etat
	 *            Correspond � l'�tat du Workflow (cha�ne de caract�res pouvant
	 *            prendre les valeurs "Valide", "En attente" ou "Refuse".
	 * @param cible
	 *            La communaut� cible du workflow
	 */
	public LinkWf(Wf workflow, String etat, Object cible) {
		this.workflow = workflow;
		this.etat = etat;
		this.cible = cible;
	}

	
	/****************************************************************
	 *                    Getters/Setters de la classe              *
	 ****************************************************************/

	/**
	 * Permet de r�cup�rer l'�tat du Workflow concern�.
	 * @author Thomas & Bastien F.
	 * @return etat qui retourne l'�tat associ� au Workflow
	 */
	public String getState() {
		return etat;
	}

	/**
	 * Permet d'entrer un nouvel �tat pour le Workflow concern�.
	 * @param etat
	 *            Correspond � l'�tat du Workflow (cha�ne de caract�res pouvant
	 *            prendre les valeurs "Valide", "En attente" ou "Refuse".
	 */
	public void setState(String etat) {
		this.etat = etat;
	}

	/**
	 * Permet de r�cup�rer le Workflow concern� par ce LinkWf.
	 * Peut notamment �tre utile pour r�cup�rer la liste des Wf n�cessitant une validation.
	 * @author Thomas & Bastien F.
	 * @return workflow donc le workflow concern� par ce LinkWf
	 */
	public Wf getWorkflow() {
		return workflow;
	}

	/**
	 * Permet de passer un nouveau Workflow concern� par cet �tat.
	 * @author Thomas & Bastien F.
	 * @param workflow
	 *            Le Workflow concern� par l'�tat.
	 */
	public void setWorkflow(Wf workflow) {
		this.workflow = workflow;
	}

	/**
	 * Permet de r�cup�rer la cible du workflow (communaut� de type DataMDM, ModelMDM ou Nature)
	 * @return cible qui est la cible du workflow
	 */
	public Object getCible() {
		return cible;
	}

	/**
	 * Permet de passer une nouvelle cible concern� par cet �tat
	 * @param cible
	 * La cible concern� par l'�tat
	 */
	public void setCible(Object cible) {
		this.cible = cible;
	}

}
