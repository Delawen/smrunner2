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
public class DOF_abajoOptional_Test {

    public DOF_abajoOptional_Test() {
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
        String[] prueba = { "test/roadrunner/medios/html/DOF_optional1.xhtml",
                            "test/roadrunner/medios/html/DOF_optional2.xhtml"};
        SMRunner rr = new SMRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
        assertEquals("<b>&VARIABLE;</b>(<i>opcionalidad</i>)?",w.toString());
    }
    
    @Test
    public void test2()
    {
        System.out.println("DOF's");
        String[] prueba = { "test/roadrunner/medios/html/DOF_optional2.xhtml",
                            "test/roadrunner/medios/html/DOF_optional1.xhtml"};
        SMRunner rr = new SMRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
        assertEquals("<b>&VARIABLE;</b>(<i>opcionalidad</i>)?",w.toString());
    }

}