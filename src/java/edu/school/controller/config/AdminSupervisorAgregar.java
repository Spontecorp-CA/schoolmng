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
    private static final Logger LOGGER = Logger.getLogger(AdminSupervisorAgregar.class.getName());

    @PostConstruct
    public void init() {
        makeUserList();
        makeSupervisores();
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
        for(StatusSupervisor ss : ssList){
            String[] datos = new String[2];
            StringBuffer sbnombre = new StringBuffer();
            StringBuffer sbgrupo = new StringBuffer();
            User user = ss.getSupervisorId().getUserId();
            DatosPersona dp = datosPersonaFacade.findByCi(user.getCi());
            sbnombre.append(dp.getApellido()).append(", ").append(dp.getNombre());
            datos[0] = sbnombre.toString();
            if(null != ss.getColegioId()){
                datos[1] = "Colegio";
            } else if( null != ss.getEtapaId()){
                datos[1] = ss.getEtapaId().getNombre();
            } else if( null != ss.getCursoId()){
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

    public List<String> getGrupos() {
        List<String> grupos = new ArrayList<>();
        Map<Etapa, List<Curso>> cursosMap = mapearCursos();

        switch (nivel) {
            case Constantes.GRUPO_GRADO:
                for (Map.Entry<Etapa, List<Curso>> mapa : cursosMap.entrySet()) {
                    List<Curso> cursos = mapa.getValue();
                    cursos.sort((Curso c1, Curso c2)
                            -> c1.getNombre().compareTo(c2.getNombre()));

                    for (Curso curso : cursos) {
                        grupos.add(curso.getNombre());
                    }
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

                switch (nivel) {
                    case Constantes.GRUPO_GRADO:
                        Curso grado = cursoFacade.findByName(grupo);
                        StatusSupervisor ss = statusSupervisorFacade.findByGrupo(grado);
                        if(null != ss){
                            // aqui va la parte de indicar que va a cambiar el supervisor
                            showSupervisorFound(ss);
                        } else {
                            createStatusSupervisor(superv, grado, dp);
                        }
                        break;
                    case Constantes.GRUPO_ETAPA:
                        Etapa etapa = etapaFacade.findByNombre(grupo);
                        ss = statusSupervisorFacade.findByGrupo(etapa);
                        if(null != ss){
                            showSupervisorFound(ss);
                        } else {
                            createStatusSupervisor(superv, etapa, dp);
                        }
                        break;
                    case Constantes.GRUPO_COLEGIO:
                        Colegio colegio = colegioFacade.findByRif("J12345678");
                        ss = statusSupervisorFacade.findByGrupo(colegio);
                        if(null != ss){
                            // el colegio ya tiene un supervisor va a agregar otro
                            showSupervisorFound(ss);
                        } else {
                            createStatusSupervisor(superv, colegio, dp);
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
    
    private Object[] findUser(Object candidato){
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
    
    private void createStatusSupervisor(Supervisor superv, Object nivel, DatosPersona dp){
        supervisorFacade.create(superv);

        String nombreNivel;
        StatusSupervisor ss = new StatusSupervisor();
        ss.setSupervisorId(superv);
        if(nivel instanceof Curso){
            ss.setCursoId((Curso)nivel);
            nombreNivel = ((Curso)nivel).getNombre();
        } else if(nivel instanceof Etapa){
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
    
    private void showSupervisorFound(StatusSupervisor ss){
        Supervisor sup = ss.getSupervisorId();
        System.out.println("Encontró a " + sup.getUserId());
    }

    public void clearFields() {
        supervisor = "";
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
        Object candidato = null;
        int dospuntos = str.lastIndexOf(":");
        String ciStr = str.substring(dospuntos + 1).trim();
        int ci = Integer.parseInt(ciStr);

        try {
            candidato = docenteFacade.findByCi(ci);
        } catch (DocenteNotFoundException e) {
            candidato = administrativoFacade.findByCi(ci);
        }

        return candidato;
    }

    private List<Seccion> findSeccion(Docente docente) {
        return seccionHasDocenteFacade.findAll(docente);
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

}
