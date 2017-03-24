package edu.school.controller.strategy;

import edu.school.entities.User;

/**
 *
 * @author jgcastillo
 */
public class NotificaEtapa implements NotificacionIntrf{

    private final User user;

    public NotificaEtapa(User user) {
        this.user = user;
    }
    
    @Override
    public void notifica() {
        System.out.println("Envía al supervisor de colegio para "
                + "su posterior envío, es supervisor de la etapa ...");
    }
    
}
