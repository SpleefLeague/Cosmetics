/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spleefleague.cosmetics.cosmetic.item;

import com.spleefleague.entitybuilder.DBEntity;
import com.spleefleague.entitybuilder.DBLoad;
import com.spleefleague.entitybuilder.DBLoadable;
import com.spleefleague.entitybuilder.DBSaveable;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author NickM13
 */

public class CosmeticBase extends DBEntity implements DBLoadable, DBSaveable {
    public enum CosmeticType {
        Clothes,
        Effect
    }
    public enum CosmeticSlot {
        Hat,
        Armor,
        Particle,
        Status
    }
    @DBLoad(fieldName="_id")
    protected long id;
    @DBLoad(fieldName="name")
    protected String name;
    @DBLoad(fieldName="description")
    protected String description;
    @DBLoad(fieldName="type")
    protected CosmeticType type;
    @DBLoad(fieldName="slot")
    protected CosmeticSlot slot;
    @DBLoad(fieldName="price")
    protected long price;
    @DBLoad(fieldName="material")
    protected Material material;
    protected int purchaseMenuPos;
    
    public CosmeticBase() {
        
    }
    
    public void activateCosmetic(Player p) {
        
    }
    
    public void deactivateCosmetic(Player p) {
        
    }
    
    public long getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public CosmeticType getType() {
        return type;
    }
    
    public CosmeticSlot getSlot() {
        return slot;
    }
    
    public long getPrice() {
        return price;
    }
    
    public Material getMaterial() {
        return material;
    }
    
    public ItemStack getItemStack() {
        return new ItemStack(material);
    }
    
    public void setPurchaseMenuPos(int purchaseMenuPos) {
        this.purchaseMenuPos = purchaseMenuPos;
    }
    
    public int getPurchaseMenuPos() {
        return purchaseMenuPos;
    }
}
