package edu.school.controller.administrativos;

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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class CargaRepresentanteController implements Serializable {

    @EJB
    private RepresentanteFacadeLocal representanteFacade;
    @EJB
    private DatosPersonaFacadeLocal datosPersonaFacade;
    @EJB
    private CursoFacadeLocal cursoFacade;
    @EJB
    private CursoHasAlumnoFacadeLocal cursoHasAlumnoFacade;
    @EJB
    private UserFacadeLocal userFacade;
    @EJB
    private RolFacadeLocal rolFacade;
    @EJB
    private UserHasRolFacadeLocal userHasRolFacade;
    @EJB
    private AlumnoHasRepresentanteFacadeLocal alumnoHasRepresentanteFacade;

    @Inject
    private Representante representante;
    @Inject
    private Alumno alumno;
    @Inject
    private DatosPersona datosRepresentante;
    @Inject
    private Nivel nivel;
    @Inject
    private Curso curso;

    private boolean repExist = false;

//    public Representante getRepresentante() {
//        return representante;
//    }
//
//    public void setRepresentante(Representante representante) {
//        this.representante = representante;
//    }
//
    public DatosPersona getDatosRepresentante() {
        return datosRepresentante;
    }

    public void setDatosRepresentante(DatosPersona datosRepresentante) {
        this.datosRepresentante = datosRepresentante;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
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

    public List<SelectItem> getCursos() {
        List<SelectItem> cursos = new ArrayList<>();
        List<Curso> cursoList = cursoFacade.findAll(nivel);
        cursos.add(new SelectItem(null, "Seleccione curso..."));
        if (cursoList != null && !cursoList.isEmpty()) {
            cursoList.stream().forEach(cur -> {
                cursos.add(new SelectItem(cur, cur.getNombre() + "-" + cur.getSeccion()));
            });
        }
        return cursos;
    }

    public List<SelectItem> getAlumnos() {
        List<SelectItem> alumnos = new ArrayList<>();
        List<CursoHasAlumno> chaList = cursoHasAlumnoFacade.findAll(curso);
        
        Collections.sort(chaList, new Comparator<CursoHasAlumno>() {
            @Override
            public int compare(CursoHasAlumno o1, CursoHasAlumno o2) {
                return o1.getAlumnoId().getDatosPersonaId().getApellido()
                        .compareTo(o2.getAlumnoId().getDatosPersonaId().getApellido());
            }
        });
        
        if (chaList != null && !chaList.isEmpty()) {
            chaList.stream()
                    .forEach(cha -> {
                Alumno pupil = cha.getAlumnoId();
                alumnos.add(new SelectItem(pupil, pupil.getDatosPersonaId().getApellido() 
                        + ", " + pupil.getDatosPersonaId().getNombre()));
            });
        }
        return alumnos;
    }

    public void lookForCI() {
        int ciTemp = datosRepresentante.getCi();
        if (datosPersonaFacade.find(ciTemp) != null) {
            datosRepresentante = datosPersonaFacade.find(ciTemp);
            repExist = true;
        }
    }
    
    public void saveRepresentante(){
        try {
            DatosPersona dp = datosPersonaFacade.find(datosRepresentante.getCi());
            if (dp == null) {
                assignDatosPersonalesToRepresentante();
                assignUserToRepresentante();
                representanteFacade.create(representante);
            } else {
                representante = representanteFacade.find(dp);
            }
            
            assignRepresentanteToAlumno();
            JsfUtils.messageSuccess("Representante creado/asignado con éxito");
        } catch (Exception e) {
            JsfUtils.messageError("Ha ocurrido un error creando/asignando el representante");
        }
        
    }
    
    private void assignDatosPersonalesToRepresentante(){
        representante.setDatosPersonaId(datosRepresentante);
    }
    
    private void assignUserToRepresentante(){
        User user = new User();
        user.setCi(datosRepresentante.getCi());
        userFacade.create(user);
        
        UserHasRol uhr = new UserHasRol();
        Rol rol = rolFacade.find(Constantes.ROL_REPRESENTANTE);
        uhr.setRolId(rol);
        uhr.setUserId(user);
        uhr.setEscritorio(Constantes.ESCRITORIO_REPRESENTANTE);
        userHasRolFacade.create(uhr);
        representante.setUserId(user);
    }
    
    private void assignRepresentanteToAlumno(){
        AlumnoHasRepresentante ahr = new AlumnoHasRepresentante();
        
        System.out.println("EL alumno es:  " + alumno.getId());
        System.out.println("El representantes es " + representante.getId());
        
        ahr.setAlumnoId(alumno);
        ahr.setRepresentanteId(representante);
        alumnoHasRepresentanteFacade.create(ahr);
    }
}
