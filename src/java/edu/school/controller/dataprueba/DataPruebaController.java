package edu.school.controller.dataprueba;

import edu.school.ejb.AdministrativoFacadeLocal;
import edu.school.ejb.DatosPersonaFacadeLocal;
import edu.school.ejb.DocenteFacadeLocal;
import edu.school.ejb.RepresentanteFacadeLocal;
import edu.school.ejb.RolFacadeLocal;
import edu.school.ejb.UserFacadeLocal;
import edu.school.ejb.UserHasRolFacadeLocal;
import edu.school.entities.Administrativo;
import edu.school.entities.DatosPersona;
import edu.school.entities.Docente;
import edu.school.entities.Representante;
import edu.school.entities.Rol;
import edu.school.entities.User;
import edu.school.entities.UserHasRol;
import edu.school.utilities.Constantes;
import edu.school.utilities.Utilities;
import java.util.ArrayList;
import java.util.List;
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
    
    @Override
    public void verifyDataInicial(){
        List<User> usuarios = userFacade.findAll();
        if(usuarios.size() < 2){
            loadDataInicial();
        }
    }

    public void loadDataInicial() {
        loadUsers();
        loadDatosPersona();
        asignaRoles();
        loadPersonal();
    }

    private void loadUsers() {
        List<User> usuarios = new ArrayList<>();
        usuarios.add(createUser(2L, 1234567, "jadmin@test.com", 
                Utilities.getSecurePassword("123456")));
        usuarios.add(createUser(3L, 2345678, "jdocente@test.com", 
                Utilities.getSecurePassword("123456")));
        usuarios.add(createUser(4L, 3456789, "jrepre@test.com", 
                Utilities.getSecurePassword("123456")));

        for (User user : usuarios) {
            userFacade.create(user);
        }
        
        System.out.println("Creados los usuarios");
    }
    
    private void loadDatosPersona(){
        List<DatosPersona> datospers = new ArrayList<>();
        datospers.add(createDatosPersona(1, "Juan", "Admin", 1234567, "jadmin@test.com"));
        datospers.add(createDatosPersona(2, "Jose", "Docente", 2345678, "jdocente@test.com"));
        datospers.add(createDatosPersona(3, "Julian", "Representante", 3456789, "jrepre@test.com"));
        
        for(DatosPersona dp : datospers){
            datosPersonaFacade.create(dp);
        }
        
        System.out.println("Creados los datos personales");
    }
    
    private void asignaRoles(){
        List<UserHasRol> uhrList = new ArrayList<>();
        
        // usuario administrativo
        User user = userFacade.find(1234567);
        Rol rol = rolFacade.find(2);
        String escritorio = Constantes.ESCRITORIO_ADMIN;
        uhrList.add(createUHR(2, user, rol, escritorio));
        
        // usuario docente
        user = userFacade.find(2345678);
        rol = rolFacade.find(3);
        escritorio = Constantes.ESCRITORIO_DOCENTE;
        uhrList.add(createUHR(3, user, rol, escritorio));
        
        // usuario representante
        user = userFacade.find(3456789);
        rol = rolFacade.find(4);
        escritorio = Constantes.ESCRITORIO_REPRESENTANTE;
        uhrList.add(createUHR(4, user, rol, escritorio));
        
        for(UserHasRol uhr : uhrList){
            uhRolFacade.create(uhr);
        }
        
        System.out.println("Asignados los roles iniciales");
    }
    
    private void loadPersonal(){
        User user = userFacade.find(1234567);
        createPersonal(user, "Admin");
        
        user = userFacade.find(2345678);
        createPersonal(user, "Docente");
        
        user = userFacade.find(3456789);
        createPersonal(user, "Representante");
    }

    private User createUser(Long id, Integer ci, String usuario, String psw) {
        User user = new User(id);
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
    
    private UserHasRol createUHR(Integer id, User user, Rol rol, String escritorio){
        UserHasRol uhr = new UserHasRol(id);
        uhr.setUserId(user);
        uhr.setRolId(rol);
        uhr.setEscritorio(escritorio);
        return uhr;
    }
    
    private void createPersonal(User user, String tipo){
        DatosPersona dp = datosPersonaFacade.find(user.getCi().intValue());
        switch(tipo){
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
    
}
