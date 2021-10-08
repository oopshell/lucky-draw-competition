/*
 * Student name: Tiantian Li
 * Student ID: 1174998
 * LMS username: tiantian3@student.unimelb.edu.au
 */

import java.util.Scanner;
import java.util.ArrayList;

import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.EOFException;


/**
 * This class implements a competition
 */
public abstract class Competition implements Serializable
{
    private String name;  // competition name
    private int id;  // competition identifier
    private String type;  // the type of the competition, LuckyNumbers or RandomPick
    private boolean isTestingMode;  // testing mode or normal mode
    private boolean isActive;  // this competition is active or not
    private int firstEntryId;  // the beginning entry id in this competition
    transient private ArrayList<Entry> entries; // list of entries
    transient private ArrayList<Entry> winningEntries;  // list of winning entries


    /**
     * Construct and initialise a Competition
     * with a competition name, id and the previous entry id
     * @param aName a competition name
     * @param cptId competition identifier
     * @param preEntryId the id of the previous entry
     */
    public Competition(String aName, int cptId, int preEntryId)
    {
        name = aName;
        id = cptId;
        type = null;
        isTestingMode = true;
        isActive = true;
        firstEntryId = preEntryId + 1;
        entries = new ArrayList<Entry>();
        winningEntries = new ArrayList<Entry>();
    }


    /**
     * Competition id getter method
     * @return the id of this competition
     */
    public int getId()
    {
        return id;
    }


    /**
     * Competition name getter method
     * @return the name of this competition
     */
    public String getName()
    {
        return name;
    }


    /**
     * Competition type getter method
     * @return the type of this competition, LuckyNumbers or RandomPick
     */
    public String getType()
    {
        return type;
    }


    /**
     * Competition mode getter method
     * @return boolean value - whether the mode of this competition is testing mode
     */
    public boolean getIsTestingMode()
    {
        return isTestingMode;
    }


    /**
     * Competition activity status getter method
     * @return boolean value - whether this competition is active
     */
    public boolean getIsActive()
    {
        return isActive;
    }


    /**
     * Competition beginning entry id getter method
     * @return the beginning entry of this competition
     */
    public int getFirstEntryId()
    {
        return firstEntryId;
    }


    /**
     * Get the entries list of this competition
     * @return the entries list of this competition
     */
    public ArrayList<Entry> getEntries()
    {
        return entries;
    }


    /**
     * Get the winning entries list of this competition
     * @return the winning entries list of this competition
     */
    public ArrayList<Entry> getWinningEntries()
    {
        return winningEntries;
    }


    /**
     * Competition type setter method
     * @param aType the type of this competition
     */
    public void setType(String aType)
    {
        type = aType;
    }


    /**
     * Competition mode setter method
     * @param isTestingMode boolean value - the competition is in testing mode or not
     */
    public void setIsTestingMode(boolean isTestingMode)
    {
        this.isTestingMode = isTestingMode;
    }


    /**
     * Competition activity status setter method
     * @param isActive boolean value - the competition is active or not
     */
    public void setIsActive(boolean isActive)
    {
        this.isActive = isActive;
    }


    /**
     * Add an entry to the entries list
     * @param aEntry an entry
     */
    public void addEntry(Entry aEntry)
    {
        entries.add(aEntry);
    }


    /**
     * Add an entry to the winning entries list
     * @param aWinningEntry a winning entry
     */
    public void addWinningEntry(Entry aWinningEntry)
    {
        winningEntries.add(aWinningEntry);
    }


    /**
     * Remove an entry from winning entries list
     * @param index the index of the entry to be removed
     */
    public void removeWinningEntry(int index)
    {
        winningEntries.remove(index);
    }


    /**
     * Add entries to this competition
     * @param keyboard a Scanner object to get user input
     * @param dp an object of DataProvider
     * @return the number of added entries
     */
    public abstract int addEntries(Scanner keyboard, DataProvider dp);


    /**
     * Draw winning entry for this competition
     * @param dp an object of DataProvider
     * @return boolean value - drawing winners is successful or not
     */
    public abstract boolean drawWinners(DataProvider dp);


    /**
     * Print the report of this competition
     */
    public void report()
    {
        System.out.println();
        System.out.printf("Competition ID: %d, name: %s, active: ", id, name);
        if (isActive)
            System.out.printf("yes%n");
        else
            System.out.printf("no%n");
        System.out.println("Number of entries: " + entries.size());
        if (!isActive)
        {
            System.out.println("Number of winning entries: " + winningEntries.size());
            int totPrize = 0;
            for (Entry e : winningEntries)
            {
                totPrize += e.getPrize();
            }
            System.out.println("Total awarded prizes: " + totPrize);
        }
    }


    /**
     * Write entries of this competition into a file
     * @param fileName a file name
     * @throws IOException if a file cannot be opened/read
     */
    public void writeEntriesToFile(String fileName) throws IOException
    {
        String[] fileNames = fileName.split("\\.(?=[^\\.]+$)");
        String entryFileName = fileNames[0] + "C" + id + "E." + fileNames[1];
        String winnerFileName = fileNames[0] + "C" + id + "WE." + fileNames[1];
        try
        {
            FileOutputStream eFileOutputStream = new FileOutputStream(entryFileName);
            ObjectOutputStream eObjectOutputStream = new ObjectOutputStream(eFileOutputStream);
            FileOutputStream wFileOutputStream = new FileOutputStream(winnerFileName);
            ObjectOutputStream wObjectOutputStream = new ObjectOutputStream(wFileOutputStream);
            for (Entry e : entries)
            {
                eObjectOutputStream.writeObject(e);
                eObjectOutputStream.flush();
            }
            for (Entry e : winningEntries)
            {
                wObjectOutputStream.writeObject(e);
                wObjectOutputStream.flush();
            }
            eFileOutputStream.close();
            eObjectOutputStream.close();
            wFileOutputStream.close();
            wObjectOutputStream.close();
        }
        catch (IOException e)
        {
            System.out.println("File not found. Please try again.");
        }
        catch (Exception e)
        {
            System.out.println("Some exceptions occur. Please contact us.");
        }
    }


    /**
     * Read and load entries of this competition from a file
     * @param fileName a file name
     * @throws IOException if a file cannot be opened/read
     */
    public void readEntriesFromFile(String fileName) throws IOException
    {
        entries = new ArrayList<Entry>();
        winningEntries = new ArrayList<Entry>();
        String[] fileNames = fileName.split("\\.(?=[^\\.]+$)");
        String entryFileName = fileNames[0] + "C" + id + "E." + fileNames[1];
        String winnerFileName = fileNames[0] + "C" + id + "WE." + fileNames[1];
        try
        {
            FileInputStream eFileInputStream = new FileInputStream(entryFileName);
            ObjectInputStream eObjectInputStream = new ObjectInputStream(eFileInputStream);
            FileInputStream wFileInputStream = new FileInputStream(winnerFileName);
            ObjectInputStream wObjectInputStream = new ObjectInputStream(wFileInputStream);
            while (true)
            {
                try
                {
                    Entry e = (Entry) eObjectInputStream.readObject();
                    entries.add(e);
                }
                catch (EOFException e)
                {
                    break;
                }
            }
            while (true)
            {
                try
                {
                    Entry e = (Entry) wObjectInputStream.readObject();
                    winningEntries.add(e);
                }
                catch (EOFException e)
                {
                    break;
                }
            }
            eFileInputStream.close();
            eObjectInputStream.close();
            wFileInputStream.close();
            wObjectInputStream.close();
        }
        catch (IOException e)
        {
            System.out.println("File not found. Please try again.");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Class not found. Please try again.");
        }
        catch (Exception e)
        {
            System.out.println("Some exceptions occur. Please contact us.");
        }
    }

}
