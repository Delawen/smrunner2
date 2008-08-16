package smrunner.utils;

import java.util.Iterator;
import smrunner.iterator.EdibleIterator;
import SMTree.utils.Enclosure;
import SMTree.utils.Kinship;
import smrunner.iterator.ForwardTokenIterator;
import smrunner.iterator.BackwardTokenIterator;
import SMTree.iterator.SMTreeIterator;
import SMTree.*;
import SMTree.iterator.BackwardIterator;
import SMTree.iterator.ForwardIterator;
import java.util.LinkedList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.StyledEditorKit.BoldAction;
import smrunner.iterator.SMx;
import smrunner.iterator.TokenIterator;
import smrunner.iterator.Way;
import smrunner.iterator.Way;
import smrunner.node.*;
import smrunner.operator.DirectionOperator;
import smrunner.iterator.webPageBackwardIterator;
import smrunner.iterator.webPageForwardIterator;
import smrunner.node.Variable;
import smrunner.operator.Operator;

/**
 *  @author delawen
 */
// <editor-fold defaultstate="collapsed" desc=" UML Marker ">
// #[regen=yes,id=DCE.6DA19461-88AB-136F-23B7-1FB2AC471B20]
// </editor-fold>
public class Wrapper implements Edible, Iterable{

    SMTree<Item> treeWrapper;
    private EdibleIterator<Token> itWrapper;
    private EdibleIterator<Token> itSample;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker ">
    // #[regen=yes,id=DCE.4C4FEE21-B6CA-E719-277C-03EDB616EFAF]
    // </editor-fold>
    public Wrapper () {
        super();
        treeWrapper = new SMTree<Item>();
    }

    public Wrapper(Item rootItem)
    {
        this();
        treeWrapper.setRootObject(rootItem);
    }

    public Wrapper(Wrapper w) throws CloneNotSupportedException {
        treeWrapper = w.getTree().clone();
    }

    public Wrapper(Item rootItem,Item firstChildRoot)
    {
        this(rootItem);
        treeWrapper.addObject(firstChildRoot, treeWrapper.getRoot(), Kinship.CHILD);
    }

    public Wrapper(SMTree<Item> tree)
    {
        super();
        this.treeWrapper = tree;
    }

   public Wrapper(String regexp)
    {
        super();
        treeWrapper = new SMTree<Item>();

        Stack<Item> s = new Stack<Item>();
        Item lastParentItem = new Tuple();

        treeWrapper.setRootObject(lastParentItem);
        treeWrapper.addObject(new DOF(), treeWrapper.getRoot(), Kinship.CHILD);

        for(int i=0; i<regexp.length(); i++ )
        {
            if(regexp.charAt(i) == '(')
            {
                Item oldParent = lastParentItem;
                lastParentItem = s.push(new Tuple());
                treeWrapper.addObject(lastParentItem,treeWrapper.getNode(oldParent), Kinship.CHILD);

            }
            else if (regexp.charAt(i) == ')')
            {
                if(s.isEmpty())
                    throw new IllegalArgumentException("La expresión regular no está bien formada");

                if(regexp.charAt(i+1) == '+')
                {
                    i++;
                    Item auxItem = s.pop();
                    SMTreeNode auxNode = treeWrapper.getNode(auxItem);
                    treeWrapper.getMapa().remove(auxNode);
                    auxNode.setObject(new List());
                    if(auxItem == lastParentItem)
                        lastParentItem = (Item) auxNode.getObject();
                    treeWrapper.getMapa().add(auxNode);
                    if(lastParentItem != treeWrapper.getRootObject())
                        lastParentItem = treeWrapper.getNode(lastParentItem).getParent().getObject();
                }
                else if(regexp.charAt(i+1) == '?')
                {
                    i++;
                    Item auxItem = s.pop();
                    SMTreeNode auxNode = treeWrapper.getNode(auxItem);
                    treeWrapper.getMapa().remove(auxNode);
                    auxNode.setObject(new Optional());
                    if(auxItem == lastParentItem)
                        lastParentItem = (Item) auxNode.getObject();
                    treeWrapper.getMapa().add(auxNode);
                    if(lastParentItem != getTree().getRootObject())
                        lastParentItem = treeWrapper.getNode(lastParentItem).getParent().getObject();
                }
                else
                    continue;
            }
            else if(regexp.charAt(i) == '<')
            {
                int j = regexp.indexOf(">", i);
                if(j<0)
                    throw new IllegalArgumentException("La expresión regular no está bien formada");

                String tag = regexp.substring(i, j+1);

                treeWrapper.addObject(new Tag(tag), treeWrapper.getNode(lastParentItem), Kinship.CHILD);

                i = j;
            }
            else if(regexp.charAt(i) == '#')
            {
                treeWrapper.addObject(new Variable(), treeWrapper.getNode(lastParentItem), Kinship.CHILD);
            }
            else
            {
                int j = i;
                while(j<regexp.length() && regexp.charAt(j)!='<' && regexp.charAt(j)!='>'
                        && regexp.charAt(j)!='(' && regexp.charAt(j)!=')')
                {
                    j++;
                }

                String text = regexp.substring(i, j);

                treeWrapper.addObject(new Text(text), treeWrapper.getNode(lastParentItem), Kinship.CHILD);
                i = j-1;
            }
        }

        treeWrapper.addObject(new DOF(), treeWrapper.getRoot(), Kinship.CHILD);
    }

    /**
     * Eats a square candidate.
     * Returns null if it cannot be eaten or the item after the element.
     * @param e Edible to be eaten
     * @param t Where to start eating
     * @param d Direction
     * @return item after the element on the edible e
     */
    public Item eatOneSquare(Edible e, Item t, DirectionOperator d)
    {
        //Iteradores de los Edibles:
        Mismatch m = null;

        if(t == null)
            throw new IllegalArgumentException();

        crearIteradorEdible(d, t, e);
        crearIteradorWrapper(d, this.getTree().getRootObject());

        Item edibleToken = null;

        if(e instanceof Sample)
        {
            while(itWrapper.hasNext())
            {
                if(!itSample.hasNext())
                    return null;

                edibleToken = itSample.next();
                if(!itWrapper.isNextAndNext(edibleToken))
                {
                    Item i = itWrapper.next();
                    m = new Mismatch(this, e, i,(Token) edibleToken, d);   
                    if(!repairingInternal(m, itWrapper,itSample))
                        return null;
                }
            }
        }
        else if(e instanceof Wrapper)
        {
            SMx itemsUsed = new SMx();
            java.util.List<Way> alternativeWays = null;
            Item indexWrapper;
              
            Item lastNodeSample = null;
            itSample.goTo(t);
            if(itSample.hasPrevious())
                lastNodeSample = itSample.previous();
            Item lastNodeWrapper = null;
            while(itWrapper.hasNext())
            {  
                if(!itSample.hasNext())
                    throw new RuntimeException("paranoia!!!");
//                    return null;
                
                alternativeWays = itSample.nextAllWays();
                alternativeWays = cleanAlternativeWays(alternativeWays);

                for(Way w: alternativeWays)
                {   
                    for(Item i: w.getItemsUntilEndOfWay())
                    {
                        if(i instanceof List && !itemsUsed.contains(i))
                        {
                            if(i instanceof List)
                            {                        
                                Wrapper sample1 = generateSamplesList(e,d,i,1);
                                Wrapper sample2 = generateSamplesList(e,d,i,2);
                                
                                EdibleIterator itVirtualSample = null;
                                if(d == DirectionOperator.DOWNWARDS)
                                    itVirtualSample = sample1.iterator(ForwardTokenIterator.class);
                                else
                                    itVirtualSample = sample1.iterator(BackwardTokenIterator.class);
                                
                                
                                Item aux = null;
                                itWrapper.goToNext(lastNodeWrapper);                               
                                while(itWrapper.hasNext())
                                {
                                    if(!itVirtualSample.hasNext())
                                        break;
                                    
                                    aux = (Token) itVirtualSample.next();
                                    if(!itWrapper.isNextAndNext(aux))
                                    {
                                        m = new Mismatch(this, sample1, itWrapper.next(),(Token) aux, d);   
                                        if(!repairingInternal(m, itWrapper,itVirtualSample))
                                            break;
                                    }
                                }
                                
                                
                                //SEGUNDO SAMPLE
                                if(d == DirectionOperator.DOWNWARDS)
                                    itVirtualSample = sample2.iterator(ForwardTokenIterator.class);
                                else
                                    itVirtualSample = sample2.iterator(BackwardTokenIterator.class);
                                
                                itWrapper.goToNext(lastNodeWrapper);   
                                while(itWrapper.hasNext())
                                {
                                    if(!itVirtualSample.hasNext())
                                        break;
                                    aux = (Token) itVirtualSample.next();
                                    if(!itWrapper.isNextAndNext(aux))
                                    {
                                        m = new Mismatch(this, sample2, itWrapper.next(),(Token) aux, d);   
                                        if(!repairingInternal(m, itWrapper,itVirtualSample))
                                            break;
                                    }
                                }
                            }
                            itemsUsed.add(i);
                        }
                        
                        itWrapper.goToNext(lastNodeWrapper);
                        itSample.goToNext(lastNodeSample);
                    }
                 
                    itWrapper.goToNext(lastNodeWrapper);
                    itSample.goToNext(lastNodeSample);
                    if(!itWrapper.isNextAndNext(w.getWayItem()))
                    {
                        itSample.goToNext(lastNodeSample);
                        m = new Mismatch(this, e, itWrapper.next(),(Token) w.getWayItem(), d);   
                        if(!repairingInternal(m, itWrapper,itSample))
                        {  
                            edibleToken = null;
                            itSample.goTo(t);
                            itWrapper.goTo(itWrapper.getRootIterator());
                            
                            while(itWrapper.hasNext())
                            {
                                if(!itSample.hasNext())
                                    return null;

                                edibleToken = itSample.next();
                                if(!itWrapper.isNextAndNext(edibleToken))
                                {
                                    Item i = itWrapper.next();
                                    m = new Mismatch(this, e, i,(Token) edibleToken, d);   
                                    if(!repairingInternal(m, itWrapper,itSample))
                                        return null;
                                }
                            }
                            return edibleToken;
                        }
                    }
 
                    
                    itWrapper.goToNext(lastNodeWrapper);
                    itSample.goToNext(lastNodeSample);
                }
                
                
                itWrapper.goToNext(lastNodeWrapper);
                itSample.goToNext(lastNodeSample);
                lastNodeWrapper = itWrapper.next();
                lastNodeSample = itSample.next();
                edibleToken = lastNodeSample;
            } 
        }
        
        if(!(edibleToken instanceof Token))
        {
            itSample.goTo(edibleToken);
            edibleToken = itSample.next();
        }

        return edibleToken;
    }

    private Wrapper generateSamplesList(Edible e, DirectionOperator d, Item plus, int numberOfElements) {
            
        //TODO MEJORAR ESTE METODO Y NO HACERLO POR STRINGS SINO POR NODOS
        // YA QUE SINO CUAND NOS ENCONTREMOS CON &DOF; SE CONVERTIRA EN UN NODO DE TIPO TEXTO
        // Y NO HARA MATCH CON OTROS DOF'S DE TIPO DOF
        
            EdibleIterator itVirtualSamples;
            
            if(d == DirectionOperator.DOWNWARDS)
                itVirtualSamples = e.iterator(ForwardTokenIterator.class);
            else
                itVirtualSamples = e.iterator(BackwardTokenIterator.class);
            
            SMTreeNode lastRoot = itVirtualSamples.getRootIteratorNode();
            String sample = "";
            
            for(int j=0; j<numberOfElements; j++)
            {
                itVirtualSamples.setRootIterator(plus);

                while(itVirtualSamples.hasNext())
                {
                    if(DirectionOperator.DOWNWARDS == d)
                        sample += itVirtualSamples.next();
                    else
                        sample = itVirtualSamples.next() + sample;
                }
            }
            
            itVirtualSamples.setRootIteratorNode(lastRoot);
            
            if(DirectionOperator.DOWNWARDS == d)
                sample += (itVirtualSamples.next(plus, EdibleIterator.TypeOfAccess.ACCESSED))[0].getWayItem();
            else
                sample = (itVirtualSamples.next(plus, EdibleIterator.TypeOfAccess.ACCESSED))[0].getWayItem() + sample;
            
            
            Wrapper aux = new Wrapper(sample);
            
            //BORRAR DOFs que crear el wrapper por defecto  
            aux.getTree().removeSMTreeNode(aux.getTree().getRoot().getFirstChild());
            aux.getTree().removeSMTreeNode(aux.getTree().getRoot().getLastChild());
           
            return aux;
    }
    
    private boolean repairingInternal(Mismatch m, EdibleIterator itW, EdibleIterator itS) {
            Operator op = new Operator();
            Repair repair = op.repair(m);
            if(repair.getState() == StateRepair.SUCESSFULL)
            {
                repair.apply();         
                itS.goTo(repair.getIndexSample());
                itW.goTo(repair.getIndexWrapper());
            }
            
            return repair.getState() == StateRepair.SUCESSFULL;
    }
    
    private java.util.List<Way> cleanAlternativeWays(java.util.List<Way> ways) {
        
        return ways;
//        java.util.List<Way> result = new LinkedList<Way>();
//        for(Way w: ways)
//        {
//            if(!w.getItemsUntilEndOfWay().isEmpty())
//                result.add(w);
//        }
//        return result;
    }

    public SMTree<Item> getTree()
    {
        return this.treeWrapper;
    }
    public Mismatch eat (Sample s, DirectionOperator d)
    {
        if(d == DirectionOperator.DOWNWARDS)
            return eat(s, s.getToken(0), treeWrapper.getRootObject(), d);
        else
            return eat(s, s.getToken(s.getNumToken()-1), treeWrapper.getRootObject(), d);
    }
    public boolean goTo (Item i) {
        return true;
    }
    public Mismatch eat (Sample s) {
        return eat(s,DirectionOperator.DOWNWARDS);
    }

    private void crearIteradorEdible(DirectionOperator d, Item t, Edible e)
    {

        /* Segun el recorrido creamos un tipo de iterador */
        if(DirectionOperator.DOWNWARDS == d)
        {
            if(e instanceof Sample)
                itSample = e.iterator(webPageForwardIterator.class);
            else
                itSample = e.iterator(ForwardTokenIterator.class);
        }
        else
        {
            if(e instanceof Sample)
                itSample = e.iterator(webPageBackwardIterator.class);
            else
                itSample = e.iterator(BackwardTokenIterator.class);
        }

        //Nos colocamos para empezar a comer:
        itSample.goTo(t);
    }

    private void crearIteradorWrapper(DirectionOperator d, Item n)
    {
        /* Segun el recorrido creamos un tipo de iterador */
        if(DirectionOperator.DOWNWARDS == d)
        {
            itWrapper = iterator(ForwardTokenIterator.class);
        }
        else
        {
            itWrapper = iterator(BackwardTokenIterator.class);
        }

        //Nos colocamos para empezar a comer:
        itWrapper.goTo(n);
    }

    public boolean substitute (Item from, Enclosure inclusionFrom, Item to, Enclosure inclusionTo, SMTree what) {
        return treeWrapper.substituteObject(from, inclusionFrom, to, inclusionTo, what);
    }

     public boolean substitute (Item from, Enclosure inclusionFrom, Item to, Enclosure inclusionTo, Wrapper what) {
        return treeWrapper.substituteObject(from, inclusionFrom, to, inclusionTo, what.treeWrapper);
    }

    public Mismatch eat (Edible e, Item t, Item n, DirectionOperator d)
    {
        if(e == null || t == null || n == null || d == null)
            throw new IllegalArgumentException("eat no puede tener parámetros nulos.");

        Mismatch m = null;

        //Iteradores de los Edibles:
        crearIteradorWrapper(d, n);
        crearIteradorEdible(d, t, e);


        /*mientras no se produzca un mismatch y no me coma entero el sample o el wrapper*/
        Token nextSample = null;
        while(itSample.hasNext() && itWrapper.hasNext() && m==null)
        {
            //m = compruebaNext(itSample.nextAll(), e, d);
            nextSample = itSample.next();
            if(!itWrapper.isNextAndNext(nextSample))
            {
                   Item i = itWrapper.next();
                   m = new Mismatch(this, e, i, nextSample, d);      
            }
        }
        
            

        /* Si no se ha producido un mismatch pero si el sample o el wrapper se han acabado,
         * no ha funcionado bien
         */
        if(m == null && (itWrapper.hasNext() ||itSample.hasNext()))
           throw new RuntimeException("Se ha liado con los DOF. Probablemente el isNext() está fallando.");

        return m;
    }

    public EdibleIterator iterator (Class iteratorClass)
    {
        EdibleIterator wi = null;
        try {
            wi = (EdibleIterator) iteratorClass.newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(SMTree.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(SMTree.class.getName()).log(Level.SEVERE, null, ex);
        }
        wi.setTree(treeWrapper);

        return wi;
    }

    public SMTreeIterator<Item> iterator (Class iteratorClass, Item virtualRootWrapper)
    {
        SMTreeIterator<Item> wi = null;
        try {
            wi = (SMTreeIterator<Item>) iteratorClass.newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(SMTree.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(SMTree.class.getName()).log(Level.SEVERE, null, ex);
        }

        SMTreeNode<Item> virtualRootNode = treeWrapper.getNode(virtualRootWrapper);
        if(virtualRootNode==null)
            throw new NullPointerException("La raiz virtual para recorrer el wrapper no existe");

        wi.setTree(treeWrapper);
        wi.setRootIterator(virtualRootNode);

        return wi;
    }

    public Wrapper cloneSubWrapper(Token firstTokenSquare, Token lastTokenSquare, Item parent, DirectionOperator d)
    {
        SMTree<Item> treeCloned = null;
        try {
            if(DirectionOperator.UPWARDS == d)
                treeCloned = treeWrapper.cloneSubTree(lastTokenSquare, firstTokenSquare, parent);
            else
                treeCloned = treeWrapper.cloneSubTree(firstTokenSquare, lastTokenSquare, parent);
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Wrapper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new Wrapper(treeCloned);
    }

    public Token searchWellFormed(Token t, Enclosure tokenEnclosure, Token from, Enclosure tEnclosure, DirectionOperator d) {

        EdibleIterator<Token> it = null;

        if(DirectionOperator.DOWNWARDS == d)
            it = iterator(ForwardTokenIterator.class);
        else if(DirectionOperator.UPWARDS == d)
            it = iterator(BackwardTokenIterator.class);

        it.goTo(from);

        Token token=null;

        while(it.hasNext())
        {
            Item i = (Item) it.next();

            //Sabremos que estamos en una hoja si es un token
            if(i instanceof Token)
            {
                token = (Token) i;
                if((token.match(t) || (token instanceof Text && t instanceof Text))
                        && isWellFormed(from, tEnclosure, token, tokenEnclosure, d))
                    return token;
            }
        }

        return null;
    }

    private boolean isWellFormed (Token from,Enclosure inclusionFrom, Token to,Enclosure inclusionTo, DirectionOperator d) {

        if(from==null || to==null)
           throw new NullPointerException("");

        // las regiones vacias estaran bien formadas
        if(from==to && (inclusionFrom == Enclosure.NOT_ENCLOSED || inclusionTo == Enclosure.NOT_ENCLOSED))
            return true;

        boolean isWellFormed = true;
        Stack<Tag> openTags = new Stack();
        EdibleIterator<Token> it = null;

        if(d == DirectionOperator.DOWNWARDS)
            it = iterator(ForwardTokenIterator.class);
        else if(d == DirectionOperator.UPWARDS)
            it = iterator(BackwardTokenIterator.class);

        //Si 'to' no esta incluido, no desechamos
        if(Enclosure.NOT_ENCLOSED == inclusionTo)
        {
            it.goTo(to);
            to = it.previous();
        }

        it.goTo(from);

        //Si 'from' no esta incluido, no desechamos
        if(Enclosure.NOT_ENCLOSED == inclusionFrom)
            from = it.next();

        //si miramos solo si un token esta bien formado y dicho token no es una etiqueta
        if(from==to && (!(from instanceof Tag)))
            return true;
        //si miramos solo si un token esta bien formado y dicho token es una etiqueta del tipo <.../>
        else if (from==to && ((Tag)from).isOpenTag() && ((Tag)from).isOpenTag())
            return true;
        else if(from==to)
            return false;

        if(d == DirectionOperator.DOWNWARDS)
        {
            Token t;
            do
            {
                t = it.next();
                if(t instanceof Tag && ((Tag)t).isOpenTag() && ((Tag)t).isCloseTag())
                    continue;
                else if(t instanceof Tag && ((Tag)t).isOpenTag())
                    openTags.push((Tag)t);
                else if (t instanceof Tag && ((Tag)t).isCloseTag() && !openTags.empty() && openTags.lastElement().isOpenTag() && openTags.lastElement().getContent().equals(t.getContent()))
                        openTags.pop();
                else if(!(t instanceof Text) && !(t instanceof Variable))
                    isWellFormed = false;

            } while(it.hasNext() && t!=to && isWellFormed);
        }
        else if(d == DirectionOperator.UPWARDS)
        {
            Token t;
            do
            {
                t = it.next();

                if(t instanceof Tag && ((Tag)t).isOpenTag() && ((Tag)t).isCloseTag())
                    continue;
                else if(t instanceof Tag && ((Tag)t).isCloseTag())
                    openTags.push((Tag)t);
                else if (t instanceof Tag && ((Tag)t).isOpenTag() && !openTags.empty() && openTags.lastElement().isCloseTag() && openTags.lastElement().getContent().equals(t.getContent()))
                        openTags.pop();
                else if(!(t instanceof Text) && !(t instanceof Variable))
                    isWellFormed = false;

            } while(it.hasNext() && t!=to && isWellFormed);
        }

        if(!openTags.empty())
            isWellFormed=false;

        return isWellFormed;
    }

    @Override
    public String toString()
    {
        return toStringWrapper(treeWrapper.getRoot());
    }
    /**
     *
     * @param e Wrapper desde el que copio.
     * @param desde Nodo desde el que copia, contenido.
     * @param hasta Nodo hasta el que tiene que copiar, no contenido.
     * @param complejidad Indica si se introduce o no en los opcionales y las listas.
     * @return un sample que copia el wrapper.
     */
    public Sample simularSample(Wrapper e, Item desde, Item hasta, boolean complejidad, DirectionOperator d)
    {
        LinkedList<Item> ejemplo = new LinkedList<Item>();
        SMTreeNode<Item> actual = e.getTree().getNode(desde);
        SMTreeNode<Item> fin = e.getTree().getNode(hasta);

        while(actual.getObject() instanceof CompositeItem)
        {
            if(d == DirectionOperator.UPWARDS)
                actual = actual.getLastChild();
            else
                actual = actual.getFirstChild();
        }

        while(fin.getObject() instanceof CompositeItem)
        {
            if(d == DirectionOperator.UPWARDS)
                fin = fin.getLastChild();
            else
                fin = fin.getFirstChild();
        }

        int level_actual = 0;
        int level_fin = 0;

        SMTreeNode aux = actual;
        while(aux.getParent() != null)
        {
            aux = aux.getParent();
            level_actual ++;
        }

        aux = fin;
        while(aux.getParent() != null)
        {
            aux = aux.getParent();
            level_fin++;
        }

        while(level_actual > level_fin)
        {
            level_actual--;
            actual = actual.getParent();
        }

        while(level_actual < level_fin)
        {
            level_fin--;
            fin = fin.getParent();
        }

        while(fin.getParent() != actual.getParent())
        {
            fin = fin.getParent();
            actual = actual.getParent();
        }

        while(actual != fin && actual != null)
        {
            ejemplo.addAll(simularSampleRecursivo(actual, complejidad));

            actual = actual.getNext();
        }

        if(fin.getObject() instanceof Token)
            ejemplo.add(fin.getObject());

        Sample s = new Sample(ejemplo);

        return s;
    }

    private java.util.List<Item> simularSampleRecursivo(SMTreeNode<Item> actual, boolean complejidad)
    {
        java.util.List<Item> resultado = new LinkedList<Item>();
        SMTreeNode<Item> aux;

        if(actual.getObject() instanceof CompositeItem)
        {
            Item objeto = actual.getObject();
            assert(actual.getFirstChild() != null);
            actual = actual.getFirstChild();

            if(objeto instanceof List)
            {
                aux = actual;

                while(actual != null)
                {
                    resultado.addAll(simularSampleRecursivo(actual, complejidad));
                    actual = actual.getNext();
                }

                if(complejidad)
                {
                    actual = aux;
                    while(actual != null)
                    {
                        resultado.addAll(simularSampleRecursivo(actual, !complejidad));
                        actual = actual.getNext();
                    }
                }
            }
            else if(objeto instanceof Tuple)
            {
                while(actual != null)
                {
                    resultado.addAll(simularSampleRecursivo(actual, complejidad));
                    actual = actual.getNext();
                }
            }
            else if((objeto instanceof Optional) && complejidad)
            {
                while(actual != null)
                {
                    resultado.addAll(simularSampleRecursivo(actual, complejidad));
                    actual = actual.getNext();
                }
            }

        }
        else
        {
            try {
                    resultado.add((Item)actual.getObject().clone());
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Wrapper.class.getName()).log(Level.SEVERE, null, ex);
                }
        }

        return resultado;
    }

    private String toStringWrapper(SMTreeNode n)
    {

        if(n.getObject() instanceof DOF)
            return "";

        String result = "";
        SMTreeNode aux = n.getFirstChild();

        if(!(n.getObject() instanceof Tuple) && aux==null)
            return n.getObject().toString();

        if(!(n.getObject() instanceof Tuple) )
            result += "(";

        while(aux!=n.getLastChild())
        {
            result += toStringWrapper(aux);
            aux = aux.getNext();
        }

        result += toStringWrapper(aux);

        if(!(n.getObject() instanceof Tuple))
            result += ")"+n.getObject().toString();

        return result;
    }

    public Iterator iterator() {
        return new ForwardTokenIterator(treeWrapper);
    }
}

