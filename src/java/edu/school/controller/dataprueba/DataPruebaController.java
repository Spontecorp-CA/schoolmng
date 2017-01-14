package edu.school.controller.dataprueba;

import edu.school.ejb.AdministrativoFacadeLocal;
import edu.school.ejb.AlumnoFacadeLocal;
import edu.school.ejb.AlumnoHasRepresentanteFacadeLocal;
import edu.school.ejb.CursoFacadeLocal;
import edu.school.ejb.CursoHasAlumnoFacadeLocal;
import edu.school.ejb.CursoHasDocenteFacadeLocal;
import edu.school.ejb.DatosPersonaFacadeLocal;
import edu.school.ejb.DocenteFacadeLocal;
import edu.school.ejb.NivelFacadeLocal;
import edu.school.ejb.RepresentanteFacadeLocal;
import edu.school.ejb.RolFacadeLocal;
import edu.school.ejb.UserFacadeLocal;
import edu.school.ejb.UserHasRolFacadeLocal;
import edu.school.entities.Administrativo;
import edu.school.entities.Alumno;
import edu.school.entities.AlumnoHasRepresentante;
import edu.school.entities.Curso;
import edu.school.entities.CursoHasAlumno;
import edu.school.entities.CursoHasDocente;
import edu.school.entities.DatosPersona;
import edu.school.entities.Docente;
import edu.school.entities.Nivel;
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

@Stateless
public class DataPruebaController implements DataPruebaControllerLocal {

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
    private NivelFacadeLocal nivelFacade;
    @EJB
    private CursoFacadeLocal cursoFacade;
    @EJB
    private CursoHasAlumnoFacadeLocal cursoHasAlumnoFacade;
    @EJB
    private CursoHasDocenteFacadeLocal cursoHasDocenteFacade;

    @Override
    public void verifyDataInicial() {
        List<User> usuarios = userFacade.findAll();
        if (usuarios.size() < 2) {
            loadDataInicial();
        }
    }

    public void loadDataInicial() {
        loadUsers();
        loadDatosPersona();
        asignaRoles();
        loadPersonal();
        relacionAlumnoRepresentante();
        createNiveles();
        createCursos();
        asociaAlumnoCurso();
        asociaDocenteCurso();
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

        for (User user : usuarios) {
            userFacade.create(user);
        }

        System.out.println("Creados los usuarios");
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

        System.out.println("Creados los datos personales");
    }

    private void asignaRoles() {
        List<UserHasRol> uhrList = new ArrayList<>();

        // usuario administrativo
        User user = userFacade.find(1234567);
        Rol rol = rolFacade.find(2);
        String escritorio = Constantes.ESCRITORIO_ADMIN;
        uhrList.add(createUHR(2, user, rol, escritorio));

        // asigna los roles de los docentes
        for (int cedula = 4567890; cedula < 4567897; cedula++) {
            User doc = userFacade.find(cedula);
            UserHasRol uhr = asignarRol(doc, "Docente");
            uhrList.add(uhr);
        }

        // asigna los roles a los representantes
        for (int cedula = 5678900; cedula < 5678914; cedula++) {
            User doc = userFacade.find(cedula);
            UserHasRol uhr = asignarRol(doc, "Representante");
            uhrList.add(uhr);
        }

        uhRolFacade.batchCreate(uhrList);

        System.out.println("Asignados los roles iniciales");
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
        User user = userFacade.find(1234567);
        createPersonal(user, "Admin");

        // se agregan los docentes
        for (int cedula = 4567890; cedula < 4567897; cedula++) {
            user = userFacade.find(cedula);
            createPersonal(user, "Docente");
        }

        // se agregan los representantes
        for (int cedula = 5678900; cedula < 5678914; cedula++) {
            user = userFacade.find(cedula);
            createPersonal(user, "Representante");
        }

        // se agregan los alumnos
        for (int cedula = 8901230; cedula < 8901246; cedula++) {
            DatosPersona dp = datosPersonaFacade.find(cedula);
            createAlumno(dp);

            Alumno alumno = alumnoFacade.findxDatosPersona(dp);
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
        DatosPersona dp = datosPersonaFacade.find(user.getCi().intValue());
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

        Alumno alumno = null;
        Representante representante = null;
        AlumnoHasRepresentante ahr = null;
        for (int ind = 1; ind < 15; ind++) {
            if (ind < 13) {
                alumno = alumnoFacade.find(ind);
                representante = representanteFacade.find(ind);

                ahr = new AlumnoHasRepresentante();
                ahr.setAlumnoId(alumno);
                ahr.setRepresentanteId(representante);
                ahrList.add(ahr);
            } else {
                int j = 0;
                if (ind == 13) {
                    j = ind;
                } else {
                    j = ind + 1;
                }

                alumno = alumnoFacade.find(j);
                representante = representanteFacade.find(ind);
                ahr = new AlumnoHasRepresentante();
                ahr.setAlumnoId(alumno);
                ahr.setRepresentanteId(representante);
                ahrList.add(ahr);

                alumno = alumnoFacade.find((j + 1));
                representante = representanteFacade.find(ind);
                ahr = new AlumnoHasRepresentante();
                ahr.setAlumnoId(alumno);
                ahr.setRepresentanteId(representante);
                ahrList.add(ahr);
            }
        }

        ahrFacade.batchCreate(ahrList);
        System.out.println("Se relacionan los alumnos a sus representantes");
    }

    private void createNiveles() {
        List<Nivel> niveles = new ArrayList<>();
        Nivel nivel = new Nivel();

        nivel.setPrefijo(0);
        nivel.setNombre("Prescolar");
        nivel.setEtapa("Prescolar");
        niveles.add(nivel);

        nivel = new Nivel();
        nivel.setPrefijo(1);
        nivel.setNombre("Primaria_1");
        nivel.setEtapa("Primaria_1");
        niveles.add(nivel);

        nivel = new Nivel();
        nivel.setPrefijo(2);
        nivel.setNombre("Primaria_2");
        nivel.setEtapa("Primaria_2");
        niveles.add(nivel);

        nivel = new Nivel();
        nivel.setPrefijo(3);
        nivel.setNombre("Bachillerato_1");
        nivel.setEtapa("Bachillerato_1");
        niveles.add(nivel);

        nivel = new Nivel();
        nivel.setPrefijo(4);
        nivel.setNombre("Bachillerato_4");
        nivel.setEtapa("Bachillerato_4");
        niveles.add(nivel);

        nivelFacade.batchCreate(niveles);

        System.out.println("Creado los niveles");
    }

    private void createCursos() {
        List<Curso> cursos = new ArrayList<>();
        Curso curso = new Curso();

        Nivel nivel = nivelFacade.findByPrefijo(0);
        curso.setCodigo("0MA");
        curso.setNivelId(nivel);
        curso.setNombre("Maternal");
        cursos.add(curso);

        curso = new Curso();
        curso.setCodigo("01A");
        curso.setNivelId(nivel);
        curso.setNombre("Prescolar 1A");
        curso.setSeccion("A");
        cursos.add(curso);

        curso = new Curso();
        curso.setCodigo("01B");
        curso.setNivelId(nivel);
        curso.setNombre("Prescolar 1B");
        curso.setSeccion("B");
        cursos.add(curso);

        curso = new Curso();
        curso.setCodigo("02A");
        curso.setNivelId(nivel);
        curso.setNombre("Prescolar 2A");
        curso.setSeccion("A");
        cursos.add(curso);

        curso = new Curso();
        curso.setCodigo("02B");
        curso.setNivelId(nivel);
        curso.setNombre("Prescolar 2B");
        curso.setSeccion("B");
        cursos.add(curso);

        curso = new Curso();
        curso.setCodigo("03A");
        curso.setNivelId(nivel);
        curso.setNombre("Prescolar 3A");
        curso.setSeccion("A");
        cursos.add(curso);

        nivel = nivelFacade.findByPrefijo(1);
        curso = new Curso();
        curso.setCodigo("11A");
        curso.setNivelId(nivel);
        curso.setNombre("1er grado A");
        curso.setSeccion("A");
        cursos.add(curso);

        curso = new Curso();
        curso.setCodigo("11B");
        curso.setNivelId(nivel);
        curso.setNombre("1er grado B");
        curso.setSeccion("B");
        cursos.add(curso);

        curso = new Curso();
        curso.setCodigo("12A");
        curso.setNivelId(nivel);
        curso.setNombre("2do grado A");
        curso.setSeccion("A");
        cursos.add(curso);

        curso = new Curso();
        curso.setCodigo("12B");
        curso.setNivelId(nivel);
        curso.setNombre("2do grado B");
        curso.setSeccion("B");
        cursos.add(curso);

        curso = new Curso();
        curso.setCodigo("13A");
        curso.setNivelId(nivel);
        curso.setNombre("3er grado A");
        curso.setSeccion("A");
        cursos.add(curso);

        curso = new Curso();
        curso.setCodigo("13B");
        curso.setNivelId(nivel);
        curso.setNombre("3er grado B");
        curso.setSeccion("B");
        cursos.add(curso);

        nivel = nivelFacade.findByPrefijo(2);
        curso = new Curso();
        curso.setCodigo("24A");
        curso.setNivelId(nivel);
        curso.setNombre("4to grado A");
        curso.setSeccion("A");
        cursos.add(curso);

        curso = new Curso();
        curso.setCodigo("24B");
        curso.setNivelId(nivel);
        curso.setNombre("4to grado B");
        curso.setSeccion("B");
        cursos.add(curso);

        curso = new Curso();
        curso.setCodigo("25A");
        curso.setNivelId(nivel);
        curso.setNombre("5to grado A");
        curso.setSeccion("A");
        cursos.add(curso);

        curso = new Curso();
        curso.setCodigo("25B");
        curso.setNivelId(nivel);
        curso.setNombre("5to grado B");
        curso.setSeccion("B");
        cursos.add(curso);

        curso = new Curso();
        curso.setCodigo("26A");
        curso.setNivelId(nivel);
        curso.setNombre("6to grado A");
        curso.setSeccion("A");
        cursos.add(curso);

        curso = new Curso();
        curso.setCodigo("26B");
        curso.setNivelId(nivel);
        curso.setNombre("6to grado B");
        curso.setSeccion("B");
        cursos.add(curso);

        nivel = nivelFacade.findByPrefijo(3);
        curso = new Curso();
        curso.setCodigo("37A");
        curso.setNivelId(nivel);
        curso.setNombre("1er año A");
        curso.setSeccion("A");
        cursos.add(curso);

        curso = new Curso();
        curso.setCodigo("37B");
        curso.setNivelId(nivel);
        curso.setNombre("1er año B");
        curso.setSeccion("B");
        cursos.add(curso);

        curso = new Curso();
        curso.setCodigo("38A");
        curso.setNivelId(nivel);
        curso.setNombre("2do año A");
        curso.setSeccion("A");
        cursos.add(curso);

        curso = new Curso();
        curso.setCodigo("38B");
        curso.setNivelId(nivel);
        curso.setNombre("2do año B");
        curso.setSeccion("B");
        cursos.add(curso);

        curso = new Curso();
        curso.setCodigo("39A");
        curso.setNivelId(nivel);
        curso.setNombre("3er año A");
        curso.setSeccion("A");
        cursos.add(curso);

        curso = new Curso();
        curso.setCodigo("39B");
        curso.setNivelId(nivel);
        curso.setNombre("3er año B");
        curso.setSeccion("B");
        cursos.add(curso);

        nivel = nivelFacade.findByPrefijo(4);
        curso = new Curso();
        curso.setCodigo("41A");
        curso.setNivelId(nivel);
        curso.setNombre("4to año A");
        curso.setSeccion("A");
        cursos.add(curso);

        curso = new Curso();
        curso.setCodigo("41B");
        curso.setNivelId(nivel);
        curso.setNombre("4to año B");
        curso.setSeccion("B");
        cursos.add(curso);

        curso = new Curso();
        curso.setCodigo("42A");
        curso.setNivelId(nivel);
        curso.setNombre("5to año A");
        curso.setSeccion("A");
        cursos.add(curso);

        curso = new Curso();
        curso.setCodigo("42B");
        curso.setNivelId(nivel);
        curso.setNombre("4to año B");
        curso.setSeccion("B");
        cursos.add(curso);

        cursoFacade.batchCreate(cursos);
        System.out.println("cursos creados");
    }

    private void asociaAlumnoCurso() {
        List<Alumno> alumnos = alumnoFacade.findAll();
        List<CursoHasAlumno> chaList = new ArrayList<>();

        CursoHasAlumno cha = null;
        Curso curso = cursoFacade.findByCodigo("01A");
        for (int i = 0; i < 2; i++) {
            cha = new CursoHasAlumno();
            cha.setAlumnoId(alumnos.get(i));
            cha.setCursoId(curso);
            chaList.add(cha);
        }

        curso = cursoFacade.findByCodigo("11A");
        for (int i = 2; i < 4; i++) {
            cha = new CursoHasAlumno();
            cha.setAlumnoId(alumnos.get(i));
            cha.setCursoId(curso);
            chaList.add(cha);
        }

        curso = cursoFacade.findByCodigo("24A");
        for (int i = 4; i < 8; i++) {
            cha = new CursoHasAlumno();
            cha.setAlumnoId(alumnos.get(i));
            cha.setCursoId(curso);
            chaList.add(cha);
        }
        
        curso = cursoFacade.findByCodigo("37A");
        for (int i = 8; i < 12; i++) {
            cha = new CursoHasAlumno();
            cha.setAlumnoId(alumnos.get(i));
            cha.setCursoId(curso);
            chaList.add(cha);
        }
        
        curso = cursoFacade.findByCodigo("41A");
        for (int i = 12; i < 16; i++) {
            cha = new CursoHasAlumno();
            cha.setAlumnoId(alumnos.get(i));
            cha.setCursoId(curso);
            chaList.add(cha);
        }
        
        cursoHasAlumnoFacade.batchCreate(chaList);
        
        System.out.println("Alumnos asignados a los cursos");
    }

    private void asociaDocenteCurso() {
        List<Docente> docentes = docenteFacade.findAll();
        List<CursoHasDocente> chdList = new ArrayList<>();
        
        Curso curso = cursoFacade.findByCodigo("01A");
        CursoHasDocente chd = new CursoHasDocente();
        chd.setCursoId(curso);
        chd.setDocenteId(docentes.get(0));
        chdList.add(chd);
        
        curso = cursoFacade.findByCodigo("11A");
        chd = new CursoHasDocente();
        chd.setCursoId(curso);
        chd.setDocenteId(docentes.get(1));
        chdList.add(chd);
        
        curso = cursoFacade.findByCodigo("24A");
        chd = new CursoHasDocente();
        chd.setCursoId(curso);
        chd.setDocenteId(docentes.get(2));
        chdList.add(chd);
        
        curso = cursoFacade.findByCodigo("37A");
        chd = new CursoHasDocente();
        chd.setCursoId(curso);
        chd.setDocenteId(docentes.get(3));
        chdList.add(chd);
        
        curso = cursoFacade.findByCodigo("41A");
        chd = new CursoHasDocente();
        chd.setCursoId(curso);
        chd.setDocenteId(docentes.get(4));
        chdList.add(chd);
        
        cursoHasDocenteFacade.batchCreate(chdList);
        System.out.println("Asignados docentes a cursos");
    }
}
