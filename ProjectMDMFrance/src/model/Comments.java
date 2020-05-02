package model;

import java.util.List;
import java.util.Map;

public class Comments 
{
	// Variables de classe
	
	String comment ;		// Correspond au commentaire qui a �t� post�
	int timeStamp ;			// Correspond � l'heure de post du commentaire
	Wf leWf ;				// Correspond au Workflow sur lequel porte ce commentaire
	
	
	// Constructeurs
	
	public Comments(String _comment, Wf _leWf)
	{
		comment = _comment ;
		leWf = _leWf ;
	}
	
	
	// M�thodes
	
	/**
	 * Permet de modifier le commentaire.
	 * @author Bastien F. et Thomas
	 * @param _comment
	 * 			Correspond au commentaire qui sera entr�.
	 */
	public void setComment(String _comment)
	{
		this.comment = _comment ;
	}
	
	/**
	 * Permet de modifier le commentaire.
	 * @author Bastien F. et Thomas
	 * return _comment
	 * 			Commentaire renvoy�.
	 */
	public String getComment()
	{
		return(this.comment) ;
	}
	
	/**
	 * Permet de modifier le TimeStamp
	 * @author Bastien F. et Thomas
	 * @param _timeStamp
	 * 			Correspond � la nouvelle date qui sera entr�e.
	 */
	public void setTimeStamp(int _timeStamp)
	{
		this.timeStamp = _timeStamp ;
	}
	
	/**
	 * Permet de r�cup�rer le TimeStamp.
	 * @author Bastien F. et Thomas
	 * return int
	 * 			Renvoie la date � laquelle a �t� post� le commentaire.
	 */
	public int getTimeStamp()
	{
		return(this.timeStamp) ;
	}
	
	/**
	 * Permet de modifier le Workflow associ�.
	 * @author Bastien F. et Thomas
	 * @param _Wf
	 * 			Correspond au nouveau Wf entr�.
	 */
	public void setWf(Wf _Wf)
	{
		this.leWf = _Wf ;
	}
	
	/**
	 * Permet de r�cup�rer le Workflow associ�.
	 * @author Bastien F. et Thomas
	 * return Wf
	 * 			Renvoie le nouveau Wf.
	 */
	public Wf getWf()
	{
		return(this.leWf) ;
	}
}
