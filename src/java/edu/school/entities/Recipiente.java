/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.school.entities;

import java.io.Serializable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jgcastillo
 */
@Entity
@Table(name = "recipiente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recipiente.findAll", query = "SELECT r FROM Recipiente r")
    , @NamedQuery(name = "Recipiente.findById", query = "SELECT r FROM Recipiente r WHERE r.id = :id")
    , @NamedQuery(name = "Recipiente.findByRecipiente", query = "SELECT r FROM Recipiente r WHERE r.recipiente = :recipiente")})
public class Recipiente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 100)
    @Column(name = "recipiente")
    private String recipiente;
    @JoinColumn(name = "circular_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Circular circularId;

    public Recipiente() {
    }

    public Recipiente(Integer id) {
        this.id = id;
    }
    
    public Recipiente(String recipiente, Circular circularId){
        this.recipiente = recipiente;
        this.circularId = circularId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRecipiente() {
        return recipiente;
    }

    public void setRecipiente(String recipiente) {
        this.recipiente = recipiente;
    }

    public Circular getCircularId() {
        return circularId;
    }

    public void setCircularId(Circular circularId) {
        this.circularId = circularId;
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
        if (!(object instanceof Recipiente)) {
            return false;
        }
        Recipiente other = (Recipiente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.school.entities.Recipiente[ id=" + id + " ]";
    }
    
}
