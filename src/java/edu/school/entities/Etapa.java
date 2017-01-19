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
@Table(name = "etapa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Etapa.findAll", query = "SELECT e FROM Etapa e")
    , @NamedQuery(name = "Etapa.findById", query = "SELECT e FROM Etapa e WHERE e.id = :id")
    , @NamedQuery(name = "Etapa.findByNombre", query = "SELECT e FROM Etapa e WHERE e.nombre = :nombre")
    , @NamedQuery(name = "Etapa.findByPrefijo", query = "SELECT e FROM Etapa e WHERE e.prefijo = :prefijo")})
public class Etapa implements Serializable, Comparable<Etapa> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "prefijo")
    private Integer prefijo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "etapaId")
    private Collection<Curso> cursoCollection;
    @JoinColumn(name = "colegio_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Colegio colegioId;
    @OneToMany(mappedBy = "etapaId")
    private Collection<StatusSupervisor> statusSupervisorCollection;

    public Etapa() {
    }

    public Etapa(Integer id) {
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

    public Integer getPrefijo() {
        return prefijo;
    }

    public void setPrefijo(Integer prefijo) {
        this.prefijo = prefijo;
    }

    @XmlTransient
    public Collection<Curso> getCursoCollection() {
        return cursoCollection;
    }

    public void setCursoCollection(Collection<Curso> cursoCollection) {
        this.cursoCollection = cursoCollection;
    }

    public Colegio getColegioId() {
        return colegioId;
    }

    public void setColegioId(Colegio colegioId) {
        this.colegioId = colegioId;
    }

    @XmlTransient
    public Collection<StatusSupervisor> getStatusSupervisorCollection() {
        return statusSupervisorCollection;
    }

    public void setStatusSupervisorCollection(Collection<StatusSupervisor> statusSupervisorCollection) {
        this.statusSupervisorCollection = statusSupervisorCollection;
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
        if (!(object instanceof Etapa)) {
            return false;
        }
        Etapa other = (Etapa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.school.entities.Etapa[ id=" + id + " ]";
    }

    @Override
    public int compareTo(Etapa etp) {
        return (this.getPrefijo().compareTo(etp.getPrefijo()));
    }
    
}
