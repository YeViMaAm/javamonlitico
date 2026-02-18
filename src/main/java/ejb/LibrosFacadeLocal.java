/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ejb;

import entidades.Libros;
import jakarta.ejb.Local;
import java.util.List;

/**
 *
 * @author yessv
 */
@Local
public interface LibrosFacadeLocal {

    void create(Libros libros);

    void edit(Libros libros);

    void remove(Libros libros);

    Libros find(Object id);

    List<Libros> findAll();

    List<Libros> findRange(int[] range);

    int count();
    
}
