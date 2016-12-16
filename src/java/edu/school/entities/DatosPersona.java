/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.school.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jgcastillo
 */
@Entity
@Table(name = "datos_persona")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DatosPersona.findAll", query = "SELECT d FROM DatosPersona d")
    , @NamedQuery(name = "DatosPersona.findById", query = "SELECT d FROM DatosPersona d WHERE d.id = :id")
    , @NamedQuery(name = "DatosPersona.findByNombre", query = "SELECT d FROM DatosPersona d WHERE d.nombre = :nombre")
    , @NamedQuery(name = "DatosPersona.findByApellido", query = "SELECT d FROM DatosPersona d WHERE d.apellido = :apellido")
    , @NamedQuery(name = "DatosPersona.findByCi", query = "SELECT d FROM DatosPersona d WHERE d.ci = :ci")
    , @NamedQuery(name = "DatosPersona.findByNacionalidad", query = "SELECT d FROM DatosPersona d WHERE d.nacionalidad = :nacionalidad")
    , @NamedQuery(name = "DatosPersona.findByFechaNacimiento", query = "SELECT d FROM DatosPersona d WHERE d.fechaNacimiento = :fechaNacimiento")
    , @NamedQuery(name = "DatosPersona.findByEmail", query = "SELECT d FROM DatosPersona d WHERE d.email = :email")
    , @NamedQuery(name = "DatosPersona.findByTelefono", query = "SELECT d FROM DatosPersona d WHERE d.telefono = :telefono")
    , @NamedQuery(name = "DatosPersona.findByCelular", query = "SELECT d FROM DatosPersona d WHERE d.celular = :celular")
    , @NamedQuery(name = "DatosPersona.findBySexo", query = "SELECT d FROM DatosPersona d WHERE d.sexo = :sexo")})
public class DatosPersona implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 60)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 60)
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "ci")
    private Integer ci;
    @Column(name = "nacionalidad")
    private Character nacionalidad;
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 100)
    @Column(name = "email")
    private String email;
    @Size(max = 20)
    @Column(name = "telefono")
    private String telefono;
    @Size(max = 20)
    @Column(name = "celular")
    private String celular;
    @Lob
    @Size(max = 65535)
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "sexo")
    private Character sexo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "datosPersonaId")
    private Collection<Administrativo> administrativoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "datosPersonaId")
    private Collection<Docente> docenteCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "datosPersonaId")
    private Collection<Alumno> alumnoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "datosPersonaId")
    private Collection<Representante> representanteCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "datosPersonaId")
    private Collection<Supervisor> supervisorCollection;

    public DatosPersona() {
    }

    public DatosPersona(Integer id) {
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getCi() {
        return ci;
    }

    public void setCi(Integer ci) {
        this.ci = ci;
    }

    public Character getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(Character nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    @XmlTransient
    public Collection<Administrativo> getAdministrativoCollection() {
        return administrativoCollection;
    }

    public void setAdministrativoCollection(Collection<Administrativo> administrativoCollection) {
        this.administrativoCollection = administrativoCollection;
    }

    @XmlTransient
    public Collection<Docente> getDocenteCollection() {
        return docenteCollection;
    }

    public void setDocenteCollection(Collection<Docente> docenteCollection) {
        this.docenteCollection = docenteCollection;
    }

    @XmlTransient
    public Collection<Alumno> getAlumnoCollection() {
        return alumnoCollection;
    }

    public void setAlumnoCollection(Collection<Alumno> alumnoCollection) {
        this.alumnoCollection = alumnoCollection;
    }

    @XmlTransient
    public Collection<Representante> getRepresentanteCollection() {
        return representanteCollection;
    }

    public void setRepresentanteCollection(Collection<Representante> representanteCollection) {
        this.representanteCollection = representanteCollection;
    }

    @XmlTransient
    public Collection<Supervisor> getSupervisorCollection() {
        return supervisorCollection;
    }

    public void setSupervisorCollection(Collection<Supervisor> supervisorCollection) {
        this.supervisorCollection = supervisorCollection;
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
        if (!(object instanceof DatosPersona)) {
            return false;
        }
        DatosPersona other = (DatosPersona) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.school.entities.DatosPersona[ id=" + id + " ]";
    }
    
}
