package BanksCore.Exceptions;

import BanksCore.Interfaces.IAccount;

/**
 * Thrown to indicate that account can not execute an operation you try to use.
 */
public class AccountDoesNotSupportOperationException extends Exception{

  public AccountDoesNotSupportOperationException(IAccount account) {
    super("This account does not support an operation you try to proceed: " + account);
  }
}
