package model;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.neo4j.graphdb.Node;
import scala.reflect.generic.Trees.This;
import dao.factory.DAOFactory;
import dao.factory.Neo4jDAOFactory;
import dao.model.WFBriqueSIDAO;
import dao.model.WFDataDAO;
import dao.model.WFRelationshipDAO;

import facade.MappingFacade;

public class BriqueSIWf extends Wf
{
	/****************************************************************
	 *             Variables de la classe     				         *
	 ****************************************************************/

	
	Connector connecteur ;						// Connecteur de la nouvelle brique
	Map<ModelMDM, List<ModelValue>> schema ;	// Informations concernant les modelValue et ModelMDM de cette nouvelle brique.


	Nature nature ;								// Car demande de cr�ation fa�te sur la communaut� nature BriqueSI (que nous devons r�cup�rer)
	
	
	
	
	
	/****************************************************************
	 *                  Constructeurs de la classe                   *
	 ****************************************************************/

	
/**
 * 	 * Constructeur d'une instance.
	 * @author Bastien F. & Thomas
	 * @param identifiant
	 * @param laNature
	 * 			Correspond � la communaut� Nature qui sera utile pour retrouver ses tuteur et responsable.
	 * @param demandeur 
	 * 			Correspond au demandeur de la cr�ation du Workflow.
	 * @param description
	 * 			Correspond � la description du Workflow.
	 * @param _connecteur
	 * 			Correspond � un type de connecteur � la base de donn�es
	 * @param _schema
	 * 			Correspond au sch�ma de la base de donn�es qu'on importe dans le MDM
	 * @param type
	 * 			Indique le type de demande d'�criture du Workflow. Celui-ci peut prendre les valeurs "CREATE", "UPDATE" ou "DELETE".
	 */
	public BriqueSIWf(Object identifiant, Nature laNature, DataMDM demandeur, String description, Connector _connecteur, Map<ModelMDM, List<ModelValue>> _schema, String type)
	{
		super(identifiant,description,demandeur,laNature,type) ;		// Appel du constructeur de la super-classe permettant d'initialiser le commentaire du Wf.		
		this.connecteur = _connecteur ;
		this.schema = _schema ;
		this.nature = laNature;
		
		DAOFactory factory = new Neo4jDAOFactory();
		WFBriqueSIDAO dao = factory.createWFBriqueSIDAO();
		long id = Long.parseLong(dao.saveBriqueSIWf(this).toString());
		this.setIdentifiant(id);
		
		WFRelationshipDAO rDao = factory.createWFRelationshipDAO();
		rDao.createIntraWfRelationWFBriqueWFRelationship(this.getIdentifiant(), this.nature);
				
	}
	
	
	/****************************************************************
	 *            Getters/setters de la classe                      *
	 ****************************************************************/

	/**
	 * Permet d'obtenir le sch�ma de la base de donn�es que l'on a import�
	 * @author Solene & Wensheng
	 * @return schema
	 * 		le sch�ma associ� � cette nouvelle brique
	 */
	public Map<ModelMDM, List<ModelValue>> getSchema() {
		return schema;
	}


	/**
	 * Permet d'entrer un nouveau sch�ma pour cette brique
	 * @author Solene & Wensheng
	 * @param schema
	 * 		Le nouveau sch�ma a associ� � la brique
	 */
	public void setSchema(Map<ModelMDM, List<ModelValue>> schema) {
		this.schema = schema;
	}


	/**
	 * Permet d'obtenir le type de connecteur de la nouvelle brique SI
	 * @author Solene & Wensheng
	 * @return connecteur
	 * 		Le type de connecteur de la brique SI
	 */
	public Connector getConnecteur() {
		return connecteur;
	}


	/**
	 * Permet d'entrer un nouveau type de connecteur pour la brique SI
	 * @author Solene & Wensheng
	 * @param connecteur
	 * 		Le nouveau type de connecteur que l'on veut associer � la brique SI
	 */
	public void setConnecteur(Connector connecteur) {
		this.connecteur = connecteur;
		DAOFactory factory = new Neo4jDAOFactory();
		WFDataDAO dao = factory.createWFDataDAO();
		dao.saveConnector(this);
	}


	/**
	 * Permet de r�cup�rer la nature de type brique associ�e � la brique SI
	 * @author Solene & Wensheng
	 * @return nature
	 * 		La nature de type brique associ�e � la brique SI
	 */
	public Nature getNature() {
		return nature;
	}

	
	/**
	 * Permet d'associer une nouvelle nature de type brique � la brique SI
	 * @author Solene & Wensheng
	 * @param nature
	 * 		La nature de type brique que l'on veut associ�e � la brique SI
	 */
	public void setNature(Nature nature) {
		this.nature = nature;
	}


	
	/****************************************************************
	 *                  Methodes de la classe                   *
	 ****************************************************************/

	/**
	 * Permet de sauvegarder en base le sch�ma de la brique SI avec un ensemble de 
	 * ModelMDM et de ModelValue
	 * @author Solene & Wensheng
	 */
	public void saveMap(){
		//On va generer tous les noeuds de model data
		Map<ModelMDM, List<ModelValue>> m = this.getSchema();
		
		DAOFactory factory = new Neo4jDAOFactory();
		WFDataDAO dao = factory.createWFDataDAO();
		for(Entry<ModelMDM, List<ModelValue>> t : m.entrySet())
		{
			Object resultat = dao.saveWFModelData(t.getKey(), this.getIdentifiant());
			// chauqe modele mdm
			for(ModelValue c: t.getValue())
			{
				Object resStockage = dao.saveWFModelData( c, this.getIdentifiant());
			}
		}
	}
	
	
	/**
	 * LE WORKFLOW A ETE ACCEPTE. 
	 * Methode cl� qui lancera la cr�ation de la brique dans le syst�me.
	 * @author Bastien F. et Thomas
	 * @return Instance d'une Validation.
	 */
	public void executer()
	{
		MappingFacade.createBrick(nature, connecteur, schema,  super.getAsker(), this.getDescription()) ;	
	}
	
}
