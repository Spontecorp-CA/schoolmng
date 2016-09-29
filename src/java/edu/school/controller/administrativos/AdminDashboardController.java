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
    
    public String goCargaAlumnos(){
        return "cargaalumno";
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
}
