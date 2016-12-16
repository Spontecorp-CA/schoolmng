package edu.school.controller.administrativos;

import edu.school.controller.DashboardFacade;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class AdminDashboardController extends DashboardFacade implements Serializable{

    public String goCargaCobros(){
        return "cargacobros";
    }
    
    public String goListaCobros() {
        return "listacobros";
    }
    
    public String goListaCobrosNivel() {
        return "listacobrosnivel";
    }
    
    public String goListaCobrosCurso() {
        return "listacobroscurso";
    }
    
    public String goListaCobrosAlumno() {
        return "listacobrosalumno";
    }
    
    public String goCargaAlumnos(){
        return "cargaalumno";
    }
    
    public String goCargaArchivoAlumnos() {
        return "cargaarchivoalumnos";
    }
    
    public String goListaAlumnos() {
        return "listaalumnos";
    }
    
    public String goCargaRepresentantes() {
        return "cargarepresentante";
    }

    public String goListaRepresentantes() {
        return "listarepresentantes";
    }
    
    public String goCargaMaterias() {
        return "cargamateria";
    }
    
    public String goCargaDocentes() {
        return "cargadocente";
    }
    
    public String goListaDocentes() {
        return "listadocentes";
    }
    
    public String goAsociarMateriaDocente() {
        return "asociamateriadocente";
    }
    
    public String goAsociarCursoDocente() {
        return "asociacursodocente";
    }
    
    public String goDefineSupervisorDocente() {
        return "definesupervisordocente?faces-redirect=true";
    }
    
    public String goAsignaPagosNivel() {
        return "asignapagosnivel";
    }
    
    public String goAsignaPagosCurso() {
        return "asignapagoscurso";
    }
    
    public String goAsignaPagosAlumno() {
        return "asignapagosalumno";
    }
    
    public String goEmailsCreate(){
        return "emailscreate";
    }
}
