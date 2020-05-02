package dao.neo4j;

import model.DataMDM;
import model.ModelMDM;
import model.Role;
import dao.model.ModelMDMDAO;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import resource.NodesRelationship;


public class Neo4jModelMDMDAO extends ModelMDMDAO {

	/*----------Variables-----------*/
	GraphDatabaseService gDB = new GraphDatabaseFactory().newEmbeddedDatabase("D:\\Education\\Neo4j\\graph.db");


	/*----------Constructeur-----------*/
	
	public Neo4jModelMDMDAO(){
		
	}
	
	/*----------Getters and setters-----------*/
	/*----------Methodes-----------*/

	/**
	 * Permet de sauvegarder, charger, mettre � jour et supprimer un modelMDM de
	 * la base de donn�es Neo4j
	 */

	/**
	 * methode saveModelMDM(ModelMDM) permet de sauvegarder le modelMdm dans la
	 * base de donnees et de creer la relation avec la nature dans la base
	 * 
	 * @param modelMDM
	 *            le modelMDM a sauvegarder dans la base de donnees.
	 * 
	 */
	public Long save(ModelMDM modelMDM) {
		
		GraphDatabaseService gDB = new GraphDatabaseFactory().newEmbeddedDatabase(null);
		Transaction t = gDB.beginTx();
		
		Node nodeModel = null;

		try{
			//recup�re la nature
			
			Node nature = gDB.getNodeById((long)modelMDM.getIdentifiant());
			
			// creer le noeud model		
			nodeModel = gDB.createNode();
			nodeModel.setProperty("type", "ModelMDM");
			nodeModel.setProperty("label",modelMDM.getLabel());
			nodeModel.setProperty("description", modelMDM.getDescription());
			//on creer la relation entre model et nature.
			
			nodeModel.createRelationshipTo(nature, NodesRelationship.HAS_NATURE);
			//creer une relation avec le pere si le model en a un
			ModelMDM modelPere = modelMDM.getModelPere();
			if ( modelPere != null){
				Node nodeModelPere = gDB.getNodeById((long)modelPere.getIdentifiant());
				nodeModel.createRelationshipTo(nodeModelPere, NodesRelationship.HAS_FATHER);
			}
			
			t.success();
		}
		finally
		{
			t.finish();		
			gDB.shutdown();
		}

		return nodeModel.getId();
	}

	/**
	 * Charge le modelMDM de la base contenant le label "label". 
	 */
	public ModelMDM load(String label) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/**
	 * 
	 * @param idModel object correpondant a l'identifiant du modelMDM que l'on veut charger.
	 * @return le modelMDM charge a partir de la base de donnees.
	 */
	public ModelMDM load (Object idModel){
		
		return null;
	}

	@Override
	public boolean delete(ModelMDM model) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Boolean update(ModelMDM model) {
		// TODO Auto-generated method stub
		return null;
	}

	/** Ajoute un utilisateur donn�e avec le role demand�
	 * 
	 * @param user	
	 * 		Utilisateur � ajouter
	 * @param idCom
	 *            identifiant de la communaut� model
	 * @param role
	 * 		Role que va avoir l'utilisateur ajout�
	 * 
	 * @author Lydia
	 */
	public void addMember(Object idCom, DataMDM user, Role role) 
	{
		GraphDatabaseService gDB = new GraphDatabaseFactory().newEmbeddedDatabase(null);
		Transaction t = gDB.beginTx();
		
		Node nodeData = gDB.getNodeByID((long)user.getIdentifiant());
		Node nodeModel = gDB.getNodeByID(idCom);
		try
		{
			relationship = nodeData.createRelationshipTo( nodeModel, NodesRelationship );
			relationship.setProperty( "role", role.getlabel());
			t.success();
		}
		finally
		{
		    t.finish();
			gDB.shutdown();
		}	
	}
	
	/** Accesseur pour r�cup�rer le responsable d'une communaut�
	 * 
	 * @return le responsable du ModelMDM donn�
	 * 
	 * @author Tom Kuhnen
	 */
	public DataMDM getResponsable()
	{
		return null;
	}
	
	/** Accesseur pour r�cup�rer le tuteur d'une communaut�
	 * 
	 * @return le tuteur du ModelMDM donn�
	 *  
	 * @author Tom Kuhnen
	 */
	public DataMDM getTuteur()
	{
		return null;
	}

	 /** Modifie le role de l�utilisateur dans une communaut� model de la BD
	  * 
	  * @param id
	  * 		Identifiant du modelMDM dans la base
	  * @param user
	  * 		Utilisateur dont on va modifier le role
	  * @param role
	  * 		Role que va avoir l'utilisateur
	  * 
	  * @author Aur�lie :)
	  * 	 
	  */
	public void updateRole(Object id, DataMDM user, Role role)
	{
		// requ�te � mettre ICI !! PLUS TARD !!!
	}
	
}
