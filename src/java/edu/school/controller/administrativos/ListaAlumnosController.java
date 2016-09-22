package edu.school.controller.administrativos;

import edu.school.ejb.CursoFacadeLocal;
import edu.school.ejb.CursoHasAlumnoFacadeLocal;
import edu.school.entities.Alumno;
import edu.school.entities.Curso;
import edu.school.entities.CursoHasAlumno;
import edu.school.entities.Nivel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class ListaAlumnosController implements Serializable {
    
    @EJB
    private CursoHasAlumnoFacadeLocal cursoHasAlumnoFacade;
    @EJB
    private CursoFacadeLocal cursoFacade;
    
    @Inject
    private Nivel nivel;
    
    @Inject
    private Curso curso;
    
    private List<Alumno> alumnos;

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public List<Alumno> getAlumnos() {
        if(alumnos == null){
            alumnos = fillAlumnosList();
        }
        return alumnos;
    }
    
    private List<Alumno> fillAlumnosList(){
        List<CursoHasAlumno> chaList = cursoHasAlumnoFacade.findAll(curso);
        List<Alumno> alumnosTemp = new ArrayList<>();
        chaList.stream().forEach(cha -> {
            alumnosTemp.add(cha.getAlumnoId());
        });
        return alumnosTemp;
    }
    
    public void handleCursoChange(){
        alumnos = fillAlumnosList();
    }
    
    public List<SelectItem> getCursos() {
        List<Curso> cursos = cursoFacade.findAll(nivel);
        List<SelectItem> itemsList = new ArrayList<>();
        itemsList.add(new SelectItem(null, "Seleccione Curso..."));
        cursos.stream().forEach(cu -> {
            itemsList.add(new SelectItem(cu, (cu.getNombre() + " - " + cu.getSeccion())));
        });
        return itemsList;
    }
}
