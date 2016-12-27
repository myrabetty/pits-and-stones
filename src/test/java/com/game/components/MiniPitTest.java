/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.components;

import static com.game.components.Player.Order.PLAYER_1;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elisabetta
 */
public class MiniPitTest {

    /**
     * Test of isActive method, of class MiniPit.
     */
    @Test
    public void testIsActive() {
        System.out.println("isActive");
        MiniPit instance = new MiniPit();
        instance.setActive(true);
        assertTrue(instance.isActive());
    }

    /**
     * Test of addStone method, of class MiniPit.
     */
    @Test
    public void testAddStone() {
        System.out.println("addStone");

        MiniPit instance = new MiniPit(6);
        int incomingStones = 2;
        boolean result = instance.addStone(PLAYER_1);
        assertTrue(result);
        assertEquals(7, instance.getNumberOfStones());
    }

    /**
     * Test of toString method, of class MiniPit.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        MiniPit instance = new MiniPit(6);
        String expectedString = "Pit{numberOfStones=6}";
        assertEquals(expectedString, instance.toString());
    }

}
