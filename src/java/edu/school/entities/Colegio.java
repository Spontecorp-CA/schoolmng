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
@Table(name = "colegio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Colegio.findAll", query = "SELECT c FROM Colegio c")
    , @NamedQuery(name = "Colegio.findById", query = "SELECT c FROM Colegio c WHERE c.id = :id")
    , @NamedQuery(name = "Colegio.findByNombre", query = "SELECT c FROM Colegio c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "Colegio.findByRif", query = "SELECT c FROM Colegio c WHERE c.rif = :rif")
    , @NamedQuery(name = "Colegio.findByDireccion", query = "SELECT c FROM Colegio c WHERE c.direccion = :direccion")})
public class Colegio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 100)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 20)
    @Column(name = "rif")
    private String rif;
    @Size(max = 255)
    @Column(name = "direccion")
    private String direccion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "colegioId")
    private Collection<Etapa> etapaCollection;
    @OneToMany(mappedBy = "colegioId")
    private Collection<StatusSupervisor> statusSupervisorCollection;

    public Colegio() {
    }

    public Colegio(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRif() {
        return rif;
    }

    public void setRif(String rif) {
        this.rif = rif;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @XmlTransient
    public Collection<Etapa> getEtapaCollection() {
        return etapaCollection;
    }

    public void setEtapaCollection(Collection<Etapa> etapaCollection) {
        this.etapaCollection = etapaCollection;
    }

    @XmlTransient
    public Collection<StatusSupervisor> getStatusSupervisorCollection() {
        return statusSupervisorCollection;
    }

    public void setStatusSupervisorCollection(Collection<StatusSupervisor> statusSupervisorCollection) {
        this.statusSupervisorCollection = statusSupervisorCollection;
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
        if (!(object instanceof Colegio)) {
            return false;
        }
        Colegio other = (Colegio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.school.entities.Colegio[ id=" + id + " ]";
    }
    
}
