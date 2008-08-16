/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package smrunner.regexp;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import smrunner.iterator.BackwardTokenIterator;
import smrunner.iterator.EdibleIterator;
import smrunner.iterator.ForwardTokenIterator;
import smrunner.node.Token;
import smrunner.utils.Wrapper;
import static org.junit.Assert.*;

/**
 *
 * @author santi
 */
public class nextAll_Test {

    public nextAll_Test() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Test
    public void test0() {

        String ws = "<i><a><d><f>";
        Wrapper elcomido = new Wrapper(ws);

        String ws1 = "<i>(<a>(<b>)?(<c>)?<d>)+<f>";
        Wrapper elquecome = new Wrapper(ws1);

        List<Token> tokens = new LinkedList();

        System.out.println("Recorriendo el wrapper: " + ws);
        for (Object i : elcomido) {
            tokens.add((Token) i);
        }

        EdibleIterator elquecomeIterator = elquecome.iterator(BackwardTokenIterator.class);
        ListIterator elcomidoIterator = tokens.listIterator(tokens.size());
        Token i, j;
        System.out.println();
        System.out.println();
        System.out.println("COMIDO:");
        while (elquecomeIterator.hasNext() && elcomidoIterator.hasPrevious()) {
            i = (Token) elcomidoIterator.previous();
            System.out.print(i);
            if (!elquecomeIterator.isNextAndNext(i)) {
                fail("jo..." + i);
            }
        }
        boolean prueba = elcomidoIterator.hasPrevious();
        boolean prueba1 = elquecomeIterator.hasNext();
        if (prueba1 || prueba) {
            fail("le quedan elementos por comer...");
        }
        
//        elquecomeIterator = elquecome.iterator(BackwardTokenIterator.class);
        elcomidoIterator = tokens.listIterator();
        System.out.println();
        System.out.println();
        System.out.println("COMIDO:");
        while (elquecomeIterator.hasPrevious() && elcomidoIterator.hasNext()) {
            i = (Token) elcomidoIterator.next();
            System.out.print(i);
            elquecomeIterator.previousAll();
            if (!elquecomeIterator.isPreviousAndPrevious(i)) {
                fail("jo..." + i);
            }
        }
        prueba = elcomidoIterator.hasNext();
        prueba1 = elquecomeIterator.hasPrevious();
        if (prueba1 || prueba) {
            fail("le quedan elementos por comer...");
        }
        
    }
    
    @Test
    public void test1() {

        String ws = "<i><a><d><f>";
        Wrapper elcomido = new Wrapper(ws);

        String ws1 = "<i>(<a>(<b>)?(<c>)?<d>)+<f>";
        Wrapper elquecome = new Wrapper(ws1);

        List<Token> tokens = new LinkedList();

        System.out.println("Recorriendo el wrapper: " + ws);
        for (Object i : elcomido) {
            tokens.add((Token) i);
        }

        ForwardTokenIterator elquecomeIterator = (ForwardTokenIterator) elquecome.iterator(ForwardTokenIterator.class);
        ListIterator elcomidoIterator = tokens.listIterator();
        Token i, j;
        System.out.println();
        System.out.println();
        System.out.println("COMIDO:");
        while (elquecomeIterator.hasNext() && elcomidoIterator.hasNext()) {
            i = (Token) elcomidoIterator.next();
            System.out.print(i);
            elquecomeIterator.nextAllWays();
            if (!elquecomeIterator.isNextAndNext(i)) {
                fail("jo..." + i);
            }
        }
        boolean prueba = elcomidoIterator.hasNext();
        boolean prueba1 = elquecomeIterator.hasNext();
        if (prueba1 || prueba) {
            fail("le quedan elementos por comer...");
        }
        
//        elquecomeIterator = elquecome.iterator(BackwardTokenIterator.class);
        elcomidoIterator = tokens.listIterator(tokens.size());
        System.out.println();
        System.out.println();
        System.out.println("COMIDO:");
        while (elquecomeIterator.hasPrevious() && elcomidoIterator.hasPrevious()) {
            i = (Token) elcomidoIterator.previous();
            System.out.print(i);
            elquecomeIterator.previousAll();
            if (!elquecomeIterator.isPreviousAndPrevious(i)) {
                fail("jo..." + i);
            }
        }
        prueba = elcomidoIterator.hasPrevious();
        prueba1 = elquecomeIterator.hasPrevious();
        if (prueba1 || prueba) {
            fail("le quedan elementos por comer...");
        }
        
    }
    
    @Test
        public void testManual() {

            String ws1 = "<i>(<a>(<b>(<lista>)+)?((<lista2222>)+<c>)?<d><s><s>)+<f>";
            Wrapper elquecome = new Wrapper(ws1);

            ForwardTokenIterator elquecomeIterator = (ForwardTokenIterator) elquecome.iterator(ForwardTokenIterator.class);
            System.out.println();
            System.out.println();
            System.out.println("COMIDO:");
            while (elquecomeIterator.hasNext()) {
                elquecomeIterator.next();
                elquecomeIterator.nextAllWays();
            }
        }
    
        @Test
        public void testManual2() {

            String ws1 = "<i>(<a>(<b>(<lista>)+)?((<lista2222>)+<c>)?<d><s><s>)+(<f>)?";
            Wrapper elquecome = new Wrapper(ws1);

            BackwardTokenIterator elquecomeIterator = (BackwardTokenIterator) elquecome.iterator(BackwardTokenIterator.class);
            System.out.println();
            System.out.println();
            System.out.println("COMIDO:");
            while (elquecomeIterator.hasNext()) {
                elquecomeIterator.next();
                elquecomeIterator.nextAllWays();
            }
        }
}