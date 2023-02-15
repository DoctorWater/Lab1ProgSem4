package BanksCore.Exceptions;

import java.util.UUID;

/**
 * Thrown to indicate that there is no transaction with given ID.
 */
public class NoTransactionFoundException extends Exception{

  public NoTransactionFoundException(UUID id) {
    super("No transaction with this id was found: " + id);
  }
}
