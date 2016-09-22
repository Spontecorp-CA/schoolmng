package edu.school.controller.config;

import edu.school.ejb.CursoFacadeLocal;
import edu.school.ejb.NivelFacadeLocal;
import edu.school.ejb.PeriodoFacadeLocal;
import edu.school.entities.Curso;
import edu.school.entities.Nivel;
import edu.school.entities.Periodo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class AdminCursosController implements Serializable{
    
    @EJB
    private CursoFacadeLocal cursoFacade;
    @EJB
    private PeriodoFacadeLocal periodoFacade;
    @EJB
    private NivelFacadeLocal nivelFacade;
    
    @Inject
    private Curso curso;
    

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
    
    public String cancelAction() {
        return "dashboard?faces-redirect=true";
    }
    
    public void crearCurso() {
        
        if(curso.getPeriodoInt() == null){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Falta un campo",
                            "Debe seleccionar un Período"));
            return;
        }
        
        if (curso.getNivelId() == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Falta un campo",
                            "Debe seleccionar un Nivel"));
            return;
        }
        
        if (cursoFacade.find(curso.getCodigo(), 
                curso.getNombre(), curso.getPeriodoInt()) == null) {
            cursoFacade.create(curso);
            
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Operación exitosa",
                            "Curso creado con éxito"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Curso ya existe",
                            "El Curso ya existe, trate con otro nombre/sección/código"));
        }
    }

    public List<Curso> getCursos() {
        return cursoFacade.findAllOrdered();
    }
    
    public List<SelectItem> getPeriodos(){
        List<Periodo> periodos = periodoFacade.findAll();
        List<SelectItem> itemsList = new ArrayList<>();
        itemsList.add(new SelectItem(null, "Seleccione Periodo..."));
        periodos.stream().forEach(p -> {
            itemsList.add(new SelectItem(p, p.getNombre()));
        });
        return itemsList;
    }
    
    public List<SelectItem> getNiveles(){
        List<Nivel> niveles = nivelFacade.findAll();
        List<SelectItem> itemsList = new ArrayList<>();
        itemsList.add(new SelectItem(null, "Seleccione Nivel..."));
        niveles.stream().forEach(n -> {
            itemsList.add(new SelectItem(n, n.getNombre()));
        });
        return itemsList;
    }
}
