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


public class ForwardTokenIterator<T> extends EdibleIterator<T>
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
    public void init(Object objectToInicializeIterator){
    }
    
    @Override
    public void goTo(Item i) {
        stateGT = StateGoTo.GOTO;
        lastNode = getNode(i);
    }

    @Override
    public void goToNext(Item i) {
        if(!(i instanceof Token))
            throw new IllegalStateException("Solo se puede hacer GoToNext a Tokens");
        
//        Item nextAux = next(i)[MAX_PRIORITY];
//        if(nextAux == null )
//            throw new IllegalAccessException("No hay siguiente para GoToNext()");
//        
        stateGT = StateGoTo.GOTONEXT;
        lastNode = getNode(i);
    }

    @Override
    public void goToPrevious(Item i) {
        if(!(i instanceof Token))
            throw new IllegalStateException("Solo se puede hacer GoToPrevious a Tokens");
        
//        Item previousAux = previous(i)[MAX_PRIORITY];
//        if(previousAux == null )
//            throw new IllegalAccessException("No hay previous para GoToPrevious()");
        
        stateGT = StateGoTo.GOTOPREVIOUS;
        lastNode = getNode(i);
    }

    @Override
    public boolean isNext(Item i) {
        
        if(!isToken(i))
            throw new IllegalArgumentException("El item no es un Token");
        
        if(lastNode == null)
            lastNode = getRootIteratorNode();
        Way[] result = next(lastNode);      
        
        //Normalmente result tendrá tokens asi que le preguntamos directamente:
        if(isToken(result[FIRST_WAY].getWayItem()))
        {
            if(matchTokens(result[FIRST_WAY].getWayItem(), i))
            {
                goToNext(result[FIRST_WAY].getWayItem());
                return true;
            }
            
            // Si el MAX_PRIORITY no hace match probamos con el MIN_PRIORITY
            if(isToken(result[SECOND_WAY].getWayItem()))
            {
                if(matchTokens(result[SECOND_WAY].getWayItem(), i)) 
                {
                    goToNext(result[SECOND_WAY].getWayItem());
                    return true;
                }
                else return false;
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
    
    private void addToBag(Way w, java.util.List<Way> wayTokens, java.util.List<Way> waysAdded){
        if(w.getWayItem()!=null && !waysAdded.contains(w)){
            wayTokens.add(w);
            waysAdded.add(w);
        }
    }

    @Override
    public boolean isPrevious(Item i) {
       
        if(!isToken(i))
            throw new IllegalArgumentException("El item no es un Token");
        
        if(lastNode == null || lastNode == getRootIteratorNode())
            throw new IllegalStateException("no hay previous!!");
        
        Way[] result = previous(lastNode);      
        
        //Normalmente result tendrá tokens asi que le preguntamos directamente:
        if(isToken(result[FIRST_WAY].getWayItem()))
        {
            if(matchTokens(result[FIRST_WAY].getWayItem(), i))
            {
                goToPrevious(result[FIRST_WAY].getWayItem());
                return true;
            }
            
            // Si el MAX_PRIORITY no hace match probamos con el MIN_PRIORITY
            if(isToken(result[SECOND_WAY].getWayItem()))
            {
                if(matchTokens(result[SECOND_WAY].getWayItem(), i)) 
                {
                    goToPrevious(result[SECOND_WAY].getWayItem());
                    return true;
                }
                else return false;
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
    public Way[] next(Item i) {
        
        return next(getNode(i));
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
                
            resultWay[SECOND_WAY] = new Way();
        }
        
        return resultWay;
    }

    @Override
    public Way[] previous(Item i) {
        return previous(getNode(i));
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
                
            resultWay[SECOND_WAY] = new Way();
        }
        
        return resultWay;
    }
    
    private void exchangeWays(Way[] road, Item i, TypeOfAccess t)
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
        
        //Si el camino prioritario es nulo, entonces nos quedamos con la prioridad menor
        if(road[FIRST_WAY] == null)
                    road[FIRST_WAY] = road[SECOND_WAY];
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


    public boolean hasNext() {
        SMTreeNode aux = getRootIteratorNode();
        
        while(aux.getLastChild()!=null)
            aux = aux.getLastChild();
        
        if(aux != lastNode)
            return true;
        return false;
    }

    public T next() {
        
        Way[] resultWays = null;
        if(lastNode == null)
            resultWays = next(getRootIteratorNode(),TypeOfAccess.NOT_ACCESSED);
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
        
        lastNode =  getNode(resultWays[FIRST_WAY].getWayItem());
        assert(lastNode != null);
        return  (T)resultWays[FIRST_WAY].getWayItem();
    }
    
    @Override
    public java.util.List<T> nextAll() {
        
        if(lastNode == null)
            lastNode = getRootIteratorNode();
        
        Way[] result = next(lastNode);      
                   
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
        
        
        while(!isToken(resultWays[FIRST_WAY].getWayItem()))
        {
            if(resultWays[FIRST_WAY].getDirection() != Way.DirectionWay.UP)
                resultWays = previous(resultWays[FIRST_WAY].getWayItem(),TypeOfAccess.NOT_ACCESSED);
            else
                resultWays = previous(resultWays[FIRST_WAY].getWayItem(),TypeOfAccess.ACCESSED);
        
        }    
        
        lastNode =  getNode(resultWays[FIRST_WAY].getWayItem());
        return  (T)resultWays[FIRST_WAY].getWayItem();

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

    public void set(T arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void add(T arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Iterator iterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private boolean isList(Item i)
    {
        return i instanceof smrunner.node.List; 
    }
    
    private boolean isOptional(Item i)
    {
        return i instanceof Optional; 
    }

    private boolean isTuple(Item i)
    {
        return i instanceof Tuple; 
    }
    
    private boolean isToken(Item i)
    {
        return i instanceof Token; 
    }
    
    private boolean matchTokens(Item t1, Item t2)
    {
        return t1.match(t2);
    }
}

