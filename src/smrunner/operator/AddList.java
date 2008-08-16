package smrunner.operator;

import SMTree.SMTreeNode;
import SMTree.utils.Enclosure;
import smrunner.iterator.BackwardTokenIterator;
import smrunner.iterator.EdibleIterator;
import smrunner.iterator.ForwardTokenIterator;
import smrunner.iterator.webPageBackwardIterator;
import smrunner.iterator.webPageForwardIterator;
import smrunner.node.Item;
import smrunner.node.List;
import smrunner.node.Text;
import smrunner.node.Token;
import smrunner.node.Tuple;
import smrunner.utils.Edible;
import smrunner.utils.Mismatch; 
import smrunner.utils.Repair; 
import smrunner.utils.Sample;
import smrunner.utils.StateRepair;
import smrunner.utils.Wrapper;

/**
 *  Class addList
 */
// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.31ACF038-1A60-CDE1-AF3D-8E99E0215D79]
// </editor-fold> 
public class AddList extends IOperator 
{
    AddList(WebPageOperator where) 
    {
        super(where);  
    }

    @Override
    public Repair apply(Mismatch m, DirectionOperator d) 
    {
        Edible s = m.getSample();
        Edible w = m.getWrapper();
        Item t = m.getToken();
        Item n = m.getNode();
        EdibleIterator itW = null;
        EdibleIterator itS = null;
        
        Repair rep = new Repair(m);
        
        Token firstTokenList=null, lastTokenList=null;
        Token firstTokenSquare=null, lastTokenSquare=null;
        Token lastDelim=null;
        
        if(d == DirectionOperator.DOWNWARDS)
        {
            itW = w.iterator(ForwardTokenIterator.class);
            if(s instanceof Sample) 
                itS = s.iterator(webPageForwardIterator.class);
            else
                itS = s.iterator(ForwardTokenIterator.class);
        }
        else if(d == DirectionOperator.UPWARDS)
        {
            itW = w.iterator(BackwardTokenIterator.class);
            if(s instanceof Sample) 
                itS = s.iterator(webPageBackwardIterator.class);
            else
                itS = s.iterator(BackwardTokenIterator.class);
        }
        
        if(super.where == WebPageOperator.WRAPPER)
        {
            //buscamos squareW
            
            firstTokenSquare = (Token) n;
            firstTokenList= firstTokenSquare;
            
            itS.goTo(t);
            lastDelim = (Token) itS.previous();
            if(lastDelim == null)
            {
                rep.setState(StateRepair.FAILED);
                return rep;
            }
   
            lastTokenSquare =  w.searchWellFormed(lastDelim, Enclosure.ENCLOSED, firstTokenSquare, Enclosure.ENCLOSED, d);

            if(lastTokenSquare == null)
            {
                rep.setState(StateRepair.FAILED);
                return rep;
            }

            //Si hemos llegado aquí es porque hemos encontrado una ocurrencia 
            //que delimita un código bien formado:
            lastTokenList = lastTokenSquare;

            
            // Ya tenemos definido la zona de squareW, ahora le creamos un wrapper        
            Wrapper squareW = w.cloneSubWrapper(firstTokenSquare, lastTokenSquare, new Tuple(), d);
                             
            // Comemos hacia arriba
            Item whereEat; 
            itW.goTo(firstTokenList);
            whereEat = (Item) itW.previous();
            
            if(whereEat == null) //Significa que no había previous, o sea que hemos fallado:
            {
                rep.setState(StateRepair.FAILED);
                return rep;
            }
            
            Item whatEaten = null;
            if(d == DirectionOperator.DOWNWARDS)
                whatEaten = squareW.eatOneSquare(w, whereEat, DirectionOperator.UPWARDS);
            else
                whatEaten = squareW.eatOneSquare(w, whereEat, DirectionOperator.DOWNWARDS);
            
            
            // para asegurarnos de que es un plus al menos tengo que comerme
            // un elemento de la lista, sino falla la reparacion:
            if(whatEaten == null)
            {
                rep.setState(StateRepair.FAILED);
                return rep;
            }

            Item whatEatenTemp = null;
            while( whatEaten != null)
            {
                firstTokenList = (Token) whatEaten;
                itW.goTo((Item) whatEaten);
                whereEat = (Item) itW.previous();
                if(whereEat == null) //Habíamos terminado de comer el w
                    break;
                whatEatenTemp = whatEaten;
                if(d == DirectionOperator.DOWNWARDS)
                    whatEaten = squareW.eatOneSquare(w, whereEat, DirectionOperator.UPWARDS);
                else
                    whatEaten = squareW.eatOneSquare(w, whereEat, DirectionOperator.DOWNWARDS);
                if(whatEaten == whatEatenTemp)
                    break;
            }
                
           // Ahora comemos hacia abajo
           itW.goTo(lastTokenList);
           itW.next();
           whereEat = (Item) itW.next();
           if(whereEat != null) //No hay nada que comer abajo
           {

               whatEaten = squareW.eatOneSquare(w, whereEat, d);

                while( whatEaten != null)
                {
                    lastTokenList = (Token) whatEaten;
                    itW.goTo((Item) whatEaten);
                    itW.next();
                    whereEat = (Item) itW.next();
                    if(whereEat == null)
                    {
                        SMTreeNode<Item> nodo = ((Wrapper)w).getTree().getNode(whatEaten);
                        whereEat = (Item) nodo.getParent().getObject();
                    }
                    whatEatenTemp = whatEaten;
                    whatEaten = squareW.eatOneSquare(w, whereEat, d);
                    if(whatEaten == whatEatenTemp)
                        break;
                }
           }
           
            squareW.getTree().setRootObject(new List());

//            if(DirectionOperator.UPWARDS == d)
//            {
//                Token temp = lastTokenList;
//                lastTokenList = firstTokenList;
//                firstTokenList = temp;
//            }

            rep.setReparator(squareW);
            rep.setInitialItem(firstTokenList);
            rep.setFinalItem(lastTokenList);
            rep.setState(StateRepair.SUCESSFULL);
            rep.setToRepair((Wrapper) w);
            rep.setIndexSample((Token) t);
            itW.goToNext(lastTokenList);
            rep.setIndexWrapper((Item) itW.next());
            
        }
        else if(where == WebPageOperator.SAMPLE)
        {
            //buscamos squareS        
            firstTokenSquare = (Token) t;
            itW.goTo(n);
            lastTokenList = (Token) itW.previous();
            
            itS.goTo(t);
            lastDelim = (Token) itS.previous();
            
            lastTokenSquare =  s.searchWellFormed(lastDelim,Enclosure.ENCLOSED, firstTokenSquare, Enclosure.ENCLOSED, d);

            if(lastTokenSquare == null)
            {
                rep.setState(StateRepair.FAILED);
                return rep;
            }
                        
            // Ya tenemos definido la zona de squareS, ahora le creamos un wrapper        
            Wrapper squareS = s.cloneSubWrapper(firstTokenSquare, lastTokenSquare, new Tuple(), d);
                             
            // Comemos hacia arriba en el wrapper
            Item whereEat;   
            itW.goTo(n);
            whereEat = lastTokenList;

            if(whereEat == null) //Significa que no había previous, o sea que hemos fallado:
            {
                rep.setState(StateRepair.FAILED);
                return rep;
            }
            Item whatEaten;

            if(d == DirectionOperator.DOWNWARDS)
                whatEaten = squareS.eatOneSquare(w, whereEat, DirectionOperator.UPWARDS);
            else
                whatEaten = squareS.eatOneSquare(w, whereEat, DirectionOperator.DOWNWARDS);
            
            // para asegurarnos de que es un plus al menos tengo que comerme
            // un elemento de la lista, sino falla la reparacion:
            if(whatEaten == null)
            {
                rep.setState(StateRepair.FAILED);
                return rep;
            }

            Item whatEatenTemp = null;
            while(whatEaten != null)
            {
                firstTokenList = (Token) whatEaten;
                itW.goTo(whatEaten);
                whereEat =  (Item) itW.previous();
                if(whereEat == null) //Habíamos terminado de comer el w
                    break;
                whatEatenTemp = whatEaten;
                if(d == DirectionOperator.DOWNWARDS)
                    whatEaten = squareS.eatOneSquare(w, whereEat, DirectionOperator.UPWARDS);
                else
                    whatEaten = squareS.eatOneSquare(w, whereEat, DirectionOperator.DOWNWARDS);
                if(whatEaten == whatEatenTemp)
                    break;
            }
                
           // Ahora comemos hacia abajo en el sample
           itS.goTo(lastTokenSquare);
           itS.next();
           whereEat = (Item) itS.next();
           Item lastEatenSuccess = (Item) whatEaten;

            if(whereEat != null) //No hay nada que comer abajo
           {


               whatEaten = squareS.eatOneSquare(s, whereEat, d);


                while(whatEaten != null)
                {
                    itS.goTo((Item) whatEaten);
                    itS.next();
                    whereEat = (Item) itS.next();
                    whatEatenTemp = whatEaten;
                    whatEaten = squareS.eatOneSquare(s, whereEat, d);
                    if(whatEaten == null || whatEaten == whatEatenTemp)
                        break;
                    lastEatenSuccess =  (Item) whatEaten;
                }
            }

           
            squareS.getTree().setRootObject(new List());

            rep.setReparator(squareS);
            rep.setInitialItem(firstTokenList);
            rep.setFinalItem(lastTokenList);
            rep.setState(StateRepair.SUCESSFULL);
            rep.setToRepair((Wrapper) w);
            if(lastEatenSuccess == null)
            {
                // Este caso se da cuando no he conseguido comerme nada hacia abajo (solo hay un elemento mas en el Sample que en el Wrapper)
                rep.setIndexSample((Token) whereEat);
            }
            else
            {
                itS.goTo((Item) lastEatenSuccess);
                itS.next();
                rep.setIndexSample((Token) itS.next());
            }
            
            itW.goToNext(lastTokenList);
            rep.setIndexWrapper((Item) itW.next());
           
        }
    
        return rep;     
    }

}

