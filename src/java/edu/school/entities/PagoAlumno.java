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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jgcastillo
 */
@Entity
@Table(name = "pago_alumno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PagoAlumno.findAll", query = "SELECT p FROM PagoAlumno p")
    , @NamedQuery(name = "PagoAlumno.findById", query = "SELECT p FROM PagoAlumno p WHERE p.id = :id")
    , @NamedQuery(name = "PagoAlumno.findByDescuento", query = "SELECT p FROM PagoAlumno p WHERE p.descuento = :descuento")})
public class PagoAlumno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "descuento")
    private Double descuento;
    @JoinColumn(name = "alumno_id", referencedColumnName = "id")
    @ManyToOne
    private Alumno alumnoId;
    @JoinColumn(name = "pago_id", referencedColumnName = "id")
    @ManyToOne
    private Pago pagoId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pagoAlumnoId")
    private Collection<PagoHasStatus> pagoHasStatusCollection;

    public PagoAlumno() {
    }

    public PagoAlumno(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public Alumno getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(Alumno alumnoId) {
        this.alumnoId = alumnoId;
    }

    public Pago getPagoId() {
        return pagoId;
    }

    public void setPagoId(Pago pagoId) {
        this.pagoId = pagoId;
    }

    @XmlTransient
    public Collection<PagoHasStatus> getPagoHasStatusCollection() {
        return pagoHasStatusCollection;
    }

    public void setPagoHasStatusCollection(Collection<PagoHasStatus> pagoHasStatusCollection) {
        this.pagoHasStatusCollection = pagoHasStatusCollection;
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
        if (!(object instanceof PagoAlumno)) {
            return false;
        }
        PagoAlumno other = (PagoAlumno) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.school.entities.PagoAlumno[ id=" + id + " ]";
    }
    
}
