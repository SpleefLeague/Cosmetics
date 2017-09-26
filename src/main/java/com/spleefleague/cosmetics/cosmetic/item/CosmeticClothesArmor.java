/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spleefleague.cosmetics.cosmetic.item;

import com.spleefleague.entitybuilder.DBLoad;
import java.util.Arrays;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author NickM13
 */
public class CosmeticClothesArmor extends CosmeticBase {
    @DBLoad(fieldName="armor")
    List<Material> armor;
    
    @Override
    public void activateCosmetic(Player player) {
        ItemStack chestplate = new ItemStack(armor.get(0));
        chestplate.getItemMeta().setDisplayName(name);
        chestplate.getItemMeta().setLore(Arrays.asList(description));
        ItemStack leggings = new ItemStack(armor.get(1));
        leggings.getItemMeta().setDisplayName(name);
        leggings.getItemMeta().setLore(Arrays.asList(description));
        ItemStack boots = new ItemStack(armor.get(2));
        boots.getItemMeta().setDisplayName(name);
        boots.getItemMeta().setLore(Arrays.asList(description));
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setBoots(boots);
        player.sendMessage(ChatColor.GREEN + "Armor set " + ChatColor.YELLOW + name + ChatColor.GREEN + " put on.");
    }
    
    @Override
    public void deactivateCosmetic(Player player) {
        ItemStack item = new ItemStack(Material.AIR);
        player.getInventory().setChestplate(item);
        player.getInventory().setLeggings(item);
        player.getInventory().setBoots(item);
        player.sendMessage(ChatColor.GREEN + "Armor set taken off.");
    }
}
