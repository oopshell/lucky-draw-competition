/*
 * Student name: Tiantian Li
 * Student ID: 1174998
 * LMS username: tiantian3@student.unimelb.edu.au
 */

import java.util.Scanner;


/**
 * This class implements a Lucky Number Competition
 * derived from Competition
 */
public class LuckyNumbersCompetition extends Competition
{
    private final double ENTRY_PRICE = 50;  // entry price: $50 for one entry


    /**
     * Construct and initialise a LuckyNumbersCompetition
     * with a competition name, id and the previous entry id
     * @param aName a competition name
     * @param cptId competition identifier
     * @param preEntryId the id of the previous entry
     */
    public LuckyNumbersCompetition(String aName, int cptId, int preEntryId)
    {
        super(aName, cptId, preEntryId);
        this.setType("LuckyNumbersCompetition");
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
            System.out.printf("This bill ($%.1f) is eligible for %d entries." +
                    " How many manual entries did the customer fill up?: %n",
                    totAmount, numEntries);
            int numManualFill;  // the number of manual entries
            while (true)
            {
                numManualFill = keyboard.nextInt();
                keyboard.nextLine();
                if (numManualFill < 0 || numManualFill > numEntries)
                {
                    System.out.println("The number must be in the range from 0 to 6." +
                            " Please try again.");
                }
                else
                    break;
            }

            // Record the total number of previous entries before add entries
            int numEntriesPre = getEntries().size();

            // Adding entries one by one
            // Manually fill entries
            for (int i = 0; i < numManualFill; i++)
            {
                int entryId = getFirstEntryId() + getEntries().size();
                NumbersEntry newEntry = new NumbersEntry(entryId, mId, bId);
                while (true)
                {
                    System.out.printf("Please enter %d different numbers" +
                            " (from the range %d to %d) separated by whitespace.%n",
                            newEntry.getNUMBER_COUNT(), newEntry.getMIN_NUMBER(),
                            newEntry.getMAX_NUMBER());
                    String numbers = keyboard.nextLine();

                    // Check if the manual entry is valid, and add to the competition
                    if (newEntry.checkEntryNums(numbers))
                    {   // valid entry
                        String[] chrNumbers = numbers.split("\\s+");
                        int[] entryNumbers = new int[chrNumbers.length];
                        for (int j = 0; j < chrNumbers.length; j++)
                        {
                            entryNumbers[j] = Integer.parseInt(chrNumbers[j]);
                        }
                        newEntry.setNumbers(entryNumbers);
                        addEntry(newEntry);  // add the entry to competition
                        break;
                    }
                }
            }
            // Automatically fill entries
            for (int i = 0; i < numEntries - numManualFill; i++)
            {
                int entryId = getFirstEntryId() + getEntries().size();
                AutoNumbersEntry newAutoEntry = new AutoNumbersEntry(entryId, mId, bId);
                if (getIsTestingMode())
                    newAutoEntry.setNumbers(newAutoEntry.createNumbers(getEntries().size()));
                else
                    newAutoEntry.setNumbers(newAutoEntry.createNumbers());
                addEntry(newAutoEntry);  // add the entry to competition
            }

            // Mark the bill as used
            aBill.setIsUsed(true);

            // Output the added entries
            System.out.println("The following entries have been added:");
            for (int i = numEntriesPre; i < getEntries().size(); i++)
            {
                System.out.printf("Entry ID: %-6d", getEntries().get(i).getEntryId());
                System.out.print(' ');
                System.out.print("Numbers: ");
                getEntries().get(i).PrintEntry();
                System.out.println();
            }
        }
        return getEntries().size();
    }


    /**
     * Draw a winning entry for this competition
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

        // Produce a luck entry automatically
        System.out.printf("Competition ID: %d, Competition Name: %s, Type: %s%n",
                getId(), getName(), getType());
        System.out.print("Lucky Numbers: ");
        int entryId = this.getFirstEntryId() + getEntries().size();
        AutoNumbersEntry luckyEntry = new AutoNumbersEntry(entryId, null, null);
        if (this.getIsTestingMode())
            luckyEntry.setNumbers(luckyEntry.createNumbers(this.getId()));
        else
            luckyEntry.setNumbers(luckyEntry.createNumbers());
        luckyEntry.PrintEntry();
        System.out.println();

        // Calculate winning entries
        int[] luckyNums = luckyEntry.getNumbers();
        for (Entry e : getEntries())
        {
            int[] entryNums = e.getNumbers();

            // Calculate how many numbers are winning in this entry
            int i = 0, j = 0;
            int winningNums = 0;  // the total winning numbers of this entry
            while (i < e.getNUMBER_COUNT() && j < e.getNUMBER_COUNT())
            {
                if (entryNums[i] == luckyNums[j])
                {
                    winningNums++;
                    i++; j++;
                }
                else if (entryNums[i] < luckyNums[j])
                {
                    i++;
                }
                else
                {
                    j++;
                }
            }

            // Calculate the prize of each entry
            switch (winningNums)
            {
                case 7:
                    e.setPrize(50000);
                    break;
                case 6:
                    e.setPrize(5000);
                    break;
                case 5:
                    e.setPrize(1000);
                    break;
                case 4:
                    e.setPrize(500);
                    break;
                case 3:
                    e.setPrize(100);
                    break;
                case 2:
                    e.setPrize(50);
                    break;
                default:
                    e.setPrize(0);
                    break;
            }

            // Add the winning entry to winning entries list
            if (e.getPrize() > 0)
            {
                if (getWinningEntries().isEmpty())
                {
                    addWinningEntry(e);
                }
                else
                {
                    int lastWinEntryIndex = getWinningEntries().size() - 1;
                    Entry lastWinEntry = getWinningEntries().get(lastWinEntryIndex);
                    /*
                     * If one customer has multiple winning entries,
                     * only the first highest prize will be awarded
                     */
                    if (e.getMemberId().equals(lastWinEntry.getMemberId()))
                    {  // This member has a previous entry that has been awarded
                        if (e.getPrize() > lastWinEntry.getPrize())
                        {  // This prize is higher than his/her previous one
                            int winEntryIndex = getWinningEntries().size() - 1;
                            removeWinningEntry(winEntryIndex);
                            addWinningEntry(e);
                        }
                    }
                    else
                    {
                        addWinningEntry(e);
                    }
                }
            }
        }

        // Print all the winning entries
        System.out.println("Winning entries:");
        for (Entry e : getWinningEntries())
        {
            Member aMember = dp.getMember(e.getMemberId());
            System.out.printf("Member ID: %s, Member Name: %s, Prize: %-5d%n",
                    e.getMemberId(), aMember.getName(), e.getPrize());
            System.out.printf("--> Entry ID: %d, Numbers: ", e.getEntryId());
            e.PrintEntry();
            System.out.println();
        }

        setIsActive(false);
        return true;
    }

}
