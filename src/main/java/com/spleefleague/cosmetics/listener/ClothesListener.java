/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spleefleague.cosmetics.listener;

import com.spleefleague.cosmetics.Cosmetics;
import com.spleefleague.cosmetics.player.CosmeticPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 *
 * @author NickM13
 */
public class ClothesListener implements Listener {
    private static ClothesListener instance;
    
    public static void init() {
        if(instance == null) {
            instance = new ClothesListener();
            Bukkit.getPluginManager().registerEvents(instance, Cosmetics.getInstance());
        }
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void removeClothes(InventoryClickEvent event) {
        if(event.getSlot() >= 36 && event.getSlot() <= 39) {
            CosmeticPlayer player = Cosmetics.getInstance().getPlayer((Player) event.getWhoClicked());
            player.deactivateCosmeticSlot(event.getSlot());
            event.setCancelled(true);
        }
    }
}
