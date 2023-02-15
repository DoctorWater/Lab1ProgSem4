package BanksCore.Exceptions;

import java.util.UUID;

/**
 * Thrown to indicate that more than one or none accounts were found for the given ID.
 */
public class TooManyOrNoneAccountsWereFoundException extends Exception{

  public TooManyOrNoneAccountsWereFoundException(UUID id) {
    super("Too many or none accounts with this id were found: " + id);
  }
}
