package edu.school.controller.administrativos;

import edu.school.ejb.DatosPersonaFacadeLocal;
import edu.school.ejb.DocenteFacadeLocal;
import edu.school.ejb.RolFacadeLocal;
import edu.school.ejb.UserFacadeLocal;
import edu.school.ejb.UserHasRolFacadeLocal;
import edu.school.entities.DatosPersona;
import edu.school.entities.Docente;
import edu.school.entities.Rol;
import edu.school.entities.User;
import edu.school.entities.UserHasRol;
import edu.school.utilities.Constantes;
import edu.school.utilities.JsfUtils;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class CargaDocenteController implements Serializable{
    
    @EJB
    private DocenteFacadeLocal docenteFacade;
    @EJB
    private DatosPersonaFacadeLocal datosPersonaFacade;
    @EJB
    private UserFacadeLocal userFacade;
    @EJB
    private RolFacadeLocal rolFacade;
    @EJB
    private UserHasRolFacadeLocal userHasRolFacade;
    
    @Inject
    private DatosPersona datosDocente;
    @Inject
    private Docente docente;
    
    private boolean docExist;

    public DatosPersona getDatosDocente() {
        return datosDocente;
    }

    public void setDatosDocente(DatosPersona datosDocente) {
        this.datosDocente = datosDocente;
    }
    
    public void saveDocente(){
        try {
            DatosPersona dp = datosPersonaFacade.find(datosDocente.getCi());
            if (dp == null) {
                assignDatosPersonalesToDocente();
                assignUserToDocente();
                docenteFacade.create(docente);
            } else {
                docente = docenteFacade.find(dp);
            }
            JsfUtils.messageSuccess("Docente creado/asignado con éxito");
            clearFields();
        } catch (Exception e) {
        }
    }
    
    public void clearFields(){
        datosDocente.setCi(null);
        datosDocente.setApellido(null);
        datosDocente.setNombre(null);
    }
    
    public void lookForCI(){
        int ciTemp = datosDocente.getCi();
        if (datosPersonaFacade.find(ciTemp) != null) {
            datosDocente = datosPersonaFacade.find(ciTemp);
            docExist = true;
        } else {
            datosDocente.setNombre(null);
            datosDocente.setApellido(null);
        }
    }

    private void assignDatosPersonalesToDocente() {
        datosPersonaFacade.create(datosDocente);
        docente.setDatosPersonaId(datosDocente);
    }

    private void assignUserToDocente() {
        User user = new User();
        user.setCi(datosDocente.getCi());
        userFacade.create(user);

        UserHasRol uhr = new UserHasRol();
        Rol rol = rolFacade.find(Constantes.ROL_DOCENTE);
        uhr.setRolId(rol);
        uhr.setUserId(user);
        uhr.setEscritorio(Constantes.ESCRITORIO_DOCENTE);
        userHasRolFacade.create(uhr);
        docente.setUserId(user);
    }
}
