/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package smrunner.ERcomunes;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import smrunner.node.Item;
import smrunner.operator.DirectionOperator;
import smrunner.utils.Wrapper;
import smrunner.utils.Wrapper2Test;
import static org.junit.Assert.*;

/**
 *
 * @author santi
 */
public class eatOneSquare_Test {

    public eatOneSquare_Test() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
//    @Test
//    public void test0()
//    {
//        String w1s = "<1/><2/><ul></ul><3/><4/>";
//        String w2s = "<1/><2/><ul>(<li>perro</li>)+</ul><3/><4/";
//        
//        Wrapper w1 = new Wrapper(w1s);
//        Wrapper w2 = new Wrapper(w2s);
//        
//        Item i = (Item) w1.getTree().getRootObject();
//        w1.eatOneSquare(w2, i, DirectionOperator.DOWNWARDS);
//        System.out.println(w1);
//        assertEquals(w2.toString(), w1.toString());
//    }
    
    @Test
    public void test1()
    {
        String w1s = "<algo/><a>hola</a><otroalgo/>";
        String w2s = "<algo/>(<a>adios</a>)+<otroalgo/>";
        
        Wrapper w1 = new Wrapper(w1s);
        Wrapper w2 = new Wrapper(w2s);
        
//        Item i = (Item) w1.getTree().getRoot().getFirstChild().getNext().getObject();
        Item i = (Item) w1.getTree().getRootObject();
        w1.eatOneSquare(w2, i, DirectionOperator.DOWNWARDS);
        System.out.println(w1);
        assertEquals(w1.toString(), "<algo/>(<a>&VARIABLE;</a>)+<otroalgo/>");
    }
    
    @Test
    public void test2()
    {
        String w1s = "<img/><a>hola</a><img/>";
        String w2s = "<img/>(<a>hola(<pepe/>)+</a>)+<img/>";
        
        Wrapper w1 = new Wrapper(w1s);
        Wrapper w2 = new Wrapper(w2s);
        
        Item i = (Item) w1.getTree().getRootObject();
        w1.eatOneSquare(w2, i, DirectionOperator.UPWARDS);
        
        System.out.println(w1);
        assertEquals(w1.toString(), "<img/>(<a>hola((<pepe/>)+)?</a>)+<img/>");
    }
    
    @Test
    public void test3()
    {
        String w1s = "<img/><a>hola</a><img/>";
        String w2s = "<img/>(<a>hola</a>)?<img/>";
        
        Wrapper w1 = new Wrapper(w1s);
        Wrapper w2 = new Wrapper(w2s);
        
        Item i = (Item) w1.getTree().getRootObject();
        w1.eatOneSquare(w2, i, DirectionOperator.UPWARDS);
        System.out.println(w1);
        assertEquals(w2.toString(), w1.toString());
    }
   
    @Test
    public void test4()
    {
        String w1s = "<p/><q/><t/><img/><a>hola</a><img/>";
        String w2s = "<p/><q/>(<t/>)+<img/>(<a>hola</a>)?<img/>";

        Wrapper w1 = new Wrapper(w1s);
        Wrapper w2 = new Wrapper(w2s);
        
        Item i = (Item) w1.getTree().getRootObject();
        w1.eatOneSquare(w2, i, DirectionOperator.DOWNWARDS);
        System.out.println(w1);
        assertEquals(w2.toString(), w1.toString());
    }
    
    @Test
    public void test5()
    {
        String w1s = "<ul><li>hola<b>pedro</b></li></ul>";
        String w2s = "<ul>(<li>hola(<b>pedro</b>)+</li>)+</ul>";

        Wrapper w1 = new Wrapper(w1s);
        Wrapper w2 = new Wrapper(w2s);
        
        Item i = (Item) w1.getTree().getRootObject();
        w1.eatOneSquare(w2, i, DirectionOperator.DOWNWARDS);
        System.out.println(w1);
        assertEquals("<ul>(<li>hola(<b>pedro</b>)+</li>)+</ul>", w1.toString());
    }
    
    @Test
    public void test6()
    {
        String w1s = "<ul><li>hola(<b>pedro</b>)?</li></ul>";
        String w2s = "<ul>(<li>hola(<b>pedro</b>)+</li>)+</ul>";

        Wrapper w1 = new Wrapper(w1s);
        Wrapper w2 = new Wrapper(w2s);
        
        Item i = (Item) w1.getTree().getRootObject();
        w1.eatOneSquare(w2, i, DirectionOperator.DOWNWARDS);
        System.out.println(w1);
        assertEquals("<ul>(<li>hola((<b>pedro</b>)+)?</li>)+</ul>", w1.toString());
    }
    
    @Test
    public void test7()
    {
        String w1s = "<ul></ul>";
        String w2s = "<ul>(<li>hola(<b>pedro</b>)+</li>)+</ul>";

        Wrapper w1 = new Wrapper(w1s);
        Wrapper w2 = new Wrapper(w2s);
        
        Item i = (Item) w1.getTree().getRootObject();
        w1.eatOneSquare(w2, i, DirectionOperator.DOWNWARDS);
        System.out.println(w1);
        assertEquals("<ul>((<li>hola(<b>pedro</b>)+</li>)+)?</ul>", w1.toString());
    }

}