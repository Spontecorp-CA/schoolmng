package edu.school.controller.strategy;

import edu.school.entities.Circular;
import edu.school.entities.Supervisor;
import edu.school.entities.User;
import edu.school.utilities.Constantes;
import java.util.List;
import javax.ejb.Stateless;

/**
 * Notificacion de Sección
 * @author jgcastillo
 */
@Notificacion(Notificaciones.SECCION)
@Stateless
public class NotificaSeccion implements NotificacionService{

    public NotificaSeccion() {
    }
    
    @Override
    public int notifica(User user, List<Supervisor> supervisores, 
            Circular circular) {
        supervisores.forEach(sup ->{
            System.out.println(sup.getUserId().getUsr() + " es supervisor de "
                + user.getUsr() + " con la circular: " + circular.getCodigoCircular());
        });
        
        return Constantes.CIRCULAR_PENDIENTE_APROBAR_GRADO;
    }
    
}
