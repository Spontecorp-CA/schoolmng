package edu.school.controller.administrativos;

import edu.school.ejb.CursoHasDocenteFacadeLocal;
import edu.school.ejb.DocenteFacadeLocal;
import edu.school.ejb.MateriaHasDocenteFacadeLocal;
import edu.school.entities.Curso;
import edu.school.entities.CursoHasDocente;
import edu.school.entities.Docente;
import edu.school.entities.MateriaHasDocente;
import edu.school.entities.Nivel;
import edu.school.entities.transients.DocenteCursoData;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class ListaDocentesController implements Serializable {

    @EJB
    private CursoHasDocenteFacadeLocal cursoHasDocenteFacade;
    @EJB
    private MateriaHasDocenteFacadeLocal materiaHasDocenteFacade;
    @EJB
    private DocenteFacadeLocal docenteFacade;

    @Inject
    private Nivel nivel;
    @Inject
    private Curso curso;

    private List<Curso> cursos;
    private List<DocenteCursoData> docentes;
    

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public List<DocenteCursoData> getDocentes() {
        if (docentes == null) {
            docentes = fillDocentesList();
        }
        return docentes;
    }

    public void handleCursoChange() {
        docentes = fillDocentesList();
    }

    private List<DocenteCursoData> fillDocentesList() {
        List<DocenteCursoData> docentesTemp = makeDocentesCursoData();
        Collections.sort(docentesTemp, (d1, d2) -> d1.getDocente()
                                                     .getDatosPersonaId()
                                                     .getApellido()
                .compareTo(d2.getDocente().getDatosPersonaId().getApellido()));

        return docentesTemp;
    }

    private List<DocenteCursoData> makeDocentesCursoData() {
        List<DocenteCursoData> data = new ArrayList<>();
        List<Docente> docenteList = docenteFacade.findAll();
        docenteList.stream()
                .forEach(doc ->{
                    DocenteCursoData dcd = new DocenteCursoData();
                    dcd.setDocente(doc);
                    
                    dcd.setCursos(cursoHasDocenteFacade.findAll(doc));
                    dcd.setMaterias(materiaHasDocenteFacade.findAll(doc));
                    data.add(dcd);
                });
        return data;
    }
}
