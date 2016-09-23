package edu.school.entities.transients;

import edu.school.entities.Alumno;
import edu.school.entities.Curso;
import edu.school.entities.Representante;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class RepresentanteAlumnoData implements Serializable {

    @Id
    private Long id;
    @Transient
    private Representante representante;
    @Transient
    private Alumno alumno;
    @Transient
    private Curso curso;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Representante getRepresentante() {
        return representante;
    }

    public void setRepresentante(Representante representante) {
        this.representante = representante;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
    
}
