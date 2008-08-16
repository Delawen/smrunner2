package smrunner.operator;

import smrunner.utils.Sample;
import smrunner.utils.StateRepair;
import smrunner.utils.Mismatch;
import smrunner.utils.Wrapper;
import smrunner.utils.Repair;
import smrunner.iterator.BackwardTokenIterator;
import smrunner.iterator.EdibleIterator;
import smrunner.iterator.ForwardTokenIterator;
import smrunner.iterator.webPageBackwardIterator;
import smrunner.iterator.webPageForwardIterator;
import smrunner.node.Item;
import smrunner.node.Text;
import smrunner.node.Token;
import smrunner.node.Variable;
import smrunner.utils.Edible;

/**
 *  Class addVariable
 * 
 * Implements the addVariable operation. That means: substitute a Text with a Variable.
 */
// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.80EF1EB2-DF19-D615-0B7A-1D3D66A616F8]
// </editor-fold> 
public class AddVariable extends IOperator 
{
    public AddVariable(WebPageOperator where)
    {
        super(where);
    }

    @Override
    public Repair apply(Mismatch m, DirectionOperator d) 
    {
        Repair reparacion = new Repair(m);
        Item n = m.getNode();
        Token t = m.getToken();
        if(!(n instanceof Text) || !(t instanceof Text || t instanceof Variable))
        {
            reparacion.setState(StateRepair.FAILED);
        }
        else
        {
            //finalItem:
            reparacion.setFinalItem(n);
            
            //initialItem:
            reparacion.setInitialItem(n);
            
            //indexSample:
            Edible s = m.getSample();
            EdibleIterator it;
            EdibleIterator itW;
            if(d == DirectionOperator.DOWNWARDS)
            {
                if(s instanceof Sample) 
                    it = s.iterator(webPageForwardIterator.class);
                else
                    it = s.iterator(ForwardTokenIterator.class);
                
                 itW = m.getWrapper().iterator(ForwardTokenIterator.class);
            }
            else
            {
                if(s instanceof Sample) 
                    it = s.iterator(webPageBackwardIterator.class);
                else
                    it = s.iterator(BackwardTokenIterator.class);
                
                itW = m.getWrapper().iterator(BackwardTokenIterator.class);
            }
            
            it.goTo(t);
            it.next();        
            reparacion.setIndexSample((Token)it.next());
            
            itW.goToNext(n);     
            reparacion.setIndexWrapper((Item)itW.next());
            //setState:
            reparacion.setState(StateRepair.SUCESSFULL);
            
            //reparator:
            reparacion.setReparator(new Wrapper(new Variable()));
        }
            
        return reparacion;
    }


}

