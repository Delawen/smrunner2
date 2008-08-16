/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package smrunner.iterator;

import java.util.LinkedList;
import smrunner.node.Item;
import smrunner.node.List;
import smrunner.node.Optional;

/**
 *
 * @author santi
 */
public class SMx {
    
    LinkedList<Item> smxs = new LinkedList();
    
    public boolean contains(Item i)
    {
        for(Item j : smxs)
        {
            if(j == i)
                return true;
        }
        
        return false;
    
    }
    
    public void add(Item i)
    {
        smxs.add(i);
    }

}
