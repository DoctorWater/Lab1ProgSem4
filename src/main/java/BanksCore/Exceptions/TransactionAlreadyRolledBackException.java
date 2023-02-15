package BanksCore.Exceptions;

import BanksCore.Interfaces.Transaction;

/**
 * Thrown to indicate that the transaction which you try to roll back was rolled back previously.
 */
public class TransactionAlreadyRolledBackException extends Exception {

  public TransactionAlreadyRolledBackException(Transaction transaction) {
    super("This transaction is already rolled back: " + transaction);
  }
}
