package edu.school.controller.administrativos;

import edu.school.ejb.RepresentanteFacadeLocal;
import edu.school.entities.Representante;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class ListaRepresentantesController implements Serializable {
    
    @EJB
    private RepresentanteFacadeLocal representanteFacade;
    
    private List<Representante> representantes;

    public List<Representante> getRepresentantes() {
        if(representantes == null){
            representantes = fillRepresentantesList();
        }
        return representantes;
    }
    
    private List<Representante> fillRepresentantesList(){
        return representanteFacade.findAll();
    }
}
