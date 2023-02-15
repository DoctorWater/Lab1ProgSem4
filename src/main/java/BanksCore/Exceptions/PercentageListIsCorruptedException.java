package BanksCore.Exceptions;

/**
 * Thrown to indicate that the percentage list for a debit account is not correct or corrupted..
 */
public class PercentageListIsCorruptedException extends Exception{

  public PercentageListIsCorruptedException(int count) {
    super("Percentage list must have 4 levels, instead has " + count + ".");
  }
}
