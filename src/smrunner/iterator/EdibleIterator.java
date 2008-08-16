/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package smrunner.iterator;

import SMTree.*;
import java.util.LinkedList;
import java.util.List;
import smrunner.node.Item;
import java.util.ListIterator;
import smrunner.node.Token;


/**
 *
 * @author delawen
 */

//El iterador comestible :D
public abstract class EdibleIterator<T> implements ListIterator<T>
{
    public SMTreeNode<Item> lastNode;
    private SMTreeNode<Item> rootIteratorNode;
    private SMTree<Item> tree;
    protected enum StateGoTo {NOT_ACTIVE, GOTO, GOTONEXT, GOTOPREVIOUS};
    protected StateGoTo stateGT;
    public enum TypeOfAccess {NOT_ACCESSED, ACCESSED };
    
    public final static int FIRST_WAY = 0;
    public final static int SECOND_WAY = 1;
    
    public abstract void init(Object objectToInicializeIterator);
    public abstract void goTo (Item i);
    public abstract void goToNext (Item i);
    public abstract void goToPrevious (Item i);
    public abstract boolean isNextAndNext (Item i);
    public abstract boolean isNext (Item i);
    public abstract boolean isPreviousAndPrevious (Item i);
    public abstract boolean isPrevious (Item i);
    public abstract Way[] next(Item i);
    public abstract Way[] next(SMTreeNode<Item> n);
    public abstract Way[] next(Item i, TypeOfAccess t);
    public abstract Way[] next(SMTreeNode<Item> n, TypeOfAccess t);
    public abstract List<T> nextAll();
    public abstract Way[] previous(Item i);
    public abstract Way[] previous(SMTreeNode<Item> n);
    public abstract Way[] previous(Item i, TypeOfAccess t);
    public abstract Way[] previous(SMTreeNode<Item> n, TypeOfAccess t);
    public abstract java.util.List<T> previousAll();
    public abstract java.util.List<Way> nextAllWays();

    
    
    public SMTreeNode<Item> getLastNode() {
        return lastNode;
    }

    public void setLastNode(SMTreeNode<Item> lastNode) {
//        stateGT = StateGoTo.NOT_ACTIVE;
        this.lastNode = lastNode;
    }

    public SMTreeNode<Item> getRootIteratorNode() {
        return rootIteratorNode;
    }

    public void setRootIteratorNode(SMTreeNode<Item> rootIteratorNode) {
        lastNode = null;
//        stateGT = StateGoTo.NOT_ACTIVE;
        this.rootIteratorNode = rootIteratorNode;
    }
    
    public Item getRootIterator() {

        return rootIteratorNode.getObject();
    }

    public void setRootIterator(Item rootIteratorItem) {
//        stateGT = StateGoTo.NOT_ACTIVE;
        lastNode = null;
        this.rootIteratorNode = getNode(rootIteratorItem);
    }

    public SMTree<Item> getTree() {
        return tree;
    }

    public void setTree(SMTree<Item> tree) {
        this.tree = tree;
//        stateGT = StateGoTo.NOT_ACTIVE;
        rootIteratorNode = tree.getRoot();
    }
    
    public SMTreeNode<Item> getNode(Item i){
        return tree.getNode(i);
    }
    
}