/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package smrunner.simples;

import smrunner.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import smrunner.utils.Edible;

/**
 *
 * @author delawen
 */
public class unicoSample_Test {

    public unicoSample_Test() {
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
        System.out.println("Test simple para RoadRunner: un solo sample");
        String[] prueba = { "test/roadrunner/simples/html/sample1.html"};
        SMRunner rr = new SMRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);

    }
}