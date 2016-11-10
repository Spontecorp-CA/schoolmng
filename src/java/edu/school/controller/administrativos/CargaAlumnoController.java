package edu.school.controller.administrativos;

import edu.school.ejb.AlumnoFacadeLocal;
import edu.school.ejb.AlumnoHasRepresentanteFacadeLocal;
import edu.school.ejb.CursoFacadeLocal;
import edu.school.ejb.CursoHasAlumnoFacadeLocal;
import edu.school.ejb.DatosPersonaFacadeLocal;
import edu.school.ejb.RepresentanteFacadeLocal;
import edu.school.ejb.RolFacadeLocal;
import edu.school.ejb.UserFacadeLocal;
import edu.school.ejb.UserHasRolFacadeLocal;
import edu.school.entities.Alumno;
import edu.school.entities.AlumnoHasRepresentante;
import edu.school.entities.Curso;
import edu.school.entities.CursoHasAlumno;
import edu.school.entities.DatosPersona;
import edu.school.entities.Nivel;
import edu.school.entities.Representante;
import edu.school.entities.Rol;
import edu.school.entities.User;
import edu.school.entities.UserHasRol;
import edu.school.utilities.Constantes;
import edu.school.utilities.JsfUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class CargaAlumnoController implements Serializable {

    @EJB
    private AlumnoFacadeLocal alumnoFacade;
    @EJB
    private CursoFacadeLocal cursoFacade;
    @EJB
    private DatosPersonaFacadeLocal datosPersonaFacade;
    @EJB
    private CursoHasAlumnoFacadeLocal cursoHasAlumnoFacade;
    @EJB
    private RepresentanteFacadeLocal representanteFacade;
    @EJB
    private UserFacadeLocal userFacade;
    @EJB
    private UserHasRolFacadeLocal userHasRolFacade;
    @EJB
    private AlumnoHasRepresentanteFacadeLocal alumnoHasRepresentanteFacade;
    @EJB
    private RolFacadeLocal rolFacade;

    @Inject
    private Alumno alumno;
    @Inject
    private Representante madre;
    @Inject
    private Representante padre;
    @Inject
    private Representante representante;
    @Inject
    private DatosPersona datosAlumno;
    @Inject
    private DatosPersona datosMadre;
    @Inject
    private DatosPersona datosPadre;
    @Inject
    private DatosPersona datosRepresentante;
    @Inject
    private Nivel nivel;
    @Inject
    private Curso curso;
    @Inject
    private CursoHasAlumno cursoHasAlumno;
    @Inject
    private User userMadre;
    @Inject
    private User userPadre;
    @Inject
    private User userRep;
    @Inject
    private UserHasRol userHasRol;
    @Inject
    private AlumnoHasRepresentante alumnoHasRepresentante;
    
    private boolean madreExist;
    private boolean padreExist;
    private boolean repExist;
    private List<String> parentescos;

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Representante getRepresentante() {
        return representante;
    }

    public void setRepresentante(Representante representante) {
        this.representante = representante;
    }

    public DatosPersona getDatosAlumno() {
        return datosAlumno;
    }

    public void setDatosAlumno(DatosPersona datosAlumno) {
        this.datosAlumno = datosAlumno;
    }

    public DatosPersona getDatosMadre() {
        return datosMadre;
    }

    public void setDatosMadre(DatosPersona datosMadre) {
        this.datosMadre = datosMadre;
    }

    public DatosPersona getDatosPadre() {
        return datosPadre;
    }

    public void setDatosPadre(DatosPersona datosPadre) {
        this.datosPadre = datosPadre;
    }

    public DatosPersona getDatosRepresentante() {
        return datosRepresentante;
    }

    public void setDatosRepresentante(DatosPersona datosRepresentante) {
        this.datosRepresentante = datosRepresentante;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public List<String> getParentescos() {
        List<String> parentescos = new ArrayList<>();
        parentescos.add(Constantes.PARENTESCO_MADRE);
        parentescos.add(Constantes.PARENTESCO_PADRE);
        parentescos.add(Constantes.PARENTESCO_OTRO);
        return parentescos;
    }
    
    public List<SelectItem> getCursos() {
        List<Curso> cursos = cursoFacade.findAll(nivel);
        List<SelectItem> itemsList = new ArrayList<>();
        itemsList.add(new SelectItem(null, "Seleccione Curso..."));
        cursos.stream().forEach(cu -> {
            itemsList.add(new SelectItem(cu, (cu.getNombre() + " - " + cu.getSeccion())));
        });
        return itemsList;
    }

    public String saveAlumno() {
        assignDatosPersonaToAlumno();
        alumnoFacade.create(alumno);
        if (!repExist) {
            assignDatosPersonaToRepresentante();
        }
        assignAlumnoToRepresentante();
        assingAlumnoToCurso();
        clearFields();
        JsfUtils.messageSuccess("Alumno y Representante registrados con éxito");
        return null;
    }

    private void assignDatosPersonaToAlumno() {
        datosPersonaFacade.create(datosAlumno);
        alumno.setDatosPersonaId(datosAlumno);
    }
    
    private void assignDatosPersonaToMadre() {
        datosPersonaFacade.create(datosMadre);
        if (!userMadreExist()) {
            createUser();
        }
        madre.setDatosPersonaId(datosMadre);
        madre.setUserId(userRep);
        representanteFacade.create(madre);
    }

    private void assignDatosPersonaToRepresentante() {
        datosPersonaFacade.create(datosRepresentante);
        if (!userRepExist()) {
            createUser();
        }
        representante.setDatosPersonaId(datosRepresentante);
        representante.setUserId(userRep);
        representanteFacade.create(representante);
    }
    
    private boolean userMadreExist() {
        int ciTemp = datosMadre.getCi();
        if (userFacade.find(ciTemp) != null) {
            userMadre = userFacade.find(ciTemp);
            return true;
        } else {
            return false;
        }
    }
    
    private boolean userPadreExist() {
        int ciTemp = datosPadre.getCi();
        if (userFacade.find(ciTemp) != null) {
            userPadre = userFacade.find(ciTemp);
            return true;
        } else {
            return false;
        }
    }

    private boolean userRepExist() {
        int ciTemp = datosRepresentante.getCi();
        if (userFacade.find(ciTemp) != null) {
            userRep = userFacade.find(ciTemp);
            return true;
        } else {
            return false;
        }
    }

    private void assignAlumnoToRepresentante() {
        alumnoHasRepresentante.setAlumnoId(alumno);
        alumnoHasRepresentante.setRepresentanteId(representante);
        alumnoHasRepresentanteFacade.create(alumnoHasRepresentante);
    }

    private void assingAlumnoToCurso() {
        cursoHasAlumno.setAlumnoId(alumno);
        cursoHasAlumno.setCursoId(curso);
        cursoHasAlumnoFacade.create(cursoHasAlumno);
    }

    private void createUser() {
        userRep.setCi(datosRepresentante.getCi());
        userFacade.create(userRep);
        Rol rol = rolFacade.find(Constantes.ROL_REPRESENTANTE);
        userHasRol.setUserId(userRep);
        userHasRol.setRolId(rol);
        userHasRol.setEscritorio(Constantes.ESCRITORIO_REPRESENTANTE);
        userHasRolFacade.create(userHasRol);
    }

    public void clearFields() {
        datosAlumno.setNombre("");
        datosAlumno.setApellido("");
        datosRepresentante.setNombre("");
        datosRepresentante.setApellido("");
        nivel = null;
        curso = null;
    }

    public void lookForCI() {
        int ciTemp = datosRepresentante.getCi();
        if (datosPersonaFacade.find(ciTemp) != null) {
            datosRepresentante = datosPersonaFacade.find(ciTemp);
            Representante repTemp = representanteFacade.find(datosRepresentante);
            switch(repTemp.getParentesco()){
                case Constantes.PARENTESCO_MADRE:
                    madre = repTemp;
                    madreExist = true;
                    break;
                case Constantes.PARENTESCO_PADRE:
                    padre = repTemp;
                    padreExist = true;
                    break;
                case Constantes.PARENTESCO_OTRO:
                    representante = repTemp;
                    repExist = true;
                    break;
            }
            
        }
    }
}
