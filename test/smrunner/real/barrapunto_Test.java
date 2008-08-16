/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package smrunner.real;

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
public class barrapunto_Test {

    public barrapunto_Test() {
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
        System.out.println("Barrapunto");
        String[] prueba = { "test/smrunner/real/html/barrapunto1.xhtml",
                            "test/smrunner/real/html/barrapunto2.xhtml"
                            //,"test/smrunner/real/html/debian3.xhtml"
                            };
        SMRunner rr = new SMRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
    }

}