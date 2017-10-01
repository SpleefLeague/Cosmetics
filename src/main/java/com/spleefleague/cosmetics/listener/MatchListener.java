/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spleefleague.cosmetics.listener;

import com.spleefleague.core.events.BattleEndEvent;
import com.spleefleague.cosmetics.Cosmetics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 *
 * @author NickM13
 */
public class MatchListener implements Listener {
    private static MatchListener instance;
    
    public static void init() {
        if(instance == null) {
            instance = new MatchListener();
            Bukkit.getPluginManager().registerEvents(instance, Cosmetics.getInstance());
        }
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void statusEffectListener(BattleEndEvent e) {
        e.getBattle().getPlayers().forEach((p) -> {
            Cosmetics.getInstance().getPlayer((Player) p).applyCosmetics();
        });
    }
}
