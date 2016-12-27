package com.game.components;

import com.game.components.Player.Order;

/**
 *
 * @author elisabetta
 */
public class BigPit extends Pit{
    private Order player;
    
    public BigPit(){}
    
    public BigPit(Order player, int numberOfStones){
        this.player = player;
        this.numberOfStones = numberOfStones;
    }
        public Order getPlayer() {
        return player;
    }

    public void setPlayer(Order player) {
        this.player = player;
    }
    
    @Override
    boolean addStone(Order playerId) {
        if (this.getPlayer() == playerId) {
            this.numberOfStones = numberOfStones + 1;
            return true;
        }
        return false;
    }    
    
    @Override
    public String toString() {
        return super.toString();
    }
}
