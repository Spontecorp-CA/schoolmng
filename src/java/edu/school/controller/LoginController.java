package edu.school.controller;

import edu.school.ejb.RolFacadeLocal;
import edu.school.ejb.UserFacadeLocal;
import edu.school.ejb.UserHasRolFacadeLocal;
import edu.school.entities.Rol;
import edu.school.entities.User;
import edu.school.entities.UserHasRol;
import edu.school.utilities.Constantes;
import edu.school.utilities.Utilities;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
    public void init() {
        if (!verifyUserConfigurador()) {
            createUserConfigurador();
        }
    }

    private void createUserConfigurador() {
        if (!verifyRolConfigurador()) {
            createRoles();
        }
        User admin = new User();
        admin.setUsr("admin");
        admin.setPsw(Utilities.getSecurePassword("admin"));
        admin.setStatus(Constantes.USUARIO_ACTIVO);
        userFacade.create(admin);
        Rol adminRol = rolFacade.find(Constantes.ROL_CONFIGURADOR);

        assignRolToUser(admin, adminRol);
    }

    private void assignRolToUser(User user, Rol rol) {
        UserHasRol uhr = new UserHasRol();
        uhr.setRolId(rol);
        uhr.setUserId(user);
        uhRolFacade.create(uhr);
    }

    private void createRoles() {
        if (rolFacade.findAll().isEmpty()) {
            List<Rol> roles = new ArrayList<>();
            roles.add(new Rol(Constantes.ROL_CONFIGURADOR));
            roles.add(new Rol(Constantes.ROL_ADMINISTRATIVO));
            roles.add(new Rol(Constantes.ROL_DOCENTE));
            roles.add(new Rol(Constantes.ROL_REPRESENTANTE));
            roles.add(new Rol(Constantes.ROL_ALUMNO));
            roles.stream().forEach(rol -> {
                rolFacade.create(rol);
            });
        }
    }

    private boolean verifyUserConfigurador() {
        String user = "admin";
        boolean answer = false;
        try {
            User u = userFacade.find(user);
            if (u != null && u.getUsr().equalsIgnoreCase(user)) {
                answer = true;
            }
        } catch (NoResultException e) {
        }
        return answer;
    }

    private boolean verifyRolConfigurador() {
        boolean answer = false;
        try {
            Rol r = rolFacade.find(Constantes.ROL_CONFIGURADOR);
            if (r != null && r.getName().equals(Constantes.ROL_CONFIGURADOR)) {
                answer = true;
            }
        } catch (NoResultException e) {
        }
        return answer;
    }
    
    private String getSecurePassword(String passwordToHash) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {

        }
        return generatedPassword;
    }

    public String verifyUser() {
        String page = null;
        if (usuario != null && !usuario.isEmpty()) {
            
            User user = userFacade.find(usuario);
            if (user != null && user.getPsw()
                    .equals(Utilities.getSecurePassword(password))) {
                FacesContext.getCurrentInstance().getExternalContext()
                        .getSessionMap().put("user", user);
                page = "page1?faces-redirect=true";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Aviso", "Usuario y/o clave incorrecta"));
            }
        }
        return page;
    }
    
    public void closeSession(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }
}
