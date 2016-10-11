package edu.school.controller;


import edu.school.ejb.CursoFacadeLocal;
import edu.school.ejb.CursoHasAlumnoFacadeLocal;
import edu.school.ejb.PagoAlumnoFacadeLocal;
import edu.school.entities.Alumno;
import edu.school.entities.Curso;
import edu.school.entities.CursoHasAlumno;
import edu.school.entities.Nivel;
import edu.school.entities.Pago;
import edu.school.entities.PagoAlumno;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class ListaCobrosControllerTest {
    
    @EJB
    private CursoFacadeLocal cursoFacade;
    @EJB
    private CursoHasAlumnoFacadeLocal cursoHasAlumnoFacade;
    @EJB
    private PagoAlumnoFacadeLocal pagoAlumnoFacade;
    
    private Nivel nivel;

    public ListaCobrosControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        nivel = new Nivel(3);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of pagosXNivel() method, of class ListaCobrosController.
     */
    //@Test
    public void testPagosXNivel() {
//        System.out.println("pagosXNivel");
//        
//        Set<Pago> pagos = new HashSet<>();
//
//        // se buscan los cursos que están en un nivel
//        List<Curso> cursos = cursosXNivel(nivel);
//        // se buscan los alumnos de cada curso
//        List<CursoHasAlumno> chaList = new ArrayList<>();
//        for (Curso curso : cursos) {
//            chaList.addAll(alumnosXCurso(curso));
//        }
//
//        List<PagoAlumno> paList = new ArrayList<>();
//        for (CursoHasAlumno cha : chaList) {
//            paList.addAll(pagosXAlumno(cha.getAlumnoId()));
//        }
//
//        for (PagoAlumno pa : paList) {
//            pagos.add(pa.getPagoId());
//        }
//        
//        int expected = 1;
//        assertEquals(expected, pagos.size());
    }    
    
    @Test
    public void cursosXNivel() {
        System.out.println("El nivel id es " + nivel.getId());
        List<Curso> cursos = cursoFacade.findAll(nivel);
        
        int expected = 4;
        assertEquals(expected, cursos.size());
        //return cursoFacade.findAll(nivel);
    }

    private List<CursoHasAlumno> alumnosXCurso(Curso curso) {
        return cursoHasAlumnoFacade.findAll(curso);
    }

    private List<PagoAlumno> pagosXAlumno(Alumno alumno) {
        return pagoAlumnoFacade.findAllxAlumno(alumno);
    }
}
