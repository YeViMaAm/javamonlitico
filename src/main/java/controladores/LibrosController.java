/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controladores;

import ejb.LibrosFacadeLocal;
import entidades.Libros;
import interf.Operaciones;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yessv
 */
@Named(value = "librosController")
@ViewScoped
public class LibrosController implements Serializable, Operaciones {

    @EJB 
    LibrosFacadeLocal libroEJB;
    private List<Libros> lstLibros = new ArrayList<>();
    private Libros nuevoLibro = new Libros();
    private Libros libro = new Libros();
    private Short idLibro;
    private String filtro;
    private List<Libros> itemsFiltrados;
    
    public LibrosController() { 
    }
    
    @Override
    public String buscar() {
    long startTime = System.nanoTime();
    
    try {
        List<Libros> todosLibros = libroEJB.findAll();
        
        if (filtro == null || filtro.trim().isEmpty()) {
            itemsFiltrados = new ArrayList<>(todosLibros);
        } else {
            String filtroNormalizado = filtro.trim().toLowerCase();
            itemsFiltrados = new ArrayList<>();
            
            // Estrategia de búsqueda optimizada
            for (Libros libro : todosLibros) {
                String titulo = libro.getNombrelibro().toLowerCase();
                
                // Para filtros cortos, búsqueda exacta más eficiente
                if (filtroNormalizado.length() <= 3) {
                    if (titulo.equals(filtroNormalizado)) {
                        itemsFiltrados.add(libro);
                    }
                } 
                // Para filtros más largos, búsqueda por palabras
                else if (!filtroNormalizado.contains(" ")) {
                    // Búsqueda por palabra completa
                    String[] palabras = titulo.split("\\s+");
                    for (String palabra : palabras) {
                        if (palabra.equals(filtroNormalizado)) {
                            itemsFiltrados.add(libro);
                            break;
                        }
                    }
                } 
                // Búsqueda por subcadena para otros casos
                else {
                    if (titulo.contains(filtroNormalizado)) {
                        itemsFiltrados.add(libro);
                    }
                }
            }
        }
        
        ordenarPorId();
        
        long endTime = System.nanoTime();
        double durationMs = (endTime - startTime) / 1_000_000.0;
        System.out.println("Búsqueda completada en: " + durationMs + " ms - Resultados: " + itemsFiltrados.size());
        
    } catch (Exception e) {
        System.out.println("Error en búsqueda: " + e.getMessage());
        itemsFiltrados = new ArrayList<>();
    }
    
    return "";
    }
    
    @Override
    public String registrar() {
        System.out.println("MÉTODO REGISTRAR INICIADO");
        System.out.println("Datos del libro:");
        System.out.println("Título: " + nuevoLibro.getNombrelibro());
        System.out.println("Autor: " + nuevoLibro.getAutor());
        System.out.println("Editorial: " + nuevoLibro.getEditorial());

        try {
            System.out.println("Intentando guardar en BD...");
            libroEJB.create(nuevoLibro);
            System.out.println("Libro guardado en BD exitosamente");

            nuevoLibro = new Libros();
            System.out.println("Formulario limpiado");

            return "/index.xhtml?faces-redirect=true";

        } catch (Exception e) {
            System.out.println("ERROR al guardar: " + e.getMessage());
        }
        return null;
    }
    
    @Override
    public String eliminar() {
       System.out.println("MÉTODO ELIMINAR EJECUTADO");

       if (libro == null) {
           System.out.println("ERROR: No hay libro seleccionado para eliminar");
           return null;
       }

       System.out.println("Libro a eliminar: ID=" + libro.getIdLibro() + ", Título=" + libro.getNombrelibro());

       try {
           libroEJB.remove(libro);
           System.out.println("Libro eliminado exitosamente");

           lstLibros = libroEJB.findAll();
           itemsFiltrados = libroEJB.findAll();
           ordenarPorId();

           return null;

       } catch (Exception e) {
           System.out.println("ERROR al eliminar: " + e.getMessage());
       }
       return null;
   }

    public void seleccionarLibro(Libros libroSeleccionado) {
        this.libro = libroSeleccionado;
        System.out.println("Libro seleccionado: " + libro.getNombrelibro());
    }
    
    @Override
    public String actualizar() {
        System.out.println("MÉTODO ACTUALIZAR EJECUTADO");

        if (libro == null) {
            System.out.println("ERROR: No hay libro seleccionado para actualizar");
            return null;
        }

        System.out.println("Libro a actualizar: ID=" + libro.getIdLibro() + ", Título=" + libro.getNombrelibro());

        try {
            libroEJB.edit(libro);
            System.out.println("Libro actualizado exitosamente");

            lstLibros = libroEJB.findAll();
            itemsFiltrados = libroEJB.findAll();
            ordenarPorId();

            return "/index.xhtml?faces-redirect=true";

        } catch (Exception e) {
            System.out.println("ERROR al actualizar: " + e.getMessage());
        }
        return null;
    }
    
    public Short getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Short idLibro) {
        this.idLibro = idLibro;
    }

    public void buscarPorId() {
        System.out.println("BUSCANDO LIBRO POR ID: " + idLibro);
        try {
            if (idLibro != null) {
                libro = libroEJB.find(idLibro);
                if (libro != null) {
                    System.out.println("Libro encontrado: " + libro.getNombrelibro());
                } else {
                    System.out.println("No se encontró libro con ID: " + idLibro);
                }
            } else {
                System.out.println("ID Libro es NULL");
            }
        } catch (Exception e) {
            System.out.println("ERROR al buscar libro: " + e.getMessage());
        }
    }
    
    @PostConstruct
    private void init(){
        iniciar();
    }
    
    private void iniciar(){
        try{
            libro = new Libros();
            lstLibros = libroEJB.findAll();
            itemsFiltrados = libroEJB.findAll();
            
            
            ordenarPorId();
            System.out.println("init() ejecutando correctamente, lista size: " + lstLibros.size());
        }catch (Exception e){
            System.out.println("Error en init(): " + e.getMessage());
        }
    }
    
    private void ordenarPorId() {
        lstLibros.sort((l1, l2) -> l1.getIdLibro().compareTo(l2.getIdLibro()));
        itemsFiltrados.sort((l1, l2) -> l1.getIdLibro().compareTo(l2.getIdLibro()));
    }
    
    public List<Libros> getLstLibros() {
        return lstLibros;
    }

    public void setLstLibros(List<Libros> lstLibros) {
        this.lstLibros = lstLibros;
    }

    public LibrosFacadeLocal getLibroEJB() {
        return libroEJB;
    }

    public void setLibroEJB(LibrosFacadeLocal libroEJB) {
        this.libroEJB = libroEJB;
    }

    public Libros getNuevoLibro() {
        return nuevoLibro;
    }

    public void setNuevoLibro(Libros nuevoLibro) {
        this.nuevoLibro = nuevoLibro;
    }

    public Libros getLibro() {
        return libro;
    }

    public void setLibro(Libros libro) {
        this.libro = libro;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public List<Libros> getItemsFiltrados() {
        return itemsFiltrados;
    }

    public void setItemsFiltrados(List<Libros> itemsFiltrados) {
        this.itemsFiltrados = itemsFiltrados;
    }
}