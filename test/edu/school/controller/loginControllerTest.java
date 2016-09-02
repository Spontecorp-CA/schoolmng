package edu.school.controller;

import edu.school.ejb.RolFacade;
import edu.school.ejb.UserFacade;
import edu.school.ejb.UserHasRolFacade;
import edu.school.entities.Rol;
import edu.school.entities.User;
import edu.school.entities.UserHasRol;
import javax.ejb.EJB;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jgcastillo
 */
public class loginControllerTest {
    
    @EJB
    private UserFacade userFacade;
    @EJB
    private RolFacade rolFacade;
    @EJB
    private UserHasRolFacade uhRolFacade;
    
    private User usuario;
    private Rol rol;
    private UserHasRol userHasRol;
    
    public loginControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        usuario = new User();
        rol = new Rol();
        userHasRol = new UserHasRol();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of init method, of class loginController.
     */
    @Test
    public void testInit() {
        System.out.println("init");
        String expected = "configurador";
        assertEquals(expected, rol.getName());
    }
    
}
