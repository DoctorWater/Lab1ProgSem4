package Exceptions;

import java.util.UUID;

public class NoTransactionFoundException extends Exception{

  public NoTransactionFoundException(UUID id) {
    super("No transaction with this id was found: " + id);
  }
}
