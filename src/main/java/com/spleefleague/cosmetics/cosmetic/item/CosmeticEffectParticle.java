/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spleefleague.cosmetics.cosmetic.item;

import com.spleefleague.entitybuilder.DBLoad;
import org.bukkit.Effect;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author NickM13
 */
public class CosmeticEffectParticle extends CosmeticBase {
    @DBLoad(fieldName="effect")
    Particle effect;
    
    public CosmeticEffectParticle() {
        super();
    }
    
    @Override
    public void activateCosmetic(Player player) {
        player.spawnParticle(effect, player.getLocation(), 5);
    }
    
    @Override
    public void deactivateCosmetic(Player player) {
        
    }
}
