/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package smrunner.medios;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import smrunner.SMRunner;
import smrunner.utils.Edible;
import static org.junit.Assert.*;

/**
 *
 * @author santi
 */
public class DOF_lista_Test {

    public DOF_lista_Test() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void test()
    {
        System.out.println("DOF's");
        String[] prueba = { "test/roadrunner/medios/html/DOF_list1.xhtml",
                            "test/roadrunner/medios/html/DOF_list2.xhtml"};
        SMRunner rr = new SMRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
        assertEquals("(<li>&VARIABLE;</li>)+",w.toString());
    }
    
    @Test
    public void test2()
    {
        System.out.println("DOF's");
        String[] prueba = { "test/roadrunner/medios/html/DOF_list2.xhtml",
                            "test/roadrunner/medios/html/DOF_list1.xhtml"};
        SMRunner rr = new SMRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
        assertEquals("(<li>&VARIABLE;</li>)+",w.toString());
    }

}