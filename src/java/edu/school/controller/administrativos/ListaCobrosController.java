package edu.school.controller.administrativos;

import edu.school.ejb.CursoFacadeLocal;
import edu.school.ejb.CursoHasAlumnoFacadeLocal;
import edu.school.ejb.NivelFacadeLocal;
import edu.school.ejb.PagoAlumnoFacadeLocal;
import edu.school.ejb.PagoFacadeLocal;
import edu.school.entities.Alumno;
import edu.school.entities.Curso;
import edu.school.entities.CursoHasAlumno;
import edu.school.entities.Nivel;
import edu.school.entities.Pago;
import edu.school.entities.PagoAlumno;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class ListaCobrosController implements Serializable {

    @EJB
    private PagoFacadeLocal pagoFacade;
    @EJB
    private CursoFacadeLocal cursoFacade;
    @EJB
    private CursoHasAlumnoFacadeLocal cursoHasAlumnoFacade;
    @EJB
    private PagoAlumnoFacadeLocal pagoAlumnoFacade;
    @EJB
    private NivelFacadeLocal nivelFacade;

    @Inject
    private Nivel nivel;
    @Inject
    private Curso curso;
    @Inject
    private Alumno alumno;

    private List<SelectItem> niveles;
    private List<SelectItem> cursos;
    private List<SelectItem> alumnos;

    static final Comparator<Pago> COMPARATOR_BY_DATE
            = (Pago p1, Pago p2) -> p2.getFecha().compareTo(p1.getFecha());
    static final Comparator<Alumno> COMPARATOR_BY_APELLIDO
            = (Alumno a1, Alumno a2) -> a1.getDatosPersonaId().getApellido()
            .compareTo(a2.getDatosPersonaId().getApellido());

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

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public List<SelectItem> getNiveles() {
        List<Nivel> nivelList = nivelFacade.findAll();
        niveles = new ArrayList<>();
        niveles.add(new SelectItem(null, "Seleccione nivel..."));
        nivelList.stream().forEach(nv -> {
            niveles.add(new SelectItem(nv, nv.getNombre()));
        });
        return niveles;
    }

    public List<SelectItem> getCursos() {
        List<Curso> cursoList = cursoFacade.findAll(nivel);
        cursos = new ArrayList<>();
        cursos.add(new SelectItem(null, "Seleccione curso..."));
        cursoList.stream().forEach(cur -> {
            StringBuilder sb = new StringBuilder();
            sb.append(cur.getNombre()).append("-").append(cur.getSeccion());
            cursos.add(new SelectItem(cur, sb.toString()));
        });
        return cursos;
    }

    public List<SelectItem> getAlumnos() {
        List<CursoHasAlumno> chaList = alumnosXCurso(curso);
        List<Alumno> alumnoList = new ArrayList<>();
        for (CursoHasAlumno cha : chaList) {
            alumnoList.add(cha.getAlumnoId());
        }
        alumnoList = alumnoList.stream()
                .sorted(COMPARATOR_BY_APELLIDO)
                .collect(Collectors.toList());
        alumnos = new ArrayList<>();

        alumnos.add(new SelectItem(null, "Seleccione alumno..."));
        for (Alumno al : alumnoList) {
            StringBuilder sb = new StringBuilder();
            sb.append(al.getDatosPersonaId().getApellido());
            sb.append(", ").append(al.getDatosPersonaId().getNombre());
            alumnos.add(new SelectItem(al, sb.toString()));
        }

        return alumnos;
    }

    public List<Pago> pagos() {
        List<Pago> lista = pagoFacade.findAll();
        lista = lista.stream()
                .sorted(COMPARATOR_BY_DATE)
                .collect(Collectors.toList());

        return lista;
    }

    public List<Pago> pagosXNivel() {
        Set<Pago> pagosSet = new HashSet<>();
        List<Pago> pagos = new ArrayList<>();

        // se buscan los cursos que están en un nivel
        List<Curso> cursos = cursosXNivel(nivel);
        // se buscan los alumnos de cada curso
        List<CursoHasAlumno> chaList = new ArrayList<>();
        for (Curso curso : cursos) {
            chaList.addAll(alumnosXCurso(curso));
        }

        List<PagoAlumno> paList = new ArrayList<>();
        for (CursoHasAlumno cha : chaList) {
            paList.addAll(pagosXAlumno(cha.getAlumnoId()));
        }

        for (PagoAlumno pa : paList) {
            pagosSet.add(pa.getPagoId());
        }

        pagos.addAll(pagosSet);

        pagos = pagos.stream()
                .sorted(COMPARATOR_BY_DATE)
                .collect(Collectors.toList());

        return pagos;
    }

    public List<Pago> pagosXCurso() {
        Set<Pago> pagosSet = new HashSet<>();
        List<Pago> pagos = new ArrayList<>();

        // se buscan los alumnos del curso
        List<CursoHasAlumno> chaList = new ArrayList<>();
        if (curso != null) {
            chaList.addAll(alumnosXCurso(curso));

            List<PagoAlumno> paList = new ArrayList<>();
            for (CursoHasAlumno cha : chaList) {
                paList.addAll(pagosXAlumno(cha.getAlumnoId()));
            }

            for (PagoAlumno pa : paList) {
                pagosSet.add(pa.getPagoId());
            }

            pagos.addAll(pagosSet);

            pagos = pagos.stream()
                    .sorted(COMPARATOR_BY_DATE)
                    .collect(Collectors.toList());
        }
        return pagos;
    }

    public List<Pago> pagosXAlumno() {
        List<Pago> pagos = new ArrayList<>();

        if (alumno != null) {
            List<PagoAlumno> paList = pagosXAlumno(alumno);

            for (PagoAlumno pa : paList) {
                pagos.add(pa.getPagoId());
            }

            pagos = pagos.stream()
                    .sorted(COMPARATOR_BY_DATE)
                    .collect(Collectors.toList());
        }
        return pagos;
    }

    private List<Curso> cursosXNivel(Nivel nivel) {
        return cursoFacade.findAll(nivel);
    }

    private List<CursoHasAlumno> alumnosXCurso(Curso curso) {
        return cursoHasAlumnoFacade.findAll(curso);
    }

    private List<PagoAlumno> pagosXAlumno(Alumno alumno) {
        return pagoAlumnoFacade.findAllxAlumno(alumno);
    }

}
