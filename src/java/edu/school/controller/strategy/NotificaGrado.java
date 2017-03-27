package edu.school.controller.strategy;

import edu.school.entities.User;

/**
 *
 * @author jgcastillo
 */
@Notificacion(Notificaciones.GRADO)
public class NotificaGrado implements NotificacionService{

    private User user;

    public NotificaGrado() {
    }

    public NotificaGrado(User user) {
        this.user = user;
    }
    
    @Override
    public void notifica() {
        System.out.println("Envía al supervidor de etapa para su revisión y reenvío al "
                + "supervisor e colegio, es supervisor del grado ");
    }
    
}
