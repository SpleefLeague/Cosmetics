/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spleefleague.cosmetics.commands;

import static com.spleefleague.annotations.CommandSource.PLAYER;
import com.spleefleague.annotations.Endpoint;
import com.spleefleague.commands.command.BasicCommand;
import com.spleefleague.core.SpleefLeague;
import com.spleefleague.core.player.SLPlayer;
import com.spleefleague.core.plugin.CorePlugin;
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
        SLPlayer slplayer = SpleefLeague.getInstance().getPlayerManager().get(sender);
        sender.sendMessage(ChatColor.GREEN + "You have " + ChatColor.GOLD + slplayer.getCoins() + " coins" + ChatColor.GREEN + ".");
    }
}
