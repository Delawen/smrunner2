/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package smrunner.TagEspeciales;

import smrunner.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import smrunner.utils.Edible;
import static org.junit.Assert.*;

/**
 *
 * @author santi
 */
public class etiquetasDeAperturaYcierre_Test {

    public etiquetasDeAperturaYcierre_Test() {
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
        System.out.println("Test para comprobar etiquetas del tipo <img/>");
        String[] prueba = new String[2];
        prueba[0] = "test/roadrunner/TagEspeciales/html/sample1.xhtml";
        prueba[1] = "test/roadrunner/TagEspeciales/html/sample2.xhtml";
        SMRunner rr = new SMRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
    }
    
    @Test
    public void test2()
    {
        System.out.println("Test para comprobar etiquetas del tipo <img/>");
        String[] prueba = new String[2];
        prueba[0] = "test/roadrunner/TagEspeciales/html/sample2.xhtml";
        prueba[1] = "test/roadrunner/TagEspeciales/html/sample1.xhtml";
        SMRunner rr = new SMRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
    }
    
    @Test
    public void test3()
    {
        System.out.println("Test para comprobar etiquetas del tipo <img/>");
        String[] prueba = new String[2];
        prueba[0] = "test/roadrunner/TagEspeciales/html/sample2.xhtml";
        prueba[1] = "test/roadrunner/TagEspeciales/html/sample3.xhtml";
        SMRunner rr = new SMRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
    }
    
    @Test
    public void test4()
    {
        System.out.println("Test para comprobar etiquetas del tipo <img/>");
        String[] prueba = new String[2];
        prueba[0] = "test/roadrunner/TagEspeciales/html/sample3.xhtml";
        prueba[1] = "test/roadrunner/TagEspeciales/html/sample2.xhtml";
        SMRunner rr = new SMRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
    }
    
    @Test
    public void test5()
    {
        System.out.println("Test para comprobar etiquetas del tipo <img/>");
        String[] prueba = new String[3];
        prueba[0] = "test/roadrunner/TagEspeciales/html/sample1.xhtml";
        prueba[1] = "test/roadrunner/TagEspeciales/html/sample2.xhtml";
        prueba[2] = "test/roadrunner/TagEspeciales/html/sample3.xhtml";
        SMRunner rr = new SMRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
    }
    
    @Test
    public void test6()
    {
        System.out.println("Test para comprobar etiquetas del tipo <img/>");
        String[] prueba = new String[3];
        prueba[0] = "test/roadrunner/TagEspeciales/html/sample3.xhtml";
        prueba[1] = "test/roadrunner/TagEspeciales/html/sample2.xhtml";
        prueba[2] = "test/roadrunner/TagEspeciales/html/sample1.xhtml";
        SMRunner rr = new SMRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
    }
    
    @Test
    public void test7()
    {
        System.out.println("Test para comprobar etiquetas del tipo <img/>");
        String[] prueba = new String[3];
        prueba[0] = "test/roadrunner/TagEspeciales/html/sample2.xhtml";
        prueba[1] = "test/roadrunner/TagEspeciales/html/sample3.xhtml";
        prueba[2] = "test/roadrunner/TagEspeciales/html/sample1.xhtml";
        SMRunner rr = new SMRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
    }
}