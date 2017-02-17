package edu.school.controller.config;

import edu.school.entities.Curso;
import edu.school.entities.Periodo;
import edu.school.entities.Seccion;
import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class AdminSeccionController implements Serializable {
    
    private String seccion;
    private Curso curso;
    private Periodo periodo;
    private List<Curso> cursos;
    private List<Periodo> periodos;
    private List<Seccion> secciones;

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

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    public List<Periodo> getPeriodos() {
        return periodos;
    }

    public void setPeriodos(List<Periodo> periodos) {
        this.periodos = periodos;
    }

    public List<Seccion> getSecciones() {
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
