package edu.school.ejb;

import edu.school.entities.Curso;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

@Local
public interface CursoFacadeLocal {
    
    public void create(Curso curso);

    public void batchCreate(Collection<Curso> col);

    public void edit(Curso curso);

    public void batchEdit(Collection<Curso> col);

    public void remove(Curso curso);

    public Curso find(Object id);

    public List<Curso> findAll();

    public List<Curso> findRange(int[] range);

    public int count();

    Curso findByName(String nombre);

}
