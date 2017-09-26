/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spleefleague.cosmetics.player;

import com.spleefleague.core.player.GeneralPlayer;
import com.spleefleague.cosmetics.Cosmetics;
import com.spleefleague.cosmetics.cosmetic.item.CosmeticBase;
import com.spleefleague.cosmetics.cosmetic.item.CosmeticBase.CosmeticSlot;
import com.spleefleague.cosmetics.cosmetic.item.CosmeticClothesArmor;
import com.spleefleague.cosmetics.cosmetic.item.CosmeticClothesHat;
import com.spleefleague.cosmetics.cosmetic.item.CosmeticEffectParticle;
import com.spleefleague.cosmetics.cosmetic.item.CosmeticEffectStatus;
import com.spleefleague.entitybuilder.DBEntity;
import com.spleefleague.entitybuilder.DBLoad;
import com.spleefleague.entitybuilder.DBLoadable;
import com.spleefleague.entitybuilder.DBSave;
import com.spleefleague.entitybuilder.DBSaveable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author NickM13
 */
public class CosmeticPlayer extends GeneralPlayer {
    private Map<String, CosmeticBase> unlockedCosmetics;
    private Player player;
    @DBLoad(fieldName="coins")
    private long coins;
    @DBLoad(fieldName="activeHat")
    private String activeHat;
    @DBLoad(fieldName="activeArmor")
    private String activeArmor;
    @DBLoad(fieldName="activeStatus")
    private String activeStatus;
    @DBLoad(fieldName="activeParticle")
    private String activeParticle;
    
    public CosmeticPlayer() {
        unlockedCosmetics = new HashMap<>();
        activeHat = "";
        activeArmor = "";
        activeStatus = "";
        activeParticle = "";
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Cosmetics.getInstance(), new Runnable() {
            @Override
            public void run() {
                if(activeStatus != "") {
                    Cosmetics.getInstance().getCosmetic(activeStatus).activateCosmetic(player);
                }
            }
        }, 0L, 100L);
    }
    
    public CosmeticPlayer(Player player) {
        this();
        this.player = player;
    }
    
    public Player getPlayer() {
        return player;
    }
    
    @DBSave(fieldName="username")
    public String getName() {
        return player.getName();
    }
    
    @DBLoad(fieldName="username")
    public void setName(String name) {
        player = Bukkit.getPlayer(name);
    }
    
    @DBSave(fieldName="unlocked")
    public ArrayList<String> getUnlockedCosmetics() {
        ArrayList<String> list = new ArrayList<String>();
        for(CosmeticBase cosmetic : unlockedCosmetics.values()) {
            list.add(cosmetic.getName());
        }
        return list;
    }
    
    @DBLoad(fieldName="unlocked")
    public void setUnlockedCosmetics(ArrayList<String> cosmetics) {
        for(String name : cosmetics) {
            System.out.println(name);
            unlockedCosmetics.put(name, Cosmetics.getInstance().getCosmetics().getCosmetic(name));
        }
    }
    
    public void changeCoins(long coins) {
        this.coins += coins;
    }
    
    @DBSave(fieldName="coins")
    public long getCoins() {
        return coins;
    }
    
    @DBSave(fieldName="activeHat")
    public String getActiveHat() {
        return activeHat;
    }
    
    @DBSave(fieldName="activeArmor")
    public String getActiveArmor() {
        return activeArmor;
    }
    
    @DBSave(fieldName="activeStatus")
    public String getActiveStatus() {
        return activeStatus;
    }
    
    @DBSave(fieldName="activeParticle")
    public String getActiveParticle() {
        return activeParticle;
    }
    
    public boolean hasCosmetic(CosmeticBase item) {
        return unlockedCosmetics.containsValue(item);
    }
    
    public boolean buyCosmetic(CosmeticBase item) {
        if(coins > item.getPrice()) {
            unlockedCosmetics.put(item.getName(), item);
            coins -= item.getPrice();
            player.sendMessage("You bought " + item.getName() + "!");
            player.sendMessage(coins + " coins remaining.");
            return true;
        }
        player.sendMessage(ChatColor.RED + "You dont have enough coins!");
        return false;
    }
    
    public void applyCosmetics() {
        ItemStack air = new ItemStack(Material.AIR);
        if(activeHat.equals("")) {
            player.getInventory().setHelmet(air);
        } else {
            CosmeticClothesHat hat = (CosmeticClothesHat) Cosmetics.getInstance().getCosmetic(activeHat);
            hat.activateCosmetic(player);
        }
        if(activeArmor.equals("")) {
            player.getInventory().setChestplate(air);
            player.getInventory().setLeggings(air);
            player.getInventory().setBoots(air);
        } else {
            CosmeticClothesArmor armor = (CosmeticClothesArmor) Cosmetics.getInstance().getCosmetic(activeArmor);
            armor.activateCosmetic(player);
        }
        if(activeStatus.equals("")) {
        } else {
            CosmeticEffectStatus status = (CosmeticEffectStatus) Cosmetics.getInstance().getCosmetic(activeStatus);
            status.activateCosmetic(player);
        }
        if(activeParticle.equals("")) {
        } else {
            CosmeticEffectParticle particle = (CosmeticEffectParticle) Cosmetics.getInstance().getCosmetic(activeParticle);
            particle.activateCosmetic(player);
        }
    }
    
    public void activateCosmetic(CosmeticBase item) {
        item.activateCosmetic(player);
        switch(item.getSlot()) {
            case Hat:
                activeHat = item.getName();
                break;
            case Armor:
                activeArmor = item.getName();
                break;
            case Status:
                activeStatus = item.getName();
                break;
            case Particle:
                activeParticle = item.getName();
                break;
        }
    }
    
    public void makeParticleEffect() {
        if(activeParticle != "") {
            Cosmetics.getInstance().getCosmetic(activeParticle);
        }
    }
    
    public void deactivateCosmetic(CosmeticSlot slot) {
        ItemStack none = new ItemStack(Material.AIR);
        switch(slot) {
            case Hat:
                player.getInventory().setHelmet(none);
                activeHat = "";
                break;
            case Armor:
                player.getInventory().setChestplate(none);
                player.getInventory().setLeggings(none);
                player.getInventory().setBoots(none);
                activeArmor = "";
                break;
            case Status:
                activeStatus = "";
                break;
            case Particle:
                activeParticle = "";
                break;
        }
    }
    
    public void deactivateCosmeticSlot(int slot) {
        switch(slot) {
            case 39:
                if(activeHat != "") {
                    player.getInventory().setHelmet(new ItemStack(Material.AIR));
                    player.sendMessage("Removed hat.");
                    activeHat = "";
                }
                break;
            case 38:
            case 37:
            case 36:
                if(activeArmor != "") {
                    player.getInventory().setChestplate(new ItemStack(Material.AIR));
                    player.getInventory().setLeggings(new ItemStack(Material.AIR));
                    player.getInventory().setBoots(new ItemStack(Material.AIR));
                    player.sendMessage("Removed armor.");
                    activeArmor = "";
                }
                break;
        }
    }
}
