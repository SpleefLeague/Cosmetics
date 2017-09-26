/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spleefleague.cosmetics.listener;

import com.spleefleague.cosmetics.Cosmetics;
import com.spleefleague.cosmetics.player.CosmeticPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 *
 * @author NickM13
 */
public class LoginListener implements Listener {
    private static LoginListener instance;
    
    public static void init() {
        if(instance == null) {
            instance = new LoginListener();
            Bukkit.getPluginManager().registerEvents(instance, Cosmetics.getInstance());
        }
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void playerJoin(PlayerJoinEvent e) {
        CosmeticPlayer cplayer = Cosmetics.getInstance().getPlayer(e.getPlayer());
        if(cplayer != null) {
                cplayer.applyCosmetics();
        }
    }
}
