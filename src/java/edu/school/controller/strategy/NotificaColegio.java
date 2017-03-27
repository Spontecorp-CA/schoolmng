package edu.school.controller.strategy;

import edu.school.entities.User;

/**
 *
 * @author jgcastillo
 */
@Notificacion(Notificaciones.COLEGIO)
public class NotificaColegio implements NotificacionService{

    private User user;
    
    public NotificaColegio(){}
    
    public NotificaColegio(User user){
        this.user = user;
    }
    
    @Override
    public void notifica() {
        System.out.println("No necesita permiso, envía de una vez, "
                + "es supervisor de ...");
    }

}
