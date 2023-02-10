package Exceptions;

import Interfaces.IAccount;

public class AccountDoesNotSupportOperationException extends Exception{

  public AccountDoesNotSupportOperationException(IAccount account) {
    super("This account does not support an operation you try to proceed: " + account);
  }
}
