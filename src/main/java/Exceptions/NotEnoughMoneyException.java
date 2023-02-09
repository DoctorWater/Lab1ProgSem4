package Exceptions;

import Interfaces.IAccount;

public class NotEnoughMoneyException extends Exception{

  public NotEnoughMoneyException(IAccount account, float amount) {
    super("Account " + account.getId() + " has less money than " + amount + ".");
  }
}
