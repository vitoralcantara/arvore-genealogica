package test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import model.BriqueSIWf;
import model.DataMDM;
import model.Nature;
import model.Wf;

import org.junit.Test;

import facade.CRUDFacade;
import facade.WfFacade;


public class WfFacadeTest 
{
	 @Test
	 public void testValidateWorkflow() throws IOException 
	 {
		 //chargement de la nature brique
		 Nature natureBrique = CRUDFacade.loadNature("Brique");
		 //obtention du tuteur de brique
		 DataMDM tuteur = natureBrique.getTuteur();
		 //cr�ation d'un workflow de brique SI
		 Wf testWf = new BriqueSIWf(null, natureBrique, new DataMDM("demandeur"), "description", null, null, "type");
		 //validation du workflow selon le tuteur de la nature
		 WfFacade.validateWorkflow(testWf, true, tuteur);
		 //v�rification du fait que la validationTuteur soit �gale � "Accepte"
		 assertEquals("Accepte", testWf.getValidateTuteur().getState());
	 }
}
