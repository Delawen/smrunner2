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
public class DOF_arribaOptional_Test {

    public DOF_arribaOptional_Test() {
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
    public void test1()
    {
        System.out.println("DOF's");
        String[] prueba = { "test/roadrunner/medios/html/DOF_optional1.xhtml",
                            "test/roadrunner/medios/html/DOF_optional2.xhtml",
                            "test/roadrunner/medios/html/DOF_optional3.xhtml"};
        SMRunner rr = new SMRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
        assertEquals("(<b>&VARIABLE;</b>)?(<i>opcionalidad</i>)?",w.toString());
    }
    
    @Test
    public void test2()
    {
        System.out.println("DOF's");
        String[] prueba = { "test/roadrunner/medios/html/DOF_optional3.xhtml",
                            "test/roadrunner/medios/html/DOF_optional2.xhtml",
                            "test/roadrunner/medios/html/DOF_optional1.xhtml"};
        SMRunner rr = new SMRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
        
        assertEquals("(<b>&VARIABLE;</b>)?(<i>opcionalidad</i>)?", w.toString());
    }
    
    @Test
    public void test3()
    {
        System.out.println("DOF's");
        String[] prueba = { "test/roadrunner/medios/html/DOF_optional2.xhtml",
                            "test/roadrunner/medios/html/DOF_optional1.xhtml",
                            "test/roadrunner/medios/html/DOF_optional3.xhtml"};
        SMRunner rr = new SMRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
        assertEquals("(<b>&VARIABLE;</b>)?(<i>opcionalidad</i>)?", w.toString());
    }
    
    @Test
    public void test4()
    {
        System.out.println("DOF's");
        String[] prueba = { "test/roadrunner/medios/html/DOF_optional3.xhtml",
                            "test/roadrunner/medios/html/DOF_optional1.xhtml",
                            "test/roadrunner/medios/html/DOF_optional2.xhtml"};
        
        try{
            SMRunner rr = new SMRunner(prueba);
            Edible w = rr.process();
            System.out.println(w);
            assertEquals("(<b>&VARIABLE;</b>)?(<i>opcionalidad</i>)?", w.toString());
            // Debe fallar y aqui no debe llegar nunca:
            assertFalse(true);
        }
        catch(Exception e)
        {
            // correcto :D
        }


    }

}