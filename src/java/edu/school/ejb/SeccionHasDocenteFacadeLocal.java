package edu.school.ejb;

import edu.school.entities.Seccion;
import edu.school.entities.SeccionHasDocente;
import edu.school.entities.Docente;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

@Local
public interface SeccionHasDocenteFacadeLocal {

    void create(SeccionHasDocente cursoHasDocente);
    
    void batchCreate(Collection<SeccionHasDocente> collection);

    void edit(SeccionHasDocente cursoHasDocente);
    
    void batchEdit(Collection<SeccionHasDocente> collection);

    void remove(SeccionHasDocente cursoHasDocente);

    SeccionHasDocente find(Object id);

    List<SeccionHasDocente> findAll();
    
    List<SeccionHasDocente> findAllBySeccion(Seccion curso);
    
    List<Seccion> findAllByDocente(Docente docente);

    List<SeccionHasDocente> findRange(int[] range);

    int count();

}
