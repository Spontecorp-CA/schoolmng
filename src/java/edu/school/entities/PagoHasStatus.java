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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jgcastillo
 */
@Entity
@Table(name = "pago_has_status")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PagoHasStatus.findAll", query = "SELECT p FROM PagoHasStatus p")
    , @NamedQuery(name = "PagoHasStatus.findById", query = "SELECT p FROM PagoHasStatus p WHERE p.id = :id")
    , @NamedQuery(name = "PagoHasStatus.findByFecha", query = "SELECT p FROM PagoHasStatus p WHERE p.fecha = :fecha")})
public class PagoHasStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "pago_alumno_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PagoAlumno pagoAlumnoId;
    @JoinColumn(name = "status_pago_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private StatusPago statusPagoId;

    public PagoHasStatus() {
    }

    public PagoHasStatus(Integer id) {
        this.id = id;
    }

    public PagoHasStatus(Integer id, Date fecha) {
        this.id = id;
        this.fecha = fecha;
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

    public PagoAlumno getPagoAlumnoId() {
        return pagoAlumnoId;
    }

    public void setPagoAlumnoId(PagoAlumno pagoAlumnoId) {
        this.pagoAlumnoId = pagoAlumnoId;
    }

    public StatusPago getStatusPagoId() {
        return statusPagoId;
    }

    public void setStatusPagoId(StatusPago statusPagoId) {
        this.statusPagoId = statusPagoId;
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
        if (!(object instanceof PagoHasStatus)) {
            return false;
        }
        PagoHasStatus other = (PagoHasStatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.school.entities.PagoHasStatus[ id=" + id + " ]";
    }
    
}
