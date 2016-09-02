package edu.school.entities;

import java.util.Collection;
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
public class RolTest {
    
    Rol instance;
    
    public RolTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new Rol();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getId method, of class Rol.
     */
    @Test
    @Ignore
    public void testGetId() {
        System.out.println("getId");
        Integer expResult = null;
        Integer result = instance.getId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setId method, of class Rol.
     */
    @Test
    @Ignore
    public void testSetId() {
        System.out.println("setId");
        Integer id = null;
        Rol instance = new Rol();
        instance.setId(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    /**
     * Test of setName method, of class Rol.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "configurador";

        instance.setName(name);

    }

    /**
     * Test of getName method, of class Rol.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        String expResult = "configurador";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    

    
    
}
