package facade;

//import java.sql.Savepoint;
import java.util.ArrayList;
//import java.util.Collection;
import dao.factory.DAOFactory;
import dao.factory.Neo4jDAOFactory;
import dao.model.DataMDMDAO;
import dao.model.DataValueDAO;
import dao.model.ModelMDMDAO;
import dao.model.ModelValueDAO;
import dao.model.NatureDAO;
import model.*;

/**
 * La facade de CRUDFacade met en place des fonctions simples qui abstraient au
 * maximum la cr�ation(create),lecture (read), la mise � jour(update), la
 * suppression(delete). 
 * 
 * 
 * @author : cyril groupe CRUD.
 */

public class CRUDFacade {

	/*----------Variables----------*/

	/*----------Constructeur----------*/

	/**
	 * Constructeur de la classe
	 * 
	 * @author Gaetan
	 */
	public CRUDFacade() {
	}

	/**
	 * Permet de fournir une collection des ModelMDM pr�sentents dans la base de donn�es;
	 * On demande a ModelMDMDAO la liste des identifiants des Models pr�sent dans la base
	 * puis on recherche pour chaque id le model qui correspond on retourne ainsi la
	 * collection de ModelMDM pr�sent dans la base de donnees
	 *
	 * @return collectionModelMDM
	 * @author cyril & edwin 
	 */
	public static ArrayList<ModelMDM> readCollectionModelMDM(){
		
		//on d�clare notre liste de modelMDM
		ArrayList<ModelMDM> collectionModelMDM=new ArrayList<ModelMDM>();
		DAOFactory df = new Neo4jDAOFactory();
		ModelMDMDAO m= df.createModelMDMDAO();
		ArrayList<Object>lesId=m.getAllModelMDMId();
		for (int i=0;i<=lesId.size();i++)
		{
			collectionModelMDM.add(m.load(lesId.get(i)));
		}
		return collectionModelMDM;
	}

	/**
	 * Permet de fournir la liste des mod�les fils(r�cursivement) par rapport au mod�le p�re fournit en param�tre
	 * On demande a ModelMDMDAO la liste des identifiants des Models pr�sent dans la base
	 * dont le model Pere est sp�cifi� en parametre puis on recherche pour chaque id le model qui correspond on retourne ainsi la
	 * collection de ModelMDM pr�sent dans la base de donnees
	 * 
	 * @param modelePere 
	 * 				correspond au mod�le p�re de la liste des mod�les fils que l'on cherche
	 * @return  collectionModelMDMfils ; NULL 
	 * @author edwin & cyril 
	 */
	public static ArrayList<ModelMDM> readCollectionModelMDM(ModelMDM modelePere){
		ArrayList<ModelMDM> collectionModelMDM=new ArrayList<ModelMDM>();
		DAOFactory df = new Neo4jDAOFactory();
		ModelMDMDAO m= df.createModelMDMDAO();
		ArrayList<Object>lesId=m.getAllModelMDMFilsId();
		if (collectionModelMDM.size()==0)
			return null;
		else 
			for (int i=0;i<=lesId.size();i++)
			{
				collectionModelMDM.add(m.load(lesId.get(i)));
				}
		return collectionModelMDM;
	}
	
	
/************************************************************************************************************/	
	
	/**
	 * Permet de retourner la liste des rattachements pour mod�le sp�cifique fournit en param�tre
	 * 
	 * 
	 * @param modele
	 * @return  CollectionRattachementModelMDM ; null
	 * 
	 */
	/*
	 public static ArrayList<RattachementModelMDM> readCollectionRattachementModelMDM(ModelMDM modele){
	ArrayList<RattachementModelMDM> listeRattachementModelMDM=new ArrayList<RattachementModelMDM>();
	DAOFactory df = new Neo4jDAOFactory();
	ModelMDMDAO m= df.createModelMDMDAO();
	//recuperer les rattachements
	return listeRattachementModelMDM;
	}
	*/
/************************************************************************************************************/

	/**
	 * Permet de retourner une collection des models values pr�sents dans la base de donn�es.
	 * On demande a ModelValueDAO la liste des identifiants des Models pr�sent dans la base
	 * puis on recherche pour chaque id le model qui correspond on retourne ainsi la
	 * collection de ModelValue pr�sent dans la base de donnees
	 *
	 * @param modele
	 * 			correspond au modelMDM qui est notre objet de r�f�rence poss�dant un ou plusieurs modelValue.
	 * @return  collectionModelValue
	 * @author cyril & edwin
	 */
	public static ArrayList<ModelValue> readCollectionModelValue(ModelMDM modele){
		ArrayList<ModelValue> collectionModelValue=new ArrayList<ModelValue>();
		DAOFactory df = new Neo4jDAOFactory();
		ModelValueDAO m= df.createModelValueDAO();
		ArrayList<Object>lesId=m.getAllModelValueId();
		for (int i=0;i<=lesId.size();i++)
		{
			collectionModelValue.add(m.load(lesId.get(i)));
		}
		return collectionModelValue;
	}

	/**
	 * permet de fournir la collection des ModelValue ayant un socle (pr�sent dans la base de donn�es).
	 * On demande a ModelValueDAO la liste des identifiants des Models pr�sent dans la base
	 * dont le socle est pass� en parametre puis on recherche pour chaque id le model qui correspond 
	 * on retourne ainsi la collection de ModelValue pr�sent dans la base de donnees
	 * 
	 * @param modele
	 * 					correspond aux modelMDM qui poss�dera un ou plusieurs modelValue
	 * @param filtreSocle
	 * 					correspond � un type de socle que poss�derais notre modelValue
	 * @return collectionModelValue ou null dans le cas ou un des deux param�tres n'existe pas.
	 * @author cyril & clementF & edwin
	 *
	 */
	public static ArrayList<ModelValue> readCollectionModelValue(ModelMDM modele, String filtreSocle){
		ArrayList<ModelValue> collectionModelValue=new ArrayList<ModelValue>();
		DAOFactory df = new Neo4jDAOFactory();
		ModelValueDAO m= df.createModelValueDAO();
		
		if ((modele.getIdentifiant()!= null))  //&&(filtreSocle.getIdentifiant != NULL)
		{
			ArrayList<Object>lesId=m.getAllModelValueId(filtreSocle);
			for (int i=0;i<=lesId.size();i++)
			{
				collectionModelValue.add(m.load(lesId.get(i)));
			}
			return collectionModelValue;
		}
		else{
			return null;
		}
	}
	
	/**
	 * permet de fournir la collection des dataMDM pr�sents dans la base de donn�es. 
	 * On demande a dataMDMDAO la liste des identifiants des data pr�sent dans la base
	 * puis on recherche pour chaque id le data qui correspond on retourne ainsi la
	 * collection de dataMDM pr�sent dans la base de donnees
	 * 
	 * @param modele
	 * 			correspond au modelMDM ayant potentiellement des dataMDM il se peux qu'il en ai aucun
	 * @return collectionDataMDM ou null si le modelMDM fournit en param�tre n'existe pas.
	 * @author Cyril & edwin & clementF  
	 */	  
	public static ArrayList<DataMDM> readCollectionDataMDM(ModelMDM modele){
		ArrayList<DataMDM> collectionDataMDM=new ArrayList<DataMDM>();
		DAOFactory df = new Neo4jDAOFactory();
		DataMDMDAO d= df.createDataMDMDAO();
		
		if (modele.getIdentifiant() != null){
			ArrayList<Object>lesId=d.getAllDataMDMId();
			for (int i=0;i<=lesId.size();i++)
			{
				collectionDataMDM.add(d.load(lesId.get(i)));
			}
			return collectionDataMDM;
		}	
		else
		{
			return null; 
		}
	}

	/**
	 * permet de fournir l'ensemble des dataMDM filles d'une dataMDM donn�e. 
	 * On demande a dataMDMDAO la liste des identifiants des data pr�sent dans la base
	 * dont le modelepere est pass� en parametre puis on recherche pour chaque id le data qui correspond 
	 * on retourne ainsi la collection de dataMDM pr�sent dans la base de donnees
	 * 
	 * @param donneePere correspond � la DataMDM qui sera p�re des dataFille recherch�
	 * @return collectionDataMDM ou null si la dataMDM fournit en param�tre n'existe pas dans la BD.
	 * @author Cyril & edwin & clementF
	 */
	public static ArrayList<DataMDM> readCollectionDataMDM(DataMDM donneePere){
		ArrayList<DataMDM> collectionDataMDM=new ArrayList<DataMDM>();
		DAOFactory df = new Neo4jDAOFactory();
		DataMDMDAO d= df.createDataMDMDAO();
		if (donneePere.getIdentifiant() != null){
			ArrayList<Object>lesId=d.getAllDataMDMFilsId();
			for (int i=0;i<=lesId.size();i++)
			{
				collectionDataMDM.add(d.load(lesId.get(i)));
				readCollectionDataMDM(d.load(lesId.get(i)));
			}
			return collectionDataMDM;	
		}
		else {
			return null;
		}
	}

/********************************************************************************************************************/
	/**
	 * 
	 * @param 
	 * @return  Retourne la collection des rattachements pour une donn�e sp�cifique. Si la donn�e n�existe pas,
	 
	public static Collection<null> readCollectionRattachementDataMDM(DataMDM donn�e){
	}*/
/********************************************************************************************************************/
	
	
	/**
	 * Permet de fournir l'ensemble des dataValue d'une dataMDM donnee, si celle si existe dans la base de donn�es.
	 * On demande a dataValueDAO la liste des identifiants des data pr�sent dans la base
	 * puis on recherche pour chaque id le data qui correspond on retourne ainsi la
	 * collection de dataValue pr�sent dans la base de donnees
	 * 
	 * @param donnee
	 * 			correspond � 
	 * @return collectionDataValue ou null si 
	 * @author Cyril & clementF & edwin 
	 */
	public static ArrayList<DataValue> readCollectionDataValue(DataMDM donnee){
		ArrayList<DataValue> collectionDataValue=new ArrayList<DataValue>();
		DAOFactory df = new Neo4jDAOFactory();
		DataValueDAO d= df.createDataValueDAO();
		if(donnee.getIdentifiant()!=null) {
			ArrayList<Object>lesId=d.getAllDataValueId();
			for (int i=0;i<=lesId.size();i++)
			{
				collectionDataValue.add(d.load(lesId.get(i)));
			}
			return collectionDataValue;
		}
		else {
			return null;
		}
	}

	/**
	 * Permet de fournir la  dataValue d'une dataMDM donn�e qui correspond au modelValue donn�.
	 * 
	 * 
	 * @param dataMDM
	 * @param modelValue
	 * @return valeur,null
	 * @author Cyril & clementF & Edwin
	 * 
	 * Retourne la valeur d�attribut pour la donn�e et l�attribut indiqu�s.
	 * Si la donn�e ou l�attribut n�existe pas, le retour est null. Modification d��l�ments de la base (update)
	 */
	public static DataValue readDataValue(DataMDM dataMDM,ModelValue modelValue){
		// /!\ PAS OPTIMISE
		if 	( (dataMDM.getIdentifiant() != null ) && (modelValue.getIdentifiant() != null) ){
			
			int i =0;
			DAOFactory df = new Neo4jDAOFactory();
			DataValueDAO d= df.createDataValueDAO();
			
			ArrayList<DataValue> collectionDataValue=new ArrayList<DataValue>();
			ArrayList<Object>lesId=d.getAllDataValueId();
			
			for (int j=0;j<=lesId.size();j++)
			{
				collectionDataValue.add(d.load(lesId.get(j)));
			}
			
			while ((collectionDataValue.get(i).getDataMdm()!=dataMDM && collectionDataValue.get(i).getModelValue()!=modelValue)) {
				i++;
			}
			return collectionDataValue.get(i);
		}
		else {
			return null;
		}
	}


	/**
	 * Modifie la hierarchie entre le mod�le fils et l'ancien mod�le p�re
	 * 
	 * 
	 * @param  ModelMDM modeleFils
	 * @param  ModelMDM nouveauPere
	 * 
	 * @return  Retourne une instance du ModelMDM fils mis � jour si les modifications ont �t� enregistr�es en base, null sinon.
	 * @author Cyril & Edwin & Cl�ment A.
	 */
	public static ModelMDM updateHierarchieModelMDM(ModelMDM modeleFils,ModelMDM nouveauPere){
		modeleFils.setModelPere(nouveauPere);
		DAOFactory df = new Neo4jDAOFactory();
		ModelMDMDAO m= df.createModelMDMDAO();
		m.update(modeleFils);
		return modeleFils;
	}

	/**
	 * Modifie et met � jour le socle du ModelValue
	 * 
	 * @param modelValue
	 * @param nouveauSocle
	 * 
	 * @return Retourne une instance du ModelValue mis � jour si les modifications ont �t� enregistr�es en base, null sinon.
	 * @author Cyril & Edwin & Cl�ment A.
	 */
	public static ModelValue updateSocleModelValue(ModelValue modelValue, String nouveauSocle)
	{
		modelValue.setSocle(nouveauSocle);
		DAOFactory df = new Neo4jDAOFactory();
		ModelValueDAO d= df.createModelValueDAO();
		d.update(modelValue);
		return modelValue;
	}

	/**
	 * Modifie et met � jour la hierarchie entre le mod�le fils et le nouveau mod�le p�re
	 * 
	 * @param dataFils
	 * @param nouveauPere
	 * @return Retourne une instance du DataMDM fils mis � jour si les modifications ont �t� enregistr�es en base, null sinon.
	 * @author Cyril & Edwin & Cl�ment A.
	 */
	public static DataMDM updateHierarchieDataMDM(DataMDM dataFils,DataMDM nouveauPere){
		dataFils.setDataPere(nouveauPere);
		DAOFactory df = new Neo4jDAOFactory();
		DataMDMDAO d= df.createDataMDMDAO();
		d.update(dataFils);
		return dataFils;
		
	}
	

	/**
	 * Modifie et met � jour l'instance du DataValue concern�e
	 * 
	 * @param dataMDM
	 * @param modelValue
	 * @param value 
	 * @return  Retourne une instance du DataValue mis � jour si les modifications ont �t� enregistr�es en base, null sinon. Suppression d��l�ments de la base (delete)
	 * @author Cyril & Edwin & Cl�ment A.
	 * 
	 *
	 */
	public static DataValue updateDataValue(DataMDM dataMDM,ModelValue modelValue, String value)
	{
		// /!\ PAS OPTIMISE
		int i =0;
		DAOFactory df = new Neo4jDAOFactory();
		DataValueDAO d= df.createDataValueDAO();
		
		ArrayList<DataValue> collectionDataValue=new ArrayList<DataValue>();
		ArrayList<Object>lesId=d.getAllDataValueId();
		
		for (int j=0;j<=lesId.size();j++)
		{
			collectionDataValue.add(d.load(lesId.get(j)));
		}
		
		while ((collectionDataValue.get(i).getDataMdm()!=dataMDM && collectionDataValue.get(i).getModelValue()!=modelValue)) {
			i++;
		}
		 collectionDataValue.get(i).setValue(value);
		 d.update(collectionDataValue.get(i));
		return collectionDataValue.get(i);
	}

	
	/*************************************************************************************************************************/
	/**
	 * V�rifie et contr�le la bon d�roulement de la suppression du rattachement entre deux ModelMDM
	 * 
	 * @param mod�leRattach�
	 * @param aUnModele
	 * @return Vrai si la suppression du rattachement s�est d�roul�e correctement, faux sinon.
	 * @author Cyril & Edwin & Cl�ment A.
	 */
	/*public static boolean deleteRattachementModelMDM(ModelMDM mod�leRattach�,ModelMDM aUnModele){
		boolean bool = false;
		return bool;
	}
*/
/*************************************************************************************************************************/
	
	/**
	 *
	 * Supprime le modelValue dans la base de donn�es.
	 * La suppression d�un ModelValue entra�ne la suppression de tous les DataValue qui y sont associ�s
	 * 
	 * @param modelValue
	 * @return bool 
	 * @author Cyril & Edwin & Cl�ment A.
	 * */
	public static Boolean deleteModelValue(ModelValue modelValue)
	{
		DAOFactory df = new Neo4jDAOFactory();
		ModelValueDAO d= df.createModelValueDAO();
		return d.delete(modelValue);
	}

	/**
	 * Proc�dure visant � supprimer un rattachement entre deux dataMDM
	 * 
	 * @param donneeRattachee
	 * @param aUneDonne
	 * @return Vrai si le lien de rattachement a �t� supprim� dans la base de donn�e, faux sinon
	 * @author Cyril & Edwin & Cl�ment A.
	 */
	/*public static boolean deleteRattachementDataMDM(DataMDM donneeRattachee,DataMDM aUneDonnee){
		boolean bool = false;
		return bool;
	}
*/
	/**
	 * Permet de supprimer une dataValue dans la base de donn�es.
	 * 
	 * 
	 * @param dataMDMAssocie
	 * @param modelValueAssocie
	 * @return Vrai si l'instance du DataValue a �t� supprim�e dans la base de donn�e, faux sinon
	 * @author Cyril & Edwin & Cl�ment A.
	 */
	public static boolean  deleteDataValue(DataMDM dataMDMAssocie,ModelValue modelValueAssocie){
		boolean bool = false;
		
		return bool;
	}
	


	
	/*----------Getters and Setters----------*/

	
	/*----------Methodes CREATE-----------*/
	
	
	/**
	 * Permet la creation d'un nouvel ModelMDM dans la base de donn�e
	 * 
	 * @param nature 
	 * @param label
	 * @param description
	 * @return Retourne l�instance du nouveau ModelMDM s�il a pu �tre enregistre en base, null sinon.
	 * @author Cyril & Edwin & Cl�ment A.
	 */
	public static ModelMDM createModelMDM(Nature nature, String label, String description) {

		ModelMDM modelMDM=new ModelMDM(nature,label,description);
		DAOFactory df = new Neo4jDAOFactory();
		ModelMDMDAO m= df.createModelMDMDAO();
		modelMDM.setIdentifiant(m.save(modelMDM));
		return modelMDM;
	}

	/**
	 * Permet la creation d'un nouvel ModelMDM fils dans la base de donn�e 
	 * Il n�y a pas de nature en param�tre, car s�il y a un p�re il s�agit d�une structure.
	 * 
	 * 
	 * @param modelePere
	 * @param label
	 * @param description
	 * @return Retourne l�instance du nouveau ModelMDM s�il a pu �tre enregistre en base, null sinon. 
	 * @author Cyril & Edwin & Cl�ment A.
	 */
	public static ModelMDM createModelMDM(ModelMDM modelePere, String label, String description) {
		ModelMDM modelMDM=new ModelMDM(modelePere.getNature(),label,description);
		DAOFactory df = new Neo4jDAOFactory();
		ModelMDMDAO m= df.createModelMDMDAO();//Hierarchical
		modelMDM.setIdentifiant(m.save(modelMDM));
		return modelMDM;
	}

	/**
	 * Permet la supression d'une instance d'un ModelMDM dans la base de donn�e
	 * 
	 * @param modelMDM
	 * @return Retourne vrai si l�instance du ModelMDM a �t� supprim�e en base, faux sinon
	 * @author Cyril & Edwin & Cl�ment A.
	 */
	public static Boolean deleteModelMDM(ModelMDM modelMDM)
	{
		DAOFactory df = new Neo4jDAOFactory();
		ModelMDMDAO m= df.createModelMDMDAO();
		return m.delete(modelMDM);
	}
	
	/**
	 * Permet d'ajouter un RattachementModelMDM entre deux modelMDM
	 * 
	 * @param mod�leAyantUnRattachement
	 * @param aUnModele
	 * @return Retourne vrai si le rattachement a ete effectue en base de donnees, faux sinon
	 * @author Cyril & Edwin & Cl�ment A.
	 */
	public static Boolean createRattachementModelMDM(
			ModelMDM mod�leAyantUnRattachement, ModelMDM aUnModel) {
		return true;
	}

	/**
	 * Permet la cr�ation d'un nouveau ModelValue dans la base de donn�e
	 * Pour cela il faut sp�cifier le ModelMDM associ� � ce ModelValue ainsi que son socle d'appartenance
	 * Type sera le type de l�attribut.
	 * 
	 * @param typeAttribut
	 * @param modeleAssocie
	 * @param leSocle
	 * @param description
	 * @param valeurParDefaut
	 * @param label le label
	 * @return Retourne l�instance du nouveau ModelValue s�il a pu �tre enregistr� en base, null sinon
	 * @author Cyril & Edwin & Cl�ment A.
	 */
	public static ModelValue createModelValue(String typeAttribut, ModelMDM modeleAssocie, String leSocle, String description, String valeurParDefaut, String label) {
		
		ModelValue modelValue=new ModelValue(typeAttribut,description,modeleAssocie,valeurParDefaut,label,leSocle);//hierarchical
		DAOFactory df = new Neo4jDAOFactory();
		ModelValueDAO d= df.createModelValueDAO();
		modelValue.setIdentifiant(d.save(modelValue));
		return modelValue;
	}
	
	
	
	/**
	 * Permet la cr�ation d'une instance de DataMDM dans la base de donn�e
	 * Pour cel� il faut sp�cifier en param�tre le modeleMDM associ� � cette DataMDM
	 * 
	 * @param modeleAssocie
	 * @param label
	 * @return Retourne l�instance du nouveau DataMDM s�il a pu �tre enregistr� en base, null sinon
	 * @author Cyril & Edwin & Cl�ment A.
	 */
	public static DataMDM createDataMDM(ModelMDM modeleAssocie, String label) {
		DataMDM dataMDM=new DataMDM(modeleAssocie,label);//hierarchical
		DAOFactory df = new Neo4jDAOFactory();
		DataMDMDAO d= df.createDataMDMDAO();
		dataMDM.setIdentifiant(d.save(dataMDM));
		return dataMDM;
	}

	/**
	 * Permet la cr�ation d'une instance de DataMDM fils dans la base de donn�e
	 * Pour cel� il faut sp�cifier en param�tre le modeleMDM associ� � cette DataMDM ainsi que son DataMDM p�re
	 * De plus, dataMDM sert � constituer une hi�rarchie entre les donn�es, uniquement pour une nature le permettant (comme Structure).
	 * 
	 * @param modeleAssocie
	 * @param dataMDMPere
	 * @param label
	 * @return Retourne l�instance du nouveau DataMDM s�il a pu �tre enregistr� en base, null sinon
	 * @author Cyril & Edwin & Cl�ment A.
	 */
	public static DataMDM createDataMDM(ModelMDM modeleAssocie, DataMDM dataMDMPere, String label) {
		DataMDM dataMDM=new DataMDM(modeleAssocie,label);//hierarchical
		DAOFactory df = new Neo4jDAOFactory();
		DataMDMDAO d= df.createDataMDMDAO();
		dataMDM.setIdentifiant(d.save(dataMDM));
		return dataMDM;
	}
	
	
	/**
	 * Permet de supprimer une dataMDM dans la base de donn�es.
	 * 
	 * @param dataMDM
	 * @return Vrai si l'instance du DataMDM a �t� supprim�e dans la base de donn�e, faux sinon
	 * @author Cyril & Edwin & Cl�ment A.
	 */
	public static Boolean deleteDataMDM(DataMDM dataMDM)
	{
		DAOFactory df = new Neo4jDAOFactory();
		DataMDMDAO d= df.createDataMDMDAO();
		
		return d.delete(dataMDM);//voir la hierarchie
	}

	/**
	 * Permet de cr�er un lien de rattachement entre deux DataMDM 
	 * 
	 * @param donneeAyantUnRattachement
	 * @param aUneDonne
	 * @return Retourne vrai si l�instance du nouveau RattachementDataMDM s�il a pu �tre enregistr� en base, faux sinon
	 * @author Cyril & Edwin & Cl�ment A.
	 */
	public static Boolean createRattachementDataMDM(DataMDM donneeAyantUnRattachement, DataMDM aUneDonnee) {
		return true;
	}

	/**
	 * Permet la cr�ation d'un DataValue dans la base de donn�e
	 * 
	 * @param dataMDMAssocie
	 * @param modeleValueAssocie
	 * @param valeur
	 * @return Retourne l�instance du nouveau DataValue s�il a pu �tre enregistr� en base, null sinon
	 * @author Cyril & Edwin & Cl�ment A.
	 */
	public static DataValue createDataValue(DataMDM dataMDMAssocie, ModelValue modelValueAssocie, String valeur) {
		DataValue dataValue=new DataValue(dataMDMAssocie,modelValueAssocie,valeur);
		DAOFactory df = new Neo4jDAOFactory();
		DataValueDAO d= df.createDataValueDAO();
		dataValue.setIdentifiant(d.save(dataValue));
		return dataValue;
	}
	
	/**
	 * Permet la verification et le contr�le du bon d�rloulement de la suppression d'une dataValue dans la base de donn�e
	 * 
	 * @param dataValue
	 * @return Vrai si le DataValue a pu �tre supprimer de la base, faux sinon
	 * @author Cyril & Edwin & Cl�ment A.
	 */
	public static Boolean deleteDataValue(DataValue dataValue)
	{
		DAOFactory df = new Neo4jDAOFactory();
		DataValueDAO d= df.createDataValueDAO();
		return d.delete(dataValue);

	}
	
	
	

	/**
	 * Permet de demander la cr�ation d'une brique SI, l'objet de retour sera un ModelMDM
	 * 
	 * @param description
	 * @param label
	 * @param connecteurId
	 * @return Retourne l'instance de la brique SI cr�ee
	 * @author Cyril & Edwin & Cl�ment A.
	 */
	public static ModelMDM createBriqueSI(String description, String label, Object connecteurId) {

		ModelMDM brique = new ModelMDM(loadNature("Brique"), label, description);
		DAOFactory df = new Neo4jDAOFactory();
		ModelMDMDAO m= df.createModelMDMDAO();
		brique.setIdentifiant(m.save(brique));
		return brique;

	}

	/**
	 * Permet de charger un ModelMDM quelconque depuis la base de donn�es la fonction 
	 * 
	 * @param id : l'identifiant du modelmdm dans la base de donnees
	 * @return Retourne le ModelMDM manipul�
	 * @author Cyril & Edwin & Cl�ment A.
	 */
	public static  ModelMDM loadModelMDM(Object id) {
		DAOFactory df = new Neo4jDAOFactory();
		ModelMDMDAO m= df.createModelMDMDAO();
		ModelMDM modelMDM= m.load(id);
		return modelMDM;
	}

	/**
	 * Permet de charger un DataMDM quelconque depuis la base de donn�es la fonction
	 * 
	 * @param id : l'identifiant de l'object dans la base de donnees
	 * @return Retourne le DataMDM manipul�
	 * @author Cyril & Edwin & Cl�ment A.
	 */
	public static DataMDM loadDataMDM(Object id) {
		DAOFactory df = new Neo4jDAOFactory();
		DataMDMDAO d= df.createDataMDMDAO();
		DataMDM dataMDM= d.load(id);
		return dataMDM;
	}
	
	/**
	 * Permet de creer une nature a partir d'un String pass� en parametre
	 * 
	 * @param String label
	 * @return nature
	 * @author Gaetan
	 */
	public static Nature createNature(String label){
		Nature nature = new Nature(label);
		DAOFactory df = new Neo4jDAOFactory();
		NatureDAO n= df.createNatureDAO();
		nature.setIdentifiant(n.save(nature));
		return nature;
	}
	
	/**
	 * Permet de recuperer une nature a partir d'un identifiant pass� en parametre
	 * 
	 * @param String label
	 * @return nature
	 * @author Gaetan
	 */
	
	public static Nature loadNature(Object id){
		DAOFactory df = new Neo4jDAOFactory();
		NatureDAO n= df.createNatureDAO();
		Nature nature= n.load(id);
		return nature;
	}
	
	/**
	 * Permet de recuperer une nature a partir d'un String pass� en parametre
	 * 
	 * @param String label
	 * @return nature
	 * @author Gaetan
	 */
	public static Nature loadsNature(String type){
		DAOFactory df = new Neo4jDAOFactory();
		NatureDAO n= df.createNatureDAO();
		Nature nature= n.load(type);
		return nature;
	}
	
	

}