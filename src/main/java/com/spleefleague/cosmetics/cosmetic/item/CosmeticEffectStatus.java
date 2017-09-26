/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spleefleague.cosmetics.cosmetic.item;

import com.spleefleague.entitybuilder.DBLoad;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author NickM13
 */
public class CosmeticEffectStatus extends CosmeticBase {
    @DBLoad(fieldName="effect")
    String effect;
    
    public CosmeticEffectStatus() {
        super();
    }
    
    @Override
    public void activateCosmetic(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(effect), 100, 1, true, false), true);
    }
    
    @Override
    public void deactivateCosmetic(Player player) {
        
    }
}
