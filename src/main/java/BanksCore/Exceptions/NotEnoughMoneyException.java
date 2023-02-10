package BanksCore.Exceptions;

import BanksCore.Interfaces.IAccount;

public class NotEnoughMoneyException extends Exception{

  public NotEnoughMoneyException(IAccount account, float amount) {
    super("Account " + account.getId() + " has less money than " + amount + ".");
  }
}
