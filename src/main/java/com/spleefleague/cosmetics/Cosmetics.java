/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spleefleague.cosmetics;

import com.mongodb.client.MongoDatabase;
import com.spleefleague.commands.command.CommandLoader;
import com.spleefleague.core.SpleefLeague;
import com.spleefleague.core.menus.SLMenu;
import com.spleefleague.core.player.PlayerManager;
import com.spleefleague.core.plugin.CorePlugin;
import com.spleefleague.core.plugin.PlayerHandling;
import static com.spleefleague.core.utils.inventorymenu.InventoryMenuAPI.item;
import static com.spleefleague.core.utils.inventorymenu.InventoryMenuAPI.menu;
import com.spleefleague.core.utils.inventorymenu.InventoryMenuTemplateBuilder;
import com.spleefleague.cosmetics.cosmetic.CosmeticLoader;
import com.spleefleague.cosmetics.cosmetic.item.CosmeticBase;
import com.spleefleague.cosmetics.cosmetic.item.CosmeticBase.CosmeticSlot;
import com.spleefleague.cosmetics.listener.ClothesListener;
import com.spleefleague.cosmetics.listener.LoginListener;
import com.spleefleague.cosmetics.listener.StatusListener;
import com.spleefleague.cosmetics.player.CosmeticPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author NickM13
 */
public class Cosmetics extends CorePlugin implements PlayerHandling {
    private static Cosmetics instance;
    private CosmeticLoader cosmeticManager;
    private PlayerManager<CosmeticPlayer> playerManager;
    
    public Cosmetics() {
        super(ChatColor.GRAY + "[" + ChatColor.GOLD + "Cosmetics" + ChatColor.GRAY + "]" + ChatColor.RESET);
        instance = this;
    }
    
    public static Cosmetics getInstance() {
        return instance;
    }
    
    @Override
    public void start() {
        cosmeticManager = new CosmeticLoader();
        cosmeticManager.init();
        playerManager = new PlayerManager<>(this, CosmeticPlayer.class);
        ClothesListener.init();
        StatusListener.init();
        LoginListener.init();
        CommandLoader.loadCommands(this, "com.spleefleague.cosmetics.commands");
        createCosmeticMenus();
    }
    
    @Override
    public void stop() {
        playerManager.saveAll();
    }

    @Override
    public void syncSave(Player player) {
        playerManager.save(playerManager.get(player));
    }

    @Override
    public MongoDatabase getPluginDB() {
        return SpleefLeague.getInstance().getMongo().getDatabase("Cosmetics");
    }

    public CosmeticPlayer getPlayer(Player player) {
        return playerManager.get(player);
    }
    
    public CosmeticBase getCosmetic(String name) {
        return cosmeticManager.getCosmetic(name);
    }
    
    public ItemStack getCosmeticItem(String name) {
        return cosmeticManager.getCosmeticItem(name);
    }
    
    public CosmeticLoader getCosmetics() {
        return cosmeticManager;
    }
    
    public void createCosmeticMenus()
    {
        InventoryMenuTemplateBuilder cosmeticMenu = SLMenu
                .getNewGamemodeMenu()
                .title("Cosmetics")
                .displayName("Cosmetics")
                .displayIcon(Material.CHEST)
                .exitOnClickOutside(true);
        InventoryMenuTemplateBuilder clothesMenu = menu()
                .title("Clothing")
                .displayName("Clothing")
                .displayIcon(Material.LEATHER_CHESTPLATE);
        InventoryMenuTemplateBuilder hatMenu = menu()
                .title("Hats")
                .displayName("Hats")
                .displayIcon(Material.MELON_BLOCK);
        InventoryMenuTemplateBuilder armorMenu = menu()
                .title("Armor Sets")
                .displayName("Armor Sets")
                .displayIcon(Material.IRON_CHESTPLATE);
        InventoryMenuTemplateBuilder effectsMenu = menu()
                .title("Effects")
                .displayName("Effects")
                .displayIcon(Material.POTION);
        InventoryMenuTemplateBuilder statusMenu = menu()
                .title("Status")
                .displayName("Status")
                .displayIcon(Material.BEACON);
        InventoryMenuTemplateBuilder particleMenu = menu()
                .title("Particles")
                .displayName("Particles")
                .displayIcon(Material.WEB);
        hatMenu.component(item()
                .displayName("None")
                .displayIcon(Material.BARRIER)
                .onClick((event) -> {
                    CosmeticPlayer cp = Cosmetics.getInstance().getPlayer(event.getPlayer());
                    if(cp == null) return;
                    cp.deactivateCosmetic(CosmeticSlot.Hat);
                }));
        armorMenu.component(item()
                .displayName("None")
                .displayIcon(Material.BARRIER)
                .onClick((event) -> {
                    CosmeticPlayer cp = Cosmetics.getInstance().getPlayer(event.getPlayer());
                    if(cp == null) return;
                    cp.deactivateCosmetic(CosmeticSlot.Armor);
                }));
        statusMenu.component(item()
                .displayName("None")
                .displayIcon(Material.BARRIER)
                .onClick((event) -> {
                    CosmeticPlayer cp = Cosmetics.getInstance().getPlayer(event.getPlayer());
                    if(cp == null) return;
                    cp.deactivateCosmetic(CosmeticSlot.Status);
                }));
        particleMenu.component(item()
                .displayName("None")
                .displayIcon(Material.BARRIER)
                .onClick((event) -> {
                    CosmeticPlayer cp = Cosmetics.getInstance().getPlayer(event.getPlayer());
                    if(cp == null) return;
                    cp.deactivateCosmetic(CosmeticSlot.Particle);
                }));
        for(CosmeticBase cosmeticItem : cosmeticManager.getCosmetics().values()) {
            InventoryMenuTemplateBuilder cItem = menu()
                    .title("Confirm Purchase")
                    .displayName(cosmeticItem.getName())
                    .description(ChatColor.GOLD + "" + cosmeticItem.getPrice() + " coins")
                    .description(ChatColor.AQUA + cosmeticItem.getDescription())
                    .displayIcon(cosmeticItem.getMaterial());
            cItem.component(4, 0, item()
                    .displayName(cosmeticItem.getName())
                    .description(ChatColor.GOLD + "" + cosmeticItem.getPrice() + " coins")
                    .description(ChatColor.AQUA + cosmeticItem.getDescription())
                    .displayIcon(cosmeticItem.getMaterial()));
            cItem.component(0, 0, item()
                    .displayName(ChatColor.GREEN + "Confirm Purchase")
                    .displayIcon(Material.EMERALD_BLOCK)
                    .onClick((event) -> {
                        CosmeticPlayer cp = Cosmetics.getInstance().getPlayer(event.getPlayer());
                        if(cp == null) return;
                        if(cp.hasCosmetic(cosmeticItem)) {
                            cp.activateCosmetic(cosmeticItem);
                        } else {
                            cp.buyCosmetic(cosmeticItem);
                        }
                    }));
            cItem.component(8, 0, item()
                            .displayName(ChatColor.RED + "Cancel Purchase")
                            .displayIcon(Material.REDSTONE_BLOCK)
                            .onClick((event) -> {}));
            switch(cosmeticItem.getType()) {
                case Clothes:
                    switch(cosmeticItem.getSlot()) {
                        case Hat:
                            hatMenu.component(cosmeticItem.getPurchaseMenuPos(), cItem);
                            break;
                        case Armor:
                            armorMenu.component(cosmeticItem.getPurchaseMenuPos(), cItem);
                            break;
                    }
                    break;
                case Effect:
                    switch(cosmeticItem.getSlot()) {
                        case Particle:
                            particleMenu.component(cosmeticItem.getPurchaseMenuPos(), cItem);
                            break;
                        case Status:
                            statusMenu.component(cosmeticItem.getPurchaseMenuPos(), cItem);
                            break;
                    }
                    break;
                default: break;
            }
        }
        clothesMenu.component(hatMenu).component(armorMenu);
        effectsMenu.component(statusMenu).component(particleMenu);
        cosmeticMenu.component(clothesMenu).component(effectsMenu);
    }
}
