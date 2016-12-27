package com.game.controllers;

import com.game.components.Board;
import com.game.components.Game;
import static com.game.components.HashingUtility.generateMac;
import static com.game.components.HashingUtility.verifyMac;
import com.game.components.Player;
import com.game.components.Queue;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 *
 * @author elisabetta
 */
@Controller
public class GameController {

    @Autowired
    Queue queue;

    @Autowired
    SimpMessagingTemplate template;

    /**
     * it will handle the login of a new player and start the game when ready
     *
     * @param player
     */
    @MessageMapping("/game-login")
    public void login(Player player) {
        Optional<Player> opponent = queue.managePlayers(player);
        if (opponent.isPresent()) {
            startGame(opponent.get(), player);
        }
    }

    /**
     *
     * it receives the game, it performs the state update and post the final
     * state to both players.
     *
     * @param game
     * @throws Exception
     */
    @MessageMapping("/perform-move-server")
    public void server(Game game) throws Exception {

        Player player1 = game.getPlayer1();
        Player player2 = game.getPlayer2();
        try {
            verifyMac(game.toString(), game.getMac());
            game.update();
            Board board = game.getBoard();
            game.setMac(generateMac(game.toString()));
            template.convertAndSend("/receive-update-client/" + player1.getSessionId(), game.setActiveForPlayer(player1.getPlayerId()));
            template.convertAndSend("/receive-update-client/" + player2.getSessionId(), game.setActiveForPlayer(player2.getPlayerId()));
        } catch (IllegalArgumentException e) {
            startGame(player1, player2);
        }

    }

    /**
     * it will post to both players the game when the game in start state
     *
     * @param opponent
     * @param player
     */
    private void startGame(Player opponent, Player player) {
        Board board = new Board();
        board.init();
        Game game = new Game(board);
        game.start(opponent, player);
        game.setMac(generateMac(game.toString()));
        template.convertAndSend("/receive-update-client/" + player.getSessionId(), game.setActiveForPlayer(player.getPlayerId()));
        template.convertAndSend("/receive-update-client/" + opponent.getSessionId(), game.setActiveForPlayer(opponent.getPlayerId()));
    }
;

}
