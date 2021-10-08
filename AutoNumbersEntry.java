/*
 * Student name: Tiantian Li
 * Student ID: 1174998
 * LMS username: tiantian3@student.unimelb.edu.au
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;


/**
 * This class implements an entry with automatically generated numbers
 * derived from NumbersEntry
 */
public class AutoNumbersEntry extends NumbersEntry
{
    /**
     * Construct and initialise an AutoNumbersEntry
     * with entry id, member id and bill id
     * @param entryId entry identifier
     * @param aMemberId a member identifier
     * @param aBillId a bill identifier
     */
    public AutoNumbersEntry(int entryId, String aMemberId, String aBillId)
    {
        super(entryId, aMemberId, aBillId);
    }


    /**
     * Automatically create entry numbers
     * @return an automatically generated numbers array
     */
    public int[] createNumbers ()
    {
        ArrayList<Integer> validList = new ArrayList<Integer>();
        int[] tempNumbers = new int[getNUMBER_COUNT()];
        for (int i = 1; i <= getMAX_NUMBER(); i++)
        {
            validList.add(i);
        }
        Collections.shuffle(validList, new Random());
        for (int i = 0; i < getNUMBER_COUNT(); i++)
        {
            tempNumbers[i] = validList.get(i);
        }
        Arrays.sort(tempNumbers);
        return tempNumbers;
    }


    /**
     * Automatically create numbers applying seed
     * @param seed a seed of random generator
     * @return an automatically generated numbers array, applying seed
     */
    public int[] createNumbers (int seed)
    {
        ArrayList<Integer> validList = new ArrayList<Integer>();
        int[] tempNumbers = new int[getNUMBER_COUNT()];
        for (int i = 1; i <= getMAX_NUMBER(); i++)
        {
            validList.add(i);
        }
        Collections.shuffle(validList, new Random(seed));
        for (int i = 0; i < getNUMBER_COUNT(); i++)
        {
            tempNumbers[i] = validList.get(i);
        }
        Arrays.sort(tempNumbers);
        return tempNumbers;
    }


    /**
     * Print the numbers of this entry
     */
    public void PrintEntry()
    {
        for (int i = 0; i < getNUMBER_COUNT(); i++)
        {
            System.out.printf("%2d ", this.getNumbers()[i]);
        }
        System.out.print("[Auto]");
    }

}
