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
import javax.persistence.Lob;
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
@Table(name = "plantilla_circular")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlantillaCircular.findAll", query = "SELECT p FROM PlantillaCircular p")
    , @NamedQuery(name = "PlantillaCircular.findById", query = "SELECT p FROM PlantillaCircular p WHERE p.id = :id")
    , @NamedQuery(name = "PlantillaCircular.findByTipo", query = "SELECT p FROM PlantillaCircular p WHERE p.tipo = :tipo")
    , @NamedQuery(name = "PlantillaCircular.findByLogo", query = "SELECT p FROM PlantillaCircular p WHERE p.logo = :logo")
    , @NamedQuery(name = "PlantillaCircular.findByStatus", query = "SELECT p FROM PlantillaCircular p WHERE p.status = :status")})
public class PlantillaCircular implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "tipo")
    private Integer tipo;
    @Size(max = 100)
    @Column(name = "logo")
    private String logo;
    @Lob
    @Size(max = 65535)
    @Column(name = "header")
    private String header;
    @Lob
    @Size(max = 65535)
    @Column(name = "footer")
    private String footer;
    @Column(name = "status")
    private Integer status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "plantillaCircularId")
    private Collection<Circular> circularCollection;

    public PlantillaCircular() {
    }

    public PlantillaCircular(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @XmlTransient
    public Collection<Circular> getCircularCollection() {
        return circularCollection;
    }

    public void setCircularCollection(Collection<Circular> circularCollection) {
        this.circularCollection = circularCollection;
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
        if (!(object instanceof PlantillaCircular)) {
            return false;
        }
        PlantillaCircular other = (PlantillaCircular) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.school.entities.PlantillaCircular[ id=" + id + " ]";
    }
    
}
