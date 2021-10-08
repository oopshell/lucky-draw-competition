/*
 * Student name: Tiantian Li
 * Student ID: 1174998
 * LMS username: tiantian3@student.unimelb.edu.au
 */

// import java.io.*;

import java.util.Scanner;
import java.util.ArrayList;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.EOFException;


/**
 * This class implements the running competition,
 * and records ended competitions.
 */
public class SimpleCompetitions
{
    private ArrayList<Competition> competitionList
            = new ArrayList<Competition>(); // a list of added competitions
    private Competition curCompetiton;  // the current active competition
    private int entriesId;  // current entries identifier


    /**
     * Create and add a new competition
     * @param keyboard a Scanner object to get user input
     * @param isTesting the mode of this competition is testing or not
     * @return the new added competition
     */
    public Competition addNewCompetition(Scanner keyboard, boolean isTesting)
    {
        char compType;
        while (true)
        {
            System.out.println("Type of competition (L: LuckyNumbers, R: RandomPick)?:");
            compType = keyboard.next().toUpperCase().charAt(0);
            keyboard.nextLine();
            if (compType == 'L' || compType == 'R')
                break;
            else
                System.out.println("Invalid competition type! Please choose again.");
        }

        System.out.println("Competition name: ");
        String CompName = keyboard.nextLine();

        int cptId = competitionList.size() + 1;  // calculate the current competition id
        Competition newCompetition;
        if (compType == 'L')  // create a Lucky Numbers Competition
        {
            newCompetition = new LuckyNumbersCompetition(CompName, cptId, entriesId);
            newCompetition.setIsTestingMode(isTesting);
        }
        else  // compType == 'R'  // create a Random Pick Competition
        {
            newCompetition = new RandomPickCompetition(CompName, cptId, entriesId);
            newCompetition.setIsTestingMode(isTesting);
        }
        System.out.println("A new competition has been created!");
        System.out.println("Competition ID: " + newCompetition.getId()
                + ", Competition Name: " + newCompetition.getName()
                + ", Type: " + newCompetition.getType());
        return newCompetition;
    }


    /**
     * Print the summary report
     */
    public void report()
    {
        System.out.println("----SUMMARY REPORT----");
        // Calculate the number of completed competitions
        int numCompleted = 0;
        for (Competition cpt: competitionList)
        {
            if (!cpt.getIsActive())
                numCompleted++;
        }
        // Calculate the number of active competitions
        int numActive = 0;
        for (Competition cpt: competitionList)
        {
            if (cpt.getIsActive())
                numActive++;
        }
        // Print summary and details of each competition
        System.out.printf("+Number of completed competitions: %d%n", numCompleted);
        System.out.printf("+Number of active competitions: %d%n", numActive);
        for (Competition cpt: competitionList)
        {
            cpt.report();
        }
    }


    /**
     * Write competitions into a file (e.g., demo.dat)
     * @param keyboard a Scanner object to get user input
     */
    public void writeCompetitionToFile(Scanner keyboard)
    {
        while (true)
        {
            System.out.println("File name:");
            String fileName = keyboard.nextLine();

            FileOutputStream aFileOutputStream = null;
            ObjectOutputStream aObjectOutputStream = null;
            try
            {
                aFileOutputStream = new FileOutputStream(fileName);
                aObjectOutputStream = new ObjectOutputStream(aFileOutputStream);
                for (Competition cpt : competitionList)
                {
                    cpt.writeEntriesToFile(fileName);
                    aObjectOutputStream.writeObject(cpt);
                    aObjectOutputStream.flush();
                }
                break;
            }
            catch (IOException e)
            {
                System.out.println("File not found. Please try again.");
            }
            catch (Exception e)
            {
                System.out.println("Some exceptions occur. Please contact us.");
            }
            finally
            {
                if (aFileOutputStream != null)
                {
                    try
                    {
                        aFileOutputStream.close();
                    } catch (IOException e)
                    {
                        System.out.println("File not found. Please try again.");
                    }
                }
                if (aObjectOutputStream != null)
                {
                    try
                    {
                        aObjectOutputStream.close();
                    } catch (IOException e)
                    {
                        System.out.println("File not found. Please try again.");
                    }
                }
            }
        }
    }


    /**
     * Read and load competitions from a file (e.g., demo.dat)
     * @param keyboard a Scanner object to get user input
     */
    public void readCompetitionFromFile(Scanner keyboard)
    {
        while (true)
        {
            System.out.println("File name:");
            String fileName = keyboard.nextLine();

            FileInputStream aFileInputStream = null;
            ObjectInputStream aObjectInputStream = null;
            try
            {
                aFileInputStream = new FileInputStream(fileName);
                aObjectInputStream = new ObjectInputStream(aFileInputStream);
                while (true)
                {
                    try
                    {
                        Competition cpt = (Competition) aObjectInputStream.readObject();
                        cpt.readEntriesFromFile(fileName);
                        competitionList.add(cpt);
                    }
                    catch (EOFException e)
                    {
                        break;
                    }
                }
                break;
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

            finally
            {
                if (aFileInputStream != null)
                {
                    try
                    {
                        aFileInputStream.close();
                    }
                    catch (IOException e)
                    {
                        System.out.println("File not found. Please try again.");
                    }
                }
                if (aObjectInputStream != null)
                {
                    try
                    {
                        aObjectInputStream.close();
                    }
                    catch (IOException e)
                    {
                        System.out.println("File not found. Please try again.");
                    }
                }
            }
        }
    }


    /**
     * Main program that uses the main SimpleCompetitions class
     * @param args main program arguments
     */
    public static void main(String[] args)
    {
        Scanner keyboard = new Scanner(System.in);  // initialise a scanner keyboard
        SimpleCompetitions sc = new SimpleCompetitions(); // create an object of SimpleCompetitions
        boolean anActiveComp = false;  // there exists an active competition or not
        boolean isTestingMode;  // is in testing mode or not
        sc.entriesId = 0;  // initialise entry identifier

        System.out.println("----WELCOME TO SIMPLE COMPETITIONS APP----");

        // Choose whether to load competitions from file
        char loadFromFile;
        while (true)
        {
            System.out.println("Load competitions from file? (Y/N)?");
            loadFromFile = keyboard.next().toUpperCase().charAt(0);
            keyboard.nextLine();
            if (loadFromFile == 'Y' || loadFromFile == 'N')
                break;
            else
                System.out.println("Unsupported option. Please try again!");
        }
        if (loadFromFile == 'Y')
        {
            sc.readCompetitionFromFile(keyboard);
            int lastCompetitionIndex = sc.competitionList.size() - 1;
            sc.curCompetiton = sc.competitionList.get(lastCompetitionIndex);
            anActiveComp = sc.curCompetiton.getIsActive();
            isTestingMode = sc.curCompetiton.getIsTestingMode();
        }
        else
        {
            // Choose mode: testing mode or normal mode
            while (true)
            {
                System.out.println("Which mode would you like to run? " +
                        "(Type T for Testing, and N for Normal mode):");
                char mode = keyboard.next().toUpperCase().charAt(0);
                keyboard.nextLine();
                if (mode == 'T')
                {
                    isTestingMode = true;
                    break;
                }
                else if (mode == 'N')
                {
                    isTestingMode = false;
                    break;
                }
                else
                    System.out.println("Invalid mode! Please choose again.");
            }
        }

        // Load data from members file and bills file
        DataProvider dp = null;
        String fileNameMember;
        String fileNameBill;
        while (true)
        {
            System.out.println("Member file: ");
            fileNameMember = keyboard.nextLine();
            System.out.println("Bill file: ");
            fileNameBill = keyboard.nextLine();
            try
            {
                dp = new DataProvider(fileNameMember, fileNameBill);
                break;
            }
            catch (DataAccessException e)
            {
                System.out.println("File not found. Please try again.");
            }
            catch (DataFormatException e)
            {
                System.out.println("Wrong data format! Please try another file.");
            }
            catch (Exception e)
            {
                System.out.println("Some exceptions occur. Please contact us.");
            }
        }

        // Start the main menu
        mainLoop:
        while (true)
        {
            int option;  // user's menu function option
            while (true)
            {
                // Show the menu
                System.out.println("Please select an option. Type 5 to exit.");
                System.out.println("1. Create a new competition");
                System.out.println("2. Add new entries");
                System.out.println("3. Draw winners");
                System.out.println("4. Get a summary report");
                System.out.println("5. Exit");

                // Choose a function
                String optionStr = keyboard.nextLine();
                try
                {
                    option = Integer.parseInt(optionStr);
                    if (1 <= option && option <= 5)
                        break;
                    else
                        System.out.println("Unsupported option. Please try again!");
                }
                catch (Exception e)
                {
                    System.out.println("A number is expected. Please try again.");
                }
            }

            switch (option)
            {
                case 1:  // Create a new competition
                    if (anActiveComp)
                    {   // There exists an active competition
                        System.out.println("There is an active competition. " +
                                "SimpleCompetitions does not support concurrent competitions!");
                    }
                    else
                    {
                        sc.curCompetiton = sc.addNewCompetition(keyboard, isTestingMode);
                        sc.competitionList.add(sc.curCompetiton);
                        anActiveComp = true;
                    }
                    break;

                case 2:  // Add new entries
                    if (anActiveComp)
                    {   // There exists an active competition
                        sc.entriesId += sc.curCompetiton.addEntries(keyboard, dp);
                        while (true)
                        {
                            System.out.println("Add more entries (Y/N)?");
                            char yesOrNo = keyboard.next().toUpperCase().charAt(0);
                            keyboard.nextLine();
                            if (yesOrNo == 'Y')
                                sc.entriesId += sc.curCompetiton.addEntries(keyboard, dp);
                            else if (yesOrNo == 'N')
                                break;
                            else
                                System.out.println("Unsupported option. Please try again!");
                        }
                    }
                    else
                        System.out.println("There is no active competition. Please create one!");
                    break;

                case 3:  // Draw winners
                    if (anActiveComp)
                    {   // There exists an active competition
                        if (sc.curCompetiton.drawWinners(dp))
                        {
                            sc.entriesId = 0;  // reset the entry id
                            anActiveComp = false;  // end this competition
                        }
                    }
                    else
                        System.out.println("There is no active competition. Please create one!");
                    break;

                case 4:  // Get a summary report
                    if (sc.competitionList.size() > 0)
                        sc.report();
                    else
                        System.out.println("No competition has been created yet!");
                    break;

                case 5:  // Exit
                    System.out.println("Save competitions to file? (Y/N)?");
                    char saveComp;
                    while (true)
                    {
                        saveComp = keyboard.next().toUpperCase().charAt(0);
                        keyboard.nextLine();
                        if (saveComp == 'Y' || saveComp == 'N')
                            break;
                        else
                            System.out.println("Invalid input! Please try again.");
                    }
                    if (saveComp == 'Y')
                    {
                        sc.writeCompetitionToFile(keyboard);
                        System.out.println("Competitions have been saved to file.");
                        try
                        {
                            dp.updateBills(fileNameBill);
                            System.out.println("The bill file has also " +
                                    "been automatically updated.");
                        }
                        catch (DataAccessException e)
                        {
                            System.out.println("The file of bills was not found.");
                        }
                        catch (Exception e)
                        {
                            System.out.println("Some exceptions occur. Please contact us.");
                        }
                        System.out.println("Goodbye!");
                        break mainLoop;
                    }
                    System.out.println("Goodbye!");
                    break mainLoop;

                default:
                    System.out.println("Unsupported option. Please try again!");
                    break;
            }
        }

        keyboard.close();
    }
}
