package edu.school.controller;

import edu.school.ejb.RolFacadeLocal;
import edu.school.ejb.UserFacadeLocal;
import edu.school.ejb.UserHasRolFacadeLocal;
import edu.school.entities.Rol;
import edu.school.entities.User;
import edu.school.entities.UserHasRol;
import edu.school.utilities.Constantes;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.NoResultException;

@Named
@ViewScoped
public class LoginController implements Serializable {
    
    @EJB
    private UserFacadeLocal userFacade;
    @EJB
    private RolFacadeLocal rolFacade;
    @EJB
    private UserHasRolFacadeLocal uhRolFacade;
    
    private String usuario;
    private String password;
    
    public LoginController() {
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    @PostConstruct
    public void init(){
        if(!verifyUserConfigurador()){
            createUserConfigurador();
        }
    }
    
    private void createUserConfigurador() {
        if(!verifyRolConfigurador()){
            createRolConfigurador();
        }
        User admin = new User();
        admin.setUsr("admin");
        admin.setPsw("admin");
        admin.setStatus(Constantes.USUARIO_ACTIVO);
        userFacade.create(admin);
        Rol adminRol = rolFacade.find("configurador");
        
        assignRolToUser(admin, adminRol);
    }
    
    private void assignRolToUser(User user, Rol rol){
        UserHasRol uhr = new UserHasRol();
        uhr.setRolId(rol);
        uhr.setUserId(user);
        uhRolFacade.create(uhr);
    }
    
    private void createRolConfigurador(){
        Rol configuradorRol = new Rol();
        configuradorRol.setName("configurador");
        rolFacade.create(configuradorRol);
    }
    
    private boolean verifyUserConfigurador(){
        String user = "admin";
        boolean answer = false;
        try {
            User u = userFacade.find(user);
            if(u != null && u.getUsr().equalsIgnoreCase(user)){
                answer = true;    
            }
        } catch (NoResultException e) {
        }
        return answer;
    }
    
    private boolean verifyRolConfigurador(){
        String name = "configurador";
        boolean answer = false;
        try {
            Rol r = rolFacade.find(name);
            if(r != null && r.getName().equalsIgnoreCase(name)){
                answer = true;    
            }
        } catch (NoResultException e) {
        }
         return answer;
    }
    
    public String verifyUser(){
        String page = "pageError";
        if(usuario != null && !usuario.isEmpty()){
            User user = userFacade.find(usuario);
            if(user != null && user.getPsw().equals(password)){
                page = "page1?faces-redirect=true";
            } 
        }
        return page;
    }
}
