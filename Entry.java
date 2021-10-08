/*
 * Student name: Tiantian Li
 * Student ID: 1174998
 * LMS username: tiantian3@student.unimelb.edu.au
 */

import java.io.Serializable;


/**
 * This class implements an entry
 */
public class Entry implements Serializable
{

    private int entryId;  // entry identifier
    private String memberId;  // member identifier
    private String billId;  // bill identifier
    private int prize;  // the prize of this entry


    /**
     * Construct and initialise an Entry
     * with entry id, member id and bill id
     * @param entryId entry identifier
     * @param aMemberId a member identifier
     * @param aBillId a bill identifier
     */
    public Entry(int entryId, String aMemberId, String aBillId)
    {
        this.entryId = entryId;
        this.memberId = aMemberId;
        this.billId = aBillId;
        prize = 0;
    }


    /**
     * Getter method (implemented in NumbersEntry)
     * @return -1
     */
    public int getNUMBER_COUNT()
    {
        return -1;
    }
    /**
     * Getter method (implemented in NumbersEntry)
     * @return -1
     */
    public int getMIN_NUMBER()
    {
        return -1;
    }
    /**
     * Getter method (implemented in NumbersEntry)
     * @return -1
     */
    public int getMAX_NUMBER()
    {
        return -1;
    }
    /**
     * Getter method (implemented in NumbersEntry)
     * @return null
     */
    public int[] getNumbers()
    {
        return null;
    }


    /**
     * Entry id getter method
     * @return id of this entry
     */
    public int getEntryId()
    {
        return entryId;
    }


    /**
     * Member if getter method
     * @return member id of this entry
     */
    public String getMemberId()
    {
        return memberId;
    }


    /**
     * Prize getter method
     * @return prize of this entry
     */
    public int getPrize()
    {
        return prize;
    }


    /**
     * Prize setter method
     * @param prize the prize that will be awarded to this entry
     */
    public void setPrize(int prize)
    {
        this.prize = prize;
    }


    /**
     * Print this entry
     */
    public void PrintEntry()
    {
        System.out.printf("Entry ID: %-6d", entryId);
    }

}
