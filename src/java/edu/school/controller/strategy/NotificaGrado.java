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
 * Notificacion de Grado
 *
 * @author jgcastillo
 */
@Notificacion(Notificaciones.GRADO)
@Stateless
public class NotificaGrado implements NotificacionService {

    private User user;
    private List<Supervisor> supervisores;
    private Circular circular;
    @EJB
    private CircularStatusFacadeLocal circularStatusFacade;

    public NotificaGrado() {
    }

    public NotificaGrado(User user, List<Supervisor> supervisores,
            Circular circular) {
        this.supervisores = supervisores;
        this.user = user;
    }

    @Override
    public void notifica() {
        supervisores.forEach(sup -> {
            System.out.println(sup.getUserId().getUsr() + " es supervisor de "
                    + user.getUsr() + " con la circular: " + circular.getCodigoCircular());
        });

        CircularStatus circularStatus = circularStatusFacade.findByCircular(circular);
        circularStatus.setStatus(Constantes.CIRCULAR_PENDIENTE_APROBAR_GRADO);
        circularStatus.setFecha(new Date());
        circularStatusFacade.edit(circularStatus);
    }

}
