package edu.school.controller.strategy;

import edu.school.utilities.Constantes;
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
    public int notifica() {
        
        return Constantes.CIRCULAR_PENDIENTE_APROBAR_ETAPA;
    }
    
}
