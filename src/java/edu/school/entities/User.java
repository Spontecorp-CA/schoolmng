package edu.school.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
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
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "ci")
    private Integer ci;
    @Size(max = 45)
    @Column(name = "usr")
    private String usr;
    @Size(max = 128)
    @Column(name = "psw")
    private String psw;
    @Column(name = "status")
    private Integer status;
    @OneToMany(mappedBy = "userId")
    private Collection<Adminitrativo> adminitrativoCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<Docente> docenteCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<UserHasRol> userHasRolCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<Representante> representanteCollection;

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCi() {
        return ci;
    }

    public void setCi(Integer ci) {
        this.ci = ci;
    }

    public String getUsr() {
        return usr;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @XmlTransient
    public Collection<Adminitrativo> getAdminitrativoCollection() {
        return adminitrativoCollection;
    }

    public void setAdminitrativoCollection(Collection<Adminitrativo> adminitrativoCollection) {
        this.adminitrativoCollection = adminitrativoCollection;
    }

    @XmlTransient
    public Collection<Docente> getDocenteCollection() {
        return docenteCollection;
    }

    public void setDocenteCollection(Collection<Docente> docenteCollection) {
        this.docenteCollection = docenteCollection;
    }

    @XmlTransient
    public Collection<UserHasRol> getUserHasRolCollection() {
        return userHasRolCollection;
    }

    public void setUserHasRolCollection(Collection<UserHasRol> userHasRolCollection) {
        this.userHasRolCollection = userHasRolCollection;
    }

    @XmlTransient
    public Collection<Representante> getRepresentanteCollection() {
        return representanteCollection;
    }

    public void setRepresentanteCollection(Collection<Representante> representanteCollection) {
        this.representanteCollection = representanteCollection;
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.school.entities.User[ id=" + id + " ]";
    }
    
}
