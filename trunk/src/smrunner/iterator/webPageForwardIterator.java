/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package smrunner.iterator;

import SMTree.SMTree;
import SMTree.SMTreeNode;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import smrunner.node.Item;
import smrunner.node.Token;

/**
 *
 * @author delawen
 */
public class webPageForwardIterator extends EdibleIterator
{
    private ListIterator<Token> it = null;
    private List<Token> tokens = null;
    private Token lastTokenWebPage = null;

    public webPageForwardIterator()
    {
        super();
    }
    
    public void init(Object objectToInicializeIterator)
    {   
        assert(objectToInicializeIterator instanceof java.util.List);
        java.util.List<Token> tokens = (java.util.List) objectToInicializeIterator;
        this.it = tokens.listIterator();
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
                lastTokenWebPage = it.previous();
                result = true;
            }
        }
        assert(result == true);

    }

    public boolean hasNext() {
        return it.hasNext();
    }

    public Token next() {

        try{
            lastTokenWebPage = it.next();
        } catch (NoSuchElementException e)
        {
            lastTokenWebPage = null;
        }
        return lastTokenWebPage;
    }

    public boolean hasPrevious() {
        return it.hasPrevious();
    }

    public Token previous() {
        try{
            lastTokenWebPage = it.previous();
        } catch (NoSuchElementException e)
        {
            lastTokenWebPage = null;
        }
        return lastTokenWebPage;
    }

    public boolean isNextAndNext(Item o) 
    {
        Item i = it.next();
        if(i.equals(o))
            return true;
        it.previous();
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
    public boolean isPreviousAndPrevious(Item i) {
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

    @Override
    public List previousAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List nextAllWays() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isNext(Item i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isPrevious(Item i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}