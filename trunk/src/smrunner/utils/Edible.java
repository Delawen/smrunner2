/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package smrunner.utils;

import SMTree.utils.Enclosure;
import smrunner.iterator.EdibleIterator;
import smrunner.node.Item;
import smrunner.node.Token;
import smrunner.operator.DirectionOperator;

/**
 *
 * @author delawen
 */
public interface Edible 
{

    public Wrapper cloneSubWrapper(Token firstTokenSquare, Token lastTokenSquare, Item item, DirectionOperator d);
    public EdibleIterator iterator(Class type);
    public Token searchWellFormed(Token token, Enclosure tokenEnclosure, Token t, Enclosure tEnclosure, DirectionOperator d);
}
