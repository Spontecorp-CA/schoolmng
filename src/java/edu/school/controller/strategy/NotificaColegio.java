package edu.school.controller.strategy;

import edu.school.entities.User;

/**
 *
 * @author jgcastillo
 */
public class NotificaColegio implements NotificacionIntrf{

    private final User user;
    
    public NotificaColegio(User user){
        this.user = user;
    }
    
    @Override
    public void notifica() {
        System.out.println("No necesita permiso, envía de una vez, "
                + "es supervisor de ...");
    }

}
