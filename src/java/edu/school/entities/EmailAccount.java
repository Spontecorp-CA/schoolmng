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
@Table(name = "email_account")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmailAccount.findAll", query = "SELECT e FROM EmailAccount e")
    , @NamedQuery(name = "EmailAccount.findById", query = "SELECT e FROM EmailAccount e WHERE e.id = :id")
    , @NamedQuery(name = "EmailAccount.findByNombre", query = "SELECT e FROM EmailAccount e WHERE e.nombre = :nombre")
    , @NamedQuery(name = "EmailAccount.findByUser", query = "SELECT e FROM EmailAccount e WHERE e.user = :user")
    , @NamedQuery(name = "EmailAccount.findByPassword", query = "SELECT e FROM EmailAccount e WHERE e.password = :password")
    , @NamedQuery(name = "EmailAccount.findBySmtp", query = "SELECT e FROM EmailAccount e WHERE e.smtp = :smtp")
    , @NamedQuery(name = "EmailAccount.findByPuerto", query = "SELECT e FROM EmailAccount e WHERE e.puerto = :puerto")})
public class EmailAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 80)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 80)
    @Column(name = "user")
    private String user;
    @Size(max = 255)
    @Column(name = "password")
    private String password;
    @Size(max = 30)
    @Column(name = "smtp")
    private String smtp;
    @Size(max = 5)
    @Column(name = "puerto")
    private String puerto;

    public EmailAccount() {
    }

    public EmailAccount(Integer id) {
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSmtp() {
        return smtp;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    public String getPuerto() {
        return puerto;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
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
        if (!(object instanceof EmailAccount)) {
            return false;
        }
        EmailAccount other = (EmailAccount) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.school.entities.EmailAccount[ id=" + id + " ]";
    }
    
}
