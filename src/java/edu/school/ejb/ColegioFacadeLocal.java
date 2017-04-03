package edu.school.ejb;

import edu.school.entities.Colegio;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ColegioFacadeLocal {

    Colegio create(Colegio colegio);
    
    void batchCreate(Collection<Colegio> collection);

    void edit(Colegio colegio);

    void batchEdit(Collection<Colegio> collection);
    
    void remove(Colegio colegio);

    Colegio find(Object id);

    List<Colegio> findAll();

    List<Colegio> findRange(int[] range);

    int count();

    Colegio findByRif(String rif);
    
}
