package com.game.components;

import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

/**
 *
 * @author elisabetta
 */
@Component
@ApplicationScope
public class Queue {
    private final LinkedBlockingQueue<Player> queue = new LinkedBlockingQueue<>();
    
    /*it will attempt to find an opponent in the {@link LinkedBlockingQueue} queue for an incoming {@link Player} player.
     * If not opponent is found the player is added to the queue
     * @param the {@link Player} accessing the queue 
     * @return the opponent {@link Player}  
     */
    public synchronized Optional<Player> managePlayers(Player player){
        if (queue.isEmpty()) {
            queue.add(player);
            return Optional.empty();
        }
        return Optional.of(queue.remove());       
    };    
}
