/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.school.entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jgcastillo
 */
@Entity
@Table(name = "autorizacion_status")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AutorizacionStatus.findAll", query = "SELECT a FROM AutorizacionStatus a")
    , @NamedQuery(name = "AutorizacionStatus.findById", query = "SELECT a FROM AutorizacionStatus a WHERE a.id = :id")
    , @NamedQuery(name = "AutorizacionStatus.findByFecha", query = "SELECT a FROM AutorizacionStatus a WHERE a.fecha = :fecha")
    , @NamedQuery(name = "AutorizacionStatus.findByStatus", query = "SELECT a FROM AutorizacionStatus a WHERE a.status = :status")})
public class AutorizacionStatus implements Serializable {

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
    private String status;
    @JoinColumn(name = "autorizacion_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Autorizacion autorizacionId;

    public AutorizacionStatus() {
    }

    public AutorizacionStatus(Integer id) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Autorizacion getAutorizacionId() {
        return autorizacionId;
    }

    public void setAutorizacionId(Autorizacion autorizacionId) {
        this.autorizacionId = autorizacionId;
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
        if (!(object instanceof AutorizacionStatus)) {
            return false;
        }
        AutorizacionStatus other = (AutorizacionStatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.school.entities.AutorizacionStatus[ id=" + id + " ]";
    }
    
}
