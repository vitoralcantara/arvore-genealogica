package model;

/**
 * La classe Connector permet de repr�senter la base de ce qu'est un connecteur.
 * C'est une classe abstraite qui doit �tre red�finie pour correspondre � des
 * connecteurs plus sp�cifiques.
 * 
 * @author GroupeMapping
 */
public abstract class Connector
{
	/****************************************************************
	 *                    Membres de la classe                      *
	 ****************************************************************/
	
	private Object	id;
	private String	login;
	private String	password;
	
	
	/****************************************************************
	 *                    M�thodes de la classe                     *
	 ****************************************************************/

	public abstract Object saveConnector();
	
	
	/****************************************************************
	 *                    Accesseurs de la classe                   *
	 ****************************************************************/

	public Object getId()
	{
		return this.id;
	}

	public void setId(Object _id)
	{
		this.id = _id;
	}

	public String getLogin()
	{
		return this.login;
	}

	public void setLogin(String _login)
	{
		this.login = _login;
	}

	public String getPassword()
	{
		return this.password;
	}

	public void setPassword(String _password)
	{
		this.password = _password;
	}
}
