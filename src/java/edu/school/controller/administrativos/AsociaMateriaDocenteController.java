package edu.school.controller.administrativos;

import edu.school.ejb.DocenteFacadeLocal;
import edu.school.ejb.MateriaFacadeLocal;
import edu.school.entities.Docente;
import edu.school.entities.Materia;
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
public class AsociaMateriaDocenteController implements Serializable{
    
    @EJB
    private DocenteFacadeLocal docenteFacade;
    @EJB
    private MateriaFacadeLocal materiaFacade;
    
    private List<Docente> docentes;
    private DualListModel<Materia> materias;
    private Docente docenteSelected;
    
    @PostConstruct
    public void init(){
        List<Materia> materiasSourge = makeMateriaList();
        List<Materia> materiasTarget = new ArrayList();
        materias = new DualListModel<>(materiasSourge, materiasTarget);
    }

    public List<Docente> getDocentes() {
        if(docentes == null){
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

    public void saveSelection(){
        System.out.println(docenteSelected.getDatosPersonaId().getApellido() 
                + " " + docenteSelected.getDatosPersonaId().getNombre());
        List<Materia> materiasSelected = materias.getTarget();

        for(int i = 0; i < materiasSelected.size(); i++){
            if (!(materiasSelected.get(i) instanceof Materia)) {
                Materia mat = materiaFacade.findByStringId(String.valueOf(materiasSelected.get(i)));
                System.out.println(mat.getNombre());
            } else {
                System.out.println("por else " + materiasSelected.get(i).getNombre());
            }
        }
    }
    
    private List<Docente> makeDocenteList(){
        return docenteFacade.findAll();
    }
    
    private List<Materia> makeMateriaList(){
        return materiaFacade.findAll();
    }
}
