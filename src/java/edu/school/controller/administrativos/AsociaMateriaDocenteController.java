package edu.school.controller.administrativos;

import edu.school.ejb.DocenteFacadeLocal;
import edu.school.ejb.MateriaFacadeLocal;
import edu.school.ejb.MateriaHasDocenteFacadeLocal;
import edu.school.entities.Docente;
import edu.school.entities.Materia;
import edu.school.entities.MateriaHasDocente;
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
public class AsociaMateriaDocenteController implements Serializable {

    @EJB
    private DocenteFacadeLocal docenteFacade;
    @EJB
    private MateriaFacadeLocal materiaFacade;
    @EJB
    private MateriaHasDocenteFacadeLocal mhdFacade;

    private List<Docente> docentes;
    private DualListModel<Materia> materias;
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

    public DualListModel<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(DualListModel<Materia> materias) {
        this.materias = materias;
    }

    public Docente getDocenteSelected() {
        return docenteSelected;
    }

    public void setDocenteSelected(Docente docenteSelected) {
        this.docenteSelected = docenteSelected;
    }

    public void saveSelection() {
        List<Materia> materiasSelected = (List<Materia>) materias.getTarget();
        List<MateriaHasDocente> mhdList = new ArrayList<>();
        
        if (!materiasSelected.isEmpty()) {
            materiasSelected.stream().forEach(mat -> {
                MateriaHasDocente mhd = new MateriaHasDocente();
                mhd.setDocenteId(docenteSelected);
                mhd.setMateriaId(mat);
                mhdList.add(mhd);                
            });
        } else {
            JsfUtils.messageError("No ha seleccionado materias para este profesor");
            return;
        }

        if (!mhdList.isEmpty()) {
            mhdList.stream().forEach(mhd -> {
                mhdFacade.create(mhd);
            });
            clearFields();
            JsfUtils.messageSuccess("Asignadas la materias con éxito");            
        } else {
            clearFields();
            JsfUtils.messageError("Ha ocurrido un error en la asignación, consulte a soporte");
        }
    }

    private List<Docente> makeDocenteList() {
        return docenteFacade.findAll();
    }

    private List<Materia> makeMateriaList() {
        return materiaFacade.findAll();
    }
    
    public void clearFields(){
        docenteSelected = null;
        makeDualList();
    }
    
    private void makeDualList(){
        List<Materia> materiasSourge = makeMateriaList();
        List<Materia> materiasTarget = new ArrayList();
        materias = new DualListModel<>(materiasSourge, materiasTarget);
    }
}
