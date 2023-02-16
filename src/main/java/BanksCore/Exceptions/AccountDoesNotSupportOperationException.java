package BanksCore.Exceptions;

import BanksCore.Interfaces.Account;

/**
 * Thrown to indicate that account can not execute an operation you try to use.
 */
public class AccountDoesNotSupportOperationException extends Exception{

  public AccountDoesNotSupportOperationException(Account account) {
    super("This account does not support an operation you try to proceed: " + account);
  }
}
