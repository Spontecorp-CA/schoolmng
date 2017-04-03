/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.school.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jgcastillo
 */
@Entity
@Table(name = "circular_status")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CircularStatus.findAll", query = "SELECT c FROM CircularStatus c")
    , @NamedQuery(name = "CircularStatus.findById", query = "SELECT c FROM CircularStatus c WHERE c.id = :id")
    , @NamedQuery(name = "CircularStatus.findByFecha", query = "SELECT c FROM CircularStatus c WHERE c.fecha = :fecha")
    , @NamedQuery(name = "CircularStatus.findByStatus", query = "SELECT c FROM CircularStatus c WHERE c.status = :status")})
public class CircularStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Size(max = 10)
    @Column(name = "status")
    private int status;
    @JoinColumn(name = "circular_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Circular circularId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "circularStatusId")
    private Collection<Autorizacion> autorizacionCollection;

    public CircularStatus() {
    }

    public CircularStatus(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Circular getCircularId() {
        return circularId;
    }

    public void setCircularId(Circular circularId) {
        this.circularId = circularId;
    }

    @XmlTransient
    public Collection<Autorizacion> getAutorizacionCollection() {
        return autorizacionCollection;
    }

    public void setAutorizacionCollection(Collection<Autorizacion> autorizacionCollection) {
        this.autorizacionCollection = autorizacionCollection;
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
        if (!(object instanceof CircularStatus)) {
            return false;
        }
        CircularStatus other = (CircularStatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.school.entities.CircularStatus[ id=" + id + " ]";
    }
    
}
