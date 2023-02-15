package BanksCore.Exceptions;

import BanksCore.Interfaces.ITransaction;

/**
 * Thrown to indicate that the transaction which you try to roll back was rolled back previously.
 */
public class TransactionAlreadyRolledBackException extends Exception {

  public TransactionAlreadyRolledBackException(ITransaction transaction) {
    super("This transaction is already rolled back: " + transaction);
  }
}
