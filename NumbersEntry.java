/*
 * Student name: Tiantian Li
 * Student ID: 1174998
 * LMS username: tiantian3@student.unimelb.edu.au
 */

import java.util.Arrays;


/**
 * This class implements an entry with numbers
 * derived from Entry
 */
public class NumbersEntry extends Entry
{
    private final int NUMBER_COUNT = 7;  // the total number of entry numbers
    private final int MIN_NUMBER = 1;  // the minimum value of entry numbers
    private final int MAX_NUMBER = 35;  // the maximum value of entry numbers
    private int[] numbers;  // entry numbers


    /**
     * Construct and initialise a NumbersEntry
     * with entry id, member id and bill id
     * @param entryId entry identifier
     * @param aMemberId a member identifier
     * @param aBillId a bill identifier
     */
    public NumbersEntry(int entryId, String aMemberId, String aBillId)
    {
        super(entryId, aMemberId, aBillId);
        numbers = new int[getNUMBER_COUNT()];
    }


    /**
     * Get the constant - total number of entry numbers
     * @return NUMBER_COUNT
     */
    public int getNUMBER_COUNT()
    {
        return NUMBER_COUNT;
    }


    /**
     * Get the constant - the minimum value of entry numbers
     * @return MIN_NUMBER
     */
    public int getMIN_NUMBER()
    {
        return MIN_NUMBER;
    }


    /**
     * Get the constant - the maximum value of entry numbers
     * @return MAX_NUMBER
     */
    public int getMAX_NUMBER()
    {
        return MAX_NUMBER;
    }


    /**
     * Entry numbers getter method
     * @return numbers of this entry
     */
    public int[] getNumbers()
    {
        int[] temp = new int[numbers.length];
        for (int i = 0; i < numbers.length; i++)
        {
            temp[i] = numbers[i];
        }
        return temp;
    }


    /**
     * Entry numbers setter method
     * @param numbers an int array of numbers
     */
    public void setNumbers(int[] numbers)
    {
        Arrays.sort(numbers);
        for (int i = 0; i < this.numbers.length; i++)
        {
            this.numbers[i] = numbers[i];
        }
    }


    /**
     * Check manually inputted entry numbers
     * @param strNumbers a String of manually inputted numbers
     * @return this manualEntry is valid or not
     */
    public boolean checkEntryNums(String strNumbers)
    {
        String[] chrNumbers = strNumbers.split("\\s+");
        int[] manualEntry = new int[chrNumbers.length];

        // Check if all numbers are integer
        try
        {
            for (int j = 0; j < chrNumbers.length; j++)
            {
                manualEntry[j] = Integer.parseInt(chrNumbers[j]);
            }
        }
        catch (NumberFormatException e)
        {
            System.out.println("Invalid input! Numbers are expected." +
                    " Please try again!");
            return false;
        }
        catch (Exception e)
        {
            System.out.println("Some exceptions occur. Please contact us.");
            return false;
        }

        // Sort the entry numbers
        Arrays.sort(manualEntry);
        // Check if the number of inputs is fewer than required numbers
        if (manualEntry.length < getNUMBER_COUNT())
        {
            System.out.printf("Invalid input! Fewer than %d numbers are provided." +
                    " Please try again!%n", getNUMBER_COUNT());
            return false;
        }
        // Check if the number of inputs is more than required numbers
        if (manualEntry.length > getNUMBER_COUNT())
        {
            System.out.printf("Invalid input! More than %d numbers are provided." +
                    " Please try again!%n", getNUMBER_COUNT());
            return false;
        }
        // Check if the numbers are out of the set range
        for (int i : manualEntry)
        {
            if (!(getMIN_NUMBER() <= i && i <= getMAX_NUMBER()))
            {
                System.out.printf("Invalid input! All numbers must be in the range from " +
                        "%d to %d!%n", getMIN_NUMBER(), getMAX_NUMBER());
                return false;
            }
        }
        // Check if identical numbers exist in the input
        for (int i = 1; i < getNUMBER_COUNT(); i++)
        {
            if (manualEntry[i] == manualEntry[i - 1])
            {
                System.out.println("Invalid input! All numbers must be different!");
                return false;
            }
        }
        return true;
    }


    /**
     * Print the numbers of this entry
     */
    public void PrintEntry()
    {
        for (int i = 0; i < getNUMBER_COUNT(); i++)
        {
            if (i == getNUMBER_COUNT() - 1)
                System.out.printf("%2d", numbers[i]);
            else
                System.out.printf("%2d ", numbers[i]);
        }
    }
}
