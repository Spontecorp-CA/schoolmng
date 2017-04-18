package edu.school.controller.config;

import edu.school.ejb.AdministrativoFacadeLocal;
import edu.school.ejb.ColegioFacadeLocal;
import edu.school.ejb.CursoFacadeLocal;
import edu.school.ejb.DatosPersonaFacadeLocal;
import edu.school.ejb.DocenteFacadeLocal;
import edu.school.ejb.EtapaFacadeLocal;
import edu.school.ejb.SeccionHasDocenteFacadeLocal;
import edu.school.ejb.StatusSupervisorFacadeLocal;
import edu.school.ejb.SupervisorFacadeLocal;
import edu.school.ejb.UserFacadeLocal;
import edu.school.entities.Administrativo;
import edu.school.entities.Colegio;
import edu.school.entities.Curso;
import edu.school.entities.DatosPersona;
import edu.school.entities.Docente;
import edu.school.entities.Etapa;
import edu.school.entities.Seccion;
import edu.school.entities.StatusSupervisor;
import edu.school.entities.Supervisor;
import edu.school.entities.User;
import edu.school.excepciones.DocenteNotFoundException;
import edu.school.excepciones.SupervisorNotFoundException;
import edu.school.utilities.Constantes;
import edu.school.utilities.JsfUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

@Named
@ViewScoped
public class AdminSupervisorAgregar implements Serializable {

    @EJB
    private AdministrativoFacadeLocal administrativoFacade;
    @EJB
    private DocenteFacadeLocal docenteFacade;
    @EJB
    private UserFacadeLocal userFacade;
    @EJB
    private SupervisorFacadeLocal supervisorFacade;
    @EJB
    private StatusSupervisorFacadeLocal statusSupervisorFacade;
    @EJB
    private DatosPersonaFacadeLocal datosPersonaFacade;
    @EJB
    private SeccionHasDocenteFacadeLocal seccionHasDocenteFacade;
    @EJB
    private CursoFacadeLocal cursoFacade;
    @EJB
    private EtapaFacadeLocal etapaFacade;
    @EJB
    private ColegioFacadeLocal colegioFacade;

    private String supervisor = "";
    private String nivel = Constantes.GRUPO_GRADO;
    private String grupo;
    private List<String> usuarios;
    private List<String[]> supervisores;
    private SupervisorData selected;
    private static final Logger LOGGER = Logger.getLogger(AdminSupervisorAgregar.class.getName());
    private String rifColegio;

    @PostConstruct
    public void init() {
        makeUserList();
        makeSupervisores();
        rifColegio = findRifColegio();
    }
    
    private String findRifColegio(){
        List<Colegio> colegios = colegioFacade.findAll();
        return colegios.get(0).getRif();
    }

    private List<String> makeUserList() {

        List<Administrativo> administrativos = administrativoFacade.findAll();
        List<Docente> docentes = docenteFacade.findAll();
        usuarios = new ArrayList<>();

        StringBuilder sb;
        DatosPersona dp;
        for (Administrativo admin : administrativos) {
            if (admin.getId() != null) {
                sb = new StringBuilder();
                dp = admin.getDatosPersonaId();
                sb.append(dp.getApellido());
                sb.append(", ").append(dp.getNombre());
                sb.append(": ").append(dp.getCi());
                usuarios.add(sb.toString());
            }
        }

        for (Docente admin : docentes) {
            if (admin.getId() != null) {
                sb = new StringBuilder();
                dp = admin.getDatosPersonaId();
                sb.append(dp.getApellido());
                sb.append(", ").append(dp.getNombre());
                sb.append(": ").append(dp.getCi());
                usuarios.add(sb.toString());
            }
        }

        Collections.sort(usuarios);
        return usuarios;
    }

    private void makeSupervisores() {
        supervisores = new ArrayList<>();
        List<StatusSupervisor> ssList = statusSupervisorFacade
                .findAllByStatus(Constantes.SUPERVISOR_ACTIVO);
        for (StatusSupervisor ss : ssList) {
            String[] datos = new String[2];
            StringBuffer sbnombre = new StringBuffer();
            StringBuffer sbgrupo = new StringBuffer();
            User user = ss.getSupervisorId().getUserId();
            DatosPersona dp = datosPersonaFacade.findByCi(user.getCi());
            sbnombre.append(dp.getApellido()).append(", ").append(dp.getNombre());
            datos[0] = sbnombre.toString();
            if (null != ss.getColegioId()) {
                datos[1] = "Colegio";
            } else if (null != ss.getEtapaId()) {
                datos[1] = ss.getEtapaId().getNombre();
            } else if (null != ss.getCursoId()) {
                datos[1] = ss.getCursoId().getNombre();
            }
            supervisores.add(datos);
        }

    }

    public List<String> completeUser(String query) {
        List<String> filtrados = new ArrayList<>();

        for (String user : usuarios) {
            if (user.toLowerCase().contains(query.toLowerCase())) {
                filtrados.add(user);
            }
        }
        return filtrados;
    }

    public List<String[]> getSupervisores() {
        if(supervisores == null){
            makeSupervisores();
        }
        return supervisores;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public List<String> getUsuarios() {
        return usuarios;
    }

    public SupervisorData getSelected() {
        return selected;
    }

    public void setSelected(SupervisorData selected) {
        this.selected = selected;
    }

    public List<String> getGrupos() {
        List<String> grupos = new ArrayList<>();
        Map<Etapa, List<Curso>> cursosMap = mapearCursos();

        switch (nivel) {
            case Constantes.GRUPO_GRADO:
                for (Map.Entry<Etapa, List<Curso>> mapa : cursosMap.entrySet()) {
                    List<Curso> cursos = mapa.getValue();
                    cursos.stream().sorted((Curso c1, Curso c2) 
                            -> c1.getNombre().compareTo(c2.getNombre()))
                            .forEach(cur -> grupos.add(cur.getNombre()));

                }
                break;
            case Constantes.GRUPO_ETAPA:
                for (Map.Entry<Etapa, List<Curso>> mapa : cursosMap.entrySet()) {
                    grupos.add(mapa.getKey().getNombre());
                }
                break;
            case Constantes.GRUPO_COLEGIO:
                grupos.add("Colegio");
                break;
        }

        return grupos;
    }

    public void createSuper() {
        if (null != supervisor) {
            if (!supervisor.isEmpty()) {
                Object candidato = findCandidato(supervisor);

                Object[] subject = findUser(candidato);
                User user = (User) subject[0];
                DatosPersona dp = (DatosPersona) subject[1];

                Supervisor superv = new Supervisor();
                superv.setUserId(user);

                StatusSupervisor ss;
                switch (nivel) {
                    case Constantes.GRUPO_GRADO:
                        Curso grado = cursoFacade.findByName(grupo);
                        try {
                            ss = statusSupervisorFacade.findByGrupo(grado);
                            if (null != ss) {
                                // aqui va la parte de indicar que va a cambiar el supervisor
                                showSupervisorFound(ss);
                                changeStatusSupervisor(ss, grado, dp);
                            }
                            createStatusSupervisor(superv, grado, dp);
                        } catch (SupervisorNotFoundException e) {
                            JsfUtils.messageWarning("No hay supervisor para este grado");
                        }
                        break;
                    case Constantes.GRUPO_ETAPA:
                        Etapa etapa = etapaFacade.findByNombre(grupo);
                        try {
                            ss = statusSupervisorFacade.findByGrupo(etapa);
                            if (null != ss) {
                                showSupervisorFound(ss);
                                changeStatusSupervisor(ss, etapa, dp);
                            }
                            createStatusSupervisor(superv, etapa, dp);
                        } catch (SupervisorNotFoundException e) {
                            JsfUtils.messageWarning("No hay supervisor para esta etapa");
                        }
                        break;
                    case Constantes.GRUPO_COLEGIO:
                        Colegio colegio = colegioFacade.findByRif(rifColegio);
                        try {
                            ss = statusSupervisorFacade.findByGrupo(colegio);
                            if (null != ss) {
                                // el colegio ya tiene un supervisor va a agregar otro
                                showSupervisorFound(ss);
                            }
                            createStatusSupervisor(superv, colegio, dp);
                        } catch (SupervisorNotFoundException e) {
                            JsfUtils.messageWarning("No hay supervisor para el colegio");
                        }
                        break;
                }
                clearFields();

            } else {
                JsfUtils.messageWarning("Debe seleccionar a un usuario");
            }
        } else {
            JsfUtils.messageWarning("Debe seleccionar a un usuario");
        }
    }

    public void updateSupervisor() {
        User user = userFacade.findByCi(selected.getId());

        List<Supervisor> supervisorList = supervisorFacade.findByCi(selected.getId());
        StatusSupervisor ss = null;
        for (Supervisor spvr : supervisorList) {
            StatusSupervisor ssTemp = statusSupervisorFacade.findBySupervisor(spvr);
            if (null != ssTemp) {
                ss = ssTemp;
                break;
            }
        }

        Supervisor superNew = new Supervisor();
        superNew.setUserId(user);
        Object docOrAdmin = findDocenteOrAdministrativo(user.getCi());
        Object[] subject = findUser(docOrAdmin);
        DatosPersona dp = (DatosPersona) subject[1];

        if(null != ss.getCursoId()){
            changeStatusSupervisor(ss, ss.getCursoId(), dp);
        } else if (null != ss.getEtapaId()){
            changeStatusSupervisor(ss, ss.getEtapaId(), dp);
        } else if (null != ss.getColegioId()){
            changeStatusSupervisor(ss, ss.getColegioId(), dp);
        }
        
        switch (nivel) {
            case Constantes.GRUPO_GRADO:
                Curso grado = cursoFacade.findByName(grupo);
                createStatusSupervisor(superNew, grado, dp);
                break;
            case Constantes.GRUPO_ETAPA:
                Etapa etapa = etapaFacade.findByNombre(grupo);
                createStatusSupervisor(superNew, etapa, dp);
                break;
            case Constantes.GRUPO_COLEGIO:
                Colegio colegio = colegioFacade.findByRif(rifColegio);
                createStatusSupervisor(superNew, colegio, dp);
                break;
        }

    }

    public void disableSupervisor() {
        User user = userFacade.findByCi(selected.getId());
        
        List<Supervisor> supervisorList = supervisorFacade.findByCi(selected.getId());
        StatusSupervisor ss = null;
        for (Supervisor spvr : supervisorList) {
            StatusSupervisor ssTemp = statusSupervisorFacade.findBySupervisor(spvr);
            if (null != ssTemp) {
                ss = ssTemp;
                break;
            }
        }
        
        Object docOrAdmin = findDocenteOrAdministrativo(user.getCi());
        Object[] subject = findUser(docOrAdmin);
        DatosPersona dp = (DatosPersona) subject[1];

        if (null != ss.getCursoId()) {
            changeStatusSupervisor(ss, ss.getCursoId(), dp);
        } else if (null != ss.getEtapaId()) {
            changeStatusSupervisor(ss, ss.getEtapaId(), dp);
        } else if (null != ss.getColegioId()) {
            changeStatusSupervisor(ss, ss.getColegioId(), dp);
        }
    }
    
    
    private Object[] findUser(Object candidato) {
        DatosPersona dp;
        User user;

        if (candidato instanceof Docente) {
            Docente docente = (Docente) candidato;
            dp = docente.getDatosPersonaId();
            user = docente.getUserId();
        } else {
            Administrativo administrativo = (Administrativo) candidato;
            dp = administrativo.getDatosPersonaId();
            user = administrativo.getUserId();
        }

        LOGGER.log(Level.INFO, "va a colocar de supervisor a {0} {1} , para el grupo {2}",
                new Object[]{dp.getNombre(), dp.getApellido(), grupo});
        return new Object[]{user, dp};
    }

    private void createStatusSupervisor(Supervisor superv, Object nivel, DatosPersona dp) {
        supervisorFacade.create(superv);

        String nombreNivel;
        StatusSupervisor ss = new StatusSupervisor();
        ss.setSupervisorId(superv);
        if (nivel instanceof Curso) {
            ss.setCursoId((Curso) nivel);
            nombreNivel = ((Curso) nivel).getNombre();
        } else if (nivel instanceof Etapa) {
            ss.setEtapaId((Etapa) nivel);
            nombreNivel = ((Etapa) nivel).getNombre();
        } else {
            ss.setColegioId((Colegio) nivel);
            nombreNivel = "Colegio";
        }
        ss.setFechaIn(new Date());
        ss.setStatus(Constantes.SUPERVISOR_ACTIVO);

        statusSupervisorFacade.create(ss);
        LOGGER.log(Level.INFO, "{0} {1} asignado como supervisor de {2}",
                new Object[]{dp.getNombre(), dp.getApellido(), nombreNivel});
        JsfUtils.messageSuccess("Supervidor asignado correctamente");
    }

    private void changeStatusSupervisor(StatusSupervisor ssOld,
            Object nivel, DatosPersona dp) {

        ssOld.setStatus(Constantes.USUARIO_INACTIVO);
        ssOld.setFechaOut(new Date());
        String nombreNivel;
        if (nivel instanceof Curso) {
            nombreNivel = ((Curso) nivel).getNombre();
        } else if (nivel instanceof Etapa) {
            nombreNivel = ((Etapa) nivel).getNombre();
        } else {
            nombreNivel = "Colegio";
        }

        LOGGER.log(Level.INFO, "{0} {1} ya no es supervisor de {2}",
                new Object[]{dp.getNombre(), dp.getApellido(), nombreNivel});
        statusSupervisorFacade.edit(ssOld);
//        createStatusSupervisor(superNew, nivel, dp);
    }

    private void showSupervisorFound(StatusSupervisor ss) {
        Supervisor sup = ss.getSupervisorId();
        System.out.println("Encontró a " + sup.getUserId());
    }

    public void clearFields() {
        supervisor = "";
        supervisores = null;
        nivel = Constantes.GRUPO_GRADO;
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        System.out.println("oldValue: " + oldValue);
        Object newValue = event.getNewValue();
        System.out.println("newValue: " + newValue);

        if (newValue != null && !newValue.equals(oldValue)) {
            System.out.println("El grupo anterior era " + oldValue
                    + " y el nuevo valor es " + newValue);
        }
    }

    public void onRowEdit(RowEditEvent event) {
        //System.out.println("Se cambió a " + ((Supervisor) event.getObject()).getGrupo());
    }

    public void onRowCancel(RowEditEvent event) {
        //System.out.println("Se canceló " + ((Supervisor) event.getObject()).getGrupo());
    }

    private Object findCandidato(String str) {
        int dospuntos = str.lastIndexOf(":");
        String ciStr = str.substring(dospuntos + 1).trim();
        int ci = Integer.parseInt(ciStr);
        return findDocenteOrAdministrativo(ci);
    }

    private Object findDocenteOrAdministrativo(int ci) {
        Object docOrAdmin = null;
        try {
            docOrAdmin = docenteFacade.findByCi(ci);
        } catch (DocenteNotFoundException e) {
            docOrAdmin = administrativoFacade.findByCi(ci);
        }
        return docOrAdmin;
    }

    private List<Seccion> findSeccion(Docente docente) {
        return seccionHasDocenteFacade.findAllByDocente(docente);
    }

    private Map<Etapa, List<Curso>> mapearCursos() {

        Map<Etapa, List<Curso>> cursosMap = new TreeMap<>();
        List<Etapa> etapas = etapaFacade.findAll();

        for (Etapa et : etapas) {
            List<Curso> cursos = cursoFacade.findAllByEtapa(et);
            cursosMap.put(et, cursos);
        }

        return cursosMap;
    }

    public List<SupervisorData> getSupervisoresData() {
        List<SupervisorData> supdataList = new ArrayList<>();

        List<StatusSupervisor> ssList = statusSupervisorFacade
                .findAllByStatus(Constantes.SUPERVISOR_ACTIVO);
        for (StatusSupervisor ss : ssList) {
            SupervisorData sd = new SupervisorData(ss);
            supdataList.add(sd);
        }
        return supdataList;
    }

    public class SupervisorData {

        private Integer id;
        private String nombre;
        private String grupo;
        private StatusSupervisor ss;

        SupervisorData(StatusSupervisor ss) {
            this.ss = ss;
            convertToSupervisorData();
        }

        private void convertToSupervisorData() {
            User user = ss.getSupervisorId().getUserId();

            DatosPersona dp = datosPersonaFacade.findByCi(user.getCi());
            this.id = dp.getCi();
            StringBuilder sb = new StringBuilder();
            sb.append(dp.getApellido()).append(", ");
            sb.append(dp.getNombre());
            this.nombre = sb.toString();

            if (null != ss.getCursoId()) {
                this.grupo = ss.getCursoId().getNombre();
            } else if (null != ss.getEtapaId()) {
                this.grupo = ss.getEtapaId().getNombre();
            } else {
                this.grupo = "Colegio";
            }
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getGrupo() {
            return grupo;
        }

        public void setGrupo(String grupo) {
            this.grupo = grupo;
        }

        public StatusSupervisor getSs() {
            return ss;
        }

        public void setSs(StatusSupervisor ss) {
            this.ss = ss;
        }

    }

}
