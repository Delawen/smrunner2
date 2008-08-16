/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package smrunner.iterator;

import java.util.LinkedList;
import java.util.List;
import smrunner.node.Item;

/**
 *
 * @author santi
 */
public class Way {
    
    private Item wayItem;

    protected enum DirectionWay {UP,DOWN,LEFT,RIGHT, NONE}; 
    private DirectionWay direction;
    
    private List<Item> itemsUntilEndOfWay;
    
    public Way()
    {
        this.wayItem = null;
        this.direction = null;
    }
    
    public Way(Item wayItem, DirectionWay direction)
    {
        this.wayItem = wayItem;
        this.direction = direction;
    }
    
    public Item getWayItem() {
        return wayItem;
    }

    public void setWayItem(Item wayItem) {
        this.wayItem = wayItem;
    }

    public DirectionWay getDirection() {
        return direction;
    }

    public void setDirection(DirectionWay direction) {
        this.direction = direction;
    }
    
    public List<Item> getItemsUntilEndOfWay() {
        if(itemsUntilEndOfWay == null)
            itemsUntilEndOfWay = new LinkedList<Item>();
        return itemsUntilEndOfWay;
    }
    
    public void addYourself()
    {
        if(itemsUntilEndOfWay == null)
            itemsUntilEndOfWay = new LinkedList<Item>();
        
        if(direction!=DirectionWay.UP)
            itemsUntilEndOfWay.add(wayItem);
    }
        
    public String toString()
    {
        return "["+wayItem+" hacia "+direction+
                ((itemsUntilEndOfWay != null )? " pasado por "+itemsUntilEndOfWay : "")+"]";
    }
    
    public boolean equals(Object o)
    {
        return ((Way)o).wayItem == this.wayItem && ((Way)o).direction == this.direction;
    }
    
    void add(Way w) {
            if(itemsUntilEndOfWay == null)
                 itemsUntilEndOfWay = new LinkedList<Item>();
            if(w.direction != DirectionWay.UP)
                itemsUntilEndOfWay.addAll(w.getItemsUntilEndOfWay());
            
    }
}
