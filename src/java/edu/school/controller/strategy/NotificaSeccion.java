package edu.school.controller.strategy;

import edu.school.ejb.SupervisorFacadeLocal;
import edu.school.entities.Supervisor;
import edu.school.entities.User;
import java.util.Optional;
import javax.ejb.EJB;

/**
 *
 * @author jgcastillo
 */
@Notificacion(Notificaciones.SECCION)
public class NotificaSeccion implements NotificacionService{

    private User user;

    public NotificaSeccion() {
    }

    public NotificaSeccion(User user) {
        this.user = user;
    }
    
    @Override
    public void notifica() {
        System.out.println("Si no es supervisor, identifica al supervisor de "
                                    + "grado para su revisión y posterior envío");
    }
    
}
