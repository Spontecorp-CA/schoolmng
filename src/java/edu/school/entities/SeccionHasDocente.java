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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jgcastillo
 */
@Entity
@Table(name = "seccion_has_docente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SeccionHasDocente.findAll", query = "SELECT s FROM SeccionHasDocente s")
    , @NamedQuery(name = "SeccionHasDocente.findById", query = "SELECT s FROM SeccionHasDocente s WHERE s.id = :id")})
public class SeccionHasDocente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "seccion_id", referencedColumnName = "id")
    @ManyToOne
    private Seccion seccionId;
    @JoinColumn(name = "docente_id", referencedColumnName = "id")
    @ManyToOne
    private Docente docenteId;

    public SeccionHasDocente() {
    }

    public SeccionHasDocente(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Seccion getSeccionId() {
        return seccionId;
    }

    public void setSeccionId(Seccion seccionId) {
        this.seccionId = seccionId;
    }

    public Docente getDocenteId() {
        return docenteId;
    }

    public void setDocenteId(Docente docenteId) {
        this.docenteId = docenteId;
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
        if (!(object instanceof SeccionHasDocente)) {
            return false;
        }
        SeccionHasDocente other = (SeccionHasDocente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.school.entities.SeccionHasDocente[ id=" + id + " ]";
    }
    
}
