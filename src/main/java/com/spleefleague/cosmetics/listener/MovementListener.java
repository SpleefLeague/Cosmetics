/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spleefleague.cosmetics.listener;

import com.spleefleague.cosmetics.Cosmetics;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 *
 * @author NickM13
 */
public class MovementListener implements Listener {
    private static MovementListener instance;
    
    public static void init() {
        if(instance == null)
        {
            instance = new MovementListener();
            Bukkit.getPluginManager().registerEvents(instance, Cosmetics.getInstance());
        }
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void statusEffectListener(PlayerMoveEvent e) {
        Cosmetics.getInstance().getPlayer(e.getPlayer()).makeParticleEffect();
    }
}
