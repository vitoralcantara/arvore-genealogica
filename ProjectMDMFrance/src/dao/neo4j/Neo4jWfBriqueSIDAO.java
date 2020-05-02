package dao.neo4j;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import model.BriqueSIWf;
import model.Connector;
import model.DataMDM;
import model.ModelMDM;
import model.ModelValue;
import model.Nature;
import dao.model.WFBriqueSIDAO;


public class Neo4jWfBriqueSIDAO extends WFBriqueSIDAO {
	/****************************************************************
	 *                    Membres de la classe                      *
	 ****************************************************************/
	
		//Pour la connexion a la base
		private GraphDatabaseService graphDB;
		
		//Les types de relations workflow
		private static enum RelationWfTypes implements RelationshipType{
			LINKWF,
			REQUEST,
			INTRA_WF,
			CONNECT_WF
		}

	/****************************************************************
	 *                    Methodes de la classe                     *
	****************************************************************/

	/**
	 * Methode permettant la connection � la base
	 * @author Solene
	 * @return true si la connexion est effectu�e, false sinon
	 */
	public boolean openConnectionBase(){
		this.graphDB = new GraphDatabaseFactory().newEmbeddedDatabase(path);
		registerShutdownHook(this.graphDB);
		
		//Comment on v�rifie que la connexion est bien �tablie???
		return true;
	}

	
	
	/**
	 * Permet de fermer la connexion � la base de donn�es
	 * @author Solene & Wensheng
	 */
	public void closeConnection(){
		this.graphDB.shutdown();
		
	}
	
	/**
	 * Permet la connexion et la d�connexion propre
	 * @author Wensheng Zhang
	 * @param graphDB
	 */
	private static void registerShutdownHook(final GraphDatabaseService graphDB){
		Runtime.getRuntime().addShutdownHook(new Thread(){
			
			public void run(){
				graphDB.shutdown();
			}
		});
	}
	
	/**
	 * @author Wensheng et Solene
	 * @param wfBrique
	 * @return idWfBriqueNode
	 */
	@Override
	public Object saveBriqueSIWf(BriqueSIWf wfBrique) {
		// TODO Auto-generated method stub
		this.openConnectionBase();
		Transaction tx = graphDB.beginTx();
		
		long idWfBriqueNode = -1;
		
		try{
			
			if (this.graphDB!=null){
				
				//Premi�re �tape : Cr�ation du noeud BriqueSIWF en base
				Node wfBriqueNode = this.graphDB.createNode();
				wfBriqueNode.setProperty("description", wfBrique.getDescription());
				
				//Deuxi�me �tape : Cr�ation du REQUEST
				
				//Recup�ration du demandeur
				DataMDM demandeur = wfBrique.getAsker();
				long idAsker =Long.parseLong(demandeur.getIdentifiant().toString());
				Node asker = this.graphDB.getNodeById(idAsker);
				//Cr�ation du REQUEST
				asker.createRelationshipTo(wfBriqueNode, RelationWfTypes.REQUEST );
				
				//Recup�ration de l'id :
				idWfBriqueNode = wfBriqueNode.getId();
				
				
			}
			
				tx.success();
			}
			finally{tx.finish();}
			this.closeConnection();
		
		return (Object)idWfBriqueNode;
	}

}
