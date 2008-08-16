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
public class list_con_RI_variable_Test {

    public list_con_RI_variable_Test() {
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
        System.out.println("addList con addVariable interno");
        String[] prueba = {
                "test/smrunner/medios/html/basico.html",
                "test/smrunner/medios/html/test1.html"};
        SMRunner rr = new SMRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
        assertEquals("<html>(<div>&VARIABLE;</div>)+</html>",w.toString());
    }
    
    @Test
    public void test2()
    {
        System.out.println("addList con addVariable interno");
        String[] prueba = {
                "test/smrunner/medios/html/test1.html",
                "test/smrunner/medios/html/basico.html"};
        SMRunner rr = new SMRunner(prueba);
        Edible w = rr.process();
        System.out.println(w);
        assertEquals("<html>(<div>&VARIABLE;</div>)+</html>",w.toString());
    }
    

}