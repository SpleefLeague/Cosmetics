/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spleefleague.cosmetics.commands;

import static com.spleefleague.annotations.CommandSource.PLAYER;
import com.spleefleague.annotations.Endpoint;
import com.spleefleague.commands.command.BasicCommand;
import com.spleefleague.core.plugin.CorePlugin;
import com.spleefleague.cosmetics.Cosmetics;
import com.spleefleague.cosmetics.player.CosmeticPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 *
 * @author NickM13
 */
public class balance extends BasicCommand {
    public balance(CorePlugin plugin, String name, String usage) {
        super(plugin, new balanceDispatcher(), name, usage);
    }
    
    @Endpoint(target = {PLAYER})
    public void showBalance(Player sender) {
        CosmeticPlayer cplayer = Cosmetics.getInstance().getPlayer(sender);
        sender.sendMessage(ChatColor.GREEN + "You have " + ChatColor.GOLD + cplayer.getCoins() + " coins.");
    }
}
