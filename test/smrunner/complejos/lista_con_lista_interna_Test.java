/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package smrunner.complejos;

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
 * @author delawen
 */
public class lista_con_lista_interna_Test {

    public lista_con_lista_interna_Test() {
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
        System.out.println("addList con RI simples");
        String[] prueba = {
            "test/smrunner/complejos/html/A_sample4.xhtml",
            "test/smrunner/complejos/html/A_sample5.xhtml"
        };
        SMRunner rr = new SMRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
        
        assertEquals("<body><ul>(<li>&VARIABLE;((<b>&VARIABLE;</b>)+(<i>&VARIABLE;</i>)?)?</li>)+</ul></body>", w.toString());
    }
}