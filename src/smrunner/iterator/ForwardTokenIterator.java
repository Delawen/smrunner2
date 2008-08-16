package smrunner.iterator;

import SMTree.SMTree;
import SMTree.SMTreeNode;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedList;
import smrunner.SMRunner;
import smrunner.iterator.EdibleIterator;
import smrunner.iterator.EdibleIterator.TypeOfAccess;
import smrunner.node.*;
import smrunner.node.Token;


public class ForwardTokenIterator<T> extends TokenIterator<T>
{
    
    public ForwardTokenIterator()
    {
        super();
        stateGT = StateGoTo.NOT_ACTIVE;
    }
    
    public ForwardTokenIterator(SMTree t)
    {
        this();
        setTree(t);
    }
    
    @Override
    public Way[] next(SMTreeNode<Item> n) {
        
        Way[] resultWay = new Way[2];

        Item i = n.getObject();
        
        if(isList(i)|| isOptional(i)){
            if(n.getFirstChild() != null)
                resultWay[FIRST_WAY] = new Way(n.getFirstChild().getObject(), Way.DirectionWay.DOWN);         
            else
                resultWay[FIRST_WAY] = new Way(); // No hay camino
            
            if(n.getNext() != null)
                resultWay[SECOND_WAY] = new Way(n.getNext().getObject(), Way.DirectionWay.RIGHT);
            else if (n.getParent() != null && n.getParent()!=getRootIteratorNode())
                resultWay[SECOND_WAY] = new Way(n.getParent().getObject(), Way.DirectionWay.UP);
            else 
                resultWay[SECOND_WAY] = new Way();

        }else if(isTuple(i))
        {
            if(n.getFirstChild() != null)
                 resultWay[FIRST_WAY] = new Way(n.getFirstChild().getObject(), Way.DirectionWay.DOWN);
            else
                resultWay[FIRST_WAY] = new Way();
            
            if(n.getNext() != null)
                 resultWay[SECOND_WAY] = new Way(n.getNext().getObject(), Way.DirectionWay.RIGHT);
            else if(n.getParent() != null && n.getParent()!=getRootIteratorNode())
                 resultWay[SECOND_WAY] = new Way(n.getParent().getObject(), Way.DirectionWay.UP);
            else
                 resultWay[SECOND_WAY] = new Way();
            
            
        }else
        {         
            //Si es un token:
            if(n.getNext()!=null)
                 resultWay[FIRST_WAY] = new Way(n.getNext().getObject(), Way.DirectionWay.RIGHT);
            else if(n.getParent() != null && n.getParent()!=getRootIteratorNode())
                 resultWay[FIRST_WAY] = new Way(n.getParent().getObject(), Way.DirectionWay.UP);
            else
                resultWay[FIRST_WAY] = new Way();
                
            resultWay[SECOND_WAY] = new Way(i,Way.DirectionWay.NONE);
        }
        
        return resultWay;
    }
    
    @Override
    public Way[] previous(SMTreeNode<Item> n) {
        Way[] resultWay = new Way[2];

        Item i = n.getObject();
        
        if(isList(i)|| isOptional(i)){
            if(n.getLastChild() != null)
                resultWay[FIRST_WAY] = new Way(n.getLastChild().getObject(), Way.DirectionWay.DOWN);         
            else
                resultWay[FIRST_WAY] = new Way(); // No hay camino
            
            if(n.getPrevious() != null)
                resultWay[SECOND_WAY] = new Way(n.getPrevious().getObject(), Way.DirectionWay.LEFT);
            else if (n.getParent() != null && n.getParent()!=getRootIteratorNode())
                resultWay[SECOND_WAY] = new Way(n.getParent().getObject(), Way.DirectionWay.UP);
            else 
                resultWay[SECOND_WAY] = new Way();

        }else if(isTuple(i))
        {
            if(n.getLastChild() != null)
                 resultWay[FIRST_WAY] = new Way(n.getLastChild().getObject(), Way.DirectionWay.DOWN);
            else
                resultWay[FIRST_WAY] = new Way();
            
            if(n.getPrevious() != null)
                 resultWay[SECOND_WAY] = new Way(n.getPrevious().getObject(), Way.DirectionWay.LEFT);
            else if(n.getParent() != null && n.getParent()!=getRootIteratorNode())
                 resultWay[SECOND_WAY] = new Way(n.getParent().getObject(), Way.DirectionWay.UP);
            else
                 resultWay[SECOND_WAY] = new Way();
        }else
        {         
            //Si es un token:
            if(n.getPrevious()!=null)
                 resultWay[FIRST_WAY] = new Way(n.getPrevious().getObject(), Way.DirectionWay.LEFT);
            else if(n.getParent() != null && n!=getRootIteratorNode())
                 resultWay[FIRST_WAY] = new Way(n.getParent().getObject(), Way.DirectionWay.UP);
            else
            {
                resultWay[FIRST_WAY] = new Way();
                //TODO quitar al final del todo:
                throw new RuntimeException("token que no encontro su camino :s");
            }
                
            resultWay[SECOND_WAY] = new Way(i,Way.DirectionWay.NONE);
        }
        
        return resultWay;
    }

    public boolean hasNext() {
        SMTreeNode aux = getRootIteratorNode();
        
        while(aux.getLastChild()!=null)
            aux = aux.getLastChild();
        
        if(aux == lastNode && stateGT != StateGoTo.GOTOPREVIOUS && stateGT != StateGoTo.GOTO)
            return false;
        return true;
    }

    public boolean hasPrevious() {
        
        if(stateGT == StateGoTo.GOTOPREVIOUS)
        {
            SMTreeNode aux = getRootIteratorNode();   
            while(aux.getFirstChild()!=null)
                aux = aux.getFirstChild();
            return aux != lastNode;
        }
        
        return lastNode != null && lastNode != getRootIteratorNode();
    }
}
