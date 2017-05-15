package edu.school.controller.strategy;

import edu.school.entities.Circular;
import edu.school.entities.Supervisor;
import edu.school.entities.User;
import java.util.List;
import javax.ejb.Stateless;

/**
 * Notificacion de Sección
 * @author jgcastillo
 */
@Notificacion(Notificaciones.SECCION)
@Stateless
public class NotificaSeccion implements NotificacionService{

    private User user;
    private List<Supervisor> supervisores;
    private Circular circular;

    public NotificaSeccion() {
    }

    public NotificaSeccion(User user, List<Supervisor> supervisores, 
            Circular circular) {
        this.supervisores = supervisores;
        this.user = user;
        this.circular = circular;
    }
    
    @Override
    public void notifica() {
        supervisores.forEach(sup ->{
            System.out.println(sup.getUserId().getUsr() + " es supervisor de "
                + user.getUsr() + " con la circular: " + circular.getCodigoCircular());
        });
    }
    
}
