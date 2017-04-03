package edu.school.ejb;

import edu.school.entities.Materia;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

@Local
public interface MateriaFacadeLocal {

    Materia create(Materia materia);
    
    void batchCreate(Collection<Materia> collection);

    void edit(Materia materia);
    
    void batchEdit(Collection<Materia> collection);

    void remove(Materia materia);

    Materia find(Object id);
    
    Materia find(String nombre);
    
    List<Materia> findAll();

    List<Materia> findRange(int[] range);

    int count();
    
}
