package edu.school.controller.ejb;

import edu.school.controller.strategy.NotificaColegio;
import edu.school.controller.strategy.NotificaEtapa;
import edu.school.controller.strategy.NotificaGrado;
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
import edu.school.ejb.AlumnoHasRepresentanteFacadeLocal;
import edu.school.ejb.AutorizacionFacadeLocal;
import edu.school.ejb.CircularFacadeLocal;
import edu.school.ejb.CircularStatusFacadeLocal;
import edu.school.ejb.ColegioFacadeLocal;
import edu.school.ejb.CursoFacadeLocal;
import edu.school.ejb.DocenteFacadeLocal;
import edu.school.ejb.EmailAccountFacadeLocal;
import edu.school.ejb.EtapaFacadeLocal;
import edu.school.ejb.PeriodoFacadeLocal;
import edu.school.ejb.PlantillaCircularFacadeLocal;
import edu.school.ejb.SeccionFacadeLocal;
import edu.school.ejb.SeccionHasAlumnoFacadeLocal;
import edu.school.ejb.SeccionHasDocenteFacadeLocal;
import edu.school.entities.Alumno;
import edu.school.entities.Autorizacion;
import edu.school.entities.Circular;
import edu.school.entities.CircularStatus;
import edu.school.entities.Colegio;
import edu.school.entities.Curso;
import edu.school.entities.EmailAccount;
import edu.school.entities.Etapa;
import edu.school.entities.Mail;
import edu.school.entities.Periodo;
import edu.school.entities.PlantillaCircular;
import edu.school.entities.Representante;
import edu.school.entities.Seccion;
import edu.school.entities.SeccionHasAlumno;
import edu.school.entities.StatusSupervisor;
import edu.school.utilities.JsfUtils;
import edu.school.utilities.LogFiler;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
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
    @EJB
    private PeriodoFacadeLocal periodoFacade;
    @EJB
    private SeccionFacadeLocal seccionFacade;
    @EJB
    private CursoFacadeLocal cursoFacade;
    @EJB
    private EtapaFacadeLocal etapaFacade;
    @EJB
    private ColegioFacadeLocal colegioFacade;
    @EJB
    private SeccionHasAlumnoFacadeLocal seccionHasAlumnoFacade;
    @EJB
    private SeccionHasDocenteFacadeLocal seccionHasDocenteFacade;
    @EJB
    private AlumnoHasRepresentanteFacadeLocal alumnoHasRepresentanteFacade;
    @EJB
    private PlantillaCircularFacadeLocal plantillaCircularFacade;
    @EJB
    private CircularFacadeLocal circularFacade;
    @EJB
    private CircularStatusFacadeLocal circularStatusFacade;
    @EJB
    private AutorizacionFacadeLocal autorizacionFacade;
    @EJB
    private DocenteFacadeLocal docenteFacade;
    @Inject
    private Mail mail;

    private NotificacionService notificacionSeccion;
    private Seccion seccion;
    private final Map<String, NotificacionService> notificacionMap;

    private static final LogFiler LOGGER = LogFiler.getInstance();

    public CircularControler() {
        this.notificacionMap = new HashMap<>();
    }

    @PostConstruct
    public void init() {
        makeNotificacionMap();
    }

    public Seccion getSeccion() {
        return seccion;
    }

    @Override
    public void setSeccion(Seccion seccion) {
        this.seccion = seccion;
    }

    @Override
    public void checkEnvio(final String grupoAEnviar, final User user,
            final List<Supervisor> supervisorList, Circular circular) {

        String notificaA = "";
        for(Supervisor sup : supervisorList){
            if(isColegioSupervisor(sup.getUserId())){
                notificaA = Constantes.GRUPO_COLEGIO;
            } else if(isEtapaSupervisor(sup.getUserId())){
                notificaA = Constantes.GRUPO_ETAPA;
            } else {
                notificaA = Constantes.GRUPO_GRADO;
            }
            
        }
        
        NotificacionService notificacion = notificacionMap.get(notificaA);
        int status = notificacion.notifica();
        CircularStatus cs = circularStatusFacade.findByCircular(circular);
        cs.setStatus(status);
        cs.setFecha(new Date());
        circularStatusFacade.edit(cs);
    }

    private void makeNotificacionMap() {
        notificacionMap.put(Constantes.GRUPO_COLEGIO, new NotificaColegio());
        notificacionMap.put(Constantes.GRUPO_ETAPA, new NotificaEtapa());
        notificacionMap.put(Constantes.GRUPO_GRADO, new NotificaGrado());
        notificacionMap.put(Constantes.GRUPO_SECCION, new NotificaGrado());
    }

    @Override
    public boolean isSupervisor(final User user) {
        Optional<Supervisor> optSupervisor = Optional.ofNullable(supervisorFacade.findByUser(user));
        return optSupervisor.isPresent();
    }

    @Override
    public boolean isColegioSupervisor(final User user) {
        boolean isColegioSprv = false;

        Optional<StatusSupervisor> optSttSprv = getStatusSupervisor(user);
        if (optSttSprv.isPresent()) {
            StatusSupervisor sttSprv = optSttSprv.get();
            if (null != sttSprv.getColegioId()) {
                isColegioSprv = true;
            }
        }

        return isColegioSprv;
    }

    @Override
    public boolean isEtapaSupervisor(final User user) {
        boolean isEtapaSprv = false;

        Optional<StatusSupervisor> optSttSprv = getStatusSupervisor(user);
        if (optSttSprv.isPresent()) {
            StatusSupervisor sttSprv = optSttSprv.get();
            if (null != sttSprv.getEtapaId()) {
                isEtapaSprv = true;
            }
        }

        return isEtapaSprv;
    }

    @Override
    public boolean isGradoSupervisor(final User user) {
        boolean isGradoSpvr = false;

        Optional<StatusSupervisor> optSttSprv = getStatusSupervisor(user);
        if (optSttSprv.isPresent()) {
            StatusSupervisor sttSprv = optSttSprv.get();
            if (null != sttSprv.getCursoId()) {
                isGradoSpvr = true;
            }
        }

        return isGradoSpvr;
    }

    @Override
    public List<String> mailListColegio() {
        List<Seccion> secciones = getCurrentSecciones();
        return getEmailFromSecciones(secciones);
    }

    @Override
    public List<String> mailListEtapa(Etapa etapa) {
        List<Seccion> secciones = getCurrentSeccionsByEtapa(etapa);
        return getEmailFromSecciones(secciones);
    }

    @Override
    public List<String> mailListGrado(Curso grado) {
        List<Seccion> secciones = getCurrentSeccionsByGrado(grado);
        return getEmailFromSecciones(secciones);
    }

    @Override
    public List<String> mailListSeccion(Seccion seccion) {
        List<String> recipientes = new ArrayList<>();
        List<Representante> representantes = getCurrentRepresentantes(seccion);
        representantes.stream()
                .map((repr) -> repr.getDatosPersonaId())
                .forEachOrdered((dp) -> {
                    recipientes.add(dp.getEmail());
                });
        return recipientes;
    }

    @Override
    public Mail prepareMail(final String grupo, final String nombreGrupo,
            final String para, final String subject, final String message,
            final Part file, final String directory) {

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

    private Optional<StatusSupervisor> getStatusSupervisor(User user) {
        Optional<StatusSupervisor> optSttSprv = Optional.empty();
        Optional<Supervisor> optSupervisor = Optional
                .ofNullable(supervisorFacade.findByUser(user));

        if (optSupervisor.isPresent()) {
            Supervisor supervisor = optSupervisor.get();
            optSttSprv = Optional.ofNullable(
                    statusSupervisorFacade.findBySupervisor(supervisor));
        }

        return optSttSprv;
    }

    /**
     * Construye la circular que se va a enviar
     *
     * @param grupo
     * @param nombreGrupo
     * @param para
     * @param subject
     * @param message
     * @param file
     * @param directory
     * @param user
     * @return
     */
    @Override
    public Circular makeCircular(final String grupo, final String nombreGrupo,
            final String para, final String subject, final String message,
            final Part file, final String directory, final User user) {

        EmailAccount emailAccount = emailAccountFacade.find(1);
        Circular circular = new Circular();

        circular.setGrupoDestino(grupo);
        circular.setSubgrupoNombre(nombreGrupo);
        circular.setAsunto(subject);
        circular.setFecha(new Date());
        String codigo = generateCodigoCircular(grupo, nombreGrupo);
        circular.setCodigoCircular(codigo);
        circular.setDestinatario(para);
        circular.setMessage(message);
        circular.setPlantillaCircularId(getPlantillaCircular());
        circular.setEmailAccountId(emailAccount);
        circular.setUserId(user);

        if (null != file) {
            String filePath = directory + file.getSubmittedFileName();
            circular.setFilepath(filePath);
            circular.setFilename(file.getSubmittedFileName());
        } else {
            circular.setFilepath("");
            circular.setFilename("");
        }

        circularFacade.create(circular);

        circular = circularFacade.findCircularByCodigoCircular(codigo);

        CircularStatus status = new CircularStatus();
        status.setCircularId(circular);
        status.setFecha(circular.getFecha());
        status.setStatus(Constantes.CIRCULAR_NO_ENVIADA);

        circularStatusFacade.create(status);
        status = circularStatusFacade.findByCircular(circular);

        List<Supervisor> supervisores = findAllInmediateSupervisor(user);

        for (Supervisor supervisor : supervisores) {
            Autorizacion autorizacion = new Autorizacion();
            autorizacion.setCircularStatusId(status);
            autorizacion.setSupervisorId(supervisor);
            autorizacionFacade.create(autorizacion);
        }

        return circular;
    }

    private Object getGrupoObjetivo(String grupo, String nombreGrupo) {
        Object grupoObjetivo = null;
        switch (grupo) {
            case Constantes.GRUPO_COLEGIO:
                Colegio colegio = colegioFacade.find(1);
                grupoObjetivo = colegio;
                break;
            case Constantes.GRUPO_ETAPA:
                Etapa etapa = etapaFacade.findByNombre(nombreGrupo);
                grupoObjetivo = etapa;
                break;
            case Constantes.GRUPO_GRADO:
                Curso curso = cursoFacade.findByName(nombreGrupo);
                grupoObjetivo = curso;
                break;
            case Constantes.GRUPO_SECCION:
                Seccion seccionFound = seccionFacade.findByCodigo(nombreGrupo);
                grupoObjetivo = seccionFound;
                break;
        }

        return grupoObjetivo;
    }

    private String generateCodigoCircular(String grupo, String subgrupo) {
        StringBuilder sb = new StringBuilder();
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
        Object grupoObjetivo = getGrupoObjetivo(grupo, subgrupo);

        String nombreGrupo = "COL";
        if (grupoObjetivo instanceof Colegio) {
            nombreGrupo = "COL";
        } else if (grupoObjetivo instanceof Etapa) {
            nombreGrupo = ((Etapa) grupoObjetivo).getNombre();
        } else if (grupoObjetivo instanceof Curso) {
            nombreGrupo = ((Curso) grupoObjetivo).getNombre();
        } else if (grupoObjetivo instanceof Seccion) {
            nombreGrupo = ((Seccion) grupoObjetivo).getCodigo();
        }

        sb.append(grupo).append(nombreGrupo).append(":").append(df.format(new Date()));
        return sb.toString().toUpperCase();
    }

    private List<String> getRecipientes(final String grupo,
            final String nombreGrupo) {
        List<String> recipientes = new ArrayList<>();

        Object grupoObjetivo = getGrupoObjetivo(grupo, nombreGrupo);

        if (grupoObjetivo instanceof Colegio) {
            recipientes = mailListColegio();
        } else if (grupoObjetivo instanceof Etapa) {
            recipientes = mailListEtapa((Etapa) grupoObjetivo);
        } else if (grupoObjetivo instanceof Curso) {
            recipientes = mailListGrado((Curso) grupoObjetivo);
        } else if (grupoObjetivo instanceof Seccion) {
            recipientes = mailListSeccion((Seccion) grupoObjetivo);
        }

        return recipientes;
    }

    private PlantillaCircular getPlantillaCircular() {
        return plantillaCircularFacade.findByStatus(Constantes.PLANTILLA_CIRCULAR_ACTIVA);
    }

    private Periodo getCurrentPeriod() {
        return periodoFacade.findByStatus(Constantes.PERIODO_ACTIVO);
    }

    private List<Seccion> getCurrentSecciones() {
        Periodo periodo = getCurrentPeriod();
        return seccionFacade.findAllOrderedByCurso(periodo);
    }

    private List<Seccion> getCurrentSeccionsByGrado(final Curso grado) {
        List<Seccion> secciones = getCurrentSecciones();
        return secciones.stream()
                .filter(sec -> sec.getCursoId().equals(grado))
                .collect(Collectors.toList());
    }

    private List<Seccion> getCurrentSeccionsByEtapa(final Etapa etapa) {
        List<Curso> cursos = cursoFacade.findAllByEtapa(etapa);
        final List<Seccion> secciones = new ArrayList<>();
        cursos.stream()
                .map((grado) -> getCurrentSeccionsByGrado(grado))
                .forEachOrdered((seccionesEnCurso) -> {
                    secciones.addAll(seccionesEnCurso);
                });
        return secciones;
    }

    private List<Alumno> getCurrentAlumnosEnSeccion(Seccion seccion) {
        List<SeccionHasAlumno> alumnosList = seccionHasAlumnoFacade.findAll(seccion);
        List<Alumno> alumnos = new ArrayList<>();
        alumnosList.forEach((sha) -> {
            alumnos.add(sha.getAlumnoId());
        });

        return alumnos;
    }

    private List<Representante> getCurrentRepresentantes(Seccion seccion) {
        List<Alumno> alumnos = getCurrentAlumnosEnSeccion(seccion);
        List<Representante> representantes = new ArrayList<>();
        alumnos.stream()
                .map((alumno) -> alumnoHasRepresentanteFacade.findAll(alumno))
                .forEachOrdered((almHasRepList) -> {
                    almHasRepList.forEach((almHasRep) -> {
                        representantes.add(almHasRep.getRepresentanteId());
                    });
                });
        return representantes;
    }

    private List<String> getEmailFromSecciones(List<Seccion> secciones) {
        List<Representante> representantes = new ArrayList<>();
        List<String> recipientes = new ArrayList<>();
        secciones.stream()
                .map((sec) -> getCurrentRepresentantes(sec))
                .forEachOrdered((representantesEnSeccion) -> {
                    representantes.addAll(representantesEnSeccion);
                });
        representantes.stream()
                .map((repr) -> repr.getDatosPersonaId())
                .forEachOrdered((dp) -> {
                    recipientes.add(dp.getEmail());
                });
        return recipientes;
    }

    @Override
    public boolean sendCircular(final Circular circular, final List<String> destinatarios) {
        boolean enviado = false;
//        String grupoDestino = circular.getGrupoDestino();
//        User user = circular.getUserId();
//        Map<String, NotificacionService> notificacionMap = new HashMap<>();
//
//        notificacionMap.put(Constantes.GRUPO_COLEGIO, new NotificaColegio(user));
//        notificacionMap.put(Constantes.GRUPO_ETAPA, new NotificaEtapa(user));
//        notificacionMap.put(Constantes.GRUPO_GRADO, new NotificaGrado(user));
//        notificacionMap.put(Constantes.GRUPO_SECCION, new NotificaSeccion(user));
//
//       
//        NotificacionService notificacion = notificacionMap.get(grupoDestino);
//        notificacion.notifica();
//        
//        System.out.println("Ha llegado la circular " + circular.getCodigoCircular() 
//                + " para los siguientes destinatarios: ");
//        destinatarios.forEach(dest -> {
//            System.out.println(dest);
//        });
//        
//        enviado = true;

        // obtener la circular
        // determina supervisor
        // envia circular
        // modificar esto para colocar la cuenta que va estar válida
//        EmailAccount emailAccount = emailAccountFacade.find(1);
//        StringBuilder sb = new StringBuilder();
//
//        for (int i = 0; i < destinatarios.size(); i++) {
//            sb.append(destinatarios.get(i));
//            if (i != (destinatarios.size() - 1)) {
//                sb.append(",");
//            }
//        }
//        String destinatariosList = sb.toString();
//
//        System.out.println("la lista de destinatarios es " + destinatariosList);
//
//        try {
//            Properties prop = new Properties();
//            prop.put("mail.smtp.host", emailAccount.getSmtp());
//            prop.setProperty("mail.smtp.starttls.enable", "true");
//            prop.setProperty("mail.smtp.port", emailAccount.getPuerto());
//            prop.setProperty("mail.smtp.user", emailAccount.getUser());
//            prop.setProperty("mail.smtp.auth", "true");
//
//            Session session = Session.getDefaultInstance(prop, null);
//            // parte con el text
//            MimeBodyPart text = new MimeBodyPart();
//            text.setContent(circular.getMessage(), "text/html; charset=utf-8");
//
//            // parte con el adjunto
//            MimeBodyPart adjunto = new MimeBodyPart();
//
//            if (circular.getFilepath() != null && !circular.getFilepath().equals("")) {
//                adjunto.setDataHandler(new DataHandler(
//                        new FileDataSource(circular.getFilepath())));
//                adjunto.setFileName(circular.getFilename());
//            }
//
//            // se crea el combinado
//            MimeMultipart mimeMultipart = new MimeMultipart();
//            if (circular.getFilepath() != null && !circular.getFilepath().equals("")) {
//                mimeMultipart.addBodyPart(adjunto);
//            }
//
//            mimeMultipart.addBodyPart(text);
//
//            MimeMessage message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(emailAccount.getUser()));
//            if (circular.getDestinatario() != null) {
//                message.setRecipient(Message.RecipientType.TO,
//                        new InternetAddress(circular.getDestinatario()));
//            }
//            
//            message.setRecipients(Message.RecipientType.BCC,
//                    InternetAddress.parse(destinatariosList, true));
//
//            message.setSubject(circular.getAsunto());
//            message.setContent(mimeMultipart);
//
//            Transport transport = session.getTransport("smtp");
//            transport.connect(emailAccount.getUser(), emailAccount.getPassword());
//            transport.sendMessage(message, message.getAllRecipients());
//            transport.close();
//            enviado = true;
//        } catch (MessagingException ex) {
//            LOGGER.logger.log(Level.SEVERE, "Email no enviado", ex);
//            JsfUtils.messageError("Correo no enviado, ha ocurrido un error: " + ex.getMessage());
//            enviado = false;
//        }
        return enviado;
    }

    @Override
    public Supervisor findInmmediateSupervisor(User user) {
        Optional<StatusSupervisor> optSup;
        Supervisor inmediate = null;
        boolean found = true;
        
        if (isSupervisor(user)) {
            if (isColegioSupervisor(user)) {
                inmediate = lookupCargoSupervisor(user).get().getSupervisorId();
            } else if (isEtapaSupervisor(user)) {
                Colegio colegio = colegioFacade.find(1);
                optSup = Optional.ofNullable(statusSupervisorFacade.findByGrupo(colegio));
                if (optSup.isPresent()) {
                    inmediate = optSup.get().getSupervisorId();
                } else {
                    found = false;
                }
            } else if (isGradoSupervisor(user)) {
                Supervisor supervisor = supervisorFacade.findByUser(user);
                StatusSupervisor sttSprv = statusSupervisorFacade.findBySupervisor(supervisor);
                Etapa etapa = sttSprv.getCursoId().getEtapaId();

                optSup = Optional.ofNullable(statusSupervisorFacade.findByGrupo(etapa));
                if (optSup.isPresent()) {
                    inmediate = optSup.get().getSupervisorId();
                } else {
                    found = false;
                }
            }
        } else {
            // busca el supervisor del grado y se lo asigna
            Curso curso = findCursoBySeccion(seccion);
            optSup = Optional.ofNullable(statusSupervisorFacade.findByGrupo(curso));
            if (optSup.isPresent()) {
                inmediate = optSup.get().getSupervisorId();
            } else {
                found = false;
            }
        }
        
        if(!found){
            JsfUtils.messageError("No puede enviar circulares, "
                    + "no tiene supervisor inmediato, contácte al admnistrador");
        }

        return inmediate;
    }

    @Override
    public List<Supervisor> findAllInmediateSupervisor(User user) {
        List<Supervisor> inmediates = null;
        try {
            if (isSupervisor(user)) {
                if (isGradoSupervisor(user)) {
                    inmediates = new ArrayList<>();
                    Supervisor supervUser = supervisorFacade.findByUser(user);
                    StatusSupervisor sttSprv = statusSupervisorFacade.findBySupervisor(supervUser);
                    Etapa etapa = sttSprv.getCursoId().getEtapaId();

                    Optional<Supervisor> optSuperEtapa = Optional.ofNullable(statusSupervisorFacade
                            .findByGrupo(etapa).getSupervisorId());

                    if (optSuperEtapa.isPresent()) {
                        inmediates.add(optSuperEtapa.get());
                    }
                } else {
                    inmediates = getAllColegioSupervisores();
                }
            } else {
                inmediates = new ArrayList<>();
                // busca el supervisor del grado y se lo asigna
                Curso curso = findCursoBySeccion(seccion);

                Supervisor supervisor = statusSupervisorFacade
                        .findByGrupo(curso).getSupervisorId();
                inmediates.add(supervisor);
            }

        } catch (Exception e) {
            JsfUtils.messageError("No puede enviar circulares, "
                    + "no tiene supervisor inmediato, contácte al admnistrador");
        }

        return inmediates;
    }

    private Curso findCursoBySeccion(Seccion seccion) {
        return seccion.getCursoId();
    }

    @Override
    public Optional<StatusSupervisor> lookupCargoSupervisor(User user) {
        return getStatusSupervisor(user);
    }

    private List<Supervisor> getAllColegioSupervisores() {
        List<Supervisor> colegioSupervisores = new ArrayList<>();
        List<StatusSupervisor> supervisoresColegio = statusSupervisorFacade
                .findColegioSupervisor(Constantes.SUPERVISOR_ACTIVO);

        for (StatusSupervisor ss : supervisoresColegio) {
            if (null != ss.getColegioId()) {
                colegioSupervisores.add(ss.getSupervisorId());
            }
        }

        return colegioSupervisores;
    }
}
