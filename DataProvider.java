/*
 * Student name: Tiantian Li
 * Student ID: 1174998
 * LMS username: tiantian3@student.unimelb.edu.au
 */


import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.FileNotFoundException;


/**
 * This class implements a provider of data loaded from files
 */
public class DataProvider
{
    private ArrayList<Member> members = new ArrayList<Member>();  // list of members
    private ArrayList<Bill> bills = new ArrayList<Bill>();  // list of bills
    private final int FILECOL_MEMBER = 3;  // valid number of member file columns
    private final int FILECOL_BILL = 4;  // valid number of bill file columns
    private final int LEN_BILLID = 6;  // valid length of a bill ID

    /**
     * Construct and initialise a DataProvider
     * with the name of the file storing information of members
     * and the name of the file storing information of bills
     * @param memberFile a path to the member file (e.g., members.csv)
     * @param billFile a path to the bill file (e.g., bills.csv)
     * @throws DataAccessException if a file cannot be opened/read
     * @throws DataFormatException if the format of the the content is incorrect
     */
    public DataProvider(String memberFile, String billFile)
            throws DataAccessException, DataFormatException
    {
        Scanner memberInputStream = null;
        Scanner billInputStream = null;

        // Open the member file
        try
        {
            memberInputStream = new Scanner(new FileInputStream(memberFile));
        }
        catch (FileNotFoundException e)
        {
            throw new DataAccessException();
        }
        // Read the member file
        while (memberInputStream.hasNextLine())
        {
            String aMember = memberInputStream.nextLine();
            String[] memberInfo = aMember.split(",");
            if (memberInfo.length != FILECOL_MEMBER)
            {
                throw new DataFormatException();
            }
            else
            {
                Member newMember = new Member(memberInfo[0], memberInfo[1], memberInfo[2]);
                members.add(newMember);
            }
        }
        memberInputStream.close();

        // Open the bill file
        try
        {
            billInputStream = new Scanner(new FileInputStream(billFile));
        }
        catch (FileNotFoundException e)
        {
            throw new DataAccessException();
        }

        // Read the bill file
        while (billInputStream.hasNextLine())
        {
            String aBill = billInputStream.nextLine();
            String[] billInfo = aBill.split(",");
            String billId = billInfo[0];
            String memberId = billInfo[1];
            double billTot;  // billInfo[2]
            boolean used;  // billInfo[3]

            if (billInfo.length != FILECOL_BILL)
            {
                throw new DataFormatException();
            }
            else
            {
                if (billId.length() != LEN_BILLID)
                {
                    throw new DataFormatException();
                }
                try
                {
                    billTot = Double.parseDouble(billInfo[2]);
                    used = Boolean.parseBoolean(billInfo[3]);
                }
                catch (Exception e)
                {
                    throw new DataFormatException();
                }
                Bill newBill = new Bill(billId, memberId, billTot, used);
                bills.add(newBill);
            }
        }
        billInputStream.close();
    }


    /**
     * Get the data of a specific member
     * @param id member id of the member to be retrieved
     * @return an object of Member
     */
    public Member getMember(String id)
    {
        for (Member m : members)
        {
            if (m.getId().equals(id))
                return m;
        }
        return null;
    }


    /**
     * Get the data of a specific bill
     * @param id bill id of the bill to be retrieved
     * @return an object of Bill
     */
    public Bill getBill(String id)
    {
        for (Bill b : bills)
        {
            if (b.getBillId().equals(id))
                return b;
        }
        return null;
    }


    /**
     * Update the file of bills (update the usage status)
     * @param fileNameBill a bill file name
     * @throws DataAccessException if a file cannot be opened/read
     */
    public void updateBills(String fileNameBill) throws DataAccessException
    {
        PrintWriter outputStream = null;
        try
        {
            outputStream = new PrintWriter(new FileOutputStream(fileNameBill));
        }
        catch (FileNotFoundException e)
        {
            throw new DataAccessException();
        }
        for (Bill b : bills)
        {
            outputStream.println(b.getBillId() + "," + b.getMemberId()
                    + "," + b.getBillTot() + "," + b.getIsUsed());
        }
        outputStream.close();
    }

}
