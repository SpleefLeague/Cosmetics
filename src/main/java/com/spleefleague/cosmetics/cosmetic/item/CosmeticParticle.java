/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spleefleague.cosmetics.cosmetic.item;

import com.spleefleague.entitybuilder.DBLoad;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 *
 * @author NickM13
 */
public class CosmeticParticle extends CosmeticBase {
    @DBLoad(fieldName="effect")
    Particle effect;
    
    public CosmeticParticle() {
        super();
    }
    
    @Override
    public void activateCosmetic(Player player) {
        Vector deviation = new Vector(Math.random() - 0.5, 0, Math.random() - 0.5);
        player.getWorld().spawnParticle(effect, player.getLocation().add(deviation), 50);
    }
    
    @Override
    public void deactivateCosmetic(Player player) {
        
    }
}
