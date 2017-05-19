package edu.school.controller.strategy;

import edu.school.utilities.Constantes;
import javax.ejb.Stateless;

/**
 * Notificacion de Grado
 *
 * @author jgcastillo
 */
@Notificacion(Notificaciones.GRADO)
@Stateless
public class NotificaGrado implements NotificacionService {

    public NotificaGrado() {
    }

    @Override
    public int notifica() {
        
        return Constantes.CIRCULAR_PENDIENTE_APROBAR_GRADO;
    }

}
