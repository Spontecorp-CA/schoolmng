package edu.school.controller.config;

import edu.school.ejb.CursoFacadeLocal;
import edu.school.ejb.PeriodoFacadeLocal;
import edu.school.ejb.SeccionFacadeLocal;
import edu.school.entities.Curso;
import edu.school.entities.Periodo;
import edu.school.entities.Seccion;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class AdminSeccionController implements Serializable {
    
    @EJB
    private PeriodoFacadeLocal periodoFacade;
    @EJB
    private CursoFacadeLocal cursoFacade;
    @EJB
    private SeccionFacadeLocal seccionFacade;
    
    private String seccion;
    private Curso curso;
    private Periodo periodo;
    private List<Curso> cursos;
    private List<Periodo> periodos;
    private List<Seccion> secciones;
    
    @PostConstruct
    public void init(){
        periodos = periodoFacade.findAllOrderStatus();
        periodo = periodos.get(0);
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Periodo getPeriodo() {
        return periodo;
    }
    
    public void setPeriodo(Periodo periodo){
        this.periodo = periodo;
    }

    public List<Curso> getCursos() {
        if(cursos == null){
            cursos = cursoFacade.findAllOrderedByEtapa();
        }
        return cursos;
    }
    
    public List<Periodo> getPeriodos() {
        return periodos;
    }

    public List<Seccion> getSecciones() {
        Optional<Curso> optCurso = Optional.ofNullable(curso);
        if(optCurso.isPresent()){
            System.out.println("El curso seleccionado es " + curso.getNombre());
        } else {
            Optional<Periodo> optPeriodo = Optional.ofNullable(periodo);
            if(optPeriodo.isPresent()){
                secciones = seccionFacade.findAllOrderedByCurso(optPeriodo.get());
            } else {
                System.out.println("el período es nulo");
                secciones = new ArrayList<>();
            }
        }
        return secciones;
    }

    public void setSecciones(List<Seccion> secciones) {
        this.secciones = secciones;
    }
    
    public void createSeccion(){}
    
    public void clearFields(){
        seccion = "";
    }
    
    public String cancelAction(){
        return "dashboard?send-redirect=true";
    }
}
