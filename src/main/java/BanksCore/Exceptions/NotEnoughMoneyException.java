package BanksCore.Exceptions;

import BanksCore.Interfaces.IAccount;

/**
 * Thrown to indicate that there is no enough money on the account to complete the operation.
 */
public class NotEnoughMoneyException extends Exception{

  public NotEnoughMoneyException(IAccount account, float amount) {
    super("Account " + account.getId() + " has less money than " + amount + ".");
  }
}
