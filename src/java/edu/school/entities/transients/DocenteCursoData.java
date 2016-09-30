package edu.school.entities.transients;

import edu.school.entities.Curso;
import edu.school.entities.Docente;
import edu.school.entities.Materia;
import java.util.List;

public class DocenteCursoData {
    
    private Docente docente;
    private List<Curso> cursos;
    private List<Materia> materias;

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    public List<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }
    
}
