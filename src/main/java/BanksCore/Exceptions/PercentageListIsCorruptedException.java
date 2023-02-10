package BanksCore.Exceptions;

public class PercentageListIsCorruptedException extends Exception{

  public PercentageListIsCorruptedException(int count) {
    super("Percentage list must have 4 levels, instead has " + count + ".");
  }
}
