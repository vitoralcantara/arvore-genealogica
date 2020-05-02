package dao.model;

import model.JDBC;

public abstract class JDBCDAO
{
	/****************************************************************
	 *                    Membres de la classe                      *
	 ****************************************************************/
	
	
	/****************************************************************
	 *                    M�thodes de la classe                     *
	 ****************************************************************/

	/**
	 * Sauvegarde le JDBC pass� en param�tre
	 * 
	 * @param _jdbc le JDBC � sauvegarder
	 * @return Object
	 */
	public abstract Object saveJDBC(JDBC _jdbc);
	
	
	/****************************************************************
	 *                    Accesseurs de la classe                   *
	 ****************************************************************/
	
}
