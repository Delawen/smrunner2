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
    private EdibleIterator itWrapper;
    private EdibleIterator itSample;

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
        return null;
        //Iteradores de los Edibles:
//        Mismatch m = null;
//
//        if(t == null)
//            throw new IllegalArgumentException();
//
//        crearIteradorEdible(d, t, e);
//        crearIteradorWrapper(d, this.getTree().getRootObject());
//
//        //Para resolver los mismatches:
//        Operator op;
//        Object edibleToken = null;
//
//
//        /*mientras no me coma entero el elemento*/
//        while(itWrapper.hasNext())
//        {
//            if(!itSample.hasNext())
//                return null;
//            edibleToken = itSample.nextAll();
//                m = compruebaNextYSacaVariables(edibleToken, e, d);
//            //Si es necesario, aplicamos reparaciones:
//            if(m != null)
//            {
//                op = new Operator();
//                Repair repair = op.repair(m);
//                if(repair.getState() == StateRepair.SUCESSFULL)
//                    repair.apply();
//                else
//                {
//                    //Falló, así que el edibleToken lo ponemos a null
//                    edibleToken = null;
//                    break;
//                }
//                itSample.goTo(repair.getIndexSample());
//                Item n = repair.getReparator().getTree().getRootObject();
//                itWrapper.goTo(n);
//                if(n instanceof roadrunner.node.List)
//                    ((roadrunner.node.List)n).setAccessed(true);
//            }
//        }
//
//        if(edibleToken == null)
//            return null;
//
//        Item fin;
//
//        if(edibleToken instanceof Item)
//            fin = (Item) edibleToken;
//        else
//            fin = (Item) ((java.util.List)edibleToken).get(0);
//
//        //Si es un wrapper, tenemos que recuperar lo que nos hayamos perdido
//        //e incorporarlo al square candidate
//        if((e instanceof Wrapper) && fin != null)
//        {
//            Sample s1;
//            Sample s2;
//            if(DirectionOperator.DOWNWARDS == d)
//            {
//                s1 = simularSample((Wrapper)e, t, fin, true, d);
//                s2 = simularSample((Wrapper)e, t, fin, false, d);
//            }
//            else
//            {
//                s1 = simularSample((Wrapper)e, fin, t, true, d);
//                s2 = simularSample((Wrapper)e, fin, t, false, d);
//            }
//
//            System.out.println("Wrapper: " + (Wrapper)e);
//            System.out.println("Sample1: "+ s1);
//            System.out.println("Sample2: "+ s2);
//
//
//            Mismatch mismatch_interno;
//            Item n_interno = this.treeWrapper.getRootObject();
//            Item item_interno = s1.getToken(0);
//            while(true) //Procesamos todo el sample
//            {
//
//                mismatch_interno = eat(s1, item_interno, n_interno, DirectionOperator.DOWNWARDS);
//
//                //Si al comer se ha provocado un Mismatch:
//                if(mismatch_interno != null)
//                {
//                    Operator operator = new Operator();
//                    Repair reparacion = operator.repair(mismatch_interno);
//                    if(reparacion.getState() != StateRepair.SUCESSFULL)
//                        throw new AssertionError("No pudo resolver una de las reparaciones simuladas internas: \n" + s1 + "\n" + this);
//                    reparacion.apply();
//                    item_interno = reparacion.getIndexSample();
//                    n_interno = reparacion.getReparator().getTree().getRootObject();
//                    if(n_interno instanceof roadrunner.node.List)
//                        ((roadrunner.node.List)n_interno).setAccessed(true);
//                }
//                else break; //Hemos terminado
//            }
//
//            n_interno = this.treeWrapper.getRootObject();
//            item_interno = s2.getToken(0);
//            while(true) //Procesamos todo el sample
//            {
//
//                mismatch_interno = eat(s2, item_interno, n_interno, DirectionOperator.DOWNWARDS);
//
//                //Si al comer se ha provocado un Mismatch:
//                if(mismatch_interno != null)
//                {
//                    Operator operator = new Operator();
//                    Repair reparacion = operator.repair(mismatch_interno);
//                    assert(reparacion.getState() == StateRepair.SUCESSFULL);
//                    reparacion.apply();
//                    item_interno = reparacion.getIndexSample();
//                    n_interno = reparacion.getReparator().getTree().getRootObject();
//                    if(n_interno instanceof roadrunner.node.List)
//                        ((roadrunner.node.List)n_interno).setAccessed(true);
//                }
//                else break; //Hemos terminado
//            }
//        }
//
//
//        return fin;
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
        while(itSample.hasNext() && itWrapper.hasNext() && m==null)
            ; //TODO SANTI // m = compruebaNext(itSample.nextAll(), e, d);

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

        SMTreeIterator<Item> it = null;

        if(DirectionOperator.DOWNWARDS == d)
            it = treeWrapper.iterator(ForwardIterator.class);
        else if(DirectionOperator.UPWARDS == d)
            it = treeWrapper.iterator(BackwardIterator.class);

        it.goTo(from);

        Token token=null;

        while(it.hasNext())
        {
            Item i = (Item) it.nextObject();

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

