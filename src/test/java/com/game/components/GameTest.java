/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.components;

import static com.game.components.MiniPit.INIT_NUMBER_OF_STONES;
import static com.game.components.Board.NUMBER_OF_SMALL_PITS;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import static com.game.components.Board.NO_STONES;
import static com.game.components.Player.Order.PLAYER_1;
import static com.game.components.Player.Order.PLAYER_2;

/**
 *
 * @author elisabetta
 */
public class GameTest {

    /**
     * Test of moveStones method, of class BoardUpdater.
     */
    @Test
    public void testMoveStonesSameTurn() {
        System.out.println("moveStones");

        Board board = new Board();
        board.init();
        Game game = new Game(board);
        game.setSelectedPit(0);
        game.setActivePlayer(PLAYER_1);
        game.update();
        game.setActiveForPlayer(PLAYER_1);
        List<MiniPit> pits1 = game.getBoard().getPitsPlayer1();
        List<MiniPit> pits2 = game.getBoard().getPitsPlayer2();

        for (int i = 0; i < NUMBER_OF_SMALL_PITS; i++) {
            if (i == 0) {
                assertEquals(pits1.get(i).getNumberOfStones(), 0);
                assertEquals(pits1.get(i).isActive(), false);
            } else {
                assertEquals(pits1.get(i).getNumberOfStones(), 7);
                assertEquals(pits1.get(i).isActive(), true);
            }

            assertEquals(pits2.get(i).getNumberOfStones(), INIT_NUMBER_OF_STONES);
            assertEquals(pits2.get(i).isActive(), false);
        }

        BigPit bigPit1 = game.getBoard().getBigPitPlayer1();
        assertEquals(bigPit1.getNumberOfStones(), 1);
        assertEquals(bigPit1.getPlayer(), PLAYER_1);
        BigPit bigPit2 = game.getBoard().getBigPitPlayer2();
        assertEquals(bigPit2.getNumberOfStones(), NO_STONES);
        assertEquals(bigPit2.getPlayer(), PLAYER_2);
        //assertEquals(instance.getBoard().getActivePlayer(), PLAYER_1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMoveStonesInvalidPitNumber() {
        Board board = new Board();
        board.init();
        Game game = new Game(board);
        game.setSelectedPit(10);

        game.update();
        List<MiniPit> pits1 = game.getBoard().getPitsPlayer1();
        List<MiniPit> pits2 = game.getBoard().getPitsPlayer2();
        for (int i = 0; i < NUMBER_OF_SMALL_PITS; i++) {
            assertEquals(pits1.get(i).getNumberOfStones(), INIT_NUMBER_OF_STONES);
            assertEquals(pits2.get(i).getNumberOfStones(), INIT_NUMBER_OF_STONES);
            assertEquals(pits1.get(i).isActive(), true);
            assertEquals(pits2.get(i).isActive(), false);
        }
        BigPit bigPit1 = game.getBoard().getBigPitPlayer1();
        assertEquals(bigPit1.getNumberOfStones(), NO_STONES);
        assertEquals(bigPit1.getPlayer(), PLAYER_1);
        BigPit bigPit2 = game.getBoard().getBigPitPlayer2();
        assertEquals(bigPit2.getNumberOfStones(), NO_STONES);
        assertEquals(bigPit2.getPlayer(), PLAYER_2);
        assertEquals(game.getActivePlayer(), PLAYER_1);
    }

    @Test
    public void testMoveChangeTurn() {
        Board board = new Board();
        board.init();
        Game game = new Game(board);
        game.setSelectedPit(5);
        game.setActivePlayer(PLAYER_1);
        game.update();
        assertEquals(game.getActivePlayer(), PLAYER_2);
    }

    @Test
    public void testMoveCaptureStones() {
        System.out.println("CaptureStones");
        Board board = new Board();
        board.init();
        board.getPitsForPlayer(PLAYER_1).get(0).setNumberOfStones(5);
        board.getPitsForPlayer(PLAYER_1).get(5).setNumberOfStones(0);
        Game game = new Game(board);
        game.setSelectedPit(0);
        game.setActivePlayer(PLAYER_1);
        game.update();
        List<MiniPit> pits1 = game.getBoard().getPitsPlayer1();
        List<MiniPit> pits2 = game.getBoard().getPitsPlayer2();
        game.setActiveForPlayer(PLAYER_2);

        for (int i = 0; i < NUMBER_OF_SMALL_PITS; i++) {
            if (i == 0) {
                assertEquals(pits1.get(i).getNumberOfStones(), 0);
                assertEquals(pits1.get(i).isActive(), false);
                assertEquals(pits2.get(i).getNumberOfStones(), 0);
                assertEquals(pits2.get(i).isActive(), false);
            } else if (i == 5) {
                assertEquals(pits1.get(i).getNumberOfStones(), 0);
                assertEquals(pits1.get(i).isActive(), false);
                assertEquals(pits2.get(i).getNumberOfStones(), INIT_NUMBER_OF_STONES);
                assertEquals(pits2.get(i).isActive(), true);
            } else {
                assertEquals(pits1.get(i).getNumberOfStones(), 7);
                assertEquals(pits1.get(i).isActive(), false);
                assertEquals(pits2.get(i).getNumberOfStones(), INIT_NUMBER_OF_STONES);
                assertEquals(pits2.get(i).isActive(), true);
            }
        }
        BigPit bigPit1 = game.getBoard().getBigPitPlayer1();
        assertEquals(bigPit1.getNumberOfStones(), 7);
        assertEquals(bigPit1.getPlayer(), PLAYER_1);
        BigPit bigPit2 = game.getBoard().getBigPitPlayer2();
        assertEquals(bigPit2.getNumberOfStones(), NO_STONES);
        assertEquals(bigPit2.getPlayer(), PLAYER_2);
        assertEquals(game.getActivePlayer(), PLAYER_2);

    }

    @Test
    public void testMoveGameEnds() {
        System.out.println("GameEnds");
        Board board = new Board();
        board.init();


        board.getPitsForPlayer(PLAYER_1).get(0).setNumberOfStones(0);
        board.getPitsForPlayer(PLAYER_1).get(1).setNumberOfStones(0);
        board.getPitsForPlayer(PLAYER_1).get(2).setNumberOfStones(0);
        board.getPitsForPlayer(PLAYER_1).get(3).setNumberOfStones(0);
        board.getPitsForPlayer(PLAYER_1).get(4).setNumberOfStones(0);
        board.getPitsForPlayer(PLAYER_1).get(5).setNumberOfStones(1);
        board.getBigPitPlayer1().setNumberOfStones(30);
        board.getBigPitPlayer2().setNumberOfStones(5);
        Game game = new Game(board);
        game.setSelectedPit(5);
        game.setActivePlayer(PLAYER_1);
        game.update();
        List<MiniPit> pits1 = game.getBoard().getPitsPlayer1();
        List<MiniPit> pits2 = game.getBoard().getPitsPlayer2();

        for (int i = 0; i < NUMBER_OF_SMALL_PITS; i++) {
            assertEquals(pits1.get(i).getNumberOfStones(), NO_STONES);
            assertEquals(pits2.get(i).getNumberOfStones(), NO_STONES);
            assertEquals(pits1.get(i).isActive(), false);
            assertEquals(pits2.get(i).isActive(), false);
        }

        assertTrue(game.isOver());
        assertEquals(board.getBigPitPlayer1().getNumberOfStones(), 31);
        assertEquals(board.getBigPitPlayer2().getNumberOfStones(), 41);
    }

}
