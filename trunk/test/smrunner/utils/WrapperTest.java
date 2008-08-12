/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package smrunner.utils;

import smrunner.utils.Mismatch;
import smrunner.utils.Sample;
import smrunner.utils.Edible;
import smrunner.utils.Wrapper;
import SMTree.SMTree;
import SMTree.SMTreeNode;
import SMTree.utils.Enclosure;
import SMTree.utils.Kinship;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import smrunner.iterator.EdibleIterator;
import smrunner.iterator.ForwardTokenIterator;
import smrunner.iterator.webPageForwardIterator;
import static org.junit.Assert.*;
import smrunner.node.*;
import smrunner.operator.DirectionOperator;

/**
 *
 * @author delawen
 */
public class WrapperTest 
{
    
    private static Wrapper instance;

    private static Wrapper instance2;
    
    private static Wrapper instance3;

    public WrapperTest() 
    {
    }

    @BeforeClass
    public static void setUpClass() throws Exception 
    {
        SMTreeNode<Item> raiz = new SMTreeNode<Item>(new Tuple());
        SMTree<Item> tree = new SMTree<Item>(raiz);
        Item lista = new smrunner.node.List();
        tree.addObject(lista, raiz, Kinship.CHILD);
        SMTreeNode<Item> listaNode = tree.getNode(lista);
            tree.addObject(new Tag("<li>"), listaNode, Kinship.CHILD);
                tree.addObject(new Variable(), listaNode, Kinship.CHILD);
            tree.addObject(new Tag("</li>"), listaNode, Kinship.CHILD);
       instance = new Wrapper(tree);
       
        SMTreeNode<Item> raiz2 = new SMTreeNode<Item>(new Tuple());
        SMTree<Item> tree2 = new SMTree<Item>(raiz2);
        tree2.addObject(new DOF(), raiz2, Kinship.CHILD);
        Item lista2 = new smrunner.node.List();
        tree2.addObject(lista2, raiz2, Kinship.CHILD);
        SMTreeNode<Item> listaNode2 = tree2.getNode(lista2);
            tree2.addObject(new Tag("<li>"), listaNode2, Kinship.CHILD);
                tree2.addObject(new Variable(), listaNode2, Kinship.CHILD);
            tree2.addObject(new Tag("</li>"), listaNode2, Kinship.CHILD);
        tree2.addObject(new DOF(), raiz2, Kinship.CHILD);
       instance2 = new Wrapper(tree2);
       
        SMTreeNode<Item> raiz3 = new SMTreeNode<Item>(new Tuple());
        SMTree<Item> tree3 = new SMTree<Item>(raiz3);
        tree3.addObject(new DOF(), raiz3, Kinship.CHILD);
        Item lista3 = new smrunner.node.List();
        tree3.addObject(lista3, raiz3, Kinship.CHILD);
        SMTreeNode<Item> listaNode3 = tree3.getNode(lista3);
        Item optional = new smrunner.node.Optional();
            tree3.addObject(new Tag("<li>"), listaNode3, Kinship.CHILD);
                tree3.addObject(optional, listaNode3, Kinship.CHILD);
                SMTreeNode<Item> optionalNode = tree3.getNode(optional);
                    tree3.addObject(new Tag("<p>"), optionalNode, Kinship.CHILD);
                        tree3.addObject(new Variable(), optionalNode, Kinship.CHILD);
                    tree3.addObject(new Tag("</p>"), optionalNode, Kinship.CHILD);
                tree3.addObject(new Variable(), listaNode3, Kinship.CHILD);
            tree3.addObject(new Tag("</li>"), listaNode3, Kinship.CHILD);
        tree3.addObject(new DOF(), raiz3, Kinship.CHILD);
       instance3 = new Wrapper(tree3);
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

    /**
     * Test of eatOneSquare method, of class Wrapper.
     */
    @Test
    public void eatSquare() {
        System.out.println("eatSquare");
        Edible e = new Sample("test/roadrunner/utils/sample1.html");
        
        EdibleIterator it;
        if(e instanceof Sample) 
            it = e.iterator(webPageForwardIterator.class);
        else
            it = e.iterator(ForwardTokenIterator.class);
        
        it.next();
        Item t = (Item) it.next();
        DirectionOperator d = DirectionOperator.DOWNWARDS;
        Item expResult = null;
        while(it.hasNext())
        {
            expResult = (Item) it.next();
            if(expResult.getContent().equals("li") && expResult instanceof Tag && ((Tag)expResult).isCloseTag())
                break;
        }
        assertNotNull(expResult);
        Item result = instance.eatOneSquare(e, t, d);
        assertEquals(expResult, result);
    }

    /**
     * Test of eat method, of class Wrapper.
     */
    @Test
    public void eat() {
        System.out.println("eat");
        Sample s = new Sample("test/roadrunner/utils/sample2.html");
        DirectionOperator d = DirectionOperator.DOWNWARDS;
        Mismatch result = instance2.eat(s, d);
        assertNull(result);
        d = DirectionOperator.UPWARDS;
        result = instance2.eat(s, d);
        assertNull(result);      
        s = new Sample("test/roadrunner/utils/sample3.html");
        d = DirectionOperator.DOWNWARDS;
        result = instance3.eat(s, d);
        assertNull(result);
        d = DirectionOperator.UPWARDS;
        result = instance3.eat(s, d);
        assertNull(result);
        d = DirectionOperator.DOWNWARDS;
        result = instance2.eat(s, d);
        assertNotNull(result);
    }

    /**
     * Test of searchWellFormed method, of class Wrapper.
     */
    @Test
    public void searchWellFormed() 
    {
        System.out.println("searchWellFormed");
        Token from = (Token) instance.getTree().getRoot().getFirstChild().getFirstChild().getObject();
        Token t = new Tag("</li>");
        DirectionOperator d = DirectionOperator.DOWNWARDS;
        Token expResult = (Token) instance.getTree().getRoot().getFirstChild().getLastChild().getObject();
        Token result = instance.searchWellFormed(t, Enclosure.ENCLOSED, from, Enclosure.ENCLOSED, d);
        assertEquals(expResult, result);
    }

    @Test
    public void eatWrapper()
    {
        System.out.println("eating another Wrapper");
        instance.eat(instance, instance.getTree().getRootObject(), instance.getTree().getRootObject(), DirectionOperator.DOWNWARDS);
        instance.eat(instance, instance.getTree().getRootObject(), instance.getTree().getRootObject(), DirectionOperator.UPWARDS);
        instance2.eat(instance2, instance2.getTree().getRootObject(), instance2.getTree().getRootObject(), DirectionOperator.DOWNWARDS);
        instance2.eat(instance2, instance2.getTree().getRootObject(), instance2.getTree().getRootObject(), DirectionOperator.UPWARDS);
        instance3.eat(instance3, instance3.getTree().getRootObject(), instance3.getTree().getRootObject(), DirectionOperator.DOWNWARDS);
        instance3.eat(instance3, instance3.getTree().getRootObject(), instance3.getTree().getRootObject(), DirectionOperator.UPWARDS);
    }
    
    @Test
    public void wrapperString() 
    {
        System.out.println("wrapperString");
        String w1String = "(<li>0123456789</li>)+";
        Wrapper w1 = new Wrapper(w1String);

       assertEquals(w1.toString(), w1String);
       
       String w2String = "((<li>012345</li>)+<b>hola</b>)?<i>adios</i>";
        
        Wrapper w2 = new Wrapper(w2String);
        assertEquals(w2.toString(), w2String);
        
        String w3String = "<html>(<li>elemento(<b>pss</b>)?</li>)+</html>";
        Wrapper w3 = new Wrapper(w3String);
        assertEquals(w3.toString(), w3String);
        
    }

}