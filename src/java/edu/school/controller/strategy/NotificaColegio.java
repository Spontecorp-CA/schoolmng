package edu.school.controller.strategy;

import edu.school.ejb.CircularStatusFacade;
import edu.school.ejb.CircularStatusFacadeLocal;
import edu.school.entities.Circular;
import edu.school.entities.CircularStatus;
import edu.school.entities.Supervisor;
import edu.school.entities.User;
import edu.school.utilities.Constantes;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author jgcastillo
 */
@Stateless
@Notificacion(Notificaciones.COLEGIO)
public class NotificaColegio implements NotificacionService {

    public NotificaColegio() {
    }

    @Override
    public int notifica(User user, List<Supervisor> supervisores, Circular circular) {
        
        return Constantes.CIRCULAR_PENDIENTE_APROBAR_COLEGIO;
    }

}
