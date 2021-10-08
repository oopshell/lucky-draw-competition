/*
 * Student name: Tiantian Li
 * Student ID: 1174998
 * LMS username: tiantian3@student.unimelb.edu.au
 */


/**
 * This class implements a member
 */
public class Member
{
    private String id;
    private String name;
    private String address;


    /**
     * Construct and initialise a Member
     * with member id, the name of the member and the address of the member
     * @param id member id
     * @param name the name of this member
     * @param address the address of this member
     */
    public Member(String id, String name, String address)
    {
        this.id = id;
        this.name = name;
        this.address = address;
    }


    /**
     * Member id getter method
     * @return the id of this member
     */
    public String getId()
    {
        return id;
    }


    /**
     * Member name getter method
     * @return the name of this member
     */
    public String getName()
    {
        return name;
    }


    /**
     * Member address getter method
     * @return the address of this member
     */
    public String getAddress()
    {
        return address;
    }
}
