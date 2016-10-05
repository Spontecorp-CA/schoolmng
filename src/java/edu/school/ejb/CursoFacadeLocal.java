/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.school.ejb;

import edu.school.entities.Curso;
import edu.school.entities.Nivel;
import edu.school.entities.Periodo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jgcastillo
 */
@Local
public interface CursoFacadeLocal {

    void create(Curso curso);

    void edit(Curso curso);

    void remove(Curso curso);

    Curso find(Object id);
    
    Curso find(String codigo, String nombre, Periodo periodo);
    
    Curso findByStringId(String idString);
    
    List<Curso> findAll();
    
    List<Curso> findAll(Nivel nivel);
    
    List<Curso> findAll(Periodo periodo, Nivel nivel);
    
    List<Curso> findAllOrdered();

    List<Curso> findRange(int[] range);

    int count();
    
}
