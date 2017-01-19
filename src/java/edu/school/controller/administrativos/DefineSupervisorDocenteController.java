package edu.school.controller.administrativos;

import edu.school.ejb.DatosPersonaFacadeLocal;
import edu.school.ejb.DocenteFacadeLocal;
import edu.school.entities.DatosPersona;
import edu.school.entities.Docente;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import edu.school.ejb.AdministrativoFacadeLocal;

@Named
@ViewScoped
public class DefineSupervisorDocenteController implements Serializable{
    
    @EJB
    private DocenteFacadeLocal docenteFacade;
    @EJB
    private AdministrativoFacadeLocal adminitrativoFacade;
    @EJB
    private DatosPersonaFacadeLocal datosPersonaFacade;
    
    private List<DatosPersona> personas;
    private List<Docente> docentes;
//    private List<Administrativo> administrativos;
}
