package edu.school.controller.administrativos;

import edu.school.ejb.CursoHasDocenteFacadeLocal;
import edu.school.ejb.DocenteFacadeLocal;
import edu.school.entities.Curso;
import edu.school.entities.CursoHasDocente;
import edu.school.entities.Docente;
import edu.school.entities.Nivel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class ListaDocentesController implements Serializable{
    
    @EJB
    private CursoHasDocenteFacadeLocal cursoHasDocenteFacade;
    @EJB
    private DocenteFacadeLocal docenteFacade;
    
    @Inject
    private Nivel nivel;
    @Inject
    private Curso curso;
    
    private List<Curso> cursos;
    private List<Docente> docentes;

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

    public List<Curso> getCursos() {
        return cursos;
    }

    public List<Docente> getDocentes() {
        if(docentes == null){
            docentes = fillDocentesList();
        }
        return docentes;
    }
    
    public void handleCursoChange(){
        docentes = fillDocentesList();
    }

    private List<Docente> fillDocentesList() {
        List<Docente> docentesTemp = docenteFacade.findAll();
        Collections.sort(docentesTemp, (d1, d2) -> d1.getDatosPersonaId().getApellido()
                .compareTo(d2.getDatosPersonaId().getApellido())
        
        );
        
        return docentesTemp;
    }
}
