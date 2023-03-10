package BanksCore.Exceptions;

import BanksCore.Interfaces.ITransaction;

public class TransactionAlreadyRolledBackException extends Exception {

  public TransactionAlreadyRolledBackException(ITransaction transaction) {
    super("This transaction is already rolled back: " + transaction);
  }
}
