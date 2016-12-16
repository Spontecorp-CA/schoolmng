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
@Table(name = "status_pago")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StatusPago.findAll", query = "SELECT s FROM StatusPago s")
    , @NamedQuery(name = "StatusPago.findById", query = "SELECT s FROM StatusPago s WHERE s.id = :id")
    , @NamedQuery(name = "StatusPago.findByNombre", query = "SELECT s FROM StatusPago s WHERE s.nombre = :nombre")
    , @NamedQuery(name = "StatusPago.findByValor", query = "SELECT s FROM StatusPago s WHERE s.valor = :valor")})
public class StatusPago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "valor")
    private Integer valor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "statusPagoId")
    private Collection<PagoHasStatus> pagoHasStatusCollection;

    public StatusPago() {
    }

    public StatusPago(Integer id) {
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

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
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
        if (!(object instanceof StatusPago)) {
            return false;
        }
        StatusPago other = (StatusPago) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.school.entities.StatusPago[ id=" + id + " ]";
    }
    
}
