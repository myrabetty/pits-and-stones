package com.game.components;

import static com.game.components.Board.NO_STONES;
import static com.game.components.Board.NUMBER_OF_SMALL_PITS;
import com.game.components.Player.Order;
import static com.game.components.Player.Order.PLAYER_1;
import static com.game.components.Player.Order.PLAYER_2;
import java.util.List;

/**
 *
 * @author elisabetta
 */
public class Game {

    private Order activePlayer;
    private boolean over;
    private Player player1;
    private Player player2;
    private Board board;
    private int selectedPit;
    private String mac;

    public Game() {

    }

    public Game(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    public Order getActivePlayer() {
        return activePlayer;
    }

    public boolean isOver() {
        return over;
    }

    public int getSelectedPit() {
        return selectedPit;
    }

    public void setActivePlayer(Order activePlayer) {
        this.activePlayer = activePlayer;
    }

    public void setOver(boolean gameOver) {
        this.over = gameOver;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    /**
     * attach players to game, set game in start state.
     *
     * @param player1
     * @param player2
     */
    public void start(Player player1, Player player2) {
        player1.setPlayerId(PLAYER_1);
        player2.setPlayerId(PLAYER_2);
        this.player1 = player1;
        this.player2 = player2;
        setActivePlayer(PLAYER_1);
    }

    public void setSelectedPit(int selectedPit) {
        this.selectedPit = selectedPit;
    }

    /**
     * update the state of the game.
     *
     */
    public void update() {
        PitCoordinate selected = new PitCoordinate(getActivePlayer(), getSelectedPit());
        checkConsistency(selected);
        PitCoordinate finalCoord = performMove(selected);
        updateGame(finalCoord);
    }

    /**
     * it will update the state of the game
     *
     */
    private void updateGame(PitCoordinate finalCoord) {
        setOver(gameOver());
        if (isOver()) {
            end();
        } else {
            setActivePlayer(determineActivePlayer(finalCoord));
        }
    }

    /**
     * it will return true if the game is over. I.e. all pits of active player
     * are empty
     *
     * @return
     */
    private boolean gameOver() {
        List<MiniPit> list = board.getPitsForPlayer(getActivePlayer());
        int stonesLeft = list.stream().mapToInt(p -> p.getNumberOfStones()).sum();
        return stonesLeft == 0;
    }

    /**
     * it will perform the complete move of the selected stones and capture
     * stones if applies
     *
     * @return The {@link PitCoordinate} of the pit into which the last stone
     * was placed.
     */
    private PitCoordinate performMove(PitCoordinate selected) {
        PitCoordinate finalCoord = exhaustStones(selected);
        captureStones(finalCoord);
        return finalCoord;
    }

    /**
     * it will check the consistency of the request to update the board state
     *
     * @param selected The {@link PitCoordinate} of the pit that was selected by
     * the active player.
     * @return true if the input is consistent otherwise false
     */
    private void checkConsistency(PitCoordinate selected) {
        if (!(getActivePlayer() == PLAYER_1 || getActivePlayer() == PLAYER_2)) {
            throw new IllegalArgumentException();
        }
        if (selected.getPitId() < 0 || selected.getPitId() > NUMBER_OF_SMALL_PITS) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * empties the selected pit
     *
     * @param selected The {@link PitCoordinate} of the pit that was selected by
     * the active player.
     * @return The number of stones removed from the selected pit.
     */
    private int emptySelectedPit(PitCoordinate selected) {
        int nStones = board.getPit(selected).getNumberOfStones();
        board.getPit(selected).setNumberOfStones(NO_STONES);
        return nStones;
    }

    /**
     * it will distribute the stones in the pits until none is left.
     *
     * @param selected The {@link PitCoordinate} of the pit that was selected by
     * the active player.
     * @return The coordinate of the last pit into which a stone is placed.
     */
    private PitCoordinate exhaustStones(PitCoordinate selected) {
        int numberOfStones = emptySelectedPit(selected);
        PitCoordinate coord = new PitCoordinate(selected);
        while (numberOfStones > 0) {
            coord = getNextPitCoord(coord);
            Pit pit = board.getPit(coord);
            boolean added = pit.addStone(getActivePlayer());
            if (added) {
                numberOfStones--;
            }
        }
        return coord;
    }

    /**
     * given input pit coordinates it will return the coordinates of the next
     * pit in the board.
     *
     * @param coord {@link PitCoordinate} the coordinates of the current pit
     * @return PitCoordinate the coordinates of the adjacent pit.
     */
    private PitCoordinate getNextPitCoord(PitCoordinate coord) {
        if (coord.getPitId() == NUMBER_OF_SMALL_PITS) {
            return changeBoardSide(coord);
        }
        return (new PitCoordinate(coord.getPlayerId(), coord.getPitId() + 1));
    }

    /**
     * it will change the id of the player in the new set of coordinates, if the
     * current set of coordinates indicates the last pit attached to the player.
     *
     * @param newCoord
     * @param coord
     */
    private PitCoordinate changeBoardSide(PitCoordinate coord) {
        PitCoordinate newCoord = new PitCoordinate();
        newCoord.setPitId(0);
        newCoord.setPlayerId(coord.getPlayerId().switchPlayer());
        return newCoord;
    }

    /**
     * it will perform the capture of stones if it applies, i.e. move the stone
     * from the pit and from the opposite pit to the big pit.
     *
     * @param finalCoord the coordinates of the pit where the last stone landed
     */
    private void captureStones(PitCoordinate finalCoord) {
        int numberOfStones = board.getPit(finalCoord).getNumberOfStones();
        if (getActivePlayer() == finalCoord.getPlayerId() && finalCoord.getPitId() < NUMBER_OF_SMALL_PITS && numberOfStones == 1) {
            performCapture(finalCoord);
        }
    }

    /**
     * will capture the stones from the pit with coordinates opposite to the pit
     * with {@link PitCoordinate} coord
     *
     * @param coord
     */
    private void performCapture(PitCoordinate coord) {
        Pit oppositePit = board.getPit(new PitCoordinate(getActivePlayer().switchPlayer(), getOppositePit(coord.getPitId())));
        int capturedStones = oppositePit.getNumberOfStones();
        oppositePit.setNumberOfStones(NO_STONES);
        board.getPit(coord).setNumberOfStones(NO_STONES);
        BigPit bigPit = board.getBigPitForPlayer(getActivePlayer());
        int newNumberOfStones = bigPit.getNumberOfStones() + capturedStones + 1;
        bigPit.setNumberOfStones(newNumberOfStones);
    }

    /**
     * given a pitId it returns the id of the opposite pit
     *
     * @param pitId
     * @return
     */
    private int getOppositePit(int pitId) {
        return (NUMBER_OF_SMALL_PITS - 1 - pitId);
    }

    /**
     * determines the player active for next turn {@link finalCoord} where the
     * last sone landed
     *
     * @return
     */
    private Order determineActivePlayer(PitCoordinate finalCoord) {
        if (finalCoord.getPitId() == NUMBER_OF_SMALL_PITS) {
            return finalCoord.getPlayerId();
        }
        return getActivePlayer().switchPlayer();
    }


    /**
     * will set the state of the game in end state
     */
    private void end() {
        finalUpdate(PLAYER_1);
        finalUpdate(PLAYER_2);
    }

    /**
     * given a playerId, it will perform for that player the final update of the
     * board: move all the stones in the big pit and set the pits to disabled.
     *
     * @param playerId
     */
    private void finalUpdate(Order playerId) {
        int totalStones = board.getPitsForPlayer(playerId).stream().mapToInt(p -> p.getNumberOfStones()).sum();
        BigPit pit = board.getBigPitForPlayer(playerId);
        int finalScore = pit.getNumberOfStones() + totalStones;
        pit.setNumberOfStones(finalScore);
        board.getPitsForPlayer(playerId).stream().forEach(p -> p.setNumberOfStones(NO_STONES));
    }


    @Override
    public String toString() {
        return "Game{" + "activePlayer=" + activePlayer + ", board=" + board + '}';
    }

    /**
     * set a list of pits to active
     *
     * @param list of {@link MiniPits} to activate
     */
    private void activatePits(List<MiniPit> list) {
        list.stream().forEach((MiniPit pit) -> {
            if (pit.getNumberOfStones() > 0) {
                pit.setActive(true);
            } else {
                pit.setActive(false);
            }
        });
    }

    /** it will set the final view of the game for a player.
     * playing
     *
     * @param playerId
     */
    public Game setActiveForPlayer(Order playerId) {
        Board board = getBoard();
        if (getActivePlayer() != playerId) {
            deactivatePits(board.getPitsPlayer1());
            deactivatePits(board.getPitsPlayer2());
        } else if (getActivePlayer() == Order.PLAYER_1) {
            activatePits(board.getPitsPlayer1());
        } else {
            activatePits(board.getPitsPlayer2());
        }
        return this;
    }

    /**
     * set a list of pits to inactive
     *
     * @param list of {@link MiniPits} to deactivate
     */
    private void deactivatePits(List<MiniPit> list) {
        list.stream().forEach((MiniPit pit) -> {
            pit.setActive(false);
        });
    }
}
