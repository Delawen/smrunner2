/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package smrunner.simples;

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
public class todos_Test {

    public todos_Test() {
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
    public void test5()
    {
        System.out.println("Todo Junto");
        String[] prueba = {
                "test/roadrunner/simples/html/sample1.html",
                "test/roadrunner/simples/html/sample2.html",
                "test/roadrunner/simples/html/sample3.html",
                "test/roadrunner/simples/html/sample4.html",
                "test/roadrunner/simples/html/sample5.html"};
        SMRunner rr = new SMRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
        assertEquals("<body>(<p>&VARIABLE;</p>)+(<b>&VARIABLE;</b>(<i>Hola</i>)?)?</body>",w.toString());
    }

}