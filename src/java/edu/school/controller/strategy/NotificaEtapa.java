package edu.school.controller.strategy;

import edu.school.ejb.CircularStatusFacadeLocal;
import edu.school.entities.Circular;
import edu.school.entities.CircularStatus;
import edu.school.entities.Supervisor;
import edu.school.entities.User;
import edu.school.utilities.Constantes;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Notificacion de Etapa
 * @author jgcastillo
 */
@Notificacion(Notificaciones.ETAPA)
@Stateless
public class NotificaEtapa implements NotificacionService{

    public NotificaEtapa() {
    }
    
    @Override
    public int notifica(User user, List<Supervisor> supervisores, Circular circular) {
        supervisores.forEach(sup ->{
            System.out.println(sup.getUserId().getUsr() + " es supervisor de "
                + user.getUsr() + " con la circular: " + circular.getCodigoCircular());
        });
        
        return Constantes.CIRCULAR_PENDIENTE_APROBAR_COLEGIO;
    }
    
}
