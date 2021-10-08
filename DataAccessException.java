/*
 * Student name: Tiantian Li
 * Student ID: 1174998
 * LMS username: tiantian3@student.unimelb.edu.au
 */


/**
 * This class implements data access exception
 */
public class DataAccessException extends Exception
{
    /**
     * Default constructor of Exception
     */
    public DataAccessException()
    {
        super("The file was not found. Please try again.");
    }


    /**
     * Constructor with an exception message
     * @param aMessage an exception message
     */
    public DataAccessException(String aMessage)
    {
        super(aMessage);
    }

}
