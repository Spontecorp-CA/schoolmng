package edu.school.controller.dataprueba;

import edu.school.ejb.AdministrativoFacadeLocal;
import edu.school.ejb.AlumnoFacadeLocal;
import edu.school.ejb.AlumnoHasRepresentanteFacadeLocal;
import edu.school.ejb.ColegioFacadeLocal;
import edu.school.ejb.CursoFacadeLocal;
import edu.school.ejb.DatosPersonaFacadeLocal;
import edu.school.ejb.DocenteFacadeLocal;
import edu.school.ejb.RepresentanteFacadeLocal;
import edu.school.ejb.RolFacadeLocal;
import edu.school.ejb.UserFacadeLocal;
import edu.school.ejb.UserHasRolFacadeLocal;
import edu.school.entities.Administrativo;
import edu.school.entities.Alumno;
import edu.school.entities.AlumnoHasRepresentante;
import edu.school.entities.DatosPersona;
import edu.school.entities.Docente;
import edu.school.entities.Representante;
import edu.school.entities.Rol;
import edu.school.entities.User;
import edu.school.entities.UserHasRol;
import edu.school.utilities.Constantes;
import edu.school.utilities.Utilities;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import edu.school.ejb.SeccionHasDocenteFacadeLocal;
import edu.school.ejb.EtapaFacadeLocal;
import edu.school.ejb.SeccionHasAlumnoFacadeLocal;
import edu.school.ejb.SeccionFacadeLocal;
import edu.school.entities.Colegio;
import edu.school.entities.Curso;
import edu.school.entities.Etapa;
import edu.school.entities.Seccion;
import edu.school.entities.SeccionHasAlumno;
import edu.school.entities.SeccionHasDocente;
import java.util.Collections;

@Stateless
public class DataPruebaController implements DataPruebaControllerLocal {

    @EJB
    private ColegioFacadeLocal colegioFacade;
    @EJB
    private UserFacadeLocal userFacade;
    @EJB
    private DatosPersonaFacadeLocal datosPersonaFacade;
    @EJB
    private RolFacadeLocal rolFacade;
    @EJB
    private UserHasRolFacadeLocal uhRolFacade;
    @EJB
    private AdministrativoFacadeLocal administrativoFacade;
    @EJB
    private RepresentanteFacadeLocal representanteFacade;
    @EJB
    private DocenteFacadeLocal docenteFacade;
    @EJB
    private AlumnoFacadeLocal alumnoFacade;
    @EJB
    private AlumnoHasRepresentanteFacadeLocal ahrFacade;
    @EJB
    private EtapaFacadeLocal etapaFacade;
    @EJB
    private CursoFacadeLocal cursoFacade;
    @EJB
    private SeccionFacadeLocal seccionFacade;
    @EJB
    private SeccionHasAlumnoFacadeLocal seccionHasAlumnoFacade;
    @EJB
    private SeccionHasDocenteFacadeLocal seccionHasDocenteFacade;

    @Override
    public void verifyDataInicial() {
        List<User> usuarios = userFacade.findAll();
        if (usuarios.size() < 2) {
            loadDataInicial();
        }
    }

    public void loadDataInicial() {
        creaColegio();
        loadUsers();
        loadDatosPersona();
        defineRoles();
        loadPersonal();
        relacionAlumnoRepresentante();
        createEtapas();
        createCursos();
        createSecciones();
        asociaAlumnoSeccion();
        asociaDocenteSeccion();
    }
    
    private void creaColegio(){
        Colegio colegio = new Colegio();
        colegio.setNombre("U.E. Colegio Belagua");
        colegio.setDireccion("Guatire");
        
        colegioFacade.create(colegio);
        System.out.println("Creado el Colegio...");
    }

    private void loadUsers() {
        List<User> usuarios = new ArrayList<>();
        usuarios.add(createUser(2L, 1234567, "jadmin@test.com",
                Utilities.getSecurePassword("123456")));

        // Usuarios docentes
        usuarios.add(createUser(4567890, "adocente@test.com",
                Utilities.getSecurePassword("123456")));
        usuarios.add(createUser(4567891, "bdocente@test.com",
                Utilities.getSecurePassword("123456")));
        usuarios.add(createUser(4567892, "cdocente@test.com",
                Utilities.getSecurePassword("123456")));
        usuarios.add(createUser(4567893, "ddocente@test.com",
                Utilities.getSecurePassword("123456")));
        usuarios.add(createUser(4567894, "edocente@test.com",
                Utilities.getSecurePassword("123456")));
        usuarios.add(createUser(4567895, "fdocente@test.com",
                Utilities.getSecurePassword("123456")));
        usuarios.add(createUser(4567896, "gdocente@test.com",
                Utilities.getSecurePassword("123456")));

        // Usuarios representantes
        usuarios.add(createUser(5678900, "arepre@test.com",
                Utilities.getSecurePassword("123456")));
        usuarios.add(createUser(5678901, "brepre@test.com",
                Utilities.getSecurePassword("123456")));
        usuarios.add(createUser(5678902, "crepre@test.com",
                Utilities.getSecurePassword("123456")));
        usuarios.add(createUser(5678903, "drepre@test.com",
                Utilities.getSecurePassword("123456")));
        usuarios.add(createUser(5678904, "erepre@test.com",
                Utilities.getSecurePassword("123456")));
        usuarios.add(createUser(5678905, "frepre@test.com",
                Utilities.getSecurePassword("123456")));
        usuarios.add(createUser(5678906, "grepre@test.com",
                Utilities.getSecurePassword("123456")));
        usuarios.add(createUser(5678907, "hrepre@test.com",
                Utilities.getSecurePassword("123456")));
        usuarios.add(createUser(5678908, "irepre@test.com",
                Utilities.getSecurePassword("123456")));
        usuarios.add(createUser(5678909, "jrepre@test.com",
                Utilities.getSecurePassword("123456")));
        usuarios.add(createUser(5678910, "krepre@test.com",
                Utilities.getSecurePassword("123456")));
        usuarios.add(createUser(5678911, "lrepre@test.com",
                Utilities.getSecurePassword("123456")));
        usuarios.add(createUser(5678912, "mrepre@test.com",
                Utilities.getSecurePassword("123456")));
        usuarios.add(createUser(5678913, "nrepre@test.com",
                Utilities.getSecurePassword("123456")));

        userFacade.batchCreate(usuarios);

        usuarios = userFacade.findAll();
        System.out.println("Creados " + usuarios.size() + " usuarios.");
    }

    private void loadDatosPersona() {
        List<DatosPersona> datospers = new ArrayList<>();
        datospers.add(createDatosPersona(1, "Juan", "Admin", 1234567, "jadmin@test.com"));

        // datos de los docentes
        datospers.add(createDatosPersona("Ana", "Docente", 4567890, "adocente@test.com"));
        datospers.add(createDatosPersona("Bernardo", "Docente", 4567891, "bdocente@test.com"));
        datospers.add(createDatosPersona("Cirilo", "Docente", 4567892, "cdocente@test.com"));
        datospers.add(createDatosPersona("Delia", "Docente", 4567893, "ddocente@test.com"));
        datospers.add(createDatosPersona("Eduardo", "Docente", 4567894, "edocente@test.com"));
        datospers.add(createDatosPersona("Francisco", "Docente", 4567895, "fdocente@test.com"));
        datospers.add(createDatosPersona("Gerardo", "Docente", 4567896, "gdocente@test.com"));

        // datos de los reprsentantes
        datospers.add(createDatosPersona("Antonio", "Representante", 5678900, "arepre@test.com"));
        datospers.add(createDatosPersona("Baudilio", "Representante", 5678901, "brepre@test.com"));
        datospers.add(createDatosPersona("Carlos", "Representante", 5678902, "crepre@test.com"));
        datospers.add(createDatosPersona("Diana", "Representante", 5678903, "drepre@test.com"));
        datospers.add(createDatosPersona("Estefanía", "Representante", 5678904, "erepre@test.com"));
        datospers.add(createDatosPersona("Flor", "Representante", 5678905, "frepre@test.com"));
        datospers.add(createDatosPersona("Gustavo", "Representante", 5678906, "grepre@test.com"));
        datospers.add(createDatosPersona("Hilda", "Representante", 5678907, "hrepre@test.com"));
        datospers.add(createDatosPersona("Irene", "Representante", 5678908, "irepre@test.com"));
        datospers.add(createDatosPersona("Julian", "Representante", 5678909, "jrepre@test.com"));
        datospers.add(createDatosPersona("Keila", "Representante", 5678910, "krepre@test.com"));
        datospers.add(createDatosPersona("Laura", "Representante", 5678911, "lrepre@test.com"));
        datospers.add(createDatosPersona("Manuel", "Representante", 5678912, "mrepre@test.com"));
        datospers.add(createDatosPersona("Nelson", "Representante", 5678913, "nrepre@test.com"));

        // datos de alumnos
        datospers.add(createDatosPersona("Andrés", "Alumno", 8901230, "aalumno@test.com"));
        datospers.add(createDatosPersona("Baldomero", "Alumno", 8901231, "balumno@test.com"));
        datospers.add(createDatosPersona("Carolina", "Alumno", 8901232, "calumno@test.com"));
        datospers.add(createDatosPersona("Diego", "Alumno", 8901233, "dalumno@test.com"));
        datospers.add(createDatosPersona("Esteban", "Alumno", 8901234, "ealumno@test.com"));
        datospers.add(createDatosPersona("Faustino", "Alumno", 8901235, "falumno@test.com"));
        datospers.add(createDatosPersona("Guillermo", "Alumno", 8901236, "galumno@test.com"));
        datospers.add(createDatosPersona("Héctor", "Alumno", 8901237, "halumno@test.com"));
        datospers.add(createDatosPersona("Ignacio", "Alumno", 8901238, "ialumno@test.com"));
        datospers.add(createDatosPersona("Justo", "Alumno", 8901239, "jalumno@test.com"));
        datospers.add(createDatosPersona("Kevin", "Alumno", 8901240, "kalumno@test.com"));
        datospers.add(createDatosPersona("Lucas", "Alumno", 8901241, "lalumno@test.com"));
        datospers.add(createDatosPersona("Maria", "Alumno", 8901242, "malumno@test.com"));
        datospers.add(createDatosPersona("Nidia", "Alumno", 8901243, "nalumno@test.com"));
        datospers.add(createDatosPersona("Orianna", "Alumno", 8901244, "oalumno@test.com"));
        datospers.add(createDatosPersona("Paula", "Alumno", 8901245, "palumno@test.com"));

        datosPersonaFacade.batchCreate(datospers);

        List<DatosPersona> dpList = datosPersonaFacade.findAll();
        System.out.println("Creados " + dpList.size() + " datos personales");
    }

    private void defineRoles() {
        
        List<UserHasRol> uhrList = new ArrayList<>();

        // usuario administrativo
        User user = userFacade.findByCi(1234567);
        UserHasRol uhr = asignarRol(user, "Administrativo");
        uhrList.add(uhr);

        // asigna los roles de los docentes
        for (int cedula = 4567890; cedula < 4567897; cedula++) {
            User doc = userFacade.findByCi(cedula);
            uhr = asignarRol(doc, "Docente");
            uhrList.add(uhr);
        }

        // asigna los roles a los representantes
        for (int cedula = 5678900; cedula < 5678914; cedula++) {
            User doc = userFacade.findByCi(cedula);
            uhr = asignarRol(doc, "Representante");
            uhrList.add(uhr);
        }

        System.out.println("Antes de crearlos tiene : " + uhrList.size() + " elementos");
        
        uhRolFacade.batchCreate(uhrList);

        uhrList = uhRolFacade.findAll();
        System.out.println("Asignados " + uhrList.size() + " roles iniciales");
        
    }

    private UserHasRol asignarRol(User user, String rolName) {
        Rol rol = rolFacade.find(rolName);
        String escritorio = "";
        switch (rolName) {
            case "Administrativo":
                escritorio = Constantes.ESCRITORIO_ADMIN;
                break;
            case "Docente":
                escritorio = Constantes.ESCRITORIO_DOCENTE;
                break;
            case "Representante":
                escritorio = Constantes.ESCRITORIO_REPRESENTANTE;
                break;
        }
        UserHasRol uhr = new UserHasRol();
        uhr.setUserId(user);
        uhr.setRolId(rol);
        uhr.setEscritorio(escritorio);

        return uhr;
    }

    private void loadPersonal() {
        User user = userFacade.findByCi(1234567);
        createPersonal(user, "Admin");

        // se agregan los docentes
        for (int cedula = 4567890; cedula < 4567897; cedula++) {
            user = userFacade.findByCi(cedula);
            createPersonal(user, "Docente");
        }

        // se agregan los representantes
        for (int cedula = 5678900; cedula < 5678914; cedula++) {
            user = userFacade.findByCi(cedula);
            createPersonal(user, "Representante");
        }

        // se agregan los alumnos
        for (int cedula = 8901230; cedula < 8901246; cedula++) {
            DatosPersona dp = datosPersonaFacade.findByCi(cedula);
            createAlumno(dp);

            //Alumno alumno = alumnoFacade.findxDatosPersona(dp);
        }
    }

    private User createUser(Long id, Integer ci, String usuario, String psw) {
        User user = new User(id);
        user.setCi(ci);
        user.setUsr(usuario);
        user.setPsw(psw);
        user.setStatus(Constantes.USUARIO_ACTIVO);

        return user;
    }

    private User createUser(Integer ci, String usuario, String psw) {
        User user = new User();
        user.setCi(ci);
        user.setUsr(usuario);
        user.setPsw(psw);
        user.setStatus(Constantes.USUARIO_ACTIVO);

        return user;
    }

    private DatosPersona createDatosPersona(Integer id, String nombre,
            String apellido, Integer ci, String email) {
        DatosPersona dp = new DatosPersona(id);
        dp.setNombre(nombre);
        dp.setApellido(apellido);
        dp.setCi(ci);
        dp.setEmail(email);

        return dp;
    }

    private DatosPersona createDatosPersona(String nombre,
            String apellido, Integer ci, String email) {
        DatosPersona dp = new DatosPersona();
        dp.setNombre(nombre);
        dp.setApellido(apellido);
        dp.setCi(ci);
        dp.setEmail(email);

        return dp;
    }

    private UserHasRol createUHR(Integer id, User user, Rol rol, String escritorio) {
        UserHasRol uhr = new UserHasRol(id);
        uhr.setUserId(user);
        uhr.setRolId(rol);
        uhr.setEscritorio(escritorio);
        return uhr;
    }

    private void createPersonal(User user, String tipo) {
        DatosPersona dp = datosPersonaFacade.findByCi(user.getCi());
        switch (tipo) {
            case "Admin":
                Administrativo admin = new Administrativo();
                admin.setDatosPersonaId(dp);
                admin.setUserId(user);
                administrativoFacade.create(admin);
                break;
            case "Docente":
                Docente docente = new Docente();
                docente.setUserId(user);
                docente.setDatosPersonaId(dp);
                docenteFacade.create(docente);
                break;
            case "Representante":
                Representante repre = new Representante();
                repre.setUserId(user);
                repre.setDatosPersonaId(dp);
                representanteFacade.create(repre);
                break;
        }
    }

    private void createAlumno(DatosPersona dp) {
        Alumno alumno = new Alumno();
        alumno.setDatosPersonaId(dp);

        Random random = new Random();
        int low = 1000;
        int high = 2000;
        int matricula = random.nextInt((high - low) + 1) + low;
        alumno.setIdColegio(String.valueOf(matricula));
        alumno.setFechaIngreso(new Date());

        alumnoFacade.create(alumno);
    }

    private void relacionAlumnoRepresentante() {
        List<AlumnoHasRepresentante> ahrList = new ArrayList<>();
        AlumnoHasRepresentante ahr = null;
        
        List<Alumno> alumnos = alumnoFacade.findAll();
        List<Representante> representantes = representanteFacade.findAll();
        Collections.shuffle(representantes);
        Collections.shuffle(alumnos);
        
        int cantRepresentante = representantes.size();
        
        int ind = 0;
        int j;
        for(Representante repre : representantes){
            if(ind < (cantRepresentante - 2)){
                ahr = new AlumnoHasRepresentante();
                ahr.setAlumnoId(alumnos.get(ind));
                ahr.setRepresentanteId(representantes.get(ind));
                ahrList.add(ahr);
            } else {
                if(ind == (cantRepresentante - 2)){
                    j = ind;
                } else {
                    j = ind + 1;
                }
                ahr = new AlumnoHasRepresentante();
                ahr.setAlumnoId(alumnos.get(j));
                ahr.setRepresentanteId(representantes.get(ind));
                ahrList.add(ahr);

                ahr = new AlumnoHasRepresentante();
                ahr.setAlumnoId(alumnos.get(j + 1));
                ahr.setRepresentanteId(representantes.get(ind));
                ahrList.add(ahr);
            }
            ind++;
        }
        
        ahrFacade.batchCreate(ahrList);
        
        ahrList = ahrFacade.findAll();
        System.out.println("Se relacionan " + ahrList.size() + " alumnos a sus representantes");
    }

    private void createEtapas() {
        
        Colegio colegio = colegioFacade.findAll().get(0);
        
        List<Etapa> etapas = new ArrayList<>();
        Etapa etapa = new Etapa();

        etapa.setPrefijo(0);
        etapa.setNombre("Prescolar");
        etapa.setColegioId(colegio);
        etapas.add(etapa);

        etapa = new Etapa();
        etapa.setPrefijo(1);
        etapa.setNombre("Primaria 1");
        etapa.setColegioId(colegio);
        etapas.add(etapa);

        etapa = new Etapa();
        etapa.setPrefijo(2);
        etapa.setNombre("Primaria 2");
        etapa.setColegioId(colegio);
        etapas.add(etapa);

        etapa = new Etapa();
        etapa.setPrefijo(3);
        etapa.setColegioId(colegio);
        etapa.setNombre("Bachillerato 1");
        etapas.add(etapa);

        etapa = new Etapa();
        etapa.setPrefijo(4);
        etapa.setColegioId(colegio);
        etapa.setNombre("Bachillerato 4");
        etapas.add(etapa);

        etapaFacade.batchCreate(etapas);

        etapas = etapaFacade.findAll();
        System.out.println("Se crearon " + etapas.size() + " etapas");
    }
    
    private void createCursos(){
        List<Curso> cursos = new ArrayList<>();
        Curso curso = new Curso();
        
        Etapa etapa = etapaFacade.findByPrefijo(0);
        curso.setEtapaId(etapa);
        curso.setNombre("Maternal");
        cursos.add(curso);
        
        curso = new Curso();
        curso.setEtapaId(etapa);
        curso.setNombre("Prescolar 1");
        cursos.add(curso);
        
        curso = new Curso();
        curso.setEtapaId(etapa);
        curso.setNombre("Prescolar 2");
        cursos.add(curso);
        
        curso = new Curso();
        curso.setEtapaId(etapa);
        curso.setNombre("Prescolar 3");
        cursos.add(curso);
        
        etapa = etapaFacade.findByPrefijo(1);
        curso = new Curso();
        curso.setEtapaId(etapa);
        curso.setNombre("1er grado");
        cursos.add(curso);
        
        curso = new Curso();
        curso.setEtapaId(etapa);
        curso.setNombre("2do grado");
        cursos.add(curso);
        
        curso = new Curso();
        curso.setEtapaId(etapa);
        curso.setNombre("3er grado");
        cursos.add(curso);
        
        etapa = etapaFacade.findByPrefijo(2);
        curso = new Curso();
        curso.setEtapaId(etapa);
        curso.setNombre("4to grado");
        cursos.add(curso);
        
        curso = new Curso();
        curso.setEtapaId(etapa);
        curso.setNombre("5to grado");
        cursos.add(curso);
        
        curso = new Curso();
        curso.setEtapaId(etapa);
        curso.setNombre("6to grado");
        cursos.add(curso);
        
        etapa = etapaFacade.findByPrefijo(3);
        curso = new Curso();
        curso.setEtapaId(etapa);
        curso.setNombre("7mo grado");
        cursos.add(curso);
        
        curso = new Curso();
        curso.setEtapaId(etapa);
        curso.setNombre("8vo grado");
        cursos.add(curso);
        
        curso = new Curso();
        curso.setEtapaId(etapa);
        curso.setNombre("9no grado");
        cursos.add(curso);
        
        etapa = etapaFacade.findByPrefijo(4);
        curso = new Curso();
        curso.setEtapaId(etapa);
        curso.setNombre("4to año");
        cursos.add(curso);
        
        curso = new Curso();
        curso.setEtapaId(etapa);
        curso.setNombre("5to año");
        cursos.add(curso);
        
        cursoFacade.batchCreate(cursos);
        
        cursos = cursoFacade.findAll();
        System.out.println("Se crearon " + cursos.size() + " cursos");
    }

    private void createSecciones() {
        List<Seccion> secciones = new ArrayList<>();
        Seccion seccion = new Seccion();

        Curso curso = cursoFacade.findByName("Maternal");
        seccion.setCodigo("0MA");
        seccion.setCursoId(curso);
        secciones.add(seccion);

        seccion = new Seccion();
        curso = cursoFacade.findByName("Prescolar 1");
        seccion.setCodigo("01A");
        seccion.setCursoId(curso);
        seccion.setSeccion("A");
        secciones.add(seccion);

        seccion = new Seccion();
        seccion.setCodigo("01B");
        seccion.setCursoId(curso);
        seccion.setSeccion("B");
        secciones.add(seccion);

        curso = cursoFacade.findByName("Prescolar 2");
        seccion = new Seccion();
        seccion.setCodigo("02A");
        seccion.setCursoId(curso);
        seccion.setSeccion("A");
        secciones.add(seccion);

        seccion = new Seccion();
        seccion.setCodigo("02B");
        seccion.setCursoId(curso);
        seccion.setSeccion("B");
        secciones.add(seccion);

        curso = cursoFacade.findByName("Prescolar 3");
        seccion = new Seccion();
        seccion.setCodigo("03A");
        seccion.setCursoId(curso);
        seccion.setSeccion("A");
        secciones.add(seccion);

        curso = cursoFacade.findByName("1er grado");
        seccion = new Seccion();
        seccion.setCodigo("11A");
        seccion.setCursoId(curso);
        seccion.setSeccion("A");
        secciones.add(seccion);

        seccion = new Seccion();
        seccion.setCodigo("11B");
        seccion.setCursoId(curso);
        seccion.setSeccion("B");
        secciones.add(seccion);

        curso = cursoFacade.findByName("2do grado");
        seccion = new Seccion();
        seccion.setCodigo("12A");
        seccion.setCursoId(curso);
        seccion.setSeccion("A");
        secciones.add(seccion);

        seccion = new Seccion();
        seccion.setCodigo("12B");
        seccion.setCursoId(curso);
        seccion.setSeccion("B");
        secciones.add(seccion);

        curso = cursoFacade.findByName("3er grado");
        seccion = new Seccion();
        seccion.setCodigo("13A");
        seccion.setCursoId(curso);
        seccion.setSeccion("A");
        secciones.add(seccion);

        seccion = new Seccion();
        seccion.setCodigo("13B");
        seccion.setCursoId(curso);
        seccion.setSeccion("B");
        secciones.add(seccion);

        curso = cursoFacade.findByName("4to grado");
        seccion = new Seccion();
        seccion.setCodigo("24A");
        seccion.setCursoId(curso);
        seccion.setSeccion("A");
        secciones.add(seccion);

        seccion = new Seccion();
        seccion.setCodigo("24B");
        seccion.setCursoId(curso);
        seccion.setSeccion("B");
        secciones.add(seccion);

        curso = cursoFacade.findByName("5to grado");
        seccion = new Seccion();
        seccion.setCodigo("25A");
        seccion.setCursoId(curso);
        seccion.setSeccion("A");
        secciones.add(seccion);

        seccion = new Seccion();
        seccion.setCodigo("25B");
        seccion.setCursoId(curso);
        seccion.setSeccion("B");
        secciones.add(seccion);

        curso = cursoFacade.findByName("6to grado");
        seccion = new Seccion();
        seccion.setCodigo("26A");
        seccion.setCursoId(curso);
        seccion.setSeccion("A");
        secciones.add(seccion);

        seccion = new Seccion();
        seccion.setCodigo("26B");
        seccion.setCursoId(curso);
        seccion.setSeccion("B");
        secciones.add(seccion);

        curso = cursoFacade.findByName("7mo grado");
        seccion = new Seccion();
        seccion.setCodigo("37A");
        seccion.setCursoId(curso);
        seccion.setSeccion("A");
        secciones.add(seccion);

        seccion = new Seccion();
        seccion.setCodigo("37B");
        seccion.setCursoId(curso);
        seccion.setSeccion("B");
        secciones.add(seccion);

        curso = cursoFacade.findByName("8vo grado");
        seccion = new Seccion();
        seccion.setCodigo("38A");
        seccion.setCursoId(curso);
        seccion.setSeccion("A");
        secciones.add(seccion);

        seccion = new Seccion();
        seccion.setCodigo("38B");
        seccion.setCursoId(curso);
        seccion.setSeccion("B");
        secciones.add(seccion);

        curso = cursoFacade.findByName("9no grado");
        seccion = new Seccion();
        seccion.setCodigo("39A");
        seccion.setCursoId(curso);
        seccion.setSeccion("A");
        secciones.add(seccion);

        seccion = new Seccion();
        seccion.setCodigo("39B");
        seccion.setCursoId(curso);
        seccion.setSeccion("B");
        secciones.add(seccion);

        curso = cursoFacade.findByName("4to año");
        seccion = new Seccion();
        seccion.setCodigo("41A");
        seccion.setCursoId(curso);
        seccion.setSeccion("A");
        secciones.add(seccion);

        seccion = new Seccion();
        seccion.setCodigo("41B");
        seccion.setCursoId(curso);
        seccion.setSeccion("B");
        secciones.add(seccion);

        curso = cursoFacade.findByName("5to año");
        seccion = new Seccion();
        seccion.setCodigo("42A");
        seccion.setCursoId(curso);
        seccion.setSeccion("A");
        secciones.add(seccion);

        seccion = new Seccion();
        seccion.setCodigo("42B");
        seccion.setCursoId(curso);
        seccion.setSeccion("B");
        secciones.add(seccion);

        seccionFacade.batchCreate(secciones);
        
        secciones = seccionFacade.findAll();
        System.out.println("Se crearon " + secciones.size() + " secciones");
    }

    private void asociaAlumnoSeccion() {
        List<Alumno> alumnos = alumnoFacade.findAll();
        List<SeccionHasAlumno> chaList = new ArrayList<>();

        SeccionHasAlumno cha = null;
        Seccion seccion = seccionFacade.findByCodigo("01A");
        for (int i = 0; i < 2; i++) {
            cha = new SeccionHasAlumno();
            cha.setAlumnoId(alumnos.get(i));
            cha.setSeccionId(seccion);
            chaList.add(cha);
        }

        seccion = seccionFacade.findByCodigo("11A");
        for (int i = 2; i < 4; i++) {
            cha = new SeccionHasAlumno();
            cha.setAlumnoId(alumnos.get(i));
            cha.setSeccionId(seccion);
            chaList.add(cha);
        }

        seccion = seccionFacade.findByCodigo("24A");
        for (int i = 4; i < 8; i++) {
            cha = new SeccionHasAlumno();
            cha.setAlumnoId(alumnos.get(i));
            cha.setSeccionId(seccion);
            chaList.add(cha);
        }
        
        seccion = seccionFacade.findByCodigo("37A");
        for (int i = 8; i < 12; i++) {
            cha = new SeccionHasAlumno();
            cha.setAlumnoId(alumnos.get(i));
            cha.setSeccionId(seccion);
            chaList.add(cha);
        }
        
        seccion = seccionFacade.findByCodigo("41A");
        for (int i = 12; i < 16; i++) {
            cha = new SeccionHasAlumno();
            cha.setAlumnoId(alumnos.get(i));
            cha.setSeccionId(seccion);
            chaList.add(cha);
        }
        
        seccionHasAlumnoFacade.batchCreate(chaList);
        
        System.out.println("Alumnos asignados a los seccions");
    }

    private void asociaDocenteSeccion() {
        List<Docente> docentes = docenteFacade.findAll();
        List<SeccionHasDocente> chdList = new ArrayList<>();
        
        Seccion seccion = seccionFacade.findByCodigo("01A");
        SeccionHasDocente chd = new SeccionHasDocente();
        chd.setSeccionId(seccion);
        chd.setDocenteId(docentes.get(0));
        chdList.add(chd);
        
        seccion = seccionFacade.findByCodigo("11A");
        chd = new SeccionHasDocente();
        chd.setSeccionId(seccion);
        chd.setDocenteId(docentes.get(1));
        chdList.add(chd);
        
        seccion = seccionFacade.findByCodigo("24A");
        chd = new SeccionHasDocente();
        chd.setSeccionId(seccion);
        chd.setDocenteId(docentes.get(2));
        chdList.add(chd);
        
        seccion = seccionFacade.findByCodigo("37A");
        chd = new SeccionHasDocente();
        chd.setSeccionId(seccion);
        chd.setDocenteId(docentes.get(3));
        chdList.add(chd);
        
        seccion = seccionFacade.findByCodigo("41A");
        chd = new SeccionHasDocente();
        chd.setSeccionId(seccion);
        chd.setDocenteId(docentes.get(4));
        chdList.add(chd);
        
        seccionHasDocenteFacade.batchCreate(chdList);
        
        chdList = seccionHasDocenteFacade.findAll();
        System.out.println("Se asignaron " + chdList.size() + " docentes a seccions");
    }
}
