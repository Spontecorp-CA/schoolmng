package edu.school.controller.strategy;

import edu.school.utilities.Constantes;
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
    public int notifica() {
        
        return Constantes.CIRCULAR_PENDIENTE_APROBAR_GRADO;
    }
    
}
