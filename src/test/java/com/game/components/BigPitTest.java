/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.components;

import com.game.components.Player.Order;
import static com.game.components.Player.Order.PLAYER_1;
import static com.game.components.Player.Order.PLAYER_2;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elisabetta
 */
public class BigPitTest {

    /**
     * Test of getPlayer method, of class BigPit.
     */
    @Test
    public void testGetPlayer() {
        System.out.println("getPlayer");
        BigPit instance = new BigPit(PLAYER_1, 6);
        Order result = instance.getPlayer();
        assertEquals(PLAYER_1, instance.getPlayer());
    }

    /**
     * Test of setPlayer method, of class BigPit.
     */
    @Test
    public void testSetPlayer() {
        System.out.println("setPlayer");
        BigPit pit = new BigPit(PLAYER_1, 6);
        pit.setPlayer(PLAYER_2);
        assertEquals(PLAYER_2, pit.getPlayer());
    }

    /**
     * Test of addStone method, of class BigPit.
     */
    @Test
    public void testAddStoneNoAdd() {
        System.out.println("addStone");
        BigPit instance = new BigPit(PLAYER_1, 6);
        boolean addStone = instance.addStone(PLAYER_2);
        assertFalse("If the pit belongs to player 1 but player 2 moved,"
                + " no stone should be added.", addStone);
        assertEquals(6, instance.getNumberOfStones());
    }

    /**
     * Test of addStone method, of class BigPit.
     */
    @Test
    public void testAddStoneAdd() {
        System.out.println("addStone");
        BigPit instance = new BigPit(PLAYER_1, 6);
        int incomingStones = 5;
        boolean addStone = instance.addStone(PLAYER_1);
        assertTrue(addStone);
        assertEquals(7, instance.getNumberOfStones());

    }

    /**
     * Test of toString method, of class BigPit.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        BigPit instance = new BigPit(PLAYER_1, 6);
        String expectedString = "Pit{numberOfStones=6}";
        assertEquals(expectedString, instance.toString());
    }

}
