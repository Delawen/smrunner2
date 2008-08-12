/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package smrunner.iterator;

import SMTree.SMTree;
import SMTree.SMTreeNode;
import java.util.LinkedList;
import java.util.List;
import java.util.List;
import java.util.ListIterator;
import smrunner.node.Item;
import smrunner.node.Text;
import smrunner.node.Token;
import smrunner.utils.Edible;

/**
 *
 * @author delawen
 */
public class webPageBackwardIterator extends EdibleIterator
{
    private ListIterator<Token> it = null;
    private List<Token> tokens = null;
    private Token lastTokenWebPage = null;
    
    public void init(Object objectToInicializeIterator)
    {   
        assert(objectToInicializeIterator instanceof java.util.List);
        java.util.List<Token> tokens = (java.util.List) objectToInicializeIterator;
        this.it = tokens.listIterator(tokens.size());
        this.tokens = tokens;
    }
    public void goTo(Item t)
    {
        boolean result=false;
        
        it = tokens.listIterator();
        while(it.hasNext() && !result)
        {
            if(t==it.next())
            {
                if(it.hasNext())
                    lastTokenWebPage = it.next();
                else
                {
                    this.it = tokens.listIterator(tokens.size());
                    lastTokenWebPage = null;
                }
                result = true;
            }
        }
        assert(result == true);
    }

    public boolean hasNext() {
        return it.hasPrevious();
    }

    public Token next() {
        lastTokenWebPage = it.previous();
        return lastTokenWebPage;
    }

    public boolean hasPrevious() {
       return it.hasNext();
    }

    public Token previous() {
        lastTokenWebPage = it.next();
       return lastTokenWebPage;
    }

    public boolean isNext(Item o) 
    {
        Item i = this.next();
        if(i.equals(o))
            return true;
        this.previous();
        return false;
    }

    @Override
    public void goToNext(Item i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void goToPrevious(Item i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isPrevious(Item i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Way[] next(Item i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Way[] next(SMTreeNode n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Way[] next(Item i, TypeOfAccess t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Way[] next(SMTreeNode n, TypeOfAccess t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Way[] previous(Item i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Way[] previous(SMTreeNode n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Way[] previous(Item i, TypeOfAccess t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Way[] previous(SMTreeNode n, TypeOfAccess t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int nextIndex() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int previousIndex() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void set(Object arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void add(Object arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List nextAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}