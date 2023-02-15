package BanksCore.Exceptions;

import java.util.UUID;

/**
 * Thrown to indicate that there is no registered bank with requested ID.
 */
public class NoBankFoundException extends Throwable {

  public NoBankFoundException(UUID id) {
    super("No bank with this id was found: " + id);
  }
}
