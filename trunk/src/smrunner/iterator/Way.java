/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package smrunner.iterator;

import smrunner.node.Item;

/**
 *
 * @author santi
 */
public class Way {
    
    private Item wayItem;

    protected enum DirectionWay {UP,DOWN,LEFT,RIGHT}; 
    private DirectionWay direction;
    
    public Way(Item wayItem, DirectionWay direction)
    {
        this.wayItem = wayItem;
        this.direction = direction;
    }
    
    public Way()
    {
        this.wayItem = null;
        this.direction = null;
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
    
    public String toString()
    {
        return "["+wayItem+" en direccion "+direction+"]";
    }
    
    public boolean equals(Object o)
    {
        return ((Way)o).wayItem == this.wayItem && ((Way)o).direction == this.direction;
    }
}
