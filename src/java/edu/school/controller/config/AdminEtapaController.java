package edu.school.controller.config;

import edu.school.ejb.ColegioFacadeLocal;
import edu.school.ejb.EtapaFacadeLocal;
import edu.school.entities.Colegio;
import edu.school.entities.Etapa;
import edu.school.utilities.JsfUtils;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class AdminEtapaController implements Serializable{
    
    @EJB
    private EtapaFacadeLocal etapaFacade;
    @EJB
    private ColegioFacadeLocal colegioFacade;
    
    private String nombre;
    private String prefijo;
    private List<Etapa> etapas;
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrefijo() {
        return prefijo;
    }

    public void setPrefijo(String prefijo) {
        this.prefijo = prefijo;
    }

    public List<Etapa> getEtapas() {
        if(etapas == null){
            etapas = resetEtapas();
        }
        return etapas;
    }

    public void setEtapas(List<Etapa> etapas) {
        this.etapas = etapas;
    }
    
    public void createEtapa(){
        try {
            List<Colegio> colegios = colegioFacade.findAll();
            Colegio colegio = colegios.get(0);
            
            Etapa etapa = new Etapa();
            etapa.setNombre(nombre);
            Integer pre = Integer.parseInt(prefijo);
            etapa.setPrefijo(pre);
            etapa.setColegioId(colegio);
            
            etapaFacade.create(etapa);
            
            clearFields();
            etapas = null;
            JsfUtils.messageSuccess("Etapa creada con éxito");
        } catch (NumberFormatException e) {
            Logger.getLogger(AdminEtapaController.class.getName())
                    .log(Level.WARNING, e.getMessage());
        }
        
    }

    public String cancelAction() {
        return "dashboard";
    }

    public void clearFields() {
        nombre = "";
        prefijo = "";
    }
    
    private List<Etapa> resetEtapas(){
        List<Etapa> etapaList = etapaFacade.findAll();
        etapaList.sort((et1, et2) -> Integer.compare(et1.getPrefijo(), et2.getPrefijo()));
        return etapaList;
    }
}
