/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.components;

/**
 *
 * @author elisabetta
 */
public class Player {
    
    public static enum Order {
        PLAYER_1, PLAYER_2;

        /**
         * given a playerID will return the other player Id
         *
         * @param playerId
         * @return
         */
        Order switchPlayer() {
            if (this == PLAYER_1) {
                return PLAYER_2;
            }
            return PLAYER_1;
        }
    }
    
    private String sessionId;
    private Order playerId;

    public Player() {
    }

    public Player(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Order getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Order playerId) {
        this.playerId = playerId;
    }

}
