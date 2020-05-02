import org.neo4j.*;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;


public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		GraphDatabaseService gDB = new GraphDatabaseFactory().newEmbeddedDatabase("C:\\Neo4j\\data\\graph.db");
		Object identifiant;
		
		identifiant = (int)3;
		
		String maRequete = "start n=node("+identifiant+") match (n)-[r]->() return r;";
		System.out.println(maRequete);
		ExecutionEngine engine = new ExecutionEngine(gDB);
		//Execution de la requ�te sur la base de donn�es et r�cup�ration des r�sultats
		ExecutionResult result = engine.execute(maRequete);
	
	}

}
