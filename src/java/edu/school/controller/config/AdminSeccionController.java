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
            curso = optCurso.get();
            Optional<Periodo> optPeriodo = Optional.ofNullable(periodo);
            if (optPeriodo.isPresent()) {
                secciones = seccionFacade.findAllByPeriodoAndCurso(periodo, curso);
            }
        } else {
             secciones = seccionFacade.findAllOrderedByCurso(periodo);
        }
        return secciones;
    }

    public void setSecciones(List<Seccion> secciones) {
        this.secciones = secciones;
    }
    
    public void createSeccion(){
        Seccion seccionObj = new Seccion();
        seccionObj.setPeriodoId(periodo);
        seccionObj.setCursoId(curso);
        seccionObj.setSeccion(seccion);

        String grado = curso.getNombre();
        Integer prefijo = curso.getEtapaId().getPrefijo();
        String codigo = makeCodigo(prefijo, grado);
        seccionObj.setCodigo(codigo);
        
        seccionFacade.create(seccionObj);
        clearFields();
    }
    
    private String makeCodigo(Integer prefijo, String grado){
        StringBuilder sb = new StringBuilder();
        sb.append(prefijo);
        String nivel2 = "";
        switch(grado){
            case "Maternal": 
                nivel2 = "M";
                break;
            case "Prescolar 1":
            case "1er grado":
            case "4to año":
                nivel2 = "1";
                break;
            case "Prescolar 2":
            case "2do grado":
            case "5to año":
                nivel2 = "2";
                break;
            case "Prescolar 3":
            case "3er grado":
                nivel2 = "3";
                break;
            case "4to grado":
                nivel2 = "4";
                break;
            case "5to grado":
                nivel2 = "5";
                break;
            case "6to grado":
                nivel2 = "6";
                break;
            case "7mo grado":
                nivel2 = "7";
                break;
            case "8vo grado":
                nivel2 = "8";
                break;
            case "9no grado":
                nivel2 = "9";
                break;
        }
        sb.append(nivel2);
        sb.append(seccion);
        
        return sb.toString();
    }
    
    public void clearFields(){
        seccion = "";
        curso = null;
    }
    
    public String cancelAction(){
        return "dashboard?send-redirect=true";
    }
}
