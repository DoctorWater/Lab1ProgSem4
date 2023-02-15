package BanksCore.Exceptions;

import BanksCore.Interfaces.Account;

/**
 * Thrown to indicate that there is no enough money on the account to complete the operation.
 */
public class NotEnoughMoneyException extends Exception{

  public NotEnoughMoneyException(Account account, float amount) {
    super("Account " + account.getId() + " has less money than " + amount + ".");
  }
}
