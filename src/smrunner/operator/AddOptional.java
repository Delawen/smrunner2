package smrunner.operator;

import SMTree.utils.Enclosure;
import java.util.logging.Level;
import java.util.logging.Logger;
import smrunner.iterator.BackwardTokenIterator;
import smrunner.iterator.EdibleIterator;
import smrunner.iterator.ForwardTokenIterator;
import smrunner.iterator.webPageBackwardIterator;
import smrunner.iterator.webPageForwardIterator;
import smrunner.utils.Mismatch; 
import smrunner.utils.Repair; 
import smrunner.utils.Sample;
import smrunner.utils.StateRepair;
import smrunner.utils.Wrapper;
import smrunner.node.Item;
import smrunner.node.Optional;
import smrunner.node.Token;
import smrunner.utils.Edible;

/**
 *  Class addHook
 */
// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.E210968F-FE0F-DA54-A32E-77F957F292B1]
// </editor-fold> 
public class AddOptional extends IOperator 
{

    AddOptional(WebPageOperator where) 
    {
        super(where);
    }

    @Override
    public Repair apply(Mismatch m, DirectionOperator d) {
       
        /**
         * Hook sólo puede tener un tipo de reparaciones internas: las del search.
         */
       
        Edible s = m.getSample();
        Wrapper w = m.getWrapper();
        Token t = m.getToken();
        Token n = (Token) m.getNode();
        EdibleIterator<Token> itW = null;
        EdibleIterator<Token> itS = null;
        
        Repair rep = new Repair(m);
        rep.setState(StateRepair.BUILDING);
        
        Token firstTokenOptional=null, lastTokenOptional=null;
        
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

            // Buscamos el token que aparecera justo detras de la supuesta opcionalidad
            lastTokenOptional =  w.searchWellFormed(t, Enclosure.NOT_ENCLOSED, n, Enclosure.ENCLOSED, d);

            // Si no lo hemos encontrado entonces
            //  no se puede crear reparacion con addoptional en el wrapper
            if(lastTokenOptional == null)
            {
                rep.setState(StateRepair.FAILED);
                return rep;
            }
            
            // lastTokenOptional contiene al nodo posterior, asi que nos quedamos con el ultimo de la opcionalidad
            itW.goTo(lastTokenOptional);
            lastTokenOptional = itW.previous();
            firstTokenOptional = n;
            
            //colocamos los parámetros de la reparación
            
            rep.setInitialItem(firstTokenOptional);
            rep.setFinalItem(lastTokenOptional);
            rep.setState(StateRepair.SUCESSFULL);
            rep.setIndexSample(t);
            rep.setFinalEnclosure(Enclosure.ENCLOSED);
            rep.setInitialEnclosure(Enclosure.ENCLOSED);
            itW.goToNext(lastTokenOptional);
            rep.setIndexWrapper(itW.next());

            
            Wrapper wrapperReparator = null;
            try {
                wrapperReparator = new Wrapper(w.cloneSubWrapper(firstTokenOptional, lastTokenOptional, new Optional(), d));
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(AddOptional.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            rep.setReparator(wrapperReparator);
            
        }
        else if(where == WebPageOperator.SAMPLE)
        {     
 
            // Buscamos el token que aparecera justo detra de la supuesta opcionalidad
            lastTokenOptional =  s.searchWellFormed((Token) n, Enclosure.NOT_ENCLOSED, t, Enclosure.ENCLOSED, d);

            // Si no lo hemos encontrado paramos de buscar
            // porque no se puede crear reparacion con addoptional en el wrapper
            if(lastTokenOptional == null)
            {
                rep.setState(StateRepair.FAILED);
                return rep;
            }

            // y ahora si que nos quedamos con el token ultimo de la opcionalidad
            itS.goTo(lastTokenOptional);
            lastTokenOptional = itS.previous();
            firstTokenOptional = t;
            
            itS.goTo(t);

            itW.goTo(n);
            rep.setInitialItem(itW.previous());
            rep.setInitialEnclosure(Enclosure.NOT_ENCLOSED);
            rep.setFinalItem(n);
            rep.setFinalEnclosure(Enclosure.NOT_ENCLOSED);
            rep.setState(StateRepair.SUCESSFULL);
            itS.goTo(lastTokenOptional);
            itS.next();
            rep.setIndexSample(itS.next());
            rep.setIndexWrapper(n);

            Wrapper wrapperReparator = s.cloneSubWrapper(firstTokenOptional, lastTokenOptional, new Optional(), d);
      
            rep.setReparator(wrapperReparator);   
        }
    
        return rep;     
    }
}

