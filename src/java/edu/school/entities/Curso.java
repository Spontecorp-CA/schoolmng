/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.school.entities;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jgcastillo
 */
@Entity
@Table(name = "curso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Curso.findAll", query = "SELECT c FROM Curso c")})
public class Curso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "codigo")
    private String codigo;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 45)
    @Column(name = "seccion")
    private String seccion;
    @Size(max = 100)
    @Column(name = "mail_colegio")
    private String mailColegio;
    @OneToMany(mappedBy = "cursoId")
    private Collection<CursoHasAlumno> cursoHasAlumnoCollection;
    @JoinColumn(name = "nivel_id", referencedColumnName = "id")
    @ManyToOne
    private Nivel nivelId;
    @JoinColumn(name = "periodo_int", referencedColumnName = "id")
    @ManyToOne
    private Periodo periodoInt;
    @OneToMany(mappedBy = "cursoId")
    private Collection<CursoHasDocente> cursoHasDocenteCollection;

    public Curso() {
    }

    public Curso(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getMailColegio() {
        return mailColegio;
    }

    public void setMailColegio(String mailColegio) {
        this.mailColegio = mailColegio;
    }

    @XmlTransient
    public Collection<CursoHasAlumno> getCursoHasAlumnoCollection() {
        return cursoHasAlumnoCollection;
    }

    public void setCursoHasAlumnoCollection(Collection<CursoHasAlumno> cursoHasAlumnoCollection) {
        this.cursoHasAlumnoCollection = cursoHasAlumnoCollection;
    }

    public Nivel getNivelId() {
        return nivelId;
    }

    public void setNivelId(Nivel nivelId) {
        this.nivelId = nivelId;
    }

    public Periodo getPeriodoInt() {
        return periodoInt;
    }

    public void setPeriodoInt(Periodo periodoInt) {
        this.periodoInt = periodoInt;
    }

    @XmlTransient
    public Collection<CursoHasDocente> getCursoHasDocenteCollection() {
        return cursoHasDocenteCollection;
    }

    public void setCursoHasDocenteCollection(Collection<CursoHasDocente> cursoHasDocenteCollection) {
        this.cursoHasDocenteCollection = cursoHasDocenteCollection;
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
        if (!(object instanceof Curso)) {
            return false;
        }
        Curso other = (Curso) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.school.entities.Curso[ id=" + id + " ]";
    }
    
}
