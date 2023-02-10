package BanksCore.Exceptions;

import java.util.UUID;

public class TooManyOrNoneAccountsWereFoundException extends Exception{

  public TooManyOrNoneAccountsWereFoundException(UUID id) {
    super("Too many or none accounts with this id were found: " + id);
  }
}
