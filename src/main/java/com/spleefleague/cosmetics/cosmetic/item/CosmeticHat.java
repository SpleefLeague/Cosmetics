/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spleefleague.cosmetics.cosmetic.item;

import com.spleefleague.entitybuilder.DBLoad;
import com.spleefleague.entitybuilder.DBLoadable;
import java.util.Arrays;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author NickM13
 */
public class CosmeticHat extends CosmeticBase implements DBLoadable {
    @DBLoad(fieldName="metadata")
    int metadata;
    
    @Override
    public void activateCosmetic(Player player) {
        ItemStack item = new ItemStack(material, 1, (short)metadata);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(ChatColor.AQUA + description));
        item.setItemMeta(meta);
        player.getInventory().setHelmet(item);
        player.sendMessage(ChatColor.GREEN + "Hat " + ChatColor.YELLOW + name + ChatColor.GREEN + " put on.");
    }
    
    @Override
    public void deactivateCosmetic(Player player) {
        ItemStack item = new ItemStack(Material.AIR);
        player.getInventory().setHelmet(item);
        player.sendMessage(ChatColor.GREEN + "Hat taken off.");
    }
}
