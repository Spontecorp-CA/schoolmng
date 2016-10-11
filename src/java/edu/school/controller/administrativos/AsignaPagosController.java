package edu.school.controller.administrativos;

import edu.school.ejb.AlumnoFacadeLocal;
import edu.school.ejb.CursoFacadeLocal;
import edu.school.ejb.CursoHasAlumnoFacadeLocal;
import edu.school.ejb.NivelFacadeLocal;
import edu.school.ejb.PagoAlumnoFacadeLocal;
import edu.school.ejb.PagoFacadeLocal;
import edu.school.ejb.PagoHasStatusFacadeLocal;
import edu.school.ejb.StatusPagoFacadeLocal;
import edu.school.entities.Alumno;
import edu.school.entities.Curso;
import edu.school.entities.CursoHasAlumno;
import edu.school.entities.Nivel;
import edu.school.entities.Pago;
import edu.school.entities.PagoAlumno;
import edu.school.entities.PagoHasStatus;
import edu.school.entities.StatusPago;
import edu.school.utilities.Constantes;
import edu.school.utilities.JsfUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class AsignaPagosController implements Serializable {

    @EJB
    private NivelFacadeLocal nivelFacade;
    @EJB
    private CursoFacadeLocal cursoFacade;
    @EJB
    private CursoHasAlumnoFacadeLocal cursoHasAlumnoFacade;
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

    private Pago pagoSelected;
    private Nivel nivelSelected;
    private List<Pago> pagos;
    private List<SelectItem> niveles;

    @PostConstruct
    public void init() {
        clearFields();
    }

    public Pago getPagoSelected() {
        return pagoSelected;
    }

    public void setPagoSelected(Pago pagoSelected) {
        this.pagoSelected = pagoSelected;
    }

    public List<Pago> getPagos() {
        if (pagos == null) {
            pagos = makePagoList();
        }
        return pagos;
    }

    public Nivel getNivelSelected() {
        return nivelSelected;
    }

    public void setNivelSelected(Nivel nivelSelected) {
        this.nivelSelected = nivelSelected;
    }

    public List<SelectItem> getNiveles() {
        if (niveles == null) {
            niveles = makeNivelList();
        }
        return niveles;
    }

    public void clearFields() {
        pagos = makePagoList();
        niveles = makeNivelList();
    }

    public void saveSelection() {
        List<Curso> cursos = makeCursoXNivelList(nivelSelected);
        List<PagoAlumno> paList = new ArrayList<>();
        List<PagoHasStatus> phsList = new ArrayList<>();
        
        Optional<StatusPago> optionalStatusPago = statusPagoFacade
                                    .findXNombre(Constantes.STATUS_PAGO_PENDIENTE);
        StatusPago statusPago = optionalStatusPago.get();
        
        cursos.stream().forEach(cur -> {
            List<Alumno> alumnos = makeAlumnoList(cur);
            alumnos.stream().forEach(al -> {
                PagoAlumno pa = new PagoAlumno();
                pa.setAlumnoId(al);
                pa.setPagoId(pagoSelected);
                paList.add(pa);
                
                PagoHasStatus phs = new PagoHasStatus();
                phs.setFecha(pagoSelected.getFecha());
                phs.setPagoAlumnoId(pa);
                phs.setStatusPagoId(statusPago);
                phsList.add(phs);
            });
        });
        
        pagoAlumnoFacade.batchCreate(paList);
        pagoHasStatusFacade.batchCreate(phsList);
        JsfUtils.messageSuccess("Cobros cargados con éxito");
    }

    private List<Pago> makePagoList() {
        return pagoFacade.findAll();
    }

    private List<SelectItem> makeNivelList() {
        List<Nivel> nivelList = nivelFacade.findAll();
        List<SelectItem> items = new ArrayList<>();
        nivelList.stream().forEach(nv -> {
            items.add(new SelectItem(nv, nv.getNombre()));
        });
        return items;
    }

    private List<Curso> makeCursoXNivelList(Nivel nivel) {
        return cursoFacade.findAll(nivel);
    }

    private List<Alumno> makeAlumnoList(Curso curso) {
        List<CursoHasAlumno> alumnosXCursoList = cursoHasAlumnoFacade.findAll(curso);
        List<Alumno> alumnos = new ArrayList<>();
        alumnosXCursoList.stream().forEach(cha -> {
            alumnos.add(cha.getAlumnoId());
        });
        return alumnos;
    }
}
