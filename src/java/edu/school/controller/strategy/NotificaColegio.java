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
 * 
 * @author jgcastillo
 */
@Notificacion(Notificaciones.COLEGIO)
@Stateless
public class NotificaColegio implements NotificacionService{

    private User user;
    private List<Supervisor> supervisores;
    private Circular circular;
    
    @EJB
    private CircularStatusFacadeLocal circularStatusFacade;

    public NotificaColegio(){}
    
    public NotificaColegio(User user, List<Supervisor> supervisores, Circular circular) {
        this.supervisores = supervisores;
        this.user = user;
        this.circular = circular;
    }
    
    @Override
    public void notifica() {
        supervisores.forEach(sup -> {
            System.out.println(sup.getUserId().getUsr() + " es supervisor de "
                    + user.getUsr() + " con la circular: " + circular.getCodigoCircular());
        });
        CircularStatus circularStatus = circularStatusFacade.findByCircular(circular);
        circularStatus.setStatus(Constantes.CIRCULAR_PENDIENTE_APROBAR_COLEGIO);
        circularStatus.setFecha(new Date());
        circularStatusFacade.edit(circularStatus);
    }

}
