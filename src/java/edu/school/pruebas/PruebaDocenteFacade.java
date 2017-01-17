package edu.school.pruebas;

import edu.school.ejb.DocenteFacade;
import edu.school.entities.Docente;

public class PruebaDocenteFacade {
    public static void main(String[] args) {
        DocenteFacade docenteFacade = new DocenteFacade();
        Docente docente = docenteFacade.findByCi(4567891);
        
        System.out.println("encontrado " + docente.getDatosPersonaId().getApellido() 
               + " " + docente.getDatosPersonaId().getNombre());
    }
}
