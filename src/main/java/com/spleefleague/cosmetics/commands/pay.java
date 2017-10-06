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
import com.spleefleague.core.player.SLPlayer;
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
    public void sendMoney(Player sender, @PlayerArg Player receiver, @IntArg(min = 1) int amt) {
        CosmeticPlayer csender = Cosmetics.getInstance().getPlayer(sender);
        CosmeticPlayer creceiver = Cosmetics.getInstance().getPlayer(receiver);
        SLPlayer slsender = SpleefLeague.getInstance().getPlayerManager().get(sender);
        SLPlayer slreceiver = SpleefLeague.getInstance().getPlayerManager().get(receiver);
        if(sender == receiver) {
            error(sender, "You can't do that!");
        } else {
            if(slsender.getCoins() >= amt) {
                slsender.changeCoins(-amt);
                slreceiver.changeCoins(amt);
                success(sender, "You sent " + ChatColor.GOLD + String.valueOf(amt) + " coins " + ChatColor.GREEN + "to " + ChatColor.YELLOW + receiver.getName() + ChatColor.GREEN + ".");
                success(receiver, "You received " + ChatColor.GOLD + String.valueOf(amt) + " coins " + ChatColor.GREEN + "from " + ChatColor.YELLOW + sender.getName() + ChatColor.GREEN + ".");
            } else {
                error(sender, "You only have " + ChatColor.GOLD + slsender.getCoins() + " coins" + ChatColor.RED + "!");
            }
        }
    }
    
    @Endpoint(target = {CommandSource.PLAYER})
    public void sendMoneyAdmin(Player sender, @IntArg int amt) {
        if(SpleefLeague.getInstance().getPlayerManager().get(sender).getRank().hasPermission(Rank.DEVELOPER)) {
            CosmeticPlayer cplayer = Cosmetics.getInstance().getPlayer(sender);
            SLPlayer slplayer = SpleefLeague.getInstance().getPlayerManager().get(sender);
            slplayer.changeCoins(amt);
            if(amt >= 0) success(sender, "You have given yourself " + ChatColor.GOLD + amt + " coins" + ChatColor.GREEN + ".");
            else error(sender, "You have taken " + ChatColor.GOLD + -amt + " coins" + ChatColor.RED + " from yourself.");
        }
    }
}
