/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package smrunner.TagEspeciales;

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
public class tagComentarios_Test {

    public tagComentarios_Test() {
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
        System.out.println("Comentarios");
        String[] prueba = { "test/roadrunner/TagEspeciales/html/comentarios1.xhtml",
                            "test/roadrunner/TagEspeciales/html/comentarios2.xhtml"};
        SMRunner rr = new SMRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
        //ssertEquals("<b>&VARIABLE;</b>(<i>opcionalidad</i>)?",w.toString());
    }
}