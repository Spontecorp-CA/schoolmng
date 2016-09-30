/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.school.ejb;

import edu.school.entities.Docente;
import edu.school.entities.Materia;
import edu.school.entities.MateriaHasDocente;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jgcastillo
 */
@Local
public interface MateriaHasDocenteFacadeLocal {

    void create(MateriaHasDocente materiaHasDocente);

    void edit(MateriaHasDocente materiaHasDocente);

    void remove(MateriaHasDocente materiaHasDocente);

    MateriaHasDocente find(Object id);

    List<MateriaHasDocente> findAll();
    
    List<Materia> findAll(Docente docente);

    List<MateriaHasDocente> findRange(int[] range);

    int count();
    
}
