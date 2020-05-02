package dao.neo4j;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import model.DataMDM;
import model.Nature;
import model.Role;
import dao.model.NatureDAO;

public class Neo4jNatureDAO extends NatureDAO {

/*----------Constructeur-----------*/
	
	public Neo4jNatureDAO(){
		
	}

/*----------Other-----------*/

	@Override
	public Object save(Nature nature) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Nature load(Object id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Nature nature) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Boolean update(Nature nature) {
		// TODO Auto-generated method stub
		return null;
	}

	/** Ajoute un utilisateur avec le role demand�
	 * 
	 * @param user	
	 * 		Utilisateur � ajouter
	 * @param idCom
	 *            identifiant de la communaut� nature
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
			relationship = nodeData.createRelationshipTo( nodeModel, MDMRelationship );
			relationship.setProperty( "role", role.getlabel());
			t.success();
		}
		finally
		{
		    t.finish();
			gDB.shutdown();
		}
	}
	
	/** Accesseur pour r�cup�rer le responsable d'une communaut� Nature
	 * 
	 * @return le responsable de la Nature donn�e
	 * 
	 * @author Tom Kuhnen
	 */
	public DataMDM getResponsable()
	{
		return null;
	}

	/** Accesseur pour r�cup�rer le tuteur d'une communaut� Nature
	 * 
	 * @return le tuteur de la Nature donn�e
	 * 
	 * @author Tom Kuhnen
	 */
	public DataMDM getTuteur()
	{
		return null;
	}

	@Override
	public void setResponsable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTuteur() {
		// TODO Auto-generated method stub
		
	}

	/** Modifie le role de l�utilisateur dans une Communaut� Nature de la BD
	 * 
	 * @param id
	 * 		Identifiant du noeud en base
	 * 
	 * @param user
	 * 		Utilisateur dont on va modifier le role
	 *
	 * @param role
	 * 		Le role que l'on va appliquer � l'utilisateur
	 */
	public void updateRole(Object id, DataMDM user, Role role)
	{
		// Requete
	}
}
