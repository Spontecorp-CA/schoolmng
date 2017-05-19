package edu.school.controller.strategy;

import edu.school.utilities.Constantes;
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
    public int notifica() {
        
        return Constantes.CIRCULAR_PENDIENTE_APROBAR_COLEGIO;
    }

}
