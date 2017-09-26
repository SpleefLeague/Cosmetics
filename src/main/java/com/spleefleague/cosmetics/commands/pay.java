/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spleefleague.cosmetics.commands;

import com.spleefleague.annotations.CommandSource;
import com.spleefleague.annotations.Endpoint;
import com.spleefleague.annotations.IntArg;
import com.spleefleague.annotations.PlayerArg;
import com.spleefleague.commands.command.BasicCommand;
import com.spleefleague.core.SpleefLeague;
import com.spleefleague.core.player.Rank;
import com.spleefleague.core.plugin.CorePlugin;
import com.spleefleague.cosmetics.Cosmetics;
import com.spleefleague.cosmetics.player.CosmeticPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 *
 * @author NickM13
 */
public class pay extends BasicCommand {
    public pay(CorePlugin plugin, String name, String usage) {
        super(plugin, new payDispatcher(), name, usage);
    }
    
    @Endpoint(target = {CommandSource.PLAYER})
    public void sendMoneyFromPlayer(Player sender, @PlayerArg Player receiver, @IntArg int amt) {
        CosmeticPlayer csender = Cosmetics.getInstance().getPlayer(sender);
        CosmeticPlayer creceiver = Cosmetics.getInstance().getPlayer(receiver);
        if(amt <= 0) {
            sender.sendMessage(ChatColor.RED + "You can't do that!");
        }
        if(csender.getCoins() >= amt) {
            csender.changeCoins(-amt);
            creceiver.changeCoins(-amt);
            sender.sendMessage(ChatColor.GREEN + "You sent " + ChatColor.GOLD + String.valueOf(amt) + " coins " + ChatColor.GREEN + "to " + ChatColor.YELLOW + receiver.getName() + ".");
            receiver.sendMessage(ChatColor.GREEN + "You received " + ChatColor.GOLD + String.valueOf(amt) + " coins " + ChatColor.GREEN + "from " + ChatColor.YELLOW + sender.getName() + ".");
        } else {
            sender.sendMessage(ChatColor.RED + "You only have " + ChatColor.GOLD + csender.getCoins() + "!");
        }
    }
    
    @Endpoint(target = {CommandSource.PLAYER})
    public void sendMoneyFromAdmin(Player sender, @IntArg int amt) {
        if(SpleefLeague.getInstance().getPlayerManager().get(sender).getRank().hasPermission(Rank.DEVELOPER)) {
            CosmeticPlayer cplayer = Cosmetics.getInstance().getPlayer(sender);
            cplayer.changeCoins(amt);
            if(amt >= 0) sender.sendMessage(ChatColor.GREEN + "You have given yourself " + ChatColor.GOLD + amt + " coins.");
            else sender.sendMessage(ChatColor.RED + "You have taken from yourself " + ChatColor.GOLD + -amt + " coins.");
        }
    }
}
