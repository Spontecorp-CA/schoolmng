package edu.school.controller.config;

import edu.school.ejb.CursoFacadeLocal;
import edu.school.ejb.EtapaFacadeLocal;
import edu.school.entities.Curso;
import edu.school.entities.Etapa;
import edu.school.utilities.JsfUtils;
import java.io.Serializable;
import java.util.List;
import static java.util.stream.Collectors.toList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class AdminGradoController implements Serializable{
    
    @EJB
    private CursoFacadeLocal cursoFacade;
    @EJB
    private EtapaFacadeLocal etapaFacade;
    
    private String nombre;
    private Etapa etapa;
    private List<Curso> grados;
    private List<Etapa> etapas;
    
    @PostConstruct
    public void init(){
        etapas = makeEtapas();
    }

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
    
    public void createGrado(){
        Curso curso = new Curso();
        curso.setNombre(nombre);
        curso.setEtapaId(etapa);
        
        cursoFacade.create(curso);
        JsfUtils.messageSuccess("Grado creado con éxito");
        grados = null;
    }
    
    public void clearFields(){
        nombre = "";
    }
    
    public String cancelAction(){
        return "dashboard";
    }
    
    private List<Curso> makeGradoList(){
        List<Curso> gradoList = cursoFacade.findAll();
        gradoList.sort((cu1, cu2) -> cu1.getEtapaId().getPrefijo()
                    .compareTo(cu2.getEtapaId().getPrefijo()));
        return gradoList;
    }
    
    private List<Etapa> makeEtapas(){
        return etapaFacade.findAll()
                .stream().sorted((e1,e2) -> e1.getPrefijo().compareTo(e2.getPrefijo()))
                .collect(toList());
    }
}
