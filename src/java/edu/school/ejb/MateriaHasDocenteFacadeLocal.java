package edu.school.ejb;

import edu.school.entities.Docente;
import edu.school.entities.Materia;
import edu.school.entities.MateriaHasDocente;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

@Local
public interface MateriaHasDocenteFacadeLocal {

    void create(MateriaHasDocente materiaHasDocente);

    void batchCreate(Collection<MateriaHasDocente> collection);
    
    void edit(MateriaHasDocente materiaHasDocente);

    void batchEdit(Collection<MateriaHasDocente> collection);
    
    void remove(MateriaHasDocente materiaHasDocente);

    MateriaHasDocente find(Object id);

    List<MateriaHasDocente> findAll();
    
    List<Materia> findAll(Docente docente);

    List<MateriaHasDocente> findRange(int[] range);

    int count();
    
}
