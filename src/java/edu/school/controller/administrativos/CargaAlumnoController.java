package edu.school.controller.administrativos;

import edu.school.ejb.AlumnoFacadeLocal;
import edu.school.ejb.CursoFacadeLocal;
import edu.school.ejb.CursoHasAlumnoFacadeLocal;
import edu.school.ejb.DatosPersonaFacadeLocal;
import edu.school.entities.Alumno;
import edu.school.entities.Curso;
import edu.school.entities.CursoHasAlumno;
import edu.school.entities.DatosPersona;
import edu.school.entities.Nivel;
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
    
    @Inject
    private Alumno alumno;
    @Inject
    private DatosPersona datosPersona;
    @Inject
    private Nivel nivel;
    @Inject
    private Curso curso;
    @Inject 
    private CursoHasAlumno cursoHasAlumno;

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public DatosPersona getDatosPersona() {
        return datosPersona;
    }

    public void setDatosPersona(DatosPersona datosPersona) {
        this.datosPersona = datosPersona;
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
        List<Curso> cursos = cursoFacade.findAll(nivel);
        List<SelectItem> itemsList = new ArrayList<>();
        itemsList.add(new SelectItem(null, "Seleccione Curso..."));
        cursos.stream().forEach(cu -> {
            itemsList.add(new SelectItem(cu, (cu.getNombre() + " - " + cu.getSeccion())));
        });
        return itemsList;
    }
    
    public String saveAlumno(){
        assignDatosPersonaToAlumno();
        alumnoFacade.create(alumno);
        System.out.println("El curso seleccionado es: " + curso.getId());
        assingAlumnoToCurso();
        clearFields();
        return null;
    }
    
    private void assignDatosPersonaToAlumno(){
        datosPersonaFacade.create(datosPersona);
        alumno.setDatosPersonaId(datosPersona);
    }
    
    private void assingAlumnoToCurso(){
        cursoHasAlumno.setAlumnoId(alumno);
        cursoHasAlumno.setCursoId(curso);
        cursoHasAlumnoFacade.create(cursoHasAlumno);
    }
    
    public void clearFields(){
        datosPersona = null;
        nivel = null;
    }
}
