package edu.school.controller.config;

import edu.school.controller.DashboardFacade;
import edu.school.ejb.UserHasRolFacadeLocal;
import edu.school.entities.UserHasRol;
import edu.school.utilities.Constantes;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.chart.PieChartModel;

@Named
@ViewScoped
public class ConfigDashboardController extends DashboardFacade implements Serializable{

    private PieChartModel pieModel;
    
    @EJB
    private UserHasRolFacadeLocal userHasRolFacade;
    
    @PostConstruct
    public void init(){
        createPieModel();
    }
    
    public PieChartModel getPieModel(){
        return pieModel;
    }
    
    private void createPieModel(){
        pieModel = new PieChartModel();
        
        List<UserHasRol> usrList = userHasRolFacade.findAll();
        int admins = 0;
        int docentes = 0;
        int represents = 0;
        for(UserHasRol usr : usrList){
            switch(usr.getRolId().getName()){
                case Constantes.ROL_ADMINISTRATIVO:
                    admins++;
                    break;
                case Constantes.ROL_DOCENTE:
                    docentes++;
                    break;
                case Constantes.ROL_REPRESENTANTE:
                    represents++;
                    break;
            }
        }
        
        pieModel.set("Administrativos", admins);
        pieModel.set("Docentes", docentes);
        pieModel.set("Representantes", represents);
        
        pieModel.setTitle("Usuarios por tipo");
        pieModel.setFill(true);
        pieModel.setShowDataLabels(true);
        pieModel.setDiameter(200);
        pieModel.setLegendPosition("w");
    }
    
    public String goConfigUsers(){
        return "adminUsers?faces-redirect=true";
    }
    
    public String goConfigPeriodos(){
        return "adminPeriodo?faces-redirect=true";
    }
    
    public String goConfigGrados(){
        return "adminGrados?faces-redirect=true";
    }
    
    public String goConfigEtapas(){
        return "adminEtapas?faces-redirect=true";
    }
    
    public String goConfigCursos() {
        return "adminCursos?faces-redirect=true";
    }
    
    public String goConfigStatusPago() {
        return "adminStatusPago?faces-redirect=true";
    }
    
    public String goSupervisorAgregar() {
        return "adminSupervisorAgregar?faces-redirect=true";
    }
    
    public String goSupervisorEditar() {
        return "adminSupervisorEditar?faces-redirect=true";
    }
    
    public String goConfigMailAccount(){
        return "adminMailAccount?faces-redirect=true";
    }
    
    public String goCircularHeaderAndBottom() {
        return "adminCircularHeaderAndBottom?faces-redirect=true";
    }
    
    public void closeSession() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }
}
