/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.school.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jgcastillo
 */
@Entity
@Table(name = "alumno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Alumno.findAll", query = "SELECT a FROM Alumno a")})
public class Alumno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Column(name = "ci")
    private Integer ci;
    @Size(max = 45)
    @Column(name = "id_colegio")
    private String idColegio;
    @OneToMany(mappedBy = "alumnoId")
    private Collection<PagoAlumno> pagoAlumnoCollection;
    @JoinColumn(name = "datos_persona_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private DatosPersona datosPersonaId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "alumnoId")
    private Collection<AlumnoHasRepresentante> alumnoHasRepresentanteCollection;
    @OneToMany(mappedBy = "alumnoId")
    private Collection<CursoHasAlumno> cursoHasAlumnoCollection;

    public Alumno() {
    }

    public Alumno(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCi() {
        return ci;
    }

    public void setCi(Integer ci) {
        this.ci = ci;
    }

    public String getIdColegio() {
        return idColegio;
    }

    public void setIdColegio(String idColegio) {
        this.idColegio = idColegio;
    }

    @XmlTransient
    public Collection<PagoAlumno> getPagoAlumnoCollection() {
        return pagoAlumnoCollection;
    }

    public void setPagoAlumnoCollection(Collection<PagoAlumno> pagoAlumnoCollection) {
        this.pagoAlumnoCollection = pagoAlumnoCollection;
    }

    public DatosPersona getDatosPersonaId() {
        return datosPersonaId;
    }

    public void setDatosPersonaId(DatosPersona datosPersonaId) {
        this.datosPersonaId = datosPersonaId;
    }

    @XmlTransient
    public Collection<AlumnoHasRepresentante> getAlumnoHasRepresentanteCollection() {
        return alumnoHasRepresentanteCollection;
    }

    public void setAlumnoHasRepresentanteCollection(Collection<AlumnoHasRepresentante> alumnoHasRepresentanteCollection) {
        this.alumnoHasRepresentanteCollection = alumnoHasRepresentanteCollection;
    }

    @XmlTransient
    public Collection<CursoHasAlumno> getCursoHasAlumnoCollection() {
        return cursoHasAlumnoCollection;
    }

    public void setCursoHasAlumnoCollection(Collection<CursoHasAlumno> cursoHasAlumnoCollection) {
        this.cursoHasAlumnoCollection = cursoHasAlumnoCollection;
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
        if (!(object instanceof Alumno)) {
            return false;
        }
        Alumno other = (Alumno) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.school.entities.Alumno[ id=" + id + " ]";
    }

}
