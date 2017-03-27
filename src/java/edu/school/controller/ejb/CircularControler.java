package edu.school.controller.ejb;

import edu.school.controller.strategy.NotificaColegio;
import edu.school.controller.strategy.NotificaEtapa;
import edu.school.controller.strategy.NotificaGrado;
import edu.school.controller.strategy.NotificaSeccion;
import edu.school.controller.strategy.Notificacion;
import edu.school.ejb.StatusSupervisorFacadeLocal;
import edu.school.ejb.SupervisorFacadeLocal;
import edu.school.entities.Supervisor;
import edu.school.entities.User;
import edu.school.utilities.Constantes;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import edu.school.controller.strategy.NotificacionService;
import static edu.school.controller.strategy.Notificaciones.COLEGIO;
import static edu.school.controller.strategy.Notificaciones.ETAPA;
import static edu.school.controller.strategy.Notificaciones.GRADO;
import static edu.school.controller.strategy.Notificaciones.SECCION;
import edu.school.ejb.EmailAccountFacadeLocal;
import edu.school.entities.EmailAccount;
import edu.school.entities.Mail;
import edu.school.utilities.JsfUtils;
import javax.inject.Inject;
import javax.servlet.http.Part;

@Stateless
public class CircularControler implements CircularControllerLocal {

    @EJB
    private SupervisorFacadeLocal supervisorFacade;
    @EJB
    private StatusSupervisorFacadeLocal statusSupervisorFacade;
    @EJB
    private EmailAccountFacadeLocal emailAccountFacade;
    @Inject
    private Mail mail;
    
    @Inject
    @Notificacion(COLEGIO)
    private NotificacionService notificacionColegio;
    @Inject
    @Notificacion(ETAPA)
    private NotificacionService notificacionEtapa;
    @Inject
    @Notificacion(GRADO)
    private NotificacionService notificacionGrado;
    @Inject
    @Notificacion(SECCION)
    private NotificacionService notificacionSeccion;
    
    
    @Override
    public void checkEnvio(final String grupoAEnviar, final User user) {
        Map<String, NotificacionService> notificacionMap = new HashMap<>();
        
        notificacionMap.put(Constantes.GRUPO_COLEGIO, new NotificaColegio(user));
        notificacionMap.put(Constantes.GRUPO_ETAPA, new NotificaEtapa(user));
        notificacionMap.put(Constantes.GRUPO_GRADO, new NotificaGrado(user));
        notificacionMap.put(Constantes.GRUPO_SECCION, new NotificaSeccion(user));
        
        NotificacionService notificacion = notificacionMap.get(grupoAEnviar);
        
        // ¿el que envía (user) es supervisor del grupo?
        // si
            // es supervisor del colegio?
            // si, envía la circular
            // no, envía al supervisor inmediato superior
        
        // no es, envia al supervisor del grupo
            // supervisor del grupo aprueba?
            // si, envía al supervisor inmediato superior
            // no, notifica al emisor
        
        
        
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
    
    @Override
    public boolean isSupervisor(final User user){
        Optional<Supervisor> optSupervisor = Optional.ofNullable(supervisorFacade.findByUser(user));
        return optSupervisor.isPresent();
    }

    @Override
    public boolean isColegioSupervisor(final User user) {
        
        return false;
    }

    @Override
    public Mail prepareMail(final String grupo, final String para, final String subject, 
            final String message, final Part file, final String directory) {
        
        // modificar esto de acuerdo a la cuenta que se tenga acceso
        EmailAccount emailAccount = emailAccountFacade.find(1); 
        
        mail.setUser(emailAccount.getUser());
        mail.setPassword(emailAccount.getPassword());
        String mensaje = " ";
        
        if (null != message) {
            mensaje = message;
        }
        
        mail.setMessage(mensaje);
        mail.setSubject(subject);
        mail.setRecipient(para);
        
        // falta colocar los email's de cada miembro del grupo,
        // para ello hay que identificar el grupo, y sus integrantes
        // y agregarlos al correo.

        String filePath = directory + file.getSubmittedFileName();

        mail.setFilePath(filePath);
        mail.setFileName(file.getSubmittedFileName());
        
        return mail;

    }

    
}
