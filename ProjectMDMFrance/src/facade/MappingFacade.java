package facade;

import java.util.List;
import java.util.Map;

import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.cypher.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import model.Connector;
import model.DataMDM;
import model.DataValue;
import model.JDBC;
import model.ModelMDM;
import model.ModelValue;
import model.Nature;
import model.WebService;
import facade.CRUDFacade;


/**
 * La facade de Mapping met en place des fonctions simples qui abstraient au
 * maximum la repr�sentation d'un connecteur, du traitement du mapping et des
 * op�rations �ffectu�es.
 * 
 * @author GroupeMapping
 */
public class MappingFacade
{	
	/****************************************************************
	 *                             JDBC                             *
	 ****************************************************************/
	/**
	 * Cree un connecteur JDBC
	 * @author Groupe mapping
	 * @param _login le login utilisateur d'acc�s � la base de donn�es
	 * @param _password le psswd utilisateur d'acc�s � la base de donn�es
	 * @param _host l'adresse de la base de donn�es
	 * @param _port le port de la base de donn�es
	 * @param _typeDB le type de base de donn�es
	 * @param _database la database concern�e
	 * @return un connecteur JDBC
	 */
	public static JDBC createJDBC(String _login, String _password,
			String _host, int _port, String _typeDB, String _database)
	{
		return new JDBC(_login, _password, _host, _port, _typeDB, _database);
	}
	
	
	/**
	 * Retourne un dictionnaire d�taillant le sch�ma de la database avec en cl�
	 * les tables et en valeur les listes des colonnes.
	 * @author Groupe mapping
	 * @param _login le login utilisateur d'acc�s � la base de donn�es
	 * @param _password le psswd utilisateur d'acc�s � la base de donn�es
	 * @param _host l'adresse de la base de donn�es
	 * @param _port le port de la base de donn�es
	 * @param _typeDB le type de base de donn�es
	 * @param _database la database concern�e
	 * 
	 * @return Map<ModelMDM, List<ModelValue>>
	 */
	public static Map<ModelMDM, List<ModelValue>> getDatabaseSchema(
			String _login, String _password, String _host, int _port,
			String _typeDB, String _database)
	{
		return new JDBC(_login, _password, _host, _port, _typeDB, _database)
				.getDatabaseSchema();
	}
	

	/****************************************************************
	 *                          WebService                          *
	 ****************************************************************/
	
	/**
	 * Rrtourne un WebService
	 * @author Groupe mapping
	 * @param _login le login utilisateur d'acc�s � la base de donn�es
	 * @param _password le psswd utilisateur d'acc�s � la base de donn�es
	 * @param _url l'url associ�e au WebService
	 * @param _protocol le protocole associ� au WebService
	 * @return WebService
	 */
	public static WebService createWebService(String _login, String _password,
			String _url, String _protocol)
	{
		return new WebService(_login, _password, _url, _protocol);
	}
	
	
	/****************************************************************
	 *                          BriqueSI                            *
	 ****************************************************************/
	
	
	/**
	 * S'occupe de l'envoi de la requ�te de cr�ation d'une briqueSI
	 * @author Groupe mapping
	 * @param _login le login utilisateur d'acc�s � la base de donn�es
	 * @param _password le psswd utilisateur d'acc�s � la base de donn�es
	 * @param _host l'adresse de la base de donn�es
	 * @param _port le port de la base de donn�es
	 * @param _typeDB le type de base de donn�es
	 * @param _database la database concern�e
	 * @param _schema le schema associ�
	 * @param _requester l'origine de la demande
	 * @param _description la description associ�e � la demande
	 */
	public static void requestCreationBrick(String _login, String _password,
			String _host, int _port, String _typeDB, String _database,
			Map<ModelMDM, List<ModelValue>> _schema, DataMDM _requester,
			String _description)
	{
		// On cr�e le connecteur associ� � la demande
		// d'ajout de brique
		Connector connector = new JDBC(_login, _password, _host, _port,
				_typeDB, _database);

		// On r�cup�re la nature pour les briques SI
		Nature n = CRUDFacade.loadNature("Brique");

		// On transf�re la demande au Workflow qui va s'occuper
		// de demander les validations aux personnes concern�es.
		WfFacade.requestCreationBrique(n, _requester, _description, connector,
				_schema);
	}

	/**
	 * Cr�e la Brique SI
	 * @author Groupe mapping
	 * @param _nature 
	 * @param _connector
	 * @param _schema
	 * @param _requester l'origine de la demande
	 * @param _description la description associ�e
	 */
	public static void createBrick(Nature _nature, Connector _connector,
			Map<ModelMDM, List<ModelValue>> _schema, DataMDM _requester,
			String _description)
	{
		//CRUDFacade.createBriqueSI(description, label, connecteurId);
	}
	
	
	/****************************************************************
	 *                          Mapping S                           *
	 ****************************************************************/
	
	/**
	 * Verification n�c�ssaire avant le mapping structure entre deux Model
	 * Values on appel ensuite le Workflow afin qu'il effetue les demandes de
	 * validation
	 * 
	 * @param _brickvalue le ModelValue appartenant � la brique
	 * @param _mdmvalue le modelvalue utilis� afin de fiabiliser le brickvalue
	 * @author GroupMapping
	 */
	public static void requestStructureMapping(ModelValue _brickValue,
			ModelValue _mdmValue)
	{
		// on v�rifie la compatibilit� entre les deux types
		// avant d'aller plus loin
		if (_brickValue.getTypeAtt().equals(_mdmValue.getTypeAtt()))
		{
			// Si on a les m�mes types, on peut envisager un mapping
			// au niveau structure, si le ModelValue de la brique
			// n'a pas d�j� �t� mapp�
			
			// Partie temporaire (fonctions qui n'existent pas)
			// mais qui devrait �ter ce que sera la version finale
			// de la fonction
			/*	if (_brickValue.getRelations() == null)
			 *	{
			 *		WFFacade.requestStructureMapping(_brickValue, _mdmValue);
			 *	}	
			 */
			
			/** TESTS
			GraphDatabaseService gDB = new GraphDatabaseFactory()
					.newEmbeddedDatabase("C:\\Users\\Litz\\Neo4j\\data\\graph.db");

			ExecutionEngine e = new ExecutionEngine(gDB, null);
			ExecutionResult r = e.execute("START n = NODE("
					+ (long) _brickValue.getIdentifiant() + ")"
					+ "match n-[r:MAPPING_S]-m" + "return m;");
			
			if (r.isEmpty())
			{}*/
		}
	}

	public static void createStructureMapping(ModelValue _brickValue,
			ModelValue _mdmValue)
	{
		// CRUDFacade.createRelation(_brickValue, _mdmValue,
		// NodesRelationship.MAPPING_S);
	}
	
	
	/****************************************************************
	 *                          Mapping D                           *
	 ****************************************************************/
	
	public static void requestDataMapping(DataValue _value1, DataValue _value2)
	{

		// demander au workflow pour l'autorisation avec une fonction du type
		// WFFacade.requestDataMapping(_Value1,_Value2);

	}
	public static void createDataMapping(DataValue _brickValue,
			DataValue _mdmValue)
	{

	}
}