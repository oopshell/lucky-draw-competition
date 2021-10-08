/*
 * Student name: Tiantian Li
 * Student ID: 1174998
 * LMS username: tiantian3@student.unimelb.edu.au
 */

import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;


/**
 * This class implements a Random Pick Competition
 * derived from Competition
 */
public class RandomPickCompetition extends Competition
{
    private final double ENTRY_PRICE = 50;  // entry price: $50 for one entry
    private final int FIRST_PRIZE = 50000;  // prize for the first winning entry
    private final int SECOND_PRIZE = 5000;  // prize for the second winning entry
    private final int THIRD_PRIZE = 1000;  // prize for the third winning entry
    private final int[] prizes = {FIRST_PRIZE, SECOND_PRIZE, THIRD_PRIZE};
    private final int MAX_WINNING_ENTRIES = 3;  // the max number of winning entries


    /**
     * Construct and initialise a RandomPickCompetition
     * with a competition name, id and the previous entry id
     * @param aName a competition name
     * @param cptId competition identifier
     * @param preEntryId the id of the previous entry
     */
    public RandomPickCompetition(String aName, int cptId, int preEntryId)
    {
        super(aName, cptId, preEntryId);
        super.setType("RandomPickCompetition");
    }


    /**
     * Add entries to this competition
     * @param keyboard a Scanner object to get user input
     * @param dp an object of DataProvider
     * @return the number of added entries
     */
    public int addEntries(Scanner keyboard, DataProvider dp)
    {
        // Input the bill id and check if it is valid
        String bId;
        do
        {
            System.out.println("Bill ID: ");
            bId = keyboard.nextLine();
        } while (!Bill.validBillId(bId, dp));

        Bill aBill = dp.getBill(bId);  // retrieve the bill data
        String mId = aBill.getMemberId();  // get member id
        double totAmount = aBill.getBillTot();  // get bill total amount
        if (totAmount < ENTRY_PRICE)
        {
            /*
             * Total amount is less than the entry price
             * Not eligible to buy entries
             */
            System.out.printf("This bill is not eligible for an entry. " +
                    "The total amount is smaller than $%.1f%n", ENTRY_PRICE);
        }
        else
        {
            /*
             * Eligible to buy entries
             * Calculate the number of entries for this bill
             */
            int numEntries = (int)(totAmount / ENTRY_PRICE);
            System.out.printf("This bill ($%.1f) is eligible for %d entries.%n",
                    totAmount, numEntries);

            // Record the total number of previous entries before add entries
            int numEntriesPre = getEntries().size();

            System.out.println("The following entries have been automatically generated:");
            // Generate and add entries one by one
            for (int i = 0; i < numEntries; i++)
            {
                int entryId = getFirstEntryId() + getEntries().size();
                Entry newEntry = new Entry(entryId, mId, bId);
                addEntry(newEntry);
            }

            // Mark the bill as used
            aBill.setIsUsed(true);

            // Output the added entries
            for (int i = numEntriesPre; i < getEntries().size(); i++)
            {
                getEntries().get(i).PrintEntry();
                System.out.println();
            }
        }
        return getEntries().size();
    }


    /**
     * Draw winning entries for this competition
     * @param dp an object of DataProvider
     * @return boolean value - drawing winners is successful or not
     */
    public boolean drawWinners(DataProvider dp)
    {
        if (getEntries().size() == 0)
        {
            System.out.println("The current competition has no entries yet!");
            return false;
        }
        else
        {   // Pick three winning entries randomly
            ArrayList<Integer> winningEntryIndices = new ArrayList<Integer>();

            Random randomGenerator = null;
            if (this.getIsTestingMode())
            {
                // Testing mode use competition id as seed
                randomGenerator = new Random(this.getId());
            }
            else
            {
                randomGenerator = new Random();
            }

            int winningEntryCount = 0;  // the number of winning entries
            while (winningEntryCount < MAX_WINNING_ENTRIES)
            {
                int winningEntryIndex = randomGenerator.nextInt(getEntries().size());
                Entry winningEntry = getEntries().get(winningEntryIndex);

                /*
                 * Ensure that once an entry has been selected,
                 * it will not be selected again.
                 */
                if (winningEntry.getPrize() == 0)
                {
                    boolean awarded = false;
                    for (int i : winningEntryIndices)
                    {
                        Entry temp = getEntries().get(i);
                        // Check if this member has been awarded a prize
                        if (winningEntry.getMemberId().equals(temp.getMemberId()))
                        {
                            awarded = true;
                            break;
                        }
                    }
                    if (!awarded)
                    {   // This member has not been awarded before
                        int currentPrize = prizes[winningEntryCount];
                        winningEntry.setPrize(currentPrize);  // award the prize
                        winningEntryIndices.add(winningEntryIndex);
                    }
                    winningEntryCount++;
                }
                /*
                 * If all members who purchased entries have been awarded a prize,
                 * then the generator can be terminated immediately.
                 */
                if (winningEntryCount == getEntries().size())
                {
                    break;
                }
            }
        }

        // Print all the winning entries
        System.out.printf("Competition ID: %d, Competition Name: %s, Type: %s%n",
                getId(), getName(), getType());
        System.out.println("Winning entries:");
        for (Entry e : getEntries())
        {
            if (e.getPrize() > 0)
            {
                Member aMember = dp.getMember(e.getMemberId());
                System.out.printf("Member ID: %s, Member Name: %s, Entry ID: %d, Prize: %-5d%n",
                        e.getMemberId(), aMember.getName(), e.getEntryId(), e.getPrize());
                addWinningEntry(e);
            }
        }

        setIsActive(false);
        return true;
    }

}
