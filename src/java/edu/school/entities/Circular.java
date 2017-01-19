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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jgcastillo
 */
@Entity
@Table(name = "circular")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Circular.findAll", query = "SELECT c FROM Circular c")
    , @NamedQuery(name = "Circular.findById", query = "SELECT c FROM Circular c WHERE c.id = :id")
    , @NamedQuery(name = "Circular.findByAsunto", query = "SELECT c FROM Circular c WHERE c.asunto = :asunto")
    , @NamedQuery(name = "Circular.findByDestinatario", query = "SELECT c FROM Circular c WHERE c.destinatario = :destinatario")
    , @NamedQuery(name = "Circular.findByGrupoDestino", query = "SELECT c FROM Circular c WHERE c.grupoDestino = :grupoDestino")
    , @NamedQuery(name = "Circular.findByCodigoCircular", query = "SELECT c FROM Circular c WHERE c.codigoCircular = :codigoCircular")
    , @NamedQuery(name = "Circular.findByFilepath", query = "SELECT c FROM Circular c WHERE c.filepath = :filepath")
    , @NamedQuery(name = "Circular.findByFilename", query = "SELECT c FROM Circular c WHERE c.filename = :filename")})
public class Circular implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "asunto")
    private String asunto;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "message")
    private String message;
    @Size(max = 255)
    @Column(name = "destinatario")
    private String destinatario;
    @Size(max = 20)
    @Column(name = "grupo_destino")
    private String grupoDestino;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "codigo_circular")
    private String codigoCircular;
    @Size(max = 255)
    @Column(name = "filepath")
    private String filepath;
    @Size(max = 80)
    @Column(name = "filename")
    private String filename;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "circularId")
    private Collection<Autorizacion> autorizacionCollection;
    @JoinColumn(name = "email_account_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private EmailAccount emailAccountId;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userId;

    public Circular() {
    }

    public Circular(Integer id) {
        this.id = id;
    }

    public Circular(Integer id, String asunto, String message, String codigoCircular) {
        this.id = id;
        this.asunto = asunto;
        this.message = message;
        this.codigoCircular = codigoCircular;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getGrupoDestino() {
        return grupoDestino;
    }

    public void setGrupoDestino(String grupoDestino) {
        this.grupoDestino = grupoDestino;
    }

    public String getCodigoCircular() {
        return codigoCircular;
    }

    public void setCodigoCircular(String codigoCircular) {
        this.codigoCircular = codigoCircular;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @XmlTransient
    public Collection<Autorizacion> getAutorizacionCollection() {
        return autorizacionCollection;
    }

    public void setAutorizacionCollection(Collection<Autorizacion> autorizacionCollection) {
        this.autorizacionCollection = autorizacionCollection;
    }

    public EmailAccount getEmailAccountId() {
        return emailAccountId;
    }

    public void setEmailAccountId(EmailAccount emailAccountId) {
        this.emailAccountId = emailAccountId;
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
        if (!(object instanceof Circular)) {
            return false;
        }
        Circular other = (Circular) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.school.entities.Circular[ id=" + id + " ]";
    }
    
}
