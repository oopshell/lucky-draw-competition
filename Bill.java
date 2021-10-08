/*
 * Student name: Tiantian Li
 * Student ID: 1174998
 * LMS username: tiantian3@student.unimelb.edu.au
 */


/**
 * This class implements a bill
 */
public class Bill
{
    private String billId;
    private String memberId;
    private double billTot;
    private boolean isUsed;


    /**
     * Construct and initialise a Bill
     * with bill id, member id, total amount and usage status
     * @param billId bill id
     * @param memberId member id
     * @param amount the total amount of this bill
     * @param usedOrNot this bill is used or not
     */
    public Bill(String billId, String memberId, double amount, boolean usedOrNot)
    {
        this.billId = billId;
        this.memberId = memberId;
        this.billTot = amount;
        this.isUsed = usedOrNot;
    }


    /**
     * Bill id getter method
     * @return bill id of this bill
     */
    public String getBillId()
    {
        return billId;
    }


    /**
     * Member id getter method
     * @return member id of this bill
     */
    public String getMemberId()
    {
        return memberId;
    }


    /**
     * Bill total amount getter method
     * @return the total amount of this bill
     */
    public double getBillTot()
    {
        return billTot;
    }


    /**
     * Bill usage status getter method
     * @return boolean value - this bill has been used or not
     */
    public boolean getIsUsed()
    {
        return isUsed;
    }


    /**
     * Bill usage status setter method
     * @param isUsed boolean value - this bill has been used or not
     */
    public void setIsUsed(boolean isUsed)
    {
        this.isUsed = isUsed;
    }


    /**
     * Check if the inputted bill id is valid
     * @param bId a bill id
     * @param dp an object of DataProvider
     * @return this bill id is valid or not
     */
    public static boolean validBillId(String bId, DataProvider dp)
    {
        // Check if the length of the bill id is valid
        if (bId.length() != 6)  // the length must be 6
        {
            System.out.println("Invalid bill id! It must be a 6-digit number." +
                    " Please try again.");
            return false;
        }
        else
        {
            try
            {
                Integer.parseInt(bId);  // the bill id must consist of integers
            }
            catch (Exception e)
            {
                System.out.println("Invalid bill id! It must be a 6-digit number." +
                        " Please try again.");
                return false;
            }
        }

        Bill aBill = dp.getBill(bId);
        // Check if this bill is in the database
        if (aBill == null)
        {
            System.out.println("This bill does not exist. Please try again.");
            return false;
        }
        // Check if the member id of this bill is valid
        else if (aBill.getMemberId().equals(""))
        {
            System.out.println("This bill has no member id. Please try again.");
            return false;
        }
        // Check if this bill has been used or not
        else if (aBill.getIsUsed())
        {
            System.out.println("This bill has already been used for a competition." +
                    " Please try again.");
            return false;
        }
        else
            return true;
    }

}
