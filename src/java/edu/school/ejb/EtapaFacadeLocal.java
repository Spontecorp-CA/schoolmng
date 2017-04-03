package edu.school.ejb;

import edu.school.entities.Etapa;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

@Local
public interface EtapaFacadeLocal {

    Etapa create(Etapa nivel);
    
    void batchCreate(Collection<Etapa> collection);

    void edit(Etapa nivel);
    
    void batchEdit(Collection<Etapa> collection);

    void remove(Etapa nivel);

    Etapa find(Object id);
    
    Etapa findByNombre(String nombre);
    
    Etapa findByPrefijo(int prefijo);
    
    //boolean exist(Etapa nivel);

    List<Etapa> findAll();

    List<Etapa> findRange(int[] range);

    int count();
    
}
