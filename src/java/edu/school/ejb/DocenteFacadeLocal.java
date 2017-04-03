package edu.school.ejb;

import edu.school.entities.Docente;
import edu.school.entities.Periodo;
import edu.school.excepciones.DocenteNotFoundException;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

@Local
public interface DocenteFacadeLocal {

    Docente create(Docente docente);
    
    void batchCreate(Collection<Docente> collection);

    void edit(Docente docente);
    
    void batchEdit(Collection<Docente> collection);

    void remove(Docente docente);

    Docente find(Object id);

    List<Docente> findAll();

    List<Docente> findRange(int[] range);

    int count();

    Docente findByCi(int ci) throws DocenteNotFoundException;

    List<Docente> findAllByPeriodoWSeccionAssigned(Periodo periodo);
    
}
