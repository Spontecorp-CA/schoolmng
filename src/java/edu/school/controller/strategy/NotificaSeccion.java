package edu.school.controller.strategy;

import edu.school.entities.User;

/**
 *
 * @author jgcastillo
 */
public class NotificaSeccion implements NotificacionIntrf{

    private final User user;

    public NotificaSeccion(User user) {
        this.user = user;
    }
    
    @Override
    public void notifica() {
        System.out.println("Si no es supervisor, identifica al supervisor de "
                                    + "grado para su revisión y posterior envío");
    }
    
}
