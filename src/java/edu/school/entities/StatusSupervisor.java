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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jgcastillo
 */
@Entity
@Table(name = "status_supervisor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StatusSupervisor.findAll", query = "SELECT s FROM StatusSupervisor s")
    , @NamedQuery(name = "StatusSupervisor.findById", query = "SELECT s FROM StatusSupervisor s WHERE s.id = :id")
    , @NamedQuery(name = "StatusSupervisor.findByStatus", query = "SELECT s FROM StatusSupervisor s WHERE s.status = :status")
    , @NamedQuery(name = "StatusSupervisor.findByFechaIn", query = "SELECT s FROM StatusSupervisor s WHERE s.fechaIn = :fechaIn")
    , @NamedQuery(name = "StatusSupervisor.findByFechaOut", query = "SELECT s FROM StatusSupervisor s WHERE s.fechaOut = :fechaOut")})
public class StatusSupervisor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "status")
    private Integer status;
    @Column(name = "fecha_in")
    @Temporal(TemporalType.DATE)
    private Date fechaIn;
    @Column(name = "fecha_out")
    @Temporal(TemporalType.DATE)
    private Date fechaOut;
    @JoinColumn(name = "colegio_id", referencedColumnName = "id")
    @ManyToOne
    private Colegio colegioId;
    @JoinColumn(name = "curso_id", referencedColumnName = "id")
    @ManyToOne
    private Curso cursoId;
    @JoinColumn(name = "etapa_id", referencedColumnName = "id")
    @ManyToOne
    private Etapa etapaId;
    @JoinColumn(name = "supervisor_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Supervisor supervisorId;

    public StatusSupervisor() {
    }

    public StatusSupervisor(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getFechaIn() {
        return fechaIn;
    }

    public void setFechaIn(Date fechaIn) {
        this.fechaIn = fechaIn;
    }

    public Date getFechaOut() {
        return fechaOut;
    }

    public void setFechaOut(Date fechaOut) {
        this.fechaOut = fechaOut;
    }

    public Colegio getColegioId() {
        return colegioId;
    }

    public void setColegioId(Colegio colegioId) {
        this.colegioId = colegioId;
    }

    public Curso getCursoId() {
        return cursoId;
    }

    public void setCursoId(Curso cursoId) {
        this.cursoId = cursoId;
    }

    public Etapa getEtapaId() {
        return etapaId;
    }

    public void setEtapaId(Etapa etapaId) {
        this.etapaId = etapaId;
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
        if (!(object instanceof StatusSupervisor)) {
            return false;
        }
        StatusSupervisor other = (StatusSupervisor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.school.entities.StatusSupervisor[ id=" + id + " ]";
    }
    
}
