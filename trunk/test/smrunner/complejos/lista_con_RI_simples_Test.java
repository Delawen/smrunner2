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
import smrunner.iterator.EdibleIterator;
import smrunner.iterator.ForwardTokenIterator;
import smrunner.node.Token;
import smrunner.utils.Edible;
import smrunner.utils.Wrapper;
import static org.junit.Assert.*;

/**
 *
 * @author delawen
 */
public class lista_con_RI_simples_Test{

    public lista_con_RI_simples_Test() {
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
                            "test/roadrunner/complejos/html/A_sample1.xhtml",
                            "test/roadrunner/complejos/html/A_sample2.xhtml"};
        SMRunner rr = new SMRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
        
        assertEquals("<body><ul>(<li>&VARIABLE;(<b>&VARIABLE;</b>)?</li>)+</ul></body>", w.toString());
    }
    
    @Test
    public void test2()
    {
        System.out.println("addList con RI simples");
        String[] prueba = {
                            "test/roadrunner/complejos/html/A_sample2.xhtml",
                            "test/roadrunner/complejos/html/A_sample1.xhtml"};
        SMRunner rr = new SMRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
        
        assertEquals("<body><ul>(<li>&VARIABLE;(<b>&VARIABLE;</b>)?</li>)+</ul></body>", w.toString());
    }
    

}