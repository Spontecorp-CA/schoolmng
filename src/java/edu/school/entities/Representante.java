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
@Table(name = "representante")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Representante.findAll", query = "SELECT r FROM Representante r")
    , @NamedQuery(name = "Representante.findById", query = "SELECT r FROM Representante r WHERE r.id = :id")
    , @NamedQuery(name = "Representante.findByParentesco", query = "SELECT r FROM Representante r WHERE r.parentesco = :parentesco")
    , @NamedQuery(name = "Representante.findByTrabajo", query = "SELECT r FROM Representante r WHERE r.trabajo = :trabajo")
    , @NamedQuery(name = "Representante.findByTelefonoTrabajo1", query = "SELECT r FROM Representante r WHERE r.telefonoTrabajo1 = :telefonoTrabajo1")
    , @NamedQuery(name = "Representante.findByTelefonoTrabajo2", query = "SELECT r FROM Representante r WHERE r.telefonoTrabajo2 = :telefonoTrabajo2")
    , @NamedQuery(name = "Representante.findByExtTrabajo", query = "SELECT r FROM Representante r WHERE r.extTrabajo = :extTrabajo")
    , @NamedQuery(name = "Representante.findByFaxTrabajo", query = "SELECT r FROM Representante r WHERE r.faxTrabajo = :faxTrabajo")})
public class Representante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "parentesco")
    private String parentesco;
    @Size(max = 60)
    @Column(name = "trabajo")
    private String trabajo;
    @Size(max = 20)
    @Column(name = "telefono_trabajo_1")
    private String telefonoTrabajo1;
    @Size(max = 20)
    @Column(name = "telefono_trabajo_2")
    private String telefonoTrabajo2;
    @Size(max = 4)
    @Column(name = "ext_trabajo")
    private String extTrabajo;
    @Size(max = 20)
    @Column(name = "fax_trabajo")
    private String faxTrabajo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "representanteId")
    private Collection<AlumnoHasRepresentante> alumnoHasRepresentanteCollection;
    @JoinColumn(name = "datos_persona_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private DatosPersona datosPersonaId;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    private User userId;

    public Representante() {
    }

    public Representante(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public String getTrabajo() {
        return trabajo;
    }

    public void setTrabajo(String trabajo) {
        this.trabajo = trabajo;
    }

    public String getTelefonoTrabajo1() {
        return telefonoTrabajo1;
    }

    public void setTelefonoTrabajo1(String telefonoTrabajo1) {
        this.telefonoTrabajo1 = telefonoTrabajo1;
    }

    public String getTelefonoTrabajo2() {
        return telefonoTrabajo2;
    }

    public void setTelefonoTrabajo2(String telefonoTrabajo2) {
        this.telefonoTrabajo2 = telefonoTrabajo2;
    }

    public String getExtTrabajo() {
        return extTrabajo;
    }

    public void setExtTrabajo(String extTrabajo) {
        this.extTrabajo = extTrabajo;
    }

    public String getFaxTrabajo() {
        return faxTrabajo;
    }

    public void setFaxTrabajo(String faxTrabajo) {
        this.faxTrabajo = faxTrabajo;
    }

    @XmlTransient
    public Collection<AlumnoHasRepresentante> getAlumnoHasRepresentanteCollection() {
        return alumnoHasRepresentanteCollection;
    }

    public void setAlumnoHasRepresentanteCollection(Collection<AlumnoHasRepresentante> alumnoHasRepresentanteCollection) {
        this.alumnoHasRepresentanteCollection = alumnoHasRepresentanteCollection;
    }

    public DatosPersona getDatosPersonaId() {
        return datosPersonaId;
    }

    public void setDatosPersonaId(DatosPersona datosPersonaId) {
        this.datosPersonaId = datosPersonaId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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
        if (!(object instanceof Representante)) {
            return false;
        }
        Representante other = (Representante) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.school.entities.Representante[ id=" + id + " ]";
    }
    
}
