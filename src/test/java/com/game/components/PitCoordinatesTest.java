/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.components;

import static com.game.components.Player.Order.PLAYER_1;
import static com.game.components.Player.Order.PLAYER_2;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elisabetta
 */
public class PitCoordinatesTest {

    /**
     * Test of getPitId method, of class PitCoordinates.
     */
    @Test
    public void testGetPitId() {
        System.out.println("getPitId");
        PitCoordinate instance = new PitCoordinate(PLAYER_1, 2);
        assertEquals(2, instance.getPitId());
    }

    /**
     * Test of setPitId method, of class PitCoordinates.
     */
    @Test
    public void testSetPitId() {
        System.out.println("setPitId");
        PitCoordinate instance = new PitCoordinate();
        instance.setPitId(5);
        assertEquals(5, instance.getPitId());
    }

    /**
     * Test of getPlayerId method, of class PitCoordinates.
     */
    @Test
    public void testGetPlayerId() {
        System.out.println("getPlayerId");
        PitCoordinate instance = new PitCoordinate(PLAYER_1, 2);
        assertEquals(PLAYER_1, instance.getPlayerId());
    }

    /**
     * Test of setPlayerId method, of class PitCoordinates.
     */
    @Test
    public void testSetPlayerId() {
        System.out.println("setPlayerId");
        int playerId = 0;
        PitCoordinate instance = new PitCoordinate();
        instance.setPlayerId(PLAYER_2);
        assertEquals(PLAYER_2, instance.getPlayerId());
    }

}
