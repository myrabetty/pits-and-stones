/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.components;

import static com.game.components.Board.NUMBER_OF_SMALL_PITS;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import static com.game.components.Board.NO_STONES;
import static com.game.components.MiniPit.INIT_NUMBER_OF_STONES;
import static com.game.components.Player.Order.PLAYER_1;
import static com.game.components.Player.Order.PLAYER_2;
import static org.junit.Assert.assertFalse;

/**
 *
 * @author elisabetta
 */
public class BoardTest {

    Board instance;

    @Before
    public void setUp() {
        instance = new Board();
        instance.init();
    }

    /**
     * Test of init method, of class Board.
     */
    @Test
    public void testInit() {
        System.out.println("init");

        List<MiniPit> pits1 = instance.getPitsPlayer1();
        List<MiniPit> pits2 = instance.getPitsPlayer2();
        for (int i = 0; i < NUMBER_OF_SMALL_PITS; i++) {
            assertEquals(pits1.get(i).getNumberOfStones(), INIT_NUMBER_OF_STONES);
            assertEquals(pits2.get(i).getNumberOfStones(), INIT_NUMBER_OF_STONES);
            assertEquals(pits1.get(i).isActive(), false);
            assertEquals(pits2.get(i).isActive(), false);
        }
        BigPit bigPit1 = instance.getBigPitPlayer1();
        assertEquals(bigPit1.getNumberOfStones(), NO_STONES);
        assertEquals(bigPit1.getPlayer(), PLAYER_1);
        BigPit bigPit2 = instance.getBigPitPlayer2();
        assertEquals(bigPit2.getNumberOfStones(), NO_STONES);
        assertEquals(bigPit2.getPlayer(), PLAYER_2);
        //assertEquals(instance.getActivePlayer(), PLAYER_1);
    }

    /**
     * Test of getPitsPlayer1 method, of class Board.
     */
    @Test
    public void testGetPitsPlayer1() {
        System.out.println("getPitsPlayer1");
        assertEquals(instance.getPitsPlayer1().size(), NUMBER_OF_SMALL_PITS);
    }

    /**
     * Test of getPitsPlayer2 method, of class Board.
     */
    @Test
    public void testGetPitsPlayer2() {
        System.out.println("getPitsPlayer2");
        assertEquals(instance.getPitsPlayer2().size(), NUMBER_OF_SMALL_PITS);
    }

    /**
     * Test of getBigPitForPlayer method, of class Board.
     */
    @Test
    public void testGetBigPitForPlayer() {
        System.out.println("getBigPitForPlayer");
        assertEquals(instance.getBigPitForPlayer(PLAYER_1), instance.getBigPitPlayer1());
        assertEquals(instance.getBigPitForPlayer(PLAYER_2), instance.getBigPitPlayer2());
    }

    /**
     * Test of getPitsForPlayer method, of class Board.
     */
    @Test
    public void testGetPitsForPlayer() {
        System.out.println("getBigPitForPlayer");
        assertEquals(instance.getPitsForPlayer(PLAYER_1), instance.getPitsPlayer1());
        assertEquals(instance.getPitsForPlayer(PLAYER_2), instance.getPitsPlayer2());
    }

    /**
     * Test the toString method, of the class board.
     *
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        String expectedValue = "Board{pitsPlayer1=[Pit{numberOfStones=6}, Pit{numberOfStones=6}, Pit{numberOfStones=6}, Pit{numberOfStones=6}, Pit{numberOfStones=6}, Pit{numberOfStones=6}], pitsPlayer2=[Pit{numberOfStones=6}, Pit{numberOfStones=6}, Pit{numberOfStones=6}, Pit{numberOfStones=6}, Pit{numberOfStones=6}, Pit{numberOfStones=6}], bigPitPlayer1=Pit{numberOfStones=0}, bigPitPlayer2=Pit{numberOfStones=0}}";
        assertEquals(expectedValue, instance.toString());
    }

    /**
     * Test the gameIsOver method, of the class board.
     *
     */
    @Test
    public void testIsGameOver() {
        System.out.println("gameOver");
        Board newBoard = new Board();
        newBoard.init();
        //assertFalse(instance.gameOver());
        newBoard.getPitsPlayer1().stream().forEach(p -> p.setNumberOfStones(0));
        //assertTrue(newBoard.gameOver());
    }

    /**
     * Test the setPitsInState method, of the class board.
     *
     */
    @Test
    public void testSetPitsInState() {
        System.out.println("setPitsInState");
        Board newBoard = new Board();
        newBoard.init();
        newBoard.getPitsPlayer1().stream().forEach(p -> assertFalse(p.isActive()));
    }

    @Test
    public void testGetPit() {
        System.out.println("getPit");
        PitCoordinate coord = new PitCoordinate(PLAYER_1, 0);
        assertEquals(instance.getPitsPlayer1().get(0),instance.getPit(coord));
    }
}
