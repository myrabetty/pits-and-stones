package com.game.components;

import com.game.components.Player.Order;

/**
 *
 * @author elisabetta
 */
public class MiniPit extends Pit {

    protected static final int INIT_NUMBER_OF_STONES = 6;    
    private boolean active;

    public MiniPit() {
    }

    public MiniPit(int stones) {
        this.numberOfStones = stones;
        this.active = false;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    boolean addStone(Order activePlayer) {
        this.numberOfStones = numberOfStones + 1;
        return true;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
