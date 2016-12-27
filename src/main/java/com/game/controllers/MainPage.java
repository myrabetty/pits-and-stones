package com.game.controllers;

import com.game.components.Board;
import com.game.components.Game;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 *
 * @author elisabetta
 */
@Controller
public class MainPage {

    /** sets the initial view
     * 
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/", method = GET)
    public String getMainPage(ModelMap model, HttpSession session) {

        Board board = new Board();
        board.init();
        Game game = new Game(board);
        model.addAttribute("sessionId", session.getId());
        model.addAttribute("board", game.getBoard());
        return "mainPage";
    }

    /* old style controller to update board. Not used anymore*/
    /*@RequestMapping(value = "/init", method = GET)
    @ResponseBody
    public Game getInitBoard(HttpSession session) {
        Board board = new Board();
        board.init();
        Game game = new Game(board);
        game.setMac(generateMac(game.toString()));
        return game;
    }*/

    /*@RequestMapping(value = "/", method = POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Game getMainPage(ModelMap model,
            @RequestBody Game game) {
        try {
            verifyMac(game.toString(), game.getMac());           
            game.update();
            Board board = game.getBoard();
            game.setMac(generateMac(board.toString()));
            return game;
        } catch (IllegalArgumentException e) {
            Board newBoard = new Board();
            newBoard.init();
            return game;
        }
    }*/
}
