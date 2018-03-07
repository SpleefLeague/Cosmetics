/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spleefleague.cosmetics.player;

import com.spleefleague.core.SpleefLeague;
import com.spleefleague.core.player.GeneralPlayer;
import com.spleefleague.core.player.SLPlayer;
import com.spleefleague.cosmetics.Cosmetics;
import com.spleefleague.cosmetics.cosmetic.item.CosmeticBase;
import com.spleefleague.cosmetics.cosmetic.item.CosmeticBase.CosmeticSlot;
import com.spleefleague.cosmetics.cosmetic.item.CosmeticArmor;
import com.spleefleague.cosmetics.cosmetic.item.CosmeticHat;
import com.spleefleague.cosmetics.cosmetic.item.CosmeticParticle;
import com.spleefleague.cosmetics.cosmetic.item.CosmeticStatus;
import com.spleefleague.entitybuilder.DBLoad;
import com.spleefleague.entitybuilder.DBSave;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author NickM13
 */
public class CosmeticPlayer extends GeneralPlayer {
    private Map<String, CosmeticBase> unlockedCosmetics;
    @DBLoad(fieldName="activeHat")
    private String activeHat;
    @DBLoad(fieldName="activeArmor")
    private String activeArmor;
    @DBLoad(fieldName="activeStatus")
    private String activeStatus;
    @DBLoad(fieldName="activeParticle")
    private String activeParticle;
    private long lastParticleTime = 0;
    
    public CosmeticPlayer() {
        unlockedCosmetics = new HashMap<>();
    }
    
    public static void init() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Cosmetics.getInstance(), () -> {
            Cosmetics.getInstance().getPlayerManager().getAll().forEach((player) -> {
                    if(player.activeStatus == null) {
                        Cosmetics.getInstance().getCosmetic(player.activeStatus).activateCosmetic(player);
                    }
                });
        }, 20, 20);
    }
    
    @DBSave(fieldName="unlocked")
    public List<String> getUnlockedCosmetics() {
        List<String> list = new ArrayList<>();
        unlockedCosmetics.values().forEach((cosmetic) -> {
            list.add(cosmetic.getName());
        });
        return list;
    }
    
    @DBLoad(fieldName="unlocked")
    public void setUnlockedCosmetics(List<String> cosmetics) {
        cosmetics.forEach((name) -> {
            unlockedCosmetics.put(name, Cosmetics.getInstance().getCosmetics().getCosmetic(name));
        });
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
    
    public boolean hasCosmetic(String name) {
        return unlockedCosmetics.containsKey(name);
    }
    
    public boolean hasCosmetic(CosmeticBase item) {
        return unlockedCosmetics.containsValue(item);
    }
    
    public boolean buyCosmetic(CosmeticBase item) {
        SLPlayer slp = SpleefLeague.getInstance().getPlayerManager().get(this.getPlayer());
        if(slp.getCoins() >= item.getPrice()) {
            unlockedCosmetics.put(item.getName(), item);
            slp.changeCoins(-item.getPrice());
            this.getPlayer().sendMessage("You bought " + item.getName() + "!");
            this.getPlayer().sendMessage(slp.getCoins() + " coins remaining.");
            return true;
        }
        this.getPlayer().sendMessage(ChatColor.RED + "You dont have enough coins!");
        return false;
    }
    
    public void applyCosmetics() {
        ItemStack air = new ItemStack(Material.AIR);
        if(activeHat == null) {
            this.getPlayer().getInventory().setHelmet(air);
        } else {
            CosmeticHat hat = (CosmeticHat) Cosmetics.getInstance().getCosmetic(activeHat);
            hat.activateCosmetic(this.getPlayer());
        }
        if(activeArmor == null) {
            this.getPlayer().getInventory().setChestplate(air);
            this.getPlayer().getInventory().setLeggings(air);
            this.getPlayer().getInventory().setBoots(air);
        } else {
            CosmeticArmor armor = (CosmeticArmor) Cosmetics.getInstance().getCosmetic(activeArmor);
            armor.activateCosmetic(this.getPlayer());
        }
        if(activeStatus == null) {
        } else {
            CosmeticStatus status = (CosmeticStatus) Cosmetics.getInstance().getCosmetic(activeStatus);
            status.activateCosmetic(this.getPlayer());
        }
        if(activeParticle == null) {
        } else {
            CosmeticParticle particle = (CosmeticParticle) Cosmetics.getInstance().getCosmetic(activeParticle);
            particle.activateCosmetic(this.getPlayer());
        }
    }
    
    public void hideCosmetics() {
        ItemStack air = new ItemStack(Material.AIR);
        this.getPlayer().getInventory().setHelmet(air);
        this.getPlayer().getInventory().setChestplate(air);
        this.getPlayer().getInventory().setLeggings(air);
        this.getPlayer().getInventory().setBoots(air);
    }
    
    public void activateCosmetic(CosmeticBase item) {
        item.activateCosmetic(this.getPlayer());
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
        if(activeParticle != null && lastParticleTime < System.currentTimeMillis()) {
            lastParticleTime = System.currentTimeMillis() + 100;
            Cosmetics.getInstance().getCosmetic(activeParticle).activateCosmetic(this.getPlayer());
        }
    }
    
    public void deactivateCosmetic(CosmeticSlot slot) {
        ItemStack none = new ItemStack(Material.AIR);
        switch(slot) {
            case Hat:
                this.getPlayer().getInventory().setHelmet(none);
                activeHat = null;
                break;
            case Armor:
                this.getPlayer().getInventory().setChestplate(none);
                this.getPlayer().getInventory().setLeggings(none);
                this.getPlayer().getInventory().setBoots(none);
                activeArmor = null;
                break;
            case Status:
                activeStatus = null;
                break;
            case Particle:
                activeParticle = null;
                break;
        }
    }
    
    public void deactivateCosmeticSlot(int slot) {
        switch(slot) {
            case 39:
                if(activeHat != null) {
                    this.getPlayer().getInventory().setHelmet(new ItemStack(Material.AIR));
                    this.getPlayer().sendMessage("Removed hat.");
                    activeHat = null;
                }
                break;
            case 38:
            case 37:
            case 36:
                if(activeArmor != null) {
                    this.getPlayer().getInventory().setChestplate(new ItemStack(Material.AIR));
                    this.getPlayer().getInventory().setLeggings(new ItemStack(Material.AIR));
                    this.getPlayer().getInventory().setBoots(new ItemStack(Material.AIR));
                    this.getPlayer().sendMessage("Removed armor.");
                    activeArmor = null;
                }
                break;
        }
    }
}
