package edu.school.controller.administrativos;

import edu.school.ejb.AlumnoFacadeLocal;
import edu.school.ejb.CursoFacadeLocal;
import edu.school.ejb.CursoHasAlumnoFacadeLocal;
import edu.school.ejb.NivelFacadeLocal;
import edu.school.ejb.PagoAlumnoFacadeLocal;
import edu.school.ejb.PagoFacadeLocal;
import edu.school.ejb.PagoHasStatusFacadeLocal;
import edu.school.ejb.StatusPagoFacadeLocal;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class AsignaPagosController implements Serializable {

    @EJB
    private NivelFacadeLocal nivelFacade;
    @EJB
    private CursoFacadeLocal cursoFacadeLocal;
    @EJB
    private CursoHasAlumnoFacadeLocal cursoHasALumnoFacade;
    @EJB
    private AlumnoFacadeLocal alumnoFacade;
    @EJB
    private PagoAlumnoFacadeLocal pagoAlumnoFacade;
    @EJB
    private PagoFacadeLocal pagoFacade;
    @EJB
    private PagoHasStatusFacadeLocal pagoHasStatusFacade;
    @EJB
    private StatusPagoFacadeLocal statusPagoFacade;
    
    
}
