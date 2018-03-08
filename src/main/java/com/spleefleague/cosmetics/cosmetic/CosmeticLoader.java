/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spleefleague.cosmetics.cosmetic;

import com.spleefleague.cosmetics.cosmetic.item.CosmeticHat;
import com.spleefleague.cosmetics.cosmetic.item.CosmeticBase;
import com.mongodb.client.MongoCursor;
import com.spleefleague.cosmetics.Cosmetics;
import com.spleefleague.cosmetics.cosmetic.item.CosmeticArmor;
import com.spleefleague.cosmetics.cosmetic.item.CosmeticParticle;
import com.spleefleague.cosmetics.cosmetic.item.CosmeticStatus;
import com.spleefleague.entitybuilder.DBLoadable;
import com.spleefleague.entitybuilder.DBSaveable;
import com.spleefleague.entitybuilder.EntityBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;
import org.bson.Document;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author NickM13
 */
public class CosmeticLoader implements DBLoadable, DBSaveable {
    private Map<String, CosmeticBase> cosmeticItems;
    
    public void init() {
        cosmeticItems = new HashMap<>();
        int cosmeticCount = 0;
        MongoCursor<Document> cosmeticDbc = Cosmetics.getInstance().getPluginDB().getCollection("Cosmetics").find(new Document("slot", "Hat")).iterator();
        while (cosmeticDbc.hasNext()) {
            Document d = cosmeticDbc.next();
            try {
                CosmeticHat item = loadCosmeticClothesHat(d);
                if (item == null) continue;
                item.setPurchaseMenuPos(cosmeticCount);
                addCosmetic(item);
                cosmeticCount++;
            } catch(Exception e) {
                Cosmetics.getInstance().log("Error loading " + d.get("name"));
            }
        }
        Cosmetics.getInstance().log("Loaded " + cosmeticCount + " hats!");
        cosmeticCount = 0;
        cosmeticDbc = Cosmetics.getInstance().getPluginDB().getCollection("Cosmetics").find(new Document("slot", "Armor")).iterator();
        while (cosmeticDbc.hasNext()) {
            Document d = cosmeticDbc.next();
            try {
                CosmeticArmor item = loadCosmeticClothesArmor(d);
                if (item == null) continue;
                item.setPurchaseMenuPos(cosmeticCount);
                addCosmetic(item);
                cosmeticCount++;
            } catch(Exception e) {
                Cosmetics.getInstance().log("Error loading " + d.get("name"));
            }
        }
        Cosmetics.getInstance().log("Loaded " + cosmeticCount + " armor sets!");
        cosmeticCount = 0;
        cosmeticDbc = Cosmetics.getInstance().getPluginDB().getCollection("Cosmetics").find(new Document("slot", "Status")).iterator();
        while (cosmeticDbc.hasNext()) {
            Document d = cosmeticDbc.next();
            try {
                CosmeticStatus item = loadCosmeticEffectStatus(d);
                if (item == null) continue;
                item.setPurchaseMenuPos(cosmeticCount);
                addCosmetic(item);
                cosmeticCount++;
            } catch(Exception e) {
                Cosmetics.getInstance().log("Error loading " + d.get("name"));
            }
        }
        Cosmetics.getInstance().log("Loaded " + cosmeticCount + " status effects!");
        cosmeticCount = 0;
        cosmeticDbc = Cosmetics.getInstance().getPluginDB().getCollection("Cosmetics").find(new Document("slot", "Particle")).iterator();
        while (cosmeticDbc.hasNext()) {
            Document d = cosmeticDbc.next();
            try {
                CosmeticParticle item = loadCosmeticEffectParticle(d);
                if (item == null) continue;
                item.setPurchaseMenuPos(cosmeticCount);
                addCosmetic(item);
                cosmeticCount++;
            } catch(Exception e) {
                Cosmetics.getInstance().log("Error loading " + d.get("name"));
            }
        }
        Cosmetics.getInstance().log("Loaded " + cosmeticCount + " particle effects!");
        Cosmetics.getInstance().log("Loaded " + cosmeticItems.size() + " total cosmetics!");
    }
    
    public CosmeticHat loadCosmeticClothesHat(Document doc) {
        return EntityBuilder.load(doc, CosmeticHat.class);
    }
    
    public CosmeticArmor loadCosmeticClothesArmor(Document doc) {
        return EntityBuilder.load(doc, CosmeticArmor.class);
    }
    
    public CosmeticStatus loadCosmeticEffectStatus(Document doc) {
        return EntityBuilder.load(doc, CosmeticStatus.class);
    }
    
    public CosmeticParticle loadCosmeticEffectParticle(Document doc) {
        return EntityBuilder.load(doc, CosmeticParticle.class);
    }
    
    public CosmeticBase getCosmetic(String name) {
        if(cosmeticItems.containsKey(name))
            return cosmeticItems.get(name);
        return new CosmeticBase();
    }
    
    public ItemStack getCosmeticItem(String name) {
        if(cosmeticItems.containsKey(name))
            return cosmeticItems.get(name).getItemStack();
        else
            return new ItemStack(Material.AIR);
    }
    
    public Map<String, CosmeticBase> getCosmetics() {
        return cosmeticItems;
    }
    
    public Vector<CosmeticBase> getCosmeticsBySlot(CosmeticBase.CosmeticSlot cosmeticSlot) {
        Vector<CosmeticBase> cosmeticList = new Vector<CosmeticBase>();
        for(Entry<String, CosmeticBase> entry : cosmeticItems.entrySet()) {
            if(entry.getValue().getSlot().equals(cosmeticSlot)) {
                cosmeticList.add(entry.getValue());
            }
        }
        return cosmeticList;
    }
    
    public void addCosmetic(CosmeticBase item) {
        cosmeticItems.put(item.getName(), item);
    }
}
