package Exceptions;

public class CryptoException extends Exception{
    public CryptoException(String errorMessage, Throwable throwable) {
        super(errorMessage, throwable);
    }
}
