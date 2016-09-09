package edu.school.controller.config;

import edu.school.ejb.DatosPersonaFacadeLocal;
import edu.school.ejb.RolFacadeLocal;
import edu.school.ejb.UserFacadeLocal;
import edu.school.ejb.UserHasRolFacadeLocal;
import edu.school.entities.Adminitrativo;
import edu.school.entities.DatosPersona;
import edu.school.entities.Docente;
import edu.school.entities.Representante;
import edu.school.entities.Rol;
import edu.school.entities.User;
import edu.school.entities.UserHasRol;
import edu.school.utilities.Constantes;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class AdminUsersController implements Serializable {

    @EJB
    private UserFacadeLocal userFacade;
    @EJB
    private RolFacadeLocal rolFacade;
    @EJB
    private UserHasRolFacadeLocal uhrFacade;
    @EJB
    private DatosPersonaFacadeLocal datosPersonaFacade;

    @Inject
    private User user;
    @Inject
    private Rol rol;
    @Inject
    private UserHasRol userHasRol;
    @Inject
    private DatosPersona datosPersona;

    public DatosPersona getDatosPersona() {
        return datosPersona;
    }

    public void setDatosPersona(DatosPersona datosPersona) {
        this.datosPersona = datosPersona;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public List<SelectItem> getRoles() {
        List<Rol> rolList = rolFacade.findAll();
        List<SelectItem> roles = new ArrayList<>();
        rolList.stream()
                .filter((actor) -> !actor.getName().equals(Constantes.ROL_ALUMNO)
                        && !actor.getName().equals(Constantes.ROL_CONFIGURADOR))
                .forEach((actor) -> {
                    roles.add(new SelectItem(actor, actor.getName()));
                });
        return roles;
    }

    public void createUser() {

        DatosPersona dpTemp = datosPersonaFacade.find(datosPersona.getCi().intValue());
        if (dpTemp != null) {
            FacesContext.getCurrentInstance()
                    .addMessage(null,
                            new FacesMessage(
                                    FacesMessage.SEVERITY_ERROR,
                                    "Error al crear usuario",
                                    "La cédula de identidad ingresada ya existe en el sistema"));
            return;
        }

        switch (rol.getName()) {
            case Constantes.ROL_ADMINISTRATIVO:
                Adminitrativo administrativo = new Adminitrativo();
                administrativo.setDatosPersonaId(datosPersona);
                userHasRol.setEscritorio(Constantes.ESCRITORIO_ADMIN);
                break;
            case Constantes.ROL_DOCENTE:
                Docente docente = new Docente();
                docente.setDatosPersonaId(datosPersona);
                userHasRol.setEscritorio(Constantes.ESCRITORIO_DOCENTE);
                break;
            case Constantes.ROL_REPRESENTANTE:
                Representante representante = new Representante();
                representante.setDatosPersonaId(datosPersona);
                userHasRol.setEscritorio(Constantes.ESCRITORIO_REPRESENTANTE);
                break;
        }
        
        datosPersonaFacade.create(datosPersona);
        
        user.setCi(datosPersona.getCi());
        userHasRol.setUserId(user);
        userHasRol.setRolId(rol);

        userFacade.create(user);
        uhrFacade.create(userHasRol);

        limpiaDatos();

        FacesContext.getCurrentInstance()
                .addMessage(null,
                        new FacesMessage(
                                FacesMessage.SEVERITY_INFO,
                                "Usuario creado con éxito",
                                "Usuario creado con éxito"));

    }

    public String cancelAction() {
        return "dashboard";
    }

    public void clearFields() {
        limpiaDatos();
    }
    
    private void limpiaDatos(){
        datosPersona.setNombre("");
        datosPersona.setApellido("");
        datosPersona.setCi(null);
    }
}
