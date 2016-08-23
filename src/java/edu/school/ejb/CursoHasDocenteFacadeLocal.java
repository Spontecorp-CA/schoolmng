/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.school.ejb;

import edu.school.entities.CursoHasDocente;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jgcastillo
 */
@Local
public interface CursoHasDocenteFacadeLocal {

    void create(CursoHasDocente cursoHasDocente);

    void edit(CursoHasDocente cursoHasDocente);

    void remove(CursoHasDocente cursoHasDocente);

    CursoHasDocente find(Object id);

    List<CursoHasDocente> findAll();

    List<CursoHasDocente> findRange(int[] range);

    int count();
    
}
