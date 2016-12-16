/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.school.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jgcastillo
 */
@Entity
@Table(name = "curso_has_alumno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CursoHasAlumno.findAll", query = "SELECT c FROM CursoHasAlumno c")
    , @NamedQuery(name = "CursoHasAlumno.findById", query = "SELECT c FROM CursoHasAlumno c WHERE c.id = :id")})
public class CursoHasAlumno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "alumno_id", referencedColumnName = "id")
    @ManyToOne
    private Alumno alumnoId;
    @JoinColumn(name = "curso_id", referencedColumnName = "id")
    @ManyToOne
    private Curso cursoId;

    public CursoHasAlumno() {
    }

    public CursoHasAlumno(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Alumno getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(Alumno alumnoId) {
        this.alumnoId = alumnoId;
    }

    public Curso getCursoId() {
        return cursoId;
    }

    public void setCursoId(Curso cursoId) {
        this.cursoId = cursoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CursoHasAlumno)) {
            return false;
        }
        CursoHasAlumno other = (CursoHasAlumno) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.school.entities.CursoHasAlumno[ id=" + id + " ]";
    }
    
}
