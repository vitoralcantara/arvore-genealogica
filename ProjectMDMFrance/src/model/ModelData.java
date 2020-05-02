package model;

import facade.CRUDFacade;
import facade.GCFacade;

public class ModelData extends Wf
{
	// Variables de la sous classe
	
	private String nomObjet ; 			// Nom de l'objet qui devra �tre cr��.
	private ModelValue modelValueCible; //le model value sur lequel porte la modification si la modification porte sur un model value
	private String newDataType ;		// Type de la donn�e dans le cas o� le Workflow porte sur une modification de ModelValue.
	//Solene : ca serait super d'avoir l'id du newDataType s'il y en a un
	
	private Object newDataValue ; 		// Nouvelle donn�e si modification d'une donn�e contenue dans un DataValue.
	private Socle theSocle ;


	
	// Constructeurs
	
	/**
	 * Constructeur de la classe, permettant de cr�er un nouveau Workflow.
	 * Cette sous-classe concernera les demandes d��critures relatives aux mod�les et aux donn�es : particuli�rement le 
	 * mapping structure ModelValue, et la cr�ation (ou modification ou suppression) d�un objet ModelMDM. 
	 * Pour le mapping structure, cela correspond ainsi aux demandes d�action sur les objets de type ModelValue.
	 * Pour la cr�ation d'un objet ModelMDM, celui-ci sera plac� en tant que fils de de l'objet pass� en param�tre : modeleAssocie.
	 * Pour la modification d'un objet ModelMDM, ce sera ce dernier qui sera directement modifi�.
	 * Il pourra �galement �tre possible avec cette m�thode d'effectuer une action sur un socle associ� � un ModelValue pour un
	 * ModelMDM donn�. 
	 * Ces actions sont essentiellement des ajouts ou des modifications.
	 * @author Bastien F. et Thomas
	 * @param nom
	 * 			Indique le nom que pourra prendre le DataValue dans le cas d'une modification du mapping structure, ou le nom de
	 * 		    l'objet ModelMDM dans le cas de l'ajout d'un nouvel objet, ou dans le cas de la modification du nom d'un de ceux d�j�
	 * 			existants.
	 * 			Si la proposition d'�criture ne porte pas sur cet attribut, le param�tre sera pass� avec la valeur NULL. 
	 * @param typeAttribut
	 * 			Indique le nouveau type d'attribut dans le cas d'une modificiation d'un type d'attribut d'une ModelValue.
	 * 			Cette valeur sera indiqu�e � NULL si la demande ne porte pas sur la modification du type d'un attribut.
	 * @param demandeur
	 * 			Indique l'utilisateur qui a demand� cette proposition d'�criture.
	 * @param modeleAssocie
	 * 			Indique l'objet de r�f�rence ModelMDM sur lequel porte cette modification.
	 * 			C'est la communaut� de cet objet qui devra prendre la d�cision quant � l'issue du Workflow.
	 * 			Dans le cas de l'ajout d'un nouveau ModelMDM, celui-ci sera ajout� comme fils de celui-ci.
	 * @param modelValue
	 * 			Indique l'attribut MDM ModelValue sur lequel porte la modification
	 * @param leSocle
	 * 			Indique le nouveau socle dans le cas de la modification du socle d'une ModelValue.
	 * 			Cette donn�e peut �tre indiqu�e � NULL dans le cas o� le Workflow ne porterait pas sur ce type de modification.
	 * @param description
	 * 			Cha�ne de caract�res d�crivant la proposition d'�criture. Celle-ci sera importante pour la prise de d�cision.
	 * @param type
	 * 			Indique le type de demande d'�criture du Workflow. Celui-ci peut prendre les valeurs "CREATE", "UPDATE" ou "DELETE".
	 */
	public ModelData (Object identifiant, String nom, String typeAttribut, DataMDM demandeur, ModelMDM modeleAssocie, ModelValue modelValue, Socle leSocle, String description, String type) 
	{
		// Appel du superConstructeur, celui associ� ici aux ModelMDM.
		super(identifiant, description, demandeur, modeleAssocie, type);
		
		// R�cup�ration des variables correspondant � cette sous-classe sp�cifiquement.
		this.modelValueCible = modelValue;
		this.nomObjet = nom ;
		this.newDataType = typeAttribut ;
		this.theSocle = leSocle ;
	}
	
	/**
	 * Constructeur de la classe, permettant de cr�er un nouveau Workflow.
	 * Cette sous-classe concernera les demandes d��critures relatives aux objets et aux donn�es : cr�ation (ou modification 
	 * ou suppression) d�un objet DataMDM, ou d'une modification correspondant au mapping donn�e DataValue. Cela correspond ainsi 
	 * respectivement aux demandes d�action sur les objets de type DataMdm ou DataValue.
	 * @author Bastien F. et Thomas
	 * @param nom
	 * 			Indique le nom que pourra prendre le DataMDM cr��, ou son nouveau nom dans le cas d'une modification de celui-ci
	 * 			pour un DataMDM d�j� existant.
	 * @param dataMDMAssocie
	 * 			Indique l'objet de r�f�rence DataMDM sur lequel porte cette modification.
	 * 			C'est la communaut� de cet objet qui devra prendre la d�cision quant � l'issue du Workflow.
	 * 			Dans le cas de l'ajout d'un nouveau DataMDM, celui-ci sera ajout� comme fils de celui-ci.
	 * @param demandeur
	 * 			Indique l'utilisateur qui a demand� cette proposition d'�criture.
	 * @param description
	 * 			Cha�ne de caract�res d�crivant la proposition d'�criture. Celle-ci sera importante pour la prise de d�cision.
	 * @param valeur
	 * 			Indique la nouvelle valeur � entrer dans une DataValue, dans le cas d'une modification d'une DataValue.
	 * @param type
	 * 			Indique le type de demande d'�criture du Workflow. Celui-ci peut prendre les valeurs "CREATE", "UPDATE" ou "DELETE".
	 */
	public ModelData (Object identifiant, String nom, DataMDM dataMDMAssocie, DataMDM demandeur, String valeur, String description, String type)
	{
		// Appel du superConstructeur, celui associ� ici aux ModelMDM.
		super(identifiant, description, demandeur, dataMDMAssocie, type);
		
		// R�cup�ration des variables correspondant � cette sous-classe sp�cifiquement.
		nomObjet = nom ;
		newDataValue = valeur ;
	}
	
	/**
	 * Permet d'executer la demande apr�s que le workflow concernant un model ou un data soit valid�
	 * @author Bastien F. et Thomas
	 */
	public void executer()
	{
	}

	
//****************Accesseurs******************************
	
	/**
	 * Permet de r�cup�rer le nom de l'objet.
	 * @author Bastien F. et Thomas
	 * @return nomObjet
	 * 		Retourne un String correspondant au nom de l'objet � ajouter.
	 */
	public String getNomObjet() {
		return nomObjet;
	}

	/**
	 * Permet de passer un nouveau nom pour cet objet.
	 * @author Bastien F. et Thomas
	 * @param nomObjet
	 * 		Nom du String correspondant au nom de l'objet � ajouter.
	 */
	public void setNomObjet(String nomObjet) {
		this.nomObjet = nomObjet;
	}

	/**
	 * Permet de r�cup�rer le nouveau type de Data (mapping structure).
	 * Ceci correspond au cas o� la demande porte sur la classe ModelValue, comportant le type d'un champs.
	 * @author Bastien F. et Thomas
	 * @return newDataType
	 * 		Retourne un String correspondant au type de l'objet � ajouter ou modifier.
	 */
	public String getNewDataType() {
		return newDataType;
	}

	/**
	 * Permet de passer un nouveau type de donn�es sur un ModelValue (mapping structure).
	 * Ceci correspond au cas o� la demande porte sur la classe ModelValue, comportant le type d'un champs.
	 * @author Bastien F. et Thomas
	 * @param newDataType
	 * 		De type String permettant de passer le type du champs.
	 */
	public void setNewDataType(String newDataType) {
		this.newDataType = newDataType;
	}

	/**
	 * Permet de r�cup�rer la valeur contenue dans une dataValue (mapping donn�es).
	 * C'est la donn�e contenue r�ellement pour un champs d'un objet DataMDM.
	 * @author Bastien F. et Thomas
	 * @return newDataValue
	 * 		Retourne un Object correspondant � la valeur de ce DataValue.
	 */
	public Object getNewDataValue() 
	{
		return newDataValue;
	}

	/**
	 * Permet de passer une nouvelle valeur de donn�es sur un ModelValue (mapping structure).
	 * Ceci correspond au cas o� la demande porte sur la classe ModelValue, comportant le type d'un champs.
	 * @author Bastien F. et Thomas
	 * @param newDataType
	 * 		De type Object permettant de passer la nouvelle valeur du champs.
	 */
	public void setNewDataValue(Object newDataValue) 
	{
		this.newDataValue = newDataValue;
	}

	/**
	 * Permet de r�cup�rer le socle demand� pour ce Workflow.
	 * @author Bastien F. et Thomas
	 * @return theSocle
	 * 		Retourne le socle concern� Workflow.
	 */
	public Socle getTheSocle() 
	{
		return theSocle;
	}

	/**
	 * Permet de passer un nouveau socle pour ce Workflow
	 * @author Bastien F. et Thomas
	 * @param theSocle
	 * 		Le socle � entrer pour cette demande d'�criture ou modification.
	 */
	public void setTheSocle(Socle theSocle) 
	{
		this.theSocle = theSocle;
	}

	/**
	 * Permet de r�cup�rer le ModelValue concern�.
	 * @author Bastien F. et Thomas
	 * @return modelValueCible
	 * 		Retourne le ModelValue concern�.
	 */
	public ModelValue getModelValueCible() {
		return modelValueCible;
	}

	/**
	 * Permet de passer un nouveau ModelValue pour ce Workflow.
	 * @author Bastien F. et Thomas
	 * @param modelValueCible
	 * 		Le ModelValue � entrer dans le Workflow.
	 */
	public void setModelValueCible(ModelValue modelValueCible) {
		this.modelValueCible = modelValueCible;
	}

	//Solene : A VOIR et a discuter
	public Object getIdSuperieur() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
