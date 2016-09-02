package edu.school.ejb;

import edu.school.entities.Rol;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author jgcastillo
 */
public class RolFacadeTest {
    
    private Rol rol;
    
    public RolFacadeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        rol = new Rol();
        
        rol.setName("configurador");
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of create method, of class RolFacade.
     */
    @Test
    @Ignore
    public void testCreate() throws Exception {
        System.out.println("create");

        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        RolFacadeLocal instance = (RolFacadeLocal)container.getContext().lookup("java:global/classes/RolFacade");
        instance.create(rol);
        container.close();
        
    }

    @Test
    public void testFind() throws NamingException{
        System.out.println("find");
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        RolFacadeLocal instance = (RolFacadeLocal) container.getContext().lookup("java:global/classes/RolFacade");
        String expected = "configurador";
        rol = instance.find("configurador");
        String result = rol.getName();
        assertEquals(expected, result);
    }
}
