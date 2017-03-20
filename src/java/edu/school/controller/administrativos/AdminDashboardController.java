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
        return "cargarepresentante?faces-redirect=true";
    }

    public String goListaRepresentantes() {
        return "listarepresentantes?faces-redirect=true";
    }
    
    public String goCargaMaterias() {
        return "cargamateria?faces-redirect=true";
    }
    
    public String goCargaDocentes() {
        return "cargadocente?faces-redirect=true";
    }
    
    public String goListaDocentes() {
        return "listadocentes?faces-redirect=true";
    }
    
    public String goAsociarMateriaDocente() {
        return "asociamateriadocente?faces-redirect=true";
    }
    
    public String goAsociarCursoDocente() {
        return "asociacursodocente?faces-redirect=true";
    }
    
    public String goDefineSupervisorDocente() {
        return "definesupervisordocente?faces-redirect=true";
    }
    
    public String goAsignaPagosNivel() {
        return "asignapagosnivel?faces-redirect=true";
    }
    
    public String goAsignaPagosCurso() {
        return "asignapagoscurso?faces-redirect=true";
    }
    
    public String goAsignaPagosAlumno() {
        return "asignapagosalumno?faces-redirect=true";
    }
    
    public String goEmailsCreate(){
        return "emailscreate?faces-redirect=true";
    }
}
