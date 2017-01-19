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
@Table(name = "administrativo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Administrativo.findAll", query = "SELECT a FROM Administrativo a")
    , @NamedQuery(name = "Administrativo.findById", query = "SELECT a FROM Administrativo a WHERE a.id = :id")
    , @NamedQuery(name = "Administrativo.findByCargo", query = "SELECT a FROM Administrativo a WHERE a.cargo = :cargo")
    , @NamedQuery(name = "Administrativo.findByMailColegio", query = "SELECT a FROM Administrativo a WHERE a.mailColegio = :mailColegio")})
public class Administrativo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "cargo")
    private String cargo;
    @Size(max = 100)
    @Column(name = "mail_colegio")
    private String mailColegio;
    @JoinColumn(name = "datos_persona_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private DatosPersona datosPersonaId;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    private User userId;

    public Administrativo() {
    }

    public Administrativo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getMailColegio() {
        return mailColegio;
    }

    public void setMailColegio(String mailColegio) {
        this.mailColegio = mailColegio;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Administrativo)) {
            return false;
        }
        Administrativo other = (Administrativo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.school.entities.Administrativo[ id=" + id + " ]";
    }
    
}
