package edu.school.controller.administrativos;

import edu.school.ejb.CursoFacadeLocal;
import edu.school.ejb.CursoHasDocenteFacadeLocal;
import edu.school.ejb.DocenteFacadeLocal;
import edu.school.entities.Curso;
import edu.school.entities.CursoHasDocente;
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

@Named
@ViewScoped
public class AsociaCursoDocenteController implements Serializable {

    @EJB
    private DocenteFacadeLocal docenteFacade;
    @EJB
    private CursoFacadeLocal cursoFacade;
    @EJB
    private CursoHasDocenteFacadeLocal chdFacade;

    private List<Docente> docentes;
    private DualListModel<Curso> cursos;
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

    public DualListModel<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(DualListModel<Curso> cursos) {
        this.cursos = cursos;
    }

    public Docente getDocenteSelected() {
        return docenteSelected;
    }

    public void setDocenteSelected(Docente docenteSelected) {
        this.docenteSelected = docenteSelected;
    }

    public void saveSelection() {
        List<Curso> cursosSelected = (List<Curso>) cursos.getTarget();
        List<CursoHasDocente> chdList = new ArrayList<>();
        Curso cur;
        
        if (!cursosSelected.isEmpty()) {
            for (int i = 0; i < cursosSelected.size(); i++) {
                if (!(cursosSelected.get(i) instanceof Curso)) {
                    cur = cursoFacade.findByStringId(String.valueOf(cursosSelected.get(i)));
                } else {
                    cur = cursosSelected.get(i);
                }
                CursoHasDocente chd = new CursoHasDocente();
                chd.setDocenteId(docenteSelected);
                chd.setCursoId(cur);
                chdList.add(chd);
            }
        } else {
            JsfUtils.messageError("No ha seleccionado cursos para este profesor");
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

    private List<Curso> makeCursoList() {
        return cursoFacade.findAll();
    }
    
    public void clearFields(){
        docenteSelected = null;
        makeDualList();
    }
    
    private void makeDualList(){
        List<Curso> cursosSourge = makeCursoList();
        List<Curso> cursosTarget = new ArrayList();
        cursos = new DualListModel<>(cursosSourge, cursosTarget);
    }
}
