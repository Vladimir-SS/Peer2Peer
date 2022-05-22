package com.FirstPage;

/**
 * Class that checks for a given port number if it's considered valid(the input given is a number in the interval [1024,65353] and is not occupied by another process).
 * In case the port is invalid,an error message will be set detailing why the port number is invalid
 */
public class PortValidator {

    private int portNumber;

    private boolean isValid;

    private String errorMessage;

    private static final int maxPortValue=65353;

    /**
     * Constructor for the PortValidator class
     * @param text the text which represents the number port
     * @throws IllegalArgumentException if the text parameter is null
     */
    public PortValidator( String text) throws IllegalArgumentException{

        if(text==null){
            throw new IllegalArgumentException("Not allowed to give the text as null");
        }

        //Assume it's false
        portNumber=-1;
        isValid=false;
        errorMessage="";

        verifyValidity(text);
    }

    /**
     * Verifies if the given text can be a valid port number.It verifies that:
     * -text is null,empty of its length is bigger than 5(in which case,it can't be a valid port number)
     * -the text can be parsed as an int
     * -the text is between [1024,65353]
     * -the number obtained is not used by any other process
     * If one of these cases fails,then the port is going to be set as invalid and an appropriate error message will be set
     * @param text the text given to extract the port number from
     */
    private void verifyValidity(String text){
        //Testing possible values of text
        if(text==null){
            errorMessage="Text tried is null!";
            return;
        }

        if(text.equals( "" )){
            errorMessage="The text given is empty!";
            return;
        }

        if(text.length()>5){
            errorMessage="The input given is too long to be a port number!";
            return;
        }

        //Testing if the input given is a number
        int portNumber;
        try{
            portNumber=Integer.parseInt( text );
        }
        catch (NumberFormatException e){
            errorMessage="The input given is not a valid number!";
            return;
        }

        //Testing port values
        if(portNumber<0){
            errorMessage="The port number cannot be negative!";
            return;
        }

        if(portNumber>maxPortValue){
            errorMessage="The port number cannot be bigger than "+maxPortValue+"!";
            return;
        }

        if(portNumber<1024){
            errorMessage="The port number cannot be between [0,1023] since these are well-known ports!";
            return;
        }

        //Passed all the tests
        this.portNumber=portNumber;
        isValid=true;
    }

    /**
     * Get the error message from the port validator.If the port is valid,the error message is empty
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Get the port number.If the port number is considered invalid,then the port
     * @return an int between [1024,65353] being a valid port
     */
    public int getPortNumber() {
        return portNumber;
    }

    /**
     * Get a boolean value representing if the port given is valid or not
     * @return true if the port number is valid,false otherwise
     */
    public boolean isValid() {
        return isValid;
    }
}
