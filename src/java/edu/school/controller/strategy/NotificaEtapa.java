package edu.school.controller.strategy;

import edu.school.entities.User;

/**
 *
 * @author jgcastillo
 */
@Notificacion(Notificaciones.ETAPA)
public class NotificaEtapa implements NotificacionService{

    private User user;

    public NotificaEtapa() {
    }

    public NotificaEtapa(User user) {
        this.user = user;
    }
    
    @Override
    public void notifica() {
        System.out.println("Envía al supervisor de colegio para "
                + "su posterior envío, es supervisor de la etapa ...");
    }
    
}
