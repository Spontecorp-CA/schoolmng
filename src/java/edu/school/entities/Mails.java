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
import javax.persistence.Lob;
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
@Table(name = "mails")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mails.findAll", query = "SELECT m FROM Mails m")
    , @NamedQuery(name = "Mails.findById", query = "SELECT m FROM Mails m WHERE m.id = :id")
    , @NamedQuery(name = "Mails.findByUser", query = "SELECT m FROM Mails m WHERE m.user = :user")
    , @NamedQuery(name = "Mails.findByPassword", query = "SELECT m FROM Mails m WHERE m.password = :password")
    , @NamedQuery(name = "Mails.findByFilePath", query = "SELECT m FROM Mails m WHERE m.filePath = :filePath")
    , @NamedQuery(name = "Mails.findByFileName", query = "SELECT m FROM Mails m WHERE m.fileName = :fileName")
    , @NamedQuery(name = "Mails.findByRecipient", query = "SELECT m FROM Mails m WHERE m.recipient = :recipient")
    , @NamedQuery(name = "Mails.findBySubject", query = "SELECT m FROM Mails m WHERE m.subject = :subject")})
public class Mails implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 50)
    @Column(name = "user")
    private String user;
    @Size(max = 255)
    @Column(name = "password")
    private String password;
    @Size(max = 255)
    @Column(name = "filePath")
    private String filePath;
    @Size(max = 80)
    @Column(name = "fileName")
    private String fileName;
    @Size(max = 50)
    @Column(name = "recipient")
    private String recipient;
    @Size(max = 250)
    @Column(name = "subject")
    private String subject;
    @Lob
    @Size(max = 65535)
    @Column(name = "message")
    private String message;

    public Mails() {
    }

    public Mails(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
        if (!(object instanceof Mails)) {
            return false;
        }
        Mails other = (Mails) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.school.entities.Mails[ id=" + id + " ]";
    }
    
}
