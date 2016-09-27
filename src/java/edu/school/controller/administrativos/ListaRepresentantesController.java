package edu.school.controller.administrativos;

import edu.school.ejb.AlumnoHasRepresentanteFacadeLocal;
import edu.school.ejb.CursoFacadeLocal;
import edu.school.ejb.CursoHasAlumnoFacadeLocal;
import edu.school.ejb.NivelFacadeLocal;
import edu.school.ejb.PeriodoFacadeLocal;
import edu.school.entities.AlumnoHasRepresentante;
import edu.school.entities.Curso;
import edu.school.entities.CursoHasAlumno;
import edu.school.entities.Nivel;
import edu.school.entities.Periodo;
import edu.school.entities.transients.RepresentanteAlumnoData;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class ListaRepresentantesController implements Serializable {

    @EJB
    private AlumnoHasRepresentanteFacadeLocal alumnoHasRepresentanteFacade;
    @EJB
    private CursoHasAlumnoFacadeLocal cursoHasAlumnoFacade;
    @EJB
    private PeriodoFacadeLocal periodoFacade;
    @EJB
    private NivelFacadeLocal nivelFacade;
    @EJB
    private CursoFacadeLocal cursoFacade;

    @Inject
    private Periodo periodo;
    @Inject
    private Nivel nivel;
    @Inject
    private Curso curso;

    private List<RepresentanteAlumnoData> data;

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

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

    public List<SelectItem> getPeriodos() {
        List<Periodo> periodosList = periodoFacade.findAll();
        List<SelectItem> items = new ArrayList<>();
        items.add(new SelectItem(null, "Seleccione periodo..."));
        periodosList.stream().forEach(prd -> {
            items.add(new SelectItem(prd, prd.getNombre()));
        });
        return items;
    }

    public List<SelectItem> getNiveles() {
        List<Nivel> nivelesList = nivelFacade.findAll();
        List<SelectItem> items = new ArrayList<>();
        items.add(new SelectItem(null, "Seleccione nivel..."));
        nivelesList.stream().forEach(prd -> {
            items.add(new SelectItem(prd, prd.getNombre()));
        });
        return items;
    }

    public List<SelectItem> getCursos() {
        List<Curso> cursosList = cursoFacade.findAll(periodo, nivel);
        List<SelectItem> items = new ArrayList<>();
        items.add(new SelectItem(null, "Seleccione curso..."));
        cursosList.stream().forEach(prd -> {
            items.add(new SelectItem(prd, prd.getNombre() + "-" + prd.getSeccion()));
        });
        return items;
    }

    public List<RepresentanteAlumnoData> getData() {
        if (data == null) {
            data = fillDataList();
        }
        return data;
    }

    public void handleCursoChange() {
        data = null;
    }

    private List<RepresentanteAlumnoData> fillDataList() {
        List<RepresentanteAlumnoData> datos = new ArrayList<>();
        
        if(curso != null){
            List<CursoHasAlumno> chaList = cursoHasAlumnoFacade.findAll(curso);
            
            for(CursoHasAlumno cha : chaList){
                List<AlumnoHasRepresentante> ahrList = alumnoHasRepresentanteFacade
                                                    .findAll(cha.getAlumnoId());
                if(ahrList != null && !ahrList.isEmpty()){
                    for(AlumnoHasRepresentante ahr : ahrList){
                        RepresentanteAlumnoData rad = new RepresentanteAlumnoData();
                        rad.setAlumno(ahr.getAlumnoId());
                        rad.setRepresentante(ahr.getRepresentanteId());
                        rad.setCurso(curso);
                        datos.add(rad);
                    }
                }
            }
        }
        return datos;
    }

}