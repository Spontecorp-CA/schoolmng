package edu.school.controller.ejb;

import edu.school.controller.strategy.NotificaColegio;
import edu.school.controller.strategy.NotificaEtapa;
import edu.school.controller.strategy.NotificaGrado;
import edu.school.controller.strategy.NotificaSeccion;
import edu.school.controller.strategy.NotificacionIntrf;
import edu.school.ejb.StatusSupervisorFacadeLocal;
import edu.school.ejb.SupervisorFacadeLocal;
import edu.school.entities.Colegio;
import edu.school.entities.Curso;
import edu.school.entities.Etapa;
import edu.school.entities.StatusSupervisor;
import edu.school.entities.Supervisor;
import edu.school.entities.User;
import edu.school.utilities.Constantes;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class CircularControler implements CircularControllerLocal {

    @EJB
    private SupervisorFacadeLocal supervisorFacade;
    @EJB
    private StatusSupervisorFacadeLocal statusSupervisorFacade;

    @Override
    public void checkEnvio(final String grupoAEnviar, final User user) {

        Optional<Supervisor> optSupervisor = Optional.ofNullable(supervisorFacade.findByUser(user));
        Map<String, NotificacionIntrf> notificacionMap = new HashMap<>();
        
        notificacionMap.put(Constantes.GRUPO_COLEGIO, new NotificaColegio(user));
        notificacionMap.put(Constantes.GRUPO_ETAPA, new NotificaEtapa(user));
        notificacionMap.put(Constantes.GRUPO_GRADO, new NotificaGrado(user));
        notificacionMap.put(Constantes.GRUPO_SECCION, new NotificaSeccion(user));
        
        NotificacionIntrf notificacion = notificacionMap.get(grupoAEnviar);
        
        if(!notificacion.isSupervisor(user)){
            notificacion.notifica();
        }
        
//        if (!optSupervisor.isPresent()) {
//            System.out.printf("El usuario %s no es supervisor ", user.getUsr());
//
//        } else {
//            Supervisor supervisor = optSupervisor.get();
//            Optional<StatusSupervisor> optStatusSupervisorActual = Optional
//                    .ofNullable(statusSupervisorFacade
//                            .findBySupervisor(supervisor));
//
//            if (optStatusSupervisorActual.isPresent()) {
//                StatusSupervisor ssActual = optStatusSupervisorActual.get();
//                switch (grupoAEnviar) {
//                    case Constantes.GRUPO_COLEGIO:
//                        Optional<Colegio> optCol = Optional.ofNullable(ssActual.getColegioId());
//                        if (optCol.isPresent()) {
//                            Colegio col = optCol.get();
//                            System.out.println("No necesita permiso, envía de una vez, "
//                                    + "es supervisor de " + col.getNombre());
//                        } else {
//                            System.out.println("No puede enviar circulares al colegio");
//                        }
//                        break;
//                    case Constantes.GRUPO_ETAPA:
//                        Optional<Etapa> optEtapa = Optional.ofNullable(ssActual.getEtapaId());
//                        if (optEtapa.isPresent()) {
//                            Etapa etapa = optEtapa.get();
//                            System.out.println("Envía al supervisor de colegio para "
//                                    + "su posterior envío, es supervisor de la etapa "
//                                    + etapa.getNombre());
//                        } else {
//                            System.out.println("No puede enviar circulares a la etapa");
//                        }
//                        break;
//                    case Constantes.GRUPO_GRADO:
//                        Optional<Curso> optGrado = Optional.ofNullable(ssActual.getCursoId());
//                        if (optGrado.isPresent()) {
//                            Curso grado = optGrado.get();
//                            System.out.println("Envía al supervidor de etapa para su revisión y reenvío al "
//                                    + "supervisor e colegio, es supervisor del grado "
//                                    + grado.getNombre());
//                        } else {
//                            System.out.println("No puede enviar cirsulares al grado");
//                        }
//                        break;
//                    default:
//                        optGrado = Optional.ofNullable(ssActual.getCursoId());
//                        if (optGrado.isPresent()) {
//                            Curso grado = optGrado.get();
//                            System.out.println("Si es supervisor de grado, envía al de etapa "
//                                    + "para su revisión y posterior envío, es supervisor del grado "
//                                    + grado.getNombre());
//                        } else {
//                            System.out.println("Si no es supervisor, identifica al supervisor de "
//                                    + "grado para su revisión y posterior envío");
//                        }
//                }
//            } else {
//                System.out.printf("El usuario %s actualmente no es supervisor", user.getUsr());
//            }
//
//        }

    }

}
