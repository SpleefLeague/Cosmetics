package com.spleefleague.cosmetics.commands;

import com.spleefleague.annotations.CommandSource;
import com.spleefleague.annotations.DispatchResult;
import com.spleefleague.annotations.DispatchResultType;
import com.spleefleague.annotations.DispatchableCommand;
import com.spleefleague.annotations.Dispatcher;
import java.lang.Override;
import java.lang.String;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class payDispatcher implements Dispatcher {
  public payDispatcher() {
  }

  @Override
  public DispatchResult dispatch(DispatchableCommand rawInstance, CommandSender sender, CommandSource src, String[] args) {
    pay instance = (pay)rawInstance;
    boolean valid;
    DispatchResult result = new DispatchResult(DispatchResultType.NO_ROUTE);
    valid = false
    || src == CommandSource.PLAYER;
    if(valid) {
      result = sendMoney0(sender, instance, args);
      if(result.getType() == DispatchResultType.SUCCESS) {
        return result;
      }
    }
    valid = false
    || src == CommandSource.PLAYER;
    if(valid) {
      result = sendMoneyAdmin1(sender, instance, args);
      if(result.getType() == DispatchResultType.SUCCESS) {
        return result;
      }
    }
    return result;
  }

  private DispatchResult sendMoney0(CommandSender sender, pay instance, String[] args) {
    int position = 0;
    String arg;
    Player param0;
    int param1;
    if(args.length == position) return new DispatchResult(null, DispatchResultType.NO_VALID_ROUTE);
    arg = args[position++];
    param0 = Bukkit.getPlayer(arg);
    if(param0 == null) return new DispatchResult(ChatColor.WHITE + args[position - 1] + ChatColor.RED + " is not online.", DispatchResultType.OTHER);
    try {
      if(args.length == position) return new DispatchResult(null, DispatchResultType.NO_VALID_ROUTE);
      arg = args[position++];
      param1 = Integer.parseInt(arg);
    }
    catch (NumberFormatException e) {
      return new DispatchResult(null, DispatchResultType.NO_VALID_ROUTE);
    }
    if(position != args.length) return new DispatchResult(DispatchResultType.NO_VALID_ROUTE);
    Player senderCasted = ((Player)sender);
    instance.sendMoney(senderCasted, param0, param1);
    return new DispatchResult(DispatchResultType.SUCCESS);
  }

  private DispatchResult sendMoneyAdmin1(CommandSender sender, pay instance, String[] args) {
    int position = 0;
    String arg;
    int param0;
    try {
      if(args.length == position) return new DispatchResult(null, DispatchResultType.NO_VALID_ROUTE);
      arg = args[position++];
      param0 = Integer.parseInt(arg);
    }
    catch (NumberFormatException e) {
      return new DispatchResult(null, DispatchResultType.NO_VALID_ROUTE);
    }
    if(position != args.length) return new DispatchResult(DispatchResultType.NO_VALID_ROUTE);
    Player senderCasted = ((Player)sender);
    instance.sendMoneyAdmin(senderCasted, param0);
    return new DispatchResult(DispatchResultType.SUCCESS);
  }
}
