package Exceptions;

public class SomeArgumentIsNullException extends Exception{

  public SomeArgumentIsNullException() {
    super("Some argument in a function you try to use is null.");
  }
}
