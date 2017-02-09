package edu.school.controller.administrativos;

import edu.school.ejb.DocenteFacadeLocal;
import edu.school.entities.Docente;
import edu.school.utilities.JsfUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.DualListModel;
import edu.school.ejb.SeccionHasDocenteFacadeLocal;
import edu.school.ejb.SeccionFacadeLocal;
import edu.school.entities.Seccion;
import edu.school.entities.SeccionHasDocente;

@Named
@ViewScoped
public class AsociaCursoDocenteController implements Serializable {

    @EJB
    private DocenteFacadeLocal docenteFacade;
    @EJB
    private SeccionFacadeLocal seccionFacade;
    @EJB
    private SeccionHasDocenteFacadeLocal chdFacade;

    private List<Docente> docentes;
    private DualListModel<Seccion> secciones;
    private Docente docenteSelected;

    @PostConstruct
    public void init() {
        makeDualList();
    }

    public List<Docente> getDocentes() {
        if (docentes == null) {
            docentes = makeDocenteList();
        }
        return docentes;
    }

    public DualListModel<Seccion> getSecciones() {
        return secciones;
    }

    public void setSecciones(DualListModel<Seccion> secciones) {
        this.secciones = secciones;
    }

    public Docente getDocenteSelected() {
        return docenteSelected;
    }

    public void setDocenteSelected(Docente docenteSelected) {
        this.docenteSelected = docenteSelected;
    }

    public void saveSelection() {
        List<Seccion> seccionesSelected = (List<Seccion>) secciones.getTarget();
        List<SeccionHasDocente> chdList = new ArrayList<>();
        
        if (!seccionesSelected.isEmpty()) {
            if (!seccionesSelected.isEmpty()) {
                seccionesSelected.stream().forEach(cur -> {
                    SeccionHasDocente chd = new SeccionHasDocente();
                    chd.setDocenteId(docenteSelected);
                    chd.setSeccionId(cur);
                    chdList.add(chd);
                });
            }
        } else {
            JsfUtils.messageError("No ha seleccionado secciones para este profesor");
            return;
        }

        if (!chdList.isEmpty()) {
            chdList.stream().forEach(chd -> {
                chdFacade.create(chd);
            });
            clearFields();
            JsfUtils.messageSuccess("Asignados el(los) curso(s) con éxito");            
        } else {
            clearFields();
            JsfUtils.messageError("Ha ocurrido un error en la asignación, consulte a soporte");
        }
    }

    private List<Docente> makeDocenteList() {
        return docenteFacade.findAll();
    }

    private List<Seccion> makeSeccionList() {
        return seccionFacade.findAll();
    }
    
    public void clearFields(){
        docenteSelected = null;
        makeDualList();
    }
    
    private void makeDualList(){
        List<Seccion> seccionesSourge = makeSeccionList();
        List<Seccion> seccionesTarget = new ArrayList();
        secciones = new DualListModel<>(seccionesSourge, seccionesTarget);
    }
}
