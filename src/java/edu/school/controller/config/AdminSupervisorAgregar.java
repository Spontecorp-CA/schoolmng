package edu.school.controller.config;

import edu.school.ejb.AdministrativoFacadeLocal;
import edu.school.ejb.DatosPersonaFacadeLocal;
import edu.school.ejb.DocenteFacadeLocal;
import edu.school.ejb.HasSupervisorFacadeLocal;
import edu.school.ejb.SupervisorFacadeLocal;
import edu.school.entities.Administrativo;
import edu.school.entities.DatosPersona;
import edu.school.entities.Docente;
import edu.school.entities.Supervisor;
import edu.school.excepciones.DocenteNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

@Named
@ViewScoped
public class AdminSupervisorAgregar implements Serializable{

    @EJB
    private AdministrativoFacadeLocal administrativoFacade;
    @EJB
    private DocenteFacadeLocal docenteFacade;
    @EJB
    private SupervisorFacadeLocal supervisorFacade; 
    @EJB
    private HasSupervisorFacadeLocal hasSupervisorFacade;
    @EJB
    private DatosPersonaFacadeLocal datosPersonaFacade;
    
    private String supervisor;
    private String grupo;
    private List<String> usuarios;
    private List<Supervisor> supervisores;
    
    @PostConstruct
    public void init(){
        makeUserList();
        makeSupervisores();
    }
    
    private List<String> makeUserList(){
        
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
    
    private void makeSupervisores(){
        supervisores = new ArrayList<>();

//        supervisores.add(new Supervisor("Luis Alvarado", "1er Grado"));
//        supervisores.add(new Supervisor("Rosa Centeno", "2do Grado"));
//        supervisores.add(new Supervisor("Andreina Oropeza", "Primaria"));
//        supervisores.add(new Supervisor("Juan Girafales", "3er año"));
//        supervisores.add(new Supervisor("Rodolfo Carrasquero", "Bachillerato básico"));
//        supervisores.add(new Supervisor("Carmen Mirabal", "Bachillerato ciencias"));
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
    
    public List<Supervisor> getSupervisores(){
        return supervisores;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public List<String> getUsuarios(){
        return usuarios;
    }
    
    public List<String> getGrupos(){
        List<String> grupos = new ArrayList<>();
        grupos.add("1er Grado");
        grupos.add("2do Grado");
        grupos.add("3er Grado");
        grupos.add("4to Grado");
        grupos.add("5to Grado");
        grupos.add("6to Grado");
        grupos.add("1er año");
        grupos.add("2do año");
        grupos.add("3er año");
        grupos.add("4to año");
        grupos.add("5to año");
        grupos.add("Maternal");
        grupos.add("Presescolar 1");
        grupos.add("Presescolar 2");
        grupos.add("Primaria 1");
        grupos.add("Primaria 2");
        grupos.add("Bachillerato 1");
        grupos.add("Bachillerato 2");
        grupos.add("Colegio");
        return grupos;
    }

    public void createSuper(){
        Object candidato = findCandidato(supervisor);
        if (candidato != null) {
            DatosPersona dp;
            if (candidato instanceof Docente) {
                dp = ((Docente) candidato).getDatosPersonaId();
            } else {
                dp = ((Administrativo) candidato).getDatosPersonaId();
            }

            System.out.println("va a colocar de supervisor a " + dp.getNombre()
                    + ", " + dp.getApellido());
        }
    }
    
    public void clearFields(){
        supervisor = null;
        grupo = null;
    }
    
    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        System.out.println("oldValue: " + oldValue);
        Object newValue = event.getNewValue();
        System.out.println("newValue: " + newValue);

        if (newValue != null && !newValue.equals(oldValue)) {
            System.out.println("El grupo anterior era " + oldValue +
                    " y el nuevo valor es " + newValue);
        }
    }
    
    public void onRowEdit(RowEditEvent event) {
        //System.out.println("Se cambió a " + ((Supervisor) event.getObject()).getGrupo());
    }

    public void onRowCancel(RowEditEvent event) {
        //System.out.println("Se canceló " + ((Supervisor) event.getObject()).getGrupo());
    }
    
    private Object findCandidato(String str){
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
    
}
