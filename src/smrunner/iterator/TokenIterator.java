package smrunner.iterator;

import SMTree.SMTree;
import SMTree.SMTreeNode;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedList;
import smrunner.SMRunner;
import smrunner.iterator.EdibleIterator;
import smrunner.iterator.EdibleIterator.TypeOfAccess;
import smrunner.iterator.Way;
import smrunner.node.*;
import smrunner.node.Token;

/**
 *
 * @author santi
 */
public abstract class TokenIterator<T> extends EdibleIterator<T>{
    
    
    //TODO Refactorizar.... la mayoria de los metodos son iguales
    // por ejemplo isNext es igual que isNextAndNext pero sin hacer GoToNext()...
    
    public void init(Object objectToInicializeIterator){
    }
    
    @Override
    public void goTo(Item i) {
        stateGT = StateGoTo.GOTO;
        lastNode = getNode(i);
    }

    @Override
    public void goToNext(Item i) {
//        if(!(i instanceof Token))
//            throw new IllegalStateException("Solo se puede hacer GoToNext a Tokens");

        if(i instanceof Tuple)
        {
            stateGT = StateGoTo.GOTO;
            lastNode = getNode(i);
        }
        else if(i == null)
        {
            lastNode = getRootIteratorNode();
            stateGT = StateGoTo.GOTO;
        }
        else
        {
            stateGT = StateGoTo.GOTONEXT;
            lastNode = getNode(i);
        }
    }

    @Override
    public void goToPrevious(Item i) {
//        if(!(i instanceof Token))
//            throw new IllegalStateException("Solo se puede hacer GoToPrevious a Tokens");
        
        if(i instanceof Tuple)
            stateGT = StateGoTo.GOTO;
        else
        stateGT = StateGoTo.GOTOPREVIOUS;
        lastNode = getNode(i);
    }

    @Override
    public T next() {
        
        Way[] resultWays = null;
        if(lastNode == null)
        {
            stateGT = StateGoTo.NOT_ACTIVE;
            resultWays = next(getRootIteratorNode(),TypeOfAccess.NOT_ACCESSED);
        }
        else if(stateGT == StateGoTo.NOT_ACTIVE)
        {
            resultWays = next(lastNode, TypeOfAccess.ACCESSED);
        }
        else if(stateGT == StateGoTo.GOTO)
        {
            stateGT = StateGoTo.NOT_ACTIVE;
            resultWays = next(lastNode,TypeOfAccess.NOT_ACCESSED);
        }
        else if(stateGT == StateGoTo.GOTONEXT)   
        {
            stateGT = StateGoTo.NOT_ACTIVE;
            resultWays = next(lastNode,TypeOfAccess.ACCESSED);
        }
        else if (stateGT == StateGoTo.GOTOPREVIOUS)   
        {
            stateGT = StateGoTo.NOT_ACTIVE;
            resultWays = previous(lastNode,TypeOfAccess.ACCESSED);
            resultWays = next(resultWays[FIRST_WAY].getWayItem(),TypeOfAccess.NOT_ACCESSED);
        }
        
        
        while(!isToken(resultWays[FIRST_WAY].getWayItem()))
        {
            if(resultWays[FIRST_WAY].getDirection() == Way.DirectionWay.UP)
                resultWays = next(resultWays[FIRST_WAY].getWayItem(),TypeOfAccess.ACCESSED);
            else if (resultWays[FIRST_WAY].getDirection() != null)
                resultWays = next(resultWays[FIRST_WAY].getWayItem(),TypeOfAccess.NOT_ACCESSED);
            else
                break;
        }
        
        if(resultWays[FIRST_WAY].getWayItem()==null )
            return null;
        
        lastNode =  getNode(resultWays[FIRST_WAY].getWayItem());
        return  (T)resultWays[FIRST_WAY].getWayItem();
    }
    
    @Override
    public T previous() {
        
        Way[] resultWays = null;
        if(lastNode == null)
            throw new IllegalStateException("No se hizo next antes de previous");
        else if(stateGT == StateGoTo.NOT_ACTIVE)
        {
            resultWays = previous(lastNode, TypeOfAccess.NOT_ACCESSED);
        }
        else if(stateGT == StateGoTo.GOTO)
        {
            stateGT = StateGoTo.NOT_ACTIVE;
            resultWays = previous(lastNode,TypeOfAccess.ACCESSED);
        }
        else if(stateGT == StateGoTo.GOTONEXT)   
        {
            stateGT = StateGoTo.NOT_ACTIVE;
            resultWays = previous(lastNode,TypeOfAccess.NOT_ACCESSED);
        }
        else if (stateGT == StateGoTo.GOTOPREVIOUS)   
        {
            stateGT = StateGoTo.NOT_ACTIVE;
            resultWays = previous(lastNode,TypeOfAccess.ACCESSED);
            if(resultWays[FIRST_WAY].getWayItem() != getRootIteratorNode().getObject())
                resultWays = previous(resultWays[FIRST_WAY].getWayItem(),TypeOfAccess.ACCESSED);           
        }
        
        
        while(!isToken(resultWays[FIRST_WAY].getWayItem()) && resultWays[FIRST_WAY].getWayItem()!=null)
        {
            if(resultWays[FIRST_WAY].getDirection() != Way.DirectionWay.UP)
                resultWays = previous(resultWays[FIRST_WAY].getWayItem(),TypeOfAccess.NOT_ACCESSED);
            else
                resultWays = previous(resultWays[FIRST_WAY].getWayItem(),TypeOfAccess.ACCESSED);
        
        }
        
        if(resultWays[FIRST_WAY].getWayItem()==null )
            return null;
        
        lastNode =  getNode(resultWays[FIRST_WAY].getWayItem());
        return  (T)resultWays[FIRST_WAY].getWayItem();

    }
    
    @Override
    public boolean isNextAndNext(Item i) {
        
//        if(!isToken(i))
//            throw new IllegalArgumentException("El item no es un Token");
        
        Way[] resultWays = null;
        if(lastNode == null)
            resultWays = next(getRootIteratorNode(),TypeOfAccess.NOT_ACCESSED);
        else if(stateGT == StateGoTo.NOT_ACTIVE)
        {
            resultWays = next(lastNode, TypeOfAccess.ACCESSED);
        }
        else if(stateGT == StateGoTo.GOTO)
        {
            resultWays = next(lastNode,TypeOfAccess.NOT_ACCESSED);
        }
        else if(stateGT == StateGoTo.GOTONEXT)   
        {
            resultWays = next(lastNode,TypeOfAccess.ACCESSED);
        }
        else if (stateGT == StateGoTo.GOTOPREVIOUS)   
        {
            resultWays = previous(lastNode,TypeOfAccess.ACCESSED);
            resultWays = next(resultWays[FIRST_WAY].getWayItem(),TypeOfAccess.NOT_ACCESSED);
        }
    
        Way[] result = resultWays;
        
        //Normalmente result tendrá tokens asi que le preguntamos directamente:
        if(isToken(result[FIRST_WAY].getWayItem()))
        {
            if(matchTokens(result[FIRST_WAY].getWayItem(), i))
            {
                goToNext(result[FIRST_WAY].getWayItem());
                return true;
            }
        }
                   
        //Si hemos llegado aqui es porque tenemos que resolver algunos caminos
        //La lista tokens contendra Items por prioridad de menor a mayor que iremos resolviendo
        java.util.List<Way> tokens = new LinkedList();
        java.util.List<Way> waysAdded = new LinkedList();
        
        //Añadiremos los caminos que habiamos calculado siempre que no sean tokens
        if(isOptional(result[SECOND_WAY].getWayItem()) || isList(result[SECOND_WAY].getWayItem()))
        {
                 addToBag(result[SECOND_WAY], tokens, waysAdded);
        }
        if(isOptional(result[FIRST_WAY].getWayItem()) || isList(result[FIRST_WAY].getWayItem()))
        {
                 addToBag(result[FIRST_WAY], tokens, waysAdded);
        }
        
        Way[] partialResult = null;
        while(!tokens.isEmpty())
        {
            Way wayToResolve = tokens.get(tokens.size()-1);
            if(isToken(wayToResolve.getWayItem()))
            {
                if(matchTokens(wayToResolve.getWayItem(), i))
                {
                    goToNext(wayToResolve.getWayItem());
                    return true;
                }
                else
                    tokens.remove(tokens.size()-1);
            }
            else
            {
                tokens.remove(tokens.size()-1);
                if(wayToResolve.getDirection() == Way.DirectionWay.UP)
                    partialResult = next(wayToResolve.getWayItem() , TypeOfAccess.ACCESSED );
                else if((wayToResolve.getDirection() != null))
                    partialResult = next(wayToResolve.getWayItem() , TypeOfAccess.NOT_ACCESSED );
                else
                    assert(false);
                
                addToBag(partialResult[SECOND_WAY], tokens, waysAdded);
                addToBag(partialResult[FIRST_WAY], tokens, waysAdded);
            }
        }
        return false;
    }
    
    @Override
    public boolean isNext(Item i) {
//               if(!isToken(i))
//            throw new IllegalArgumentException("El item no es un Token");
        
        Way[] resultWays = null;
        if(lastNode == null)
            resultWays = next(getRootIteratorNode(),TypeOfAccess.NOT_ACCESSED);
        else if(stateGT == StateGoTo.NOT_ACTIVE)
        {
            resultWays = next(lastNode, TypeOfAccess.ACCESSED);
        }
        else if(stateGT == StateGoTo.GOTO)
        {
            resultWays = next(lastNode,TypeOfAccess.NOT_ACCESSED);
        }
        else if(stateGT == StateGoTo.GOTONEXT)   
        {
            resultWays = next(lastNode,TypeOfAccess.ACCESSED);
        }
        else if (stateGT == StateGoTo.GOTOPREVIOUS)   
        {
            resultWays = previous(lastNode,TypeOfAccess.ACCESSED);
            resultWays = next(resultWays[FIRST_WAY].getWayItem(),TypeOfAccess.NOT_ACCESSED);
        }
    
        Way[] result = resultWays;
        
        //Normalmente result tendrá tokens asi que le preguntamos directamente:
        if(isToken(result[FIRST_WAY].getWayItem()))
        {
            if(matchTokens(result[FIRST_WAY].getWayItem(), i))
            {
                return true;
            }
        }
                   
        //Si hemos llegado aqui es porque tenemos que resolver algunos caminos
        //La lista tokens contendra Items por prioridad de menor a mayor que iremos resolviendo
        java.util.List<Way> tokens = new LinkedList();
        java.util.List<Way> waysAdded = new LinkedList();
        
        //Añadiremos los caminos que habiamos calculado siempre que no sean tokens
        if(isOptional(result[SECOND_WAY].getWayItem()) || isList(result[SECOND_WAY].getWayItem()))
        {
                 addToBag(result[SECOND_WAY], tokens, waysAdded);
        }
        if(isOptional(result[FIRST_WAY].getWayItem()) || isList(result[FIRST_WAY].getWayItem()))
        {
                 addToBag(result[FIRST_WAY], tokens, waysAdded);
        }
        
        Way[] partialResult = null;
        while(!tokens.isEmpty())
        {
            Way wayToResolve = tokens.get(tokens.size()-1);
            if(isToken(wayToResolve.getWayItem()))
            {
                if(matchTokens(wayToResolve.getWayItem(), i))
                {
                    return true;
                }
                else
                    tokens.remove(tokens.size()-1);
            }
            else
            {
                tokens.remove(tokens.size()-1);
                if(wayToResolve.getDirection() == Way.DirectionWay.UP)
                    partialResult = next(wayToResolve.getWayItem() , TypeOfAccess.ACCESSED );
                else if((wayToResolve.getDirection() != null))
                    partialResult = next(wayToResolve.getWayItem() , TypeOfAccess.NOT_ACCESSED );
                else
                    assert(false);
                
                addToBag(partialResult[SECOND_WAY], tokens, waysAdded);
                addToBag(partialResult[FIRST_WAY], tokens, waysAdded);
            }
        }
        return false;
    }

    @Override
    public boolean isPrevious(Item i) {
//        if(!isToken(i))
//            throw new IllegalArgumentException("El item no es un Token");
        
        if(lastNode == null || lastNode == getRootIteratorNode())
            throw new IllegalStateException("no hay previous!!");

        Way[] resultWays = null;
        if(lastNode == null)
            throw new IllegalStateException("No se hizo next antes de previous");
        else if(stateGT == StateGoTo.NOT_ACTIVE)
        {
            resultWays = previous(lastNode, TypeOfAccess.NOT_ACCESSED);
        }
        else if(stateGT == StateGoTo.GOTO)
        {
            resultWays = previous(lastNode,TypeOfAccess.ACCESSED);
        }
        else if(stateGT == StateGoTo.GOTONEXT)   
        {
            resultWays = previous(lastNode,TypeOfAccess.NOT_ACCESSED);
        }
        else if (stateGT == StateGoTo.GOTOPREVIOUS)   
        {
            resultWays = previous(lastNode,TypeOfAccess.ACCESSED);        
        }
        
        Way[] result = resultWays;
        //Normalmente result tendrá tokens asi que le preguntamos directamente:
        if(isToken(result[FIRST_WAY].getWayItem()))
        {
            if(matchTokens(result[FIRST_WAY].getWayItem(), i))
            {
                return true;
            }
        }
                   
        //Si hemos llegado aqui es porque tenemos que resolver algunos caminos
        //La lista tokens contendra Items por prioridad de menor a mayor que iremos resolviendo
        java.util.List<Way> tokens = new LinkedList();
        java.util.List<Way> waysAdded = new LinkedList();
        
        //Añadiremos los caminos que habiamos calculado siempre que no sean tokens
        if(isOptional(result[SECOND_WAY].getWayItem()) || isList(result[SECOND_WAY].getWayItem()))
        {
                 addToBag(result[SECOND_WAY], tokens, waysAdded);
        }
        if(isOptional(result[FIRST_WAY].getWayItem()) || isList(result[FIRST_WAY].getWayItem()))
        {
                 addToBag(result[FIRST_WAY], tokens, waysAdded);
        }
        
        Way[] partialResult = null;
        while(!tokens.isEmpty())
        {
            Way wayToResolve = tokens.get(tokens.size()-1);
            if(isToken(wayToResolve.getWayItem()))
            {
                if(matchTokens(wayToResolve.getWayItem(), i))
                {
                    return true;
                }
                else
                    tokens.remove(tokens.size()-1);
            }
            else
            {
                tokens.remove(tokens.size()-1);
                if(wayToResolve.getDirection() == Way.DirectionWay.UP)
                    partialResult = previous(wayToResolve.getWayItem() , TypeOfAccess.ACCESSED );
                else if((wayToResolve.getDirection() != null))
                    partialResult = previous(wayToResolve.getWayItem() , TypeOfAccess.NOT_ACCESSED );
                else
                    assert(false);
                
                addToBag(partialResult[SECOND_WAY], tokens, waysAdded);
                addToBag(partialResult[FIRST_WAY], tokens, waysAdded);
                
            }
        }
        return false;
    }
    
    
    @Override
    public boolean isPreviousAndPrevious(Item i) {
       
//        if(!isToken(i))
//            throw new IllegalArgumentException("El item no es un Token");
        
        if(lastNode == null || lastNode == getRootIteratorNode())
            throw new IllegalStateException("no hay previous!!");

        Way[] resultWays = null;
        if(lastNode == null)
            throw new IllegalStateException("No se hizo next antes de previous");
        else if(stateGT == StateGoTo.NOT_ACTIVE)
        {
            resultWays = previous(lastNode, TypeOfAccess.NOT_ACCESSED);
        }
        else if(stateGT == StateGoTo.GOTO)
        {
            resultWays = previous(lastNode,TypeOfAccess.ACCESSED);
        }
        else if(stateGT == StateGoTo.GOTONEXT)   
        {
            resultWays = previous(lastNode,TypeOfAccess.NOT_ACCESSED);
        }
        else if (stateGT == StateGoTo.GOTOPREVIOUS)   
        {
            resultWays = previous(lastNode,TypeOfAccess.ACCESSED);        
        }
        
        Way[] result = resultWays;
        //Normalmente result tendrá tokens asi que le preguntamos directamente:
        if(isToken(result[FIRST_WAY].getWayItem()))
        {
            if(matchTokens(result[FIRST_WAY].getWayItem(), i))
            {
                goToPrevious(result[FIRST_WAY].getWayItem());
                return true;
            }
        }
                   
        //Si hemos llegado aqui es porque tenemos que resolver algunos caminos
        //La lista tokens contendra Items por prioridad de menor a mayor que iremos resolviendo
        java.util.List<Way> tokens = new LinkedList();
        java.util.List<Way> waysAdded = new LinkedList();
        
        //Añadiremos los caminos que habiamos calculado siempre que no sean tokens
        if(isOptional(result[SECOND_WAY].getWayItem()) || isList(result[SECOND_WAY].getWayItem()))
        {
                 addToBag(result[SECOND_WAY], tokens, waysAdded);
        }
        if(isOptional(result[FIRST_WAY].getWayItem()) || isList(result[FIRST_WAY].getWayItem()))
        {
                 addToBag(result[FIRST_WAY], tokens, waysAdded);
        }
        
        Way[] partialResult = null;
        while(!tokens.isEmpty())
        {
            Way wayToResolve = tokens.get(tokens.size()-1);
            if(isToken(wayToResolve.getWayItem()))
            {
                if(matchTokens(wayToResolve.getWayItem(), i))
                {
                    goToPrevious(wayToResolve.getWayItem());
                    return true;
                }
                else
                    tokens.remove(tokens.size()-1);
            }
            else
            {
                tokens.remove(tokens.size()-1);
                if(wayToResolve.getDirection() == Way.DirectionWay.UP)
                    partialResult = previous(wayToResolve.getWayItem() , TypeOfAccess.ACCESSED );
                else if((wayToResolve.getDirection() != null))
                    partialResult = previous(wayToResolve.getWayItem() , TypeOfAccess.NOT_ACCESSED );
                else
                    assert(false);
                
                addToBag(partialResult[SECOND_WAY], tokens, waysAdded);
                addToBag(partialResult[FIRST_WAY], tokens, waysAdded);
                
            }
        }
        return false;
    }
    @Override
    public java.util.List<T> previousAll() {
        
        Way[] result;
        if(lastNode == null)
            result = previous(getRootIteratorNode());
        else
            result = previous(lastNode);      
                   
        //Si hemos llegado aqui es porque tenemos que resolver algunos caminos
        //La lista tokens contendra Items por prioridad de menor a mayor que iremos resolviendo
        java.util.List<Way> tokens = new LinkedList();
        java.util.List<Way> waysAdded = new LinkedList();
        java.util.List<T> resultTokens = new LinkedList();
        
        //Añadiremos los caminos que habiamos calculado siempre que no sean tokens
        if(isOptional(result[SECOND_WAY].getWayItem()) || isList(result[SECOND_WAY].getWayItem()))
        {
                 addToBag(result[SECOND_WAY], tokens, waysAdded);
        }
        if(isOptional(result[FIRST_WAY].getWayItem()) || isList(result[FIRST_WAY].getWayItem()))
        {
                 addToBag(result[FIRST_WAY], tokens, waysAdded);
        }
        
        Way[] partialResult = null;
        while(!tokens.isEmpty())
        {
            Way wayToResolve = tokens.get(tokens.size()-1);
            if(isToken(wayToResolve.getWayItem()))
            {
                resultTokens.add((T)wayToResolve.getWayItem());
                tokens.remove(tokens.size()-1);
            }
            else
            {
                tokens.remove(tokens.size()-1);
                if(wayToResolve.getDirection() == Way.DirectionWay.UP)
                    partialResult = previous(wayToResolve.getWayItem() , TypeOfAccess.ACCESSED );
                else if((wayToResolve.getDirection() != null))
                    partialResult = previous(wayToResolve.getWayItem() , TypeOfAccess.NOT_ACCESSED );
                else
                    assert(false);
                
                addToBag(partialResult[SECOND_WAY], tokens, waysAdded);
                addToBag(partialResult[FIRST_WAY], tokens, waysAdded);
                
            }
        }
        return resultTokens;
    }
     
    
    @Override
    @Deprecated
    public java.util.List<T> nextAll() {
        
        Way[] result;
        if(lastNode == null)
            result = next(getRootIteratorNode());
        else
            result = next(lastNode);     
                   
        //Si hemos llegado aqui es porque tenemos que resolver algunos caminos
        //La lista tokens contendra Items por prioridad de menor a mayor que iremos resolviendo
        java.util.List<Way> tokens = new LinkedList();
        java.util.List<Way> waysAdded = new LinkedList();
        java.util.List<T> resultTokens = new LinkedList();
        
        //Añadiremos los caminos que habiamos calculado siempre que no sean tokens
        if(isOptional(result[SECOND_WAY].getWayItem()) || isList(result[SECOND_WAY].getWayItem()))
        {
                 addToBag(result[SECOND_WAY], tokens, waysAdded);
        }
        if(isOptional(result[FIRST_WAY].getWayItem()) || isList(result[FIRST_WAY].getWayItem()))
        {
                 addToBag(result[FIRST_WAY], tokens, waysAdded);
        }
        
        if(isToken(result[SECOND_WAY].getWayItem()))
        {
            resultTokens.add((T) result[SECOND_WAY].getWayItem());
        }
        
        if(isToken(result[FIRST_WAY].getWayItem()))
        {
             resultTokens.add((T) result[FIRST_WAY].getWayItem());
        }
        
        Way[] partialResult = null;
        while(!tokens.isEmpty())
        {
            Way wayToResolve = tokens.get(tokens.size()-1);
            if(isToken(wayToResolve.getWayItem()))
            {
                resultTokens.add((T)wayToResolve.getWayItem());
                tokens.remove(tokens.size()-1);
            }
            else
            {
                tokens.remove(tokens.size()-1);
                if(wayToResolve.getDirection() == Way.DirectionWay.UP)
                    partialResult = next(wayToResolve.getWayItem() , TypeOfAccess.ACCESSED );
                else if((wayToResolve.getDirection() != null))
                    partialResult = next(wayToResolve.getWayItem() , TypeOfAccess.NOT_ACCESSED );
                else
                    assert(false);
                
                addToBag(partialResult[SECOND_WAY], tokens, waysAdded);
                addToBag(partialResult[FIRST_WAY], tokens, waysAdded);
                
            }
        }
        return resultTokens;
    }
    
    @Override
    public Way[] previous(Item i, TypeOfAccess t) {
        
        Way[] resultWay = previous(i);
        exchangeWays(resultWay, i, t);
        return resultWay;
    }
    
    @Override
    public Way[] previous(SMTreeNode<Item> n, TypeOfAccess t) {
        Way[] resultWay = previous(n);
        exchangeWays(resultWay, n.getObject(), t);
        return resultWay;     
    }

    @Override
    public Way[] next(Item i, TypeOfAccess t) {
        
        Way[] resultWays = next(i);
        exchangeWays(resultWays, i, t);
        return resultWays;
    }
    
    @Override
    public Way[] next(SMTreeNode<Item> n, TypeOfAccess t) {
        Way[] resultWays = next(n);
        exchangeWays(resultWays, n.getObject(), t);
        return resultWays;
    }
    
    
    protected void exchangeWays(Way[] road, Item i, TypeOfAccess t)
    {
        if(isList(i))
        {
            if(t == TypeOfAccess.ACCESSED)
            {
                // Si la lista ha sido accedida le damos prioridad a salir de la lista
                Way tmp = road[SECOND_WAY];
                road[SECOND_WAY] = road[FIRST_WAY];
                road[FIRST_WAY] = tmp;         
            }
            else
                //Si la lista no ha sido accedida obligamos a que escoja un unico camino
            {
                //Si el camino prioritario es nulo, entonces nos quedamos con la prioridad menor
                if(road[FIRST_WAY] == null)
                    road[FIRST_WAY] = road[SECOND_WAY];
                
                //y como nos quedamos con la prioridad maxima, adios a la minima:
                road[SECOND_WAY] = new Way();
            }
        }
        else if(isOptional(i) && t == TypeOfAccess.ACCESSED)
        {
            // Si la opcionalidad ha sido accedida, no entramos
            road[FIRST_WAY] = road[SECOND_WAY];
            road[SECOND_WAY] = new Way();        

        }else if(isTuple(i) && t == TypeOfAccess.ACCESSED)
        {
            // Si la opcionalidad ha sido accedida, no entramos
            road[FIRST_WAY] = road[SECOND_WAY];
            road[SECOND_WAY] = new Way();        

        }
        else if(isToken(i) && t == TypeOfAccess.ACCESSED)
        {
            road[SECOND_WAY] = new Way();
        }
        else if(isToken(i) && t == TypeOfAccess.NOT_ACCESSED)
        {
            road[FIRST_WAY] = road[SECOND_WAY];
            road[SECOND_WAY] = new Way();
        }
        
        //Si el camino prioritario es nulo, entonces nos quedamos con la prioridad menor
        if(road[FIRST_WAY] == null)
                    road[FIRST_WAY] = road[SECOND_WAY];
    }
    
    protected void addToBag(Way w, java.util.List<Way> wayTokens, java.util.List<Way> waysAdded){
        if(w.getWayItem()!=null && !waysAdded.contains(w)){
            wayTokens.add(w);
            waysAdded.add(w);
        }
    }
    
    @Override
    public Way[] next(Item i) {
        
        return next(getNode(i));
    }

    @Override
    public Way[] previous(Item i) {
        return previous(getNode(i));
    }
    
    @Override
    public int nextIndex() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int previousIndex() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void set(T arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void add(T arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Iterator iterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    protected boolean isList(Item i)
    {
        return i instanceof smrunner.node.List; 
    }
    
    protected boolean isOptional(Item i)
    {
        return i instanceof Optional; 
    }

    protected boolean isTuple(Item i)
    {
        return i instanceof Tuple; 
    }
    
    protected boolean isToken(Item i)
    {
        return i instanceof Token; 
    }
    
    protected  boolean matchTokens(Item t1, Item t2)
    {
        return t1.match(t2);
    }
    
    
    public java.util.List<Way> nextAllWays() {
        
        Way[] partialResult = null;
    
        if(lastNode == null)
        {
            stateGT = StateGoTo.NOT_ACTIVE;
            partialResult = next(getRootIteratorNode(),TypeOfAccess.NOT_ACCESSED);
        }
        else if(stateGT == StateGoTo.NOT_ACTIVE)
        {
            partialResult = next(lastNode, TypeOfAccess.ACCESSED);
        }
        else if(stateGT == StateGoTo.GOTO)
        {
            stateGT = StateGoTo.NOT_ACTIVE;
            partialResult = next(lastNode,TypeOfAccess.NOT_ACCESSED);
        }
        else if(stateGT == StateGoTo.GOTONEXT)   
        {
            stateGT = StateGoTo.NOT_ACTIVE;
            partialResult = next(lastNode,TypeOfAccess.ACCESSED);
        }
        else if (stateGT == StateGoTo.GOTOPREVIOUS)   
        {
            stateGT = StateGoTo.NOT_ACTIVE;
            partialResult = previous(lastNode,TypeOfAccess.ACCESSED);
            partialResult = next(partialResult[FIRST_WAY].getWayItem(),TypeOfAccess.NOT_ACCESSED);
        }
        
        
        
        
        
//        if(lastNode == null)
//            partialResult = next(getRootIteratorNode());
//        else
//            partialResult = next(lastNode);     
                   
        
        
        
        //Si hemos llegado aqui es porque tenemos que resolver algunos caminos
        //La lista tokens contendra Items por prioridad de menor a mayor que iremos resolviendo
        java.util.List<Way> tokens = new LinkedList();
        java.util.List<Way> waysAdded = new LinkedList();
        java.util.List<Way> resultTokens = new LinkedList();
        
        //Añadiremos los caminos que habiamos calculado siempre que no sean tokens
        if(isOptional(partialResult[SECOND_WAY].getWayItem()) || isList(partialResult[SECOND_WAY].getWayItem()))
        {
            partialResult[SECOND_WAY].addYourself();
            addToBag(partialResult[SECOND_WAY], tokens, waysAdded);
        }
        
        if(isOptional(partialResult[FIRST_WAY].getWayItem()) || isList(partialResult[FIRST_WAY].getWayItem()))
        {
            partialResult[FIRST_WAY].addYourself();
            addToBag(partialResult[FIRST_WAY], tokens, waysAdded);
        }
        
        if(isToken(partialResult[SECOND_WAY].getWayItem()))
        {
            resultTokens.add(partialResult[SECOND_WAY]);
        }
        
        if(isToken(partialResult[FIRST_WAY].getWayItem()))
        {
             resultTokens.add(partialResult[FIRST_WAY]);
        }
        
        
        partialResult = null;
        while(!tokens.isEmpty())
        {
            Way wayToResolve = tokens.remove(tokens.size()-1);
            if(isToken(wayToResolve.getWayItem()))
            { 
                resultTokens.add(wayToResolve);
            }
            else
            {
                
                if(wayToResolve.getDirection() == Way.DirectionWay.UP)
                    partialResult = next(wayToResolve.getWayItem() , TypeOfAccess.ACCESSED );
                else if((wayToResolve.getDirection() != null))
                    partialResult = next(wayToResolve.getWayItem() , TypeOfAccess.NOT_ACCESSED );
                else
                    assert(false);                
                
                if(isOptional(partialResult[SECOND_WAY].getWayItem()) || isList(partialResult[SECOND_WAY].getWayItem()))
                {                 
                    partialResult[SECOND_WAY].addYourself(); 
                }

                if(isOptional(partialResult[FIRST_WAY].getWayItem()) || isList(partialResult[FIRST_WAY].getWayItem()))
                {   
                        partialResult[FIRST_WAY].addYourself();
                }
                
                partialResult[SECOND_WAY].add(wayToResolve);
                partialResult[FIRST_WAY].add(wayToResolve);
                
                addToBag(partialResult[SECOND_WAY], tokens, waysAdded);
                addToBag(partialResult[FIRST_WAY], tokens, waysAdded);
            }
        }
        return resultTokens;
    }
}
