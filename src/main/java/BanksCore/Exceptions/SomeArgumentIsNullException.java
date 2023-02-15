package BanksCore.Exceptions;

/**
 * Thrown to indicate that some argument a method tried to use is null.
 */
public class SomeArgumentIsNullException extends Exception {

    public SomeArgumentIsNullException() {
        super("Some argument in a function you try to use is null.");
    }
}
