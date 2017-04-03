package edu.school.ejb;

import edu.school.entities.DatosPersona;
import edu.school.entities.Representante;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

@Local
public interface RepresentanteFacadeLocal {

    Representante create(Representante representante);

    void batchCreate(Collection<Representante> collection);
    
    void edit(Representante representante);

    void batchEdit(Collection<Representante> collection);
    
    void remove(Representante representante);

    Representante find(Object id);
    
    Representante find(DatosPersona datosPersona);

    List<Representante> findAll();

    List<Representante> findRange(int[] range);

    int count();
    
}
