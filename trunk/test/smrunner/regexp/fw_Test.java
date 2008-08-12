/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smrunner.regexp;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import smrunner.iterator.EdibleIterator;
import smrunner.iterator.ForwardTokenIterator;
import smrunner.node.Token;
import smrunner.utils.Wrapper;
import static org.junit.Assert.*;

/**
 *
 * @author santi
 */
public class fw_Test {

    public fw_Test() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Test
    public void test0() {

        String ws = "<i><a><b><b><a><b><f>";
        Wrapper elcomido = new Wrapper(ws);

        String ws1 = "<i>((<a>)?(<b>)+)+<f>";
        Wrapper elquecome = new Wrapper(ws1);

        List<Token> tokens = new LinkedList();

        System.out.println("Recorriendo el wrapper: " + ws);
        for (Object i : elcomido) {
            tokens.add((Token) i);
        }

        EdibleIterator w1_IT = elquecome.iterator(ForwardTokenIterator.class);
        ListIterator w_IT = tokens.listIterator();
        Token i, j;
        System.out.println();
        System.out.println();
        System.out.println("COMIDO:");
        while (w1_IT.hasNext() && w_IT.hasNext()) {
            i = (Token) w_IT.next();
            System.out.print(i);
            if (!w1_IT.isNext(i)) {
                fail("jo..." + i);
            }
        }
        boolean prueba = w_IT.hasNext();
        boolean prueba1 = w1_IT.hasNext();
        if (prueba1 || prueba) {
            fail("le quedan elementos por comer...");
        }
        w_IT = tokens.listIterator(tokens.size());
        i = (Token) w_IT.previous();
        System.out.println("");
        System.out.println("");
        System.out.println("COMIDO:");
        while (w_IT.hasPrevious() && w1_IT.hasPrevious()) {
            i = (Token) w_IT.previous();
            System.out.print(i);
            if (!w1_IT.isPrevious(i)) {
                fail("jo..." + i);
            }
        }
        prueba = w_IT.hasPrevious();
        prueba1 = w1_IT.hasPrevious();
        if (prueba1 || prueba) {
            fail("le quedan elementos por comer...");
        }
    }

    @Test
    public void test1() {

        String ws = "(1)?(<a>2(<b/>)+4</a>)+";
        Wrapper w = new Wrapper(ws);

        String ws1 = "(1)?(<a>2(<b/>(3)?)+4</a>)+";
        Wrapper w1 = new Wrapper(ws1);

        List<Token> tokens = new LinkedList();

        System.out.println("Recorriendo el wrapper: " + ws);
        for (Object i : w) {
            tokens.add((Token) i);
        }


        System.out.println("FIN!!");
        EdibleIterator w1_IT = w1.iterator(ForwardTokenIterator.class);
        Iterator w_IT = tokens.iterator();
        Token i, j;
        while (w1_IT.hasNext() && w_IT.hasNext()) {
            i = (Token) w_IT.next();
            if (!w1_IT.isNext(i)) {
                fail("jo...");
            }
        }
        boolean prueba = w_IT.hasNext();
        boolean prueba1 = w1_IT.hasNext();
        if (prueba1 || prueba) {
            fail("le quedan elementos por comer...");
        }
    }

    @Test
    public void test2() {

        String ws = "<a><c><f><g><g><g>" +
                "<bar><ce><lo><na><oe><oe><oe>" +
                "<bar><ce><lo><na><oe><oe><oe>" +
                "<bar><ce><lo><na><oe><oe><oe>" +
                "<g><g><g><g><g><g><a><g>";
        Wrapper elcomido = new Wrapper(ws);

        String ws1 = "(((<a>)?(<b>)?(<c>)?((<d>(<e>)?)+)?(<f>)?(<g>)+)+((<bar><ce><lo><na><oe><oe><oe>)?)+)+";
        Wrapper elquecome = new Wrapper(ws1);

        List<Token> tokens = new LinkedList();

        System.out.println("Recorriendo el wrapper: " + ws);
        for (Object i : elcomido) {
            tokens.add((Token) i);
        }

        System.out.println("FIN!!");
        EdibleIterator w1_IT = elquecome.iterator(ForwardTokenIterator.class);
        ListIterator w_IT = tokens.listIterator();
        Token i, j;
        System.out.println("");
        System.out.println("");
        System.out.println("COMIDO:");
        while (w1_IT.hasNext() && w_IT.hasNext()) {
            i = (Token) w_IT.next();
            System.out.print(i);
            if (!w1_IT.isNext(i)) {
                fail("jo..." + i);
            }
        }
        boolean prueba = w_IT.hasNext();
        boolean prueba1 = w1_IT.hasNext();
        if (prueba1 || prueba) {
            fail("le quedan elementos por comer...");
        }
        w_IT = tokens.listIterator(tokens.size());
        w_IT.previous();
        System.out.println("");
        System.out.println("");
        System.out.println("COMIDO:");
        while (w_IT.hasPrevious() && w1_IT.hasPrevious()) {
            i = (Token) w_IT.previous();
            System.out.print(i);
            if (!w1_IT.isPrevious(i)) {
                fail("jo..." + i);
            }
        }
        prueba = w_IT.hasPrevious();
        prueba1 = w1_IT.hasPrevious();
        if (prueba1 || prueba) {
            fail("le quedan elementos por comer...");
        }
    }
    
    @Test
    public void test3() {

        String ws = "<s><a><n><t><i><a><g><o><l><o><z><a><n><o>";
        Wrapper elcomido = new Wrapper(ws);

        String ws1 = "((<a>)?(<b>)?(<c>)?(<d>)?(<e>)?(<f>)?(<g>)?(<h>)?" +
                "(<i>)?(<j>)?(<k>)?(<l>)?(<m>)?(<n>)?(<o>)?(<p>)?(<q>)?(<r>)?(<s>)?" +
                "(<t>)?(<u>)?(<v>)?(<w>)?(<x>)?(<y>)?(<z>)?)+";
        Wrapper elquecome = new Wrapper(ws1);

        List<Token> tokens = new LinkedList();

        System.out.println("Recorriendo el wrapper: " + ws);
        for (Object i : elcomido) {
            tokens.add((Token) i);
        }

        EdibleIterator w1_IT = elquecome.iterator(ForwardTokenIterator.class);
        ListIterator w_IT = tokens.listIterator();
        Token i, j;
        System.out.println("");
        System.out.println("");
        System.out.println("COMIDO:");
        while (w1_IT.hasNext() && w_IT.hasNext()) {
            i = (Token) w_IT.next();
            System.out.print(i);
            if (!w1_IT.isNext(i)) {
                fail("jo..." + i);
            }
        }
        boolean prueba = w_IT.hasNext();
        boolean prueba1 = w1_IT.hasNext();
        if (prueba1 || prueba) {
            fail("le quedan elementos por comer...");
        }
        w_IT = tokens.listIterator(tokens.size());
        w_IT.previous();
        System.out.println("");
        System.out.println("");
        System.out.println("COMIDO:");
        while (w_IT.hasPrevious() && w1_IT.hasPrevious()) {
            i = (Token) w_IT.previous();
            System.out.print(i);
            if (!w1_IT.isPrevious(i)) {
                fail("jo..." + i);
            }
        }
        prueba = w_IT.hasPrevious();
        prueba1 = w1_IT.hasPrevious();
        if (prueba1 || prueba) {
            fail("le quedan elementos por comer...");
        }
    }
    
    
    @Test
    public void test4() {

        String ws = "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>" +
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>"+
                "<a><a><a><a><a><a><a><a><a><a><a><a><a><a>";
        Wrapper elcomido = new Wrapper(ws);

        String ws1 = "((<a>)?(<b>)?(<c>)?(<d>)?(<e>)?(<f>)?(<g>)?(<h>)?" +
                "(<i>)?(<j>)?(<k>)?(<l>)?(<m>)?(<n>)?(<o>)?(<p>)?(<q>)?(<r>)?(<s>)?" +
                "(<t>)?(<u>)?(<v>)?(<w>)?(<x>)?(<y>)?(<z>)?)+";
        Wrapper elquecome = new Wrapper(ws1);

        List<Token> tokens = new LinkedList();

        System.out.println("Recorriendo el wrapper: " + ws);
        for (Object i : elcomido) {
            tokens.add((Token) i);
        }

        EdibleIterator w1_IT = elquecome.iterator(ForwardTokenIterator.class);
        ListIterator w_IT = tokens.listIterator();
        Token i, j;
        System.out.println("");
        System.out.println("");
        System.out.println("COMIDO:");
        while (w1_IT.hasNext() && w_IT.hasNext()) {
            i = (Token) w_IT.next();
            System.out.print(i);
            if (!w1_IT.isNext(i)) {
                fail("jo..." + i);
            }
        }
        boolean prueba = w_IT.hasNext();
        boolean prueba1 = w1_IT.hasNext();
        if (prueba1 || prueba) {
            fail("le quedan elementos por comer...");
        }
        w_IT = tokens.listIterator(tokens.size());
        w_IT.previous();
        System.out.println("");
        System.out.println("");
        System.out.println("COMIDO:");
        while (w_IT.hasPrevious() && w1_IT.hasPrevious()) {
            i = (Token) w_IT.previous();
            System.out.print(i);
            if (!w1_IT.isPrevious(i)) {
                fail("jo..." + i);
            }
        }
        prueba = w_IT.hasPrevious();
        prueba1 = w1_IT.hasPrevious();
        if (prueba1 || prueba) {
            fail("le quedan elementos por comer...");
        }
    }
    
    @Test
    public void test6() {

        String ws = "<html><body><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<ul>" +
                "<li><b>negritaopcional</b><i>testo1</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo2</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo3</i><aparecesiempre></li>" +
                "<li><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo4</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo5</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo6</i><aparecesiempre></li>" +
                "<li><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo7</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo8</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo9</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo12</i><aparecesiempre></li>" +
                "<li><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo425</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo6346</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo643</i><aparecesiempre></li>" +
                "<li><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo899</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo969056</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo85468</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo</i><aparecesiempre></li>" +
                "<li><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo</i><aparecesiempre></li>" +
                "<li><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo7</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo8</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo9</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo12</i><aparecesiempre></li>" +
                "<li><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo425</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo6346</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo643</i><aparecesiempre></li>" +
                "<li><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo899</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo969056</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo85468</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo</i><aparecesiempre></li>" +
                "<li><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo</i><aparecesiempre></li>" +
                "<li><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo7</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo8</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo9</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo12</i><aparecesiempre></li>" +
                "<li><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo425</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo6346</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo643</i><aparecesiempre></li>" +
                "<li><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo899</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo969056</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo85468</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo</i><aparecesiempre></li>" +
                "<li><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo</i><aparecesiempre></li>" +
                "<li><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo7</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo8</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo9</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo12</i><aparecesiempre></li>" +
                "<li><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo425</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo6346</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo643</i><aparecesiempre></li>" +
                "<li><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo899</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo969056</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo85468</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo</i><aparecesiempre></li>" +
                "<li><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo</i><aparecesiempre></li>" +
                "<li><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo7</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo8</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo9</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo12</i><aparecesiempre></li>" +
                "<li><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo425</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo6346</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo643</i><aparecesiempre></li>" +
                "<li><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo899</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo969056</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo85468</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo</i><aparecesiempre></li>" +
                "<li><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo</i><aparecesiempre></li>" +
                "<li><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo7</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo8</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo9</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo12</i><aparecesiempre></li>" +
                "<li><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo425</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo6346</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo643</i><aparecesiempre></li>" +
                "<li><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo899</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo969056</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo85468</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo</i><aparecesiempre></li>" +
                "<li><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo</i><aparecesiempre></li>" +
                "<li><b>negritaopcional</b><i>testo85686</i><aparecesiempre></li>" +
                "</ul>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "</body></html>";
        Wrapper elcomido = new Wrapper(ws);
                
        String ws1 = "<html><body><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<ul>" +
                "(<li>(<b>negritaopcional</b>)?(<i>#</i>)?<aparecesiempre></li>)+" +
                "</ul>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "<tokensecuencial><tokensecuencial><tokensecuencial><tokensecuencial>" +
                "</body></html>";
        Wrapper elquecome = new Wrapper(ws1);

        List<Token> tokens = new LinkedList();

        System.out.println("Recorriendo el wrapper: " + ws);
        for (Object i : elcomido) {
            tokens.add((Token) i);
        }

        EdibleIterator w1_IT = elquecome.iterator(ForwardTokenIterator.class);
        ListIterator w_IT = tokens.listIterator();
        Token i, j;
        System.out.println();
        System.out.println();
        System.out.println("COMIDO:");
        while (w1_IT.hasNext() && w_IT.hasNext()) {
            i = (Token) w_IT.next();
            System.out.print(i);
            if (!w1_IT.isNext(i)) {
                fail("jo..." + i);
            }
        }
        boolean prueba = w_IT.hasNext();
        boolean prueba1 = w1_IT.hasNext();
        if (prueba1 || prueba) {
            fail("le quedan elementos por comer...");
        }
        w_IT = tokens.listIterator(tokens.size());
        i = (Token) w_IT.previous();
        System.out.println("");
        System.out.println("");
        System.out.println("COMIDO:");
        while (w_IT.hasPrevious() && w1_IT.hasPrevious()) {
            i = (Token) w_IT.previous();
            System.out.print(i);
            if (!w1_IT.isPrevious(i)) {
                fail("jo..." + i);
            }
        }
        prueba = w_IT.hasPrevious();
        prueba1 = w1_IT.hasPrevious();
        if (prueba1 || prueba) {
            fail("le quedan elementos por comer...");
        }
    }
    
    @Test
    public void test7() {

        String ws = "<a>hola</a>";
        Wrapper elcomido = new Wrapper(ws);

        String ws1 = "<a>(#)?</a>";
        Wrapper elquecome = new Wrapper(ws1);

        List<Token> tokens = new LinkedList();

        System.out.println("Recorriendo el wrapper: " + ws);
        for (Object i : elcomido) {
            tokens.add((Token) i);
        }

        EdibleIterator w1_IT = elquecome.iterator(ForwardTokenIterator.class);
        ListIterator w_IT = tokens.listIterator();
        Token i, j;
        System.out.println();
        System.out.println();
        System.out.println("COMIDO:");
        while (w1_IT.hasNext() && w_IT.hasNext()) {
            i = (Token) w_IT.next();
            System.out.print(i);
            if (!w1_IT.isNext(i)) {
                fail("jo..." + i);
            }
        }
        boolean prueba = w_IT.hasNext();
        boolean prueba1 = w1_IT.hasNext();
        if (prueba1 || prueba) {
            fail("le quedan elementos por comer...");
        }
        w_IT = tokens.listIterator(tokens.size());
        i = (Token) w_IT.previous();
        System.out.println("");
        System.out.println("");
        System.out.println("COMIDO:");
        while (w_IT.hasPrevious() && w1_IT.hasPrevious()) {
            i = (Token) w_IT.previous();
            System.out.print(i);
            if (!w1_IT.isPrevious(i)) {
                fail("jo..." + i);
            }
        }
        prueba = w_IT.hasPrevious();
        prueba1 = w1_IT.hasPrevious();
        if (prueba1 || prueba) {
            fail("le quedan elementos por comer...");
        }
    }
}