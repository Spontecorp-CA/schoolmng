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
import edu.school.ejb.AlumnoHasRepresentanteFacadeLocal;
import edu.school.ejb.CircularFacadeLocal;
import edu.school.ejb.ColegioFacadeLocal;
import edu.school.ejb.CursoFacadeLocal;
import edu.school.ejb.EmailAccountFacadeLocal;
import edu.school.ejb.EtapaFacadeLocal;
import edu.school.ejb.PeriodoFacadeLocal;
import edu.school.ejb.PlantillaCircularFacadeLocal;
import edu.school.ejb.RecipienteFacadeLocal;
import edu.school.ejb.SeccionFacadeLocal;
import edu.school.ejb.SeccionHasAlumnoFacadeLocal;
import edu.school.entities.Alumno;
import edu.school.entities.Circular;
import edu.school.entities.Colegio;
import edu.school.entities.Curso;
import edu.school.entities.EmailAccount;
import edu.school.entities.Etapa;
import edu.school.entities.Mail;
import edu.school.entities.Periodo;
import edu.school.entities.PlantillaCircular;
import edu.school.entities.Recipiente;
import edu.school.entities.Representante;
import edu.school.entities.Seccion;
import edu.school.entities.SeccionHasAlumno;
import edu.school.entities.StatusSupervisor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
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
    private AlumnoHasRepresentanteFacadeLocal alumnoHasRepresentanteFacade;
    @EJB
    private PlantillaCircularFacadeLocal plantillaCircularFacade;
    @EJB
    private RecipienteFacadeLocal recipienteFacade;
    @EJB
    private CircularFacadeLocal circularFacade;
    @Inject
    private Mail mail;
//    @Inject
//    private Circular circular;
    
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
        boolean isColegioSprv = false;
        Supervisor supervisor = supervisorFacade.findByUser(user);
        Optional<StatusSupervisor> optSttSprv = Optional.ofNullable(
                statusSupervisorFacade.findBySupervisor(supervisor));
        
        if(optSttSprv.isPresent()){
            StatusSupervisor sttSprv = optSttSprv.get();
            if(null != sttSprv.getColegioId()){
                isColegioSprv = true;
            }
        } 
        
        return isColegioSprv;
    }
    
    @Override
    public boolean isEtapaSupervisor(final User user, final Etapa etapa){
        return false;
    }

    @Override
    public boolean isGradoSupervisor(final User user, final Curso grado){
        return false;
    }

    @Override
    public List<Recipiente> mailListColegio() {
        List<Seccion> secciones = getCurrentSecciones();
        return getEmailFromSecciones(secciones);
    }

    @Override
    public List<Recipiente> mailListEtapa(Etapa etapa) {
        List<Seccion> secciones = getCurrentSeccionsByEtapa(etapa);
        return getEmailFromSecciones(secciones);
    }

    @Override
    public List<Recipiente> mailListGrado(Curso grado) {
        List<Seccion> secciones = getCurrentSeccionsByGrado(grado);
        return getEmailFromSecciones(secciones);
    }

    @Override
    public List<Recipiente> mailListSeccion(Seccion seccion) {
        List<Recipiente> recipientes = new ArrayList<>();
        List<Representante> representantes = getCurrentRepresentantes(seccion);
        representantes.stream()
                .map((repr) -> repr.getDatosPersonaId())
                .forEachOrdered((dp) -> {
                    recipientes.add(new Recipiente(dp.getEmail(), circular));
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
    
    @Override 
    public Circular makeCircular(final String grupo, final String nombreGrupo,
            final String para, final String subject, final String message,
            final Part file, final String directory){
        
        EmailAccount emailAccount = emailAccountFacade.find(1);
        Circular circular = new Circular();
        
        circular.setGrupoDestino(grupo);
        circular.setAsunto(subject);
        circular.setFecha(new Date());
        circular.setCodigoCircular(generateCodigoCircular(grupo, nombreGrupo));
        circular.setDestinatario(para);
        circular.setMessage(message);
        circular.setPlantillaCircularId(getPlantillaCircular());
        circular.setEmailAccountId(emailAccount);
        
        String filePath = directory + file.getSubmittedFileName();
        circular.setFilepath(filePath);
        circular.setFilename(file.getSubmittedFileName());
        
        circularFacade.create(circular);
        //circular = circularFacade
        
        recipienteFacade.batchCreate(getRecipientes(grupo, nombreGrupo));
        
        return circular;
    }
    
    private Object getGrupoObjetivo(String grupo, String nombreGrupo){
        Object grupoObjetivo = null;
        switch(grupo){
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
                Seccion seccion = seccionFacade.findByCodigo(nombreGrupo);
                grupoObjetivo = seccion;
                break;
        }
        
        return grupoObjetivo;
    }
    
    private String generateCodigoCircular(String grupo, String subgrupo){
        StringBuilder sb = new StringBuilder();
        DateFormat df = new SimpleDateFormat("yyyyMMHHmm");
        Object grupoObjetivo = getGrupoObjetivo(grupo, subgrupo);
        
        String nombreGrupo = "COL";
        if(grupoObjetivo instanceof Colegio){
            nombreGrupo = "COL";
        } else if(grupoObjetivo instanceof Etapa){
            nombreGrupo = ((Etapa)grupoObjetivo).getNombre();
        } else if(grupoObjetivo instanceof Curso){
            nombreGrupo = ((Curso)grupoObjetivo).getNombre();
        } else if(grupoObjetivo instanceof Seccion){
            nombreGrupo = ((Seccion)grupoObjetivo).getCodigo();
        }
        
        sb.append(grupo).append(nombreGrupo).append(df.format(new Date()));
        return sb.toString().toUpperCase();
    }
    
    private List<Recipiente> getRecipientes(final String grupo, 
            final String nombreGrupo){
        List<Recipiente> recipientes = new ArrayList<>();
        
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
    
    private PlantillaCircular getPlantillaCircular(){
        return plantillaCircularFacade.findByStatus(Constantes.PLANTILLA_CIRCULAR_ACTIVA);
    }

    private Periodo getCurrentPeriod(){
        return periodoFacade.findByStatus(Constantes.PERIODO_ACTIVO);
    }
    
    private List<Seccion> getCurrentSecciones(){
        Periodo periodo = getCurrentPeriod();
        return seccionFacade.findAllOrderedByCurso(periodo);
    }
    
    private List<Seccion> getCurrentSeccionsByGrado(final Curso grado){
        List<Seccion> secciones = getCurrentSecciones();
        return secciones.stream()
                .filter(sec -> sec.getCursoId().equals(grado))
                .collect(Collectors.toList());
    }
    
    private List<Seccion> getCurrentSeccionsByEtapa(final Etapa etapa){
        List<Curso> cursos = cursoFacade.findAllByEtapa(etapa);
        final List<Seccion> secciones = new ArrayList<>();
        cursos.stream()
                .map((grado) -> getCurrentSeccionsByGrado(grado))
                        .forEachOrdered((seccionesEnCurso) -> {
                            secciones.addAll(seccionesEnCurso);
                        });
        return secciones;
    }
    
    private List<Alumno> getCurrentAlumnosEnSeccion(Seccion seccion){
        List<SeccionHasAlumno> alumnosList = seccionHasAlumnoFacade.findAll(seccion);
        List<Alumno> alumnos = new ArrayList<>();
        alumnosList.forEach((sha) -> {
            alumnos.add(sha.getAlumnoId());
        });
        
        return alumnos;
    }
    
    private List<Representante> getCurrentRepresentantes(Seccion seccion){
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
    
    private List<Recipiente> getEmailFromSecciones(List<Seccion> secciones){
        List<Representante> representantes = new ArrayList<>();
        List<Recipiente> recipientes = new ArrayList<>();
        secciones.stream()
                .map((seccion) -> getCurrentRepresentantes(seccion))
                .forEachOrdered((representantesEnSeccion) -> {
                    representantes.addAll(representantesEnSeccion);
                });
        representantes.stream()
                .map((repr) -> repr.getDatosPersonaId())
                .forEachOrdered((dp) -> {
                    recipientes.add(new Recipiente(dp.getEmail(), circular));
                });
        return recipientes;
    }
    
}
