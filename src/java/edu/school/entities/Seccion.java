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
@Table(name = "seccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Seccion.findAll", query = "SELECT s FROM Seccion s")
    , @NamedQuery(name = "Seccion.findById", query = "SELECT s FROM Seccion s WHERE s.id = :id")
    , @NamedQuery(name = "Seccion.findByCodigo", query = "SELECT s FROM Seccion s WHERE s.codigo = :codigo")})
public class Seccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 5)
    @Column(name = "codigo")
    private String codigo;
    @Size(max = 2)
    @Column(name = "seccion")
    private String seccion;
    @OneToMany(mappedBy = "seccionId")
    private Collection<SeccionHasAlumno> seccionHasAlumnoCollection;
    @OneToMany(mappedBy = "seccionId")
    private Collection<SeccionHasDocente> seccionHasDocenteCollection;
    @JoinColumn(name = "curso_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Curso cursoId;
    @JoinColumn(name = "periodo_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Periodo periodoId;

    public Seccion() {
    }

    public Seccion(Integer id) {
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

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    @XmlTransient
    public Collection<SeccionHasAlumno> getSeccionHasAlumnoCollection() {
        return seccionHasAlumnoCollection;
    }

    public void setSeccionHasAlumnoCollection(Collection<SeccionHasAlumno> seccionHasAlumnoCollection) {
        this.seccionHasAlumnoCollection = seccionHasAlumnoCollection;
    }

    @XmlTransient
    public Collection<SeccionHasDocente> getSeccionHasDocenteCollection() {
        return seccionHasDocenteCollection;
    }

    public void setSeccionHasDocenteCollection(Collection<SeccionHasDocente> seccionHasDocenteCollection) {
        this.seccionHasDocenteCollection = seccionHasDocenteCollection;
    }

    public Curso getCursoId() {
        return cursoId;
    }

    public void setCursoId(Curso cursoId) {
        this.cursoId = cursoId;
    }

    public Periodo getPeriodoId() {
        return periodoId;
    }

    public void setPeriodoId(Periodo periodoId) {
        this.periodoId = periodoId;
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
        if (!(object instanceof Seccion)) {
            return false;
        }
        Seccion other = (Seccion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.school.entities.Seccion[ id=" + id + " ]";
    }
    
}
