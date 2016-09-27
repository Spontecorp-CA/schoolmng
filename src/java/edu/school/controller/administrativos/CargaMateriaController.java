package edu.school.controller.administrativos;

import edu.school.ejb.MateriaFacadeLocal;
import edu.school.entities.Materia;
import edu.school.utilities.JsfUtils;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class CargaMateriaController implements Serializable{
    
    @EJB
    private MateriaFacadeLocal materiaFacade;
    
    @Inject
    private Materia materia;
    private List<Materia> materias;

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public List<Materia> getMaterias() {
        if(materias == null){
            materias = fillMaterias();
        }
        return materias;
    }
    
    public void saveMateria(){
        StringBuilder sb = new StringBuilder();
        if(materiaFacade.find(materia.getNombre()) == null){
            materiaFacade.create(materia);
            sb.append("Materia ");
            sb.append(materia.getNombre());
            sb.append(" creada con éxito");
            materias = null;
            JsfUtils.messageSuccess(sb.toString());
            clearFields();
        } else {
            sb.append("La materia ");
            sb.append(materia.getNombre());
            sb.append(" ya existe, debe usar otro nombre");
            JsfUtils.messageWarning(sb.toString());
        }
    }

    public void clearFields(){
        materia.setNombre(null);
    }
    
    private List<Materia> fillMaterias(){
        List<Materia> subjectList = materiaFacade.findAll();
        subjectList.sort((m1, m2) -> m1.getNombre().compareTo(m2.getNombre()));
        return subjectList;
    }
}
