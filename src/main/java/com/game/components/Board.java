package com.game.components;

import static com.game.components.MiniPit.INIT_NUMBER_OF_STONES;
import com.game.components.Player.Order;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 *
 * @author elisabetta
 */
public class Board {


    protected static final int NUMBER_OF_SMALL_PITS = 6;
    protected static final int NO_STONES = 0;
    private List<MiniPit> pitsPlayer1 = new ArrayList();
    private List<MiniPit> pitsPlayer2 = new ArrayList();
    private BigPit bigPitPlayer1;
    private BigPit bigPitPlayer2;

    public Board() {
    }

    public List<MiniPit> getPitsPlayer1() {
        return pitsPlayer1;
    }

    public List<MiniPit> getPitsPlayer2() {
        return pitsPlayer2;
    }

    public BigPit getBigPitPlayer1() {
        return bigPitPlayer1;
    }

    public void setBigPitPlayer1(BigPit bigPitPlayer1) {
        this.bigPitPlayer1 = bigPitPlayer1;
    }

    public BigPit getBigPitPlayer2() {
        return bigPitPlayer2;
    }

    public void setBigPitPlayer2(BigPit bigPitPlayer2) {
        this.bigPitPlayer2 = bigPitPlayer2;
    }

    /**
     * it constructs a board.
     *
     */
    public void init() {
        pitsPlayer1 = addPits();
        pitsPlayer2 = addPits();
        bigPitPlayer1 = new BigPit(Order.PLAYER_1, NO_STONES);
        bigPitPlayer2 = new BigPit(Order.PLAYER_2, NO_STONES);
    }

    /**
     * given the {@link PitCoordinate} that identify a pit it will return the
     * pit. pitId 0-5 it will return the small pits, when id = 6 it will return
     * the bigPit
     *
     * @param coord
     * @return the Pit instance associated to the input coordinates
     */
    protected Pit getPit(PitCoordinate coord) {
        Order playerId = coord.getPlayerId();
        int pitId = coord.getPitId();
        if (pitId < NUMBER_OF_SMALL_PITS) {
            return getPitsForPlayer(playerId).get(pitId);
        } else if (pitId == NUMBER_OF_SMALL_PITS) {
            return getBigPitForPlayer(playerId);
        }
        return null;
    }

    /**
     * it will return the pits associated to the input playerId
     *
     * @param playerId
     * @return the list of pits associated to a player
     */
    public List<MiniPit> getPitsForPlayer(Order playerId) {
        switch (playerId) {
            case PLAYER_1:
                return getPitsPlayer1();
            case PLAYER_2:
                return getPitsPlayer2();
            default:
                return Collections.emptyList();
        }
    }

    /**
     * it gets the bigPit associated to the player
     *
     * @param playerId
     * @return
     */
    public BigPit getBigPitForPlayer(Order playerId) {
        switch (playerId) {
            case PLAYER_1:
                return getBigPitPlayer1();
            case PLAYER_2:
                return getBigPitPlayer2();
            default:
                return null;
        }
    }

    /**
     * it will return the {@link List} of pits in the init state.
     *
     * @return list of {@link MiniPit}
     */
    private static List<MiniPit> addPits() {
        List list = new ArrayList();
        for (int i = 0; i < NUMBER_OF_SMALL_PITS; i++) {
            MiniPit bean = new MiniPit(INIT_NUMBER_OF_STONES);
            bean.setActive(false);
            list.add(bean);
        }
        return list;
    }
    
    
    @Override
    public String toString() {
        return "Board{" 
                + "pitsPlayer1=" + pitsPlayer1 + ", "
                + "pitsPlayer2=" + pitsPlayer2 + ", "
                + "bigPitPlayer1=" + bigPitPlayer1 + ", "
                + "bigPitPlayer2=" + bigPitPlayer2 + '}';
    }

    /**
     * Returns all the pits on the board. The first pit in the list is the left 
     * most pit of player 1.
     * @return All the pits on the board.
     */
    List<Pit> getAllPits() {
        List<Pit> looped = new ArrayList<>();
        looped.addAll(pitsPlayer1);
        looped.add(bigPitPlayer1);
        looped.addAll(pitsPlayer2);
        looped.add(bigPitPlayer2);
        return looped;
    }
}
