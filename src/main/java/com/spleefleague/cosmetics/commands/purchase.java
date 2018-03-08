/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spleefleague.cosmetics.commands;

import com.spleefleague.annotations.CommandSource;
import com.spleefleague.annotations.Endpoint;
import com.spleefleague.annotations.StringArg;
import com.spleefleague.commands.command.BasicCommand;
import com.spleefleague.core.plugin.CorePlugin;
import com.spleefleague.cosmetics.Cosmetics;
import com.spleefleague.cosmetics.player.CosmeticPlayer;
import org.bukkit.entity.Player;

/**
 *
 * @author NickM13
 */
public class purchase extends BasicCommand {
    public purchase(CorePlugin plugin, String name, String usage) {
        super(plugin, new purchaseDispatcher(), name, usage);
    }
    
    @Endpoint(target = {CommandSource.PLAYER})
    public void purchaseCosmetic(Player sender, @StringArg String cosmeticItemName) {
        CosmeticPlayer cplayer = Cosmetics.getInstance().getPlayer(sender);
        cplayer.buyCosmetic(Cosmetics.getInstance().getCosmetic(cosmeticItemName));
    }
}
