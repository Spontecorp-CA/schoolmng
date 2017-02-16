package edu.school.controller.config;

import edu.school.ejb.CursoFacadeLocal;
import edu.school.entities.Curso;
import edu.school.entities.Etapa;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class AdminGradoController implements Serializable{
    
    @EJB
    private CursoFacadeLocal cursoFacade;
    
    private String nombre;
    private Etapa etapa;
    private List<Curso> grados;
    private List<Etapa> etapas;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Etapa getEtapa() {
        return etapa;
    }

    public void setEtapa(Etapa etapa) {
        this.etapa = etapa;
    }

    public List<Curso> getGrados() {
        if(grados == null){
            grados = makeGradoList();
        }
        return grados;
    }

    public void setGrados(List<Curso> grados) {
        this.grados = grados;
    }

    public List<Etapa> getEtapas() {
        return etapas;
    }

    public void setEtapas(List<Etapa> etapas) {
        this.etapas = etapas;
    }
    
    private List<Curso> makeGradoList(){
        List<Curso> gradoList = cursoFacade.findAll();
        gradoList.sort((cu1, cu2) -> cu1.getEtapaId().getPrefijo()
                    .compareTo(cu2.getEtapaId().getPrefijo()));
        return gradoList;
    }
}
