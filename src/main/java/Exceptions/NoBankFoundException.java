package Exceptions;

import java.util.UUID;

public class NoBankFoundException extends Throwable {

  public NoBankFoundException(UUID id) {
    super("No bank with this id was found: " + id);
  }
}
