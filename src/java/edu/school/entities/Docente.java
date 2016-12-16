/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.school.entities;

import java.io.Serializable;
import java.util.Collection;
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
@Table(name = "docente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Docente.findAll", query = "SELECT d FROM Docente d")
    , @NamedQuery(name = "Docente.findById", query = "SELECT d FROM Docente d WHERE d.id = :id")
    , @NamedQuery(name = "Docente.findByMailColegio", query = "SELECT d FROM Docente d WHERE d.mailColegio = :mailColegio")})
public class Docente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 100)
    @Column(name = "mail_colegio")
    private String mailColegio;
    @OneToMany(mappedBy = "docenteId")
    private Collection<MateriaHasDocente> materiaHasDocenteCollection;
    @JoinColumn(name = "supervisor_id", referencedColumnName = "id")
    @ManyToOne
    private Supervisor supervisorId;
    @JoinColumn(name = "datos_persona_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private DatosPersona datosPersonaId;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    private User userId;
    @OneToMany(mappedBy = "docenteId")
    private Collection<CursoHasDocente> cursoHasDocenteCollection;

    public Docente() {
    }

    public Docente(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMailColegio() {
        return mailColegio;
    }

    public void setMailColegio(String mailColegio) {
        this.mailColegio = mailColegio;
    }

    @XmlTransient
    public Collection<MateriaHasDocente> getMateriaHasDocenteCollection() {
        return materiaHasDocenteCollection;
    }

    public void setMateriaHasDocenteCollection(Collection<MateriaHasDocente> materiaHasDocenteCollection) {
        this.materiaHasDocenteCollection = materiaHasDocenteCollection;
    }

    public Supervisor getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(Supervisor supervisorId) {
        this.supervisorId = supervisorId;
    }

    public DatosPersona getDatosPersonaId() {
        return datosPersonaId;
    }

    public void setDatosPersonaId(DatosPersona datosPersonaId) {
        this.datosPersonaId = datosPersonaId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @XmlTransient
    public Collection<CursoHasDocente> getCursoHasDocenteCollection() {
        return cursoHasDocenteCollection;
    }

    public void setCursoHasDocenteCollection(Collection<CursoHasDocente> cursoHasDocenteCollection) {
        this.cursoHasDocenteCollection = cursoHasDocenteCollection;
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
        if (!(object instanceof Docente)) {
            return false;
        }
        Docente other = (Docente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.school.entities.Docente[ id=" + id + " ]";
    }
    
}
