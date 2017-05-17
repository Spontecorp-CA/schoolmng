package edu.school.controller;

import edu.school.ejb.AutorizacionFacadeLocal;
import edu.school.ejb.StatusSupervisorFacadeLocal;
import edu.school.ejb.SupervisorFacadeLocal;
import edu.school.entities.StatusSupervisor;
import edu.school.entities.Supervisor;
import edu.school.entities.User;
import edu.school.utilities.Constantes;
import java.util.Optional;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

public abstract class DashboardFacade {

    @Inject
    private User user;

    @EJB
    private AutorizacionFacadeLocal autorizacionFacade;
    @EJB
    private SupervisorFacadeLocal supervisorFacade;
    @EJB
    private StatusSupervisorFacadeLocal statusSupervisorFacade;
    
    public User getUser() {
        user = (User) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("user");
        return user;
    }
    
    public String goUserPage(){
        return "dashboard?faces-redirect=true";
    }
    
    public int getAutorizacionesPendientes(){
        int cant = 0;
        // Se verifica si es supervisor
        Optional<Supervisor> optSupervisor = Optional.ofNullable(
                supervisorFacade.findByUser(user));
        if(optSupervisor.isPresent()){
            // si lo es se cuentan las autorizaciones pendientes que tiene
            Supervisor supervisor = optSupervisor.get();
            // determina que nivel dde supervision tiene
            StatusSupervisor nivelSupervisor = 
                    statusSupervisorFacade.findBySupervisor(supervisor);
            int statusAprobacion;
            if(nivelSupervisor.getColegioId() != null){
                statusAprobacion = Constantes.CIRCULAR_PENDIENTE_APROBAR_COLEGIO;
            } else if(nivelSupervisor.getEtapaId() != null){
                statusAprobacion = Constantes.CIRCULAR_PENDIENTE_APROBAR_ETAPA;
            } else if(nivelSupervisor.getCursoId() != null){
                statusAprobacion = Constantes.CIRCULAR_PENDIENTE_APROBAR_GRADO;
            }
            
            cant = (int)autorizacionFacade.countBySupervisor(supervisor);
        }

        return cant;
    }
}
