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
public class addListVacio {

    public addListVacio() {
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
        System.out.println("addList sin elementos");
        String[] prueba = {
            "test/smrunner/complejos/html/A_sample2.xhtml",
                "test/smrunner/complejos/html/A_sample1.xhtml",
                "test/smrunner/complejos/html/A_sample3.xhtml"};
        SMRunner rr = new SMRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
        
        assertEquals("<body><ul>((<li>&VARIABLE;(<b>&VARIABLE;</b>)?</li>)?)+</ul></body>", w.toString());
    }


    @Test
    public void test2()
    {
        System.out.println("addList sin elementos");
        String[] prueba = {
            "test/smrunner/complejos/html/A_sample3.xhtml",
                "test/smrunner/complejos/html/A_sample1.xhtml",
                "test/smrunner/complejos/html/A_sample2.xhtml"};
        SMRunner rr = new SMRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
        
        assertEquals("<body><ul>((<li>&VARIABLE;(<b>&VARIABLE;</b>)?</li>)+)?</ul></body>", w.toString());
    }

}