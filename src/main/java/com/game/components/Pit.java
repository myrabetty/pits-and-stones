/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.components;

import com.game.components.Player.Order;

/**
 *
 * @author elisabetta
 */
public abstract class Pit {

    int numberOfStones;

    public int getNumberOfStones() {
        return numberOfStones;
    }

    public void setNumberOfStones(int numberOfStones) {
        this.numberOfStones = numberOfStones;
    }

    /**
     * It will add a stone to the pit and return the number of stones left
     *
     * @param activePlayer the id of the active player
     * @return true if the stone was added to the pit.
     */
    abstract boolean addStone(Order activePlayer);

    @Override
    public String toString() {
        return "Pit{" + "numberOfStones=" + numberOfStones + '}';
    }
}
