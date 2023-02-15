package BanksCore.Exceptions;

/**
 * Thrown to indicate that user's input is not valid and leads to critical failures.
 */
public class InputIsIncorrectException extends Exception {

  public InputIsIncorrectException() {
    super("Check your input, please.");
  }
}
