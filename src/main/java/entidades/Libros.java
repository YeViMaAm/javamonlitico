/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author yessv
 */
@Entity
@Table(name = "libros")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Libros.findAll", query = "SELECT l FROM Libros l"),
    @NamedQuery(name = "Libros.findByIdLibro", query = "SELECT l FROM Libros l WHERE l.idLibro = :idLibro"),
    @NamedQuery(name = "Libros.findByNombrelibro", query = "SELECT l FROM Libros l WHERE l.nombrelibro = :nombrelibro"),
    @NamedQuery(name = "Libros.findByDescripcion", query = "SELECT l FROM Libros l WHERE l.descripcion = :descripcion"),
    @NamedQuery(name = "Libros.findByEditorial", query = "SELECT l FROM Libros l WHERE l.editorial = :editorial"),
    @NamedQuery(name = "Libros.findByAniopublicacion", query = "SELECT l FROM Libros l WHERE l.aniopublicacion = :aniopublicacion"),
    @NamedQuery(name = "Libros.findByGenero", query = "SELECT l FROM Libros l WHERE l.genero = :genero"),
    @NamedQuery(name = "Libros.findByAutor", query = "SELECT l FROM Libros l WHERE l.autor = :autor")})
    @NamedQuery(name = "Libros.findByImagenUrl", query = "SELECT l FROM Libros l WHERE l.imagenUrl = :imagenUrl")

public class Libros implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_libro")
    private Short idLibro;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombrelibro")
    private String nombrelibro;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 900)
    @Column(name = "descripcion")
    private String descripcion;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "editorial")
    private String editorial;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "aniopublicacion")
    private String aniopublicacion;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "genero")
    private String genero;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "autor")
    private String autor;
    
    @Basic(optional = true)
    @Size(max = 255)
    @Column(name = "imagen_url")
    private String imagenUrl;
    
    public Libros() {
    }

    public Libros(Short idLibro) {
        this.idLibro = idLibro;
    }

    public Libros(Short idLibro, String nombrelibro, String descripcion, String editorial, String aniopublicacion, String genero, String autor) {
        this.idLibro = idLibro;
        this.nombrelibro = nombrelibro;
        this.descripcion = descripcion;
        this.editorial = editorial;
        this.aniopublicacion = aniopublicacion;
        this.genero = genero;
        this.autor = autor;
    }

    public Short getIdLibro() {
        return idLibro;
    }
    public void setIdLibro(Short idLibro) {
        this.idLibro = idLibro;
    }

    
    public String getNombrelibro() {
        return nombrelibro;
    }
    public void setNombrelibro(String nombrelibro) {
        this.nombrelibro = nombrelibro;
    }

    
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    
    public String getEditorial() {
        return editorial;
    }
    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    
    public String getAniopublicacion() {
        return aniopublicacion;
    }
    public void setAniopublicacion(String aniopublicacion) {
        this.aniopublicacion = aniopublicacion;
    }

    
    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }
    
     public String getAutor() {
        return autor;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getImagenUrl() {
    return imagenUrl;
}

    public void setImagenUrl(String imagenUrl) {
    this.imagenUrl = imagenUrl;
}
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLibro != null ? idLibro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Libros)) {
            return false;
        }
        Libros other = (Libros) object;
        if ((this.idLibro == null && other.idLibro != null) || (this.idLibro != null && !this.idLibro.equals(other.idLibro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Libros[ idLibro=" + idLibro + " ]";
    }
    
}
