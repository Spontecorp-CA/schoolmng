package edu.school.controller.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

@Named
@ViewScoped
public class AdminSupervisorAgregar implements Serializable{

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
        usuarios = new ArrayList<>();
        
        usuarios.add("Juan Perez");
        usuarios.add("Domingo Guzman");
        usuarios.add("Jose Masparrote");
        usuarios.add("Luis Calzadilla");
        usuarios.add("María Julia Alvarez");
        usuarios.add("Antonio Fuentes");
        usuarios.add("Pedro Martínez");
        
        return usuarios;
    }
    
    private void makeSupervisores(){
        supervisores = new ArrayList<>();

        supervisores.add(new Supervisor("Luis Alvarado", "1er Grado"));
        supervisores.add(new Supervisor("Rosa Centeno", "2do Grado"));
        supervisores.add(new Supervisor("Andreina Oropeza", "Primaria"));
        supervisores.add(new Supervisor("Juan Girafales", "3er año"));
        supervisores.add(new Supervisor("Rodolfo Carrasquero", "Bachillerato básico"));
        supervisores.add(new Supervisor("Carmen Mirabal", "Bachillerato ciencias"));
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
        System.out.println("Va a crear el supervisor " + supervisor
                + " de " + grupo);
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
        System.out.println("Se cambió a " + ((Supervisor) event.getObject()).getGrupo());
    }

    public void onRowCancel(RowEditEvent event) {
        System.out.println("Se canceló " + ((Supervisor) event.getObject()).getGrupo());
    }

    
    public class Supervisor{
        private String nombre;
        private String grupo;

        public Supervisor(String nombre, String grupo) {
            this.nombre = nombre;
            this.grupo = grupo;
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
        
    }
}
