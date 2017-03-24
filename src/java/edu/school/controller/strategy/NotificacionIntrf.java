package edu.school.controller.strategy;

import edu.school.ejb.SupervisorFacade;
import edu.school.ejb.SupervisorFacadeLocal;
import edu.school.entities.Supervisor;
import edu.school.entities.User;
import java.util.Optional;

/**
 *
 * @author jgcastillo
 */
public interface NotificacionIntrf {
    
    final SupervisorFacadeLocal supervisorFacade = new SupervisorFacade();
    
    public void notifica();
    
    public default boolean isSupervisor(User user){
        Optional<Supervisor> optSupervisor = Optional.ofNullable(supervisorFacade.findByUser(user));
        System.out.println("verifica si es superfivor");
        return false;
    }
}
