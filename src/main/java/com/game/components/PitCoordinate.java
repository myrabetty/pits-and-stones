package com.game.components;

import com.game.components.Player.Order;

/**
 *
 * @author elisabetta
 */
/**
 * it allows a convenient storage of pit id and playedId which uniquely identify
 * a pit
 */
public class PitCoordinate {

    private int pitId;
    private Order playerId;

    public PitCoordinate() {
    }

    public PitCoordinate(Order playerId, int pitId) {
        this.playerId = playerId;
        this.pitId = pitId;
    }

    public PitCoordinate(PitCoordinate coord) {
        this.playerId = coord.getPlayerId();
        this.pitId = coord.getPitId();
    }    
    public int getPitId() {
        return pitId;
    }

    public void setPitId(int pitId) {
        this.pitId = pitId;
    }

    public Order getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Order playerId) {
        this.playerId = playerId;
    }

}
