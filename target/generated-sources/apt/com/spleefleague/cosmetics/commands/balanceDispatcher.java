package com.spleefleague.cosmetics.commands;

import com.spleefleague.annotations.CommandSource;
import com.spleefleague.annotations.DispatchResult;
import com.spleefleague.annotations.DispatchResultType;
import com.spleefleague.annotations.DispatchableCommand;
import com.spleefleague.annotations.Dispatcher;
import java.lang.Override;
import java.lang.String;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class balanceDispatcher implements Dispatcher {
  public balanceDispatcher() {
  }

  @Override
  public DispatchResult dispatch(DispatchableCommand rawInstance, CommandSender sender, CommandSource src, String[] args) {
    balance instance = (balance)rawInstance;
    boolean valid;
    DispatchResult result = new DispatchResult(DispatchResultType.NO_ROUTE);
    valid = false
    || src == CommandSource.PLAYER;
    if(valid) {
      result = showBalance0(sender, instance, args);
      if(result.getType() == DispatchResultType.SUCCESS) {
        return result;
      }
    }
    return result;
  }

  private DispatchResult showBalance0(CommandSender sender, balance instance, String[] args) {
    int position = 0;
    String arg;
    if(position != args.length) return new DispatchResult(DispatchResultType.NO_VALID_ROUTE);
    Player senderCasted = ((Player)sender);
    instance.showBalance(senderCasted);
    return new DispatchResult(DispatchResultType.SUCCESS);
  }
}
