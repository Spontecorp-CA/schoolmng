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
@Table(name = "has_supervisor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HasSupervisor.findAll", query = "SELECT h FROM HasSupervisor h")
    , @NamedQuery(name = "HasSupervisor.findById", query = "SELECT h FROM HasSupervisor h WHERE h.id = :id")})
public class HasSupervisor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "administrativo_id", referencedColumnName = "id")
    @ManyToOne
    private Administrativo administrativoId;
    @JoinColumn(name = "docente_id", referencedColumnName = "id")
    @ManyToOne
    private Docente docenteId;
    @JoinColumn(name = "supervisor_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Supervisor supervisorId;

    public HasSupervisor() {
    }

    public HasSupervisor(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Administrativo getAdministrativoId() {
        return administrativoId;
    }

    public void setAdministrativoId(Administrativo administrativoId) {
        this.administrativoId = administrativoId;
    }

    public Docente getDocenteId() {
        return docenteId;
    }

    public void setDocenteId(Docente docenteId) {
        this.docenteId = docenteId;
    }

    public Supervisor getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(Supervisor supervisorId) {
        this.supervisorId = supervisorId;
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
        if (!(object instanceof HasSupervisor)) {
            return false;
        }
        HasSupervisor other = (HasSupervisor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.school.entities.HasSupervisor[ id=" + id + " ]";
    }
    
}
