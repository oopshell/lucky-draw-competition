/*
 * Student name: Tiantian Li
 * Student ID: 1174998
 * LMS username: tiantian3@student.unimelb.edu.au
 */


/**
 * This class implements data format exception
 */
public class DataFormatException extends Exception
{
    /**
     * Default constructor of Exception
     */
    public DataFormatException()
    {
        super("Wrong data format! Please try another file.");
    }


    /**
     * Constructor with an exception message
     * @param aMessage an exception message
     */
    public DataFormatException(String aMessage)
    {
        super(aMessage);
    }

}
