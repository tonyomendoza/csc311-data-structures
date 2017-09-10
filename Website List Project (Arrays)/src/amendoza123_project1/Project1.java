package amendoza123_project1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Tony Mendoza
 */
public class Project1 {
    // This ArrayList is the backbone of the application. It stores websites!
    static ArrayList<Website> websites;
    // Boring stuff... but essential! They are used for quickly changing the app.
    // Do I have to comment further (or at all) on the variables? They are self-explanatory.
    static final int MAX_WEBSITE_SIZE = 20;
    static final int MAX_TOP_RANKING_LENGTH = 3;
    static final int MAX_VISITING_LENGTH = 10;
    static final String TOP_RANK = "Top 3";

    /**
     * @param args the command line arguments
     */
    // main(...) runs the program.
    // 19/30 lines {Brackets, whitespace, and comments don't count}
    public static void main(String[] args) {
        // Try to use the System's input for the interactive feature of this applicaiton.
        try (Scanner input = new Scanner(System.in)) {
            // Initialize the ArrayList to hold the websites.
            websites = new ArrayList<>();

            // Welcome the user.
            System.out.println("Welcome to the Website Popularity Sorter Console Simulation Application !\nYou can use this app to simulate visiting 20 websites and sorting them by popularity.");

            // Handle what the user inputs.
            HandleInput(input, websites);

            // Set the sorting mode for daily hits
            Website.SetSortMode(Website.SortMode.Daily);

            // Simulate a day 4 times. 
            for (int d = 0; d < 4; d++) {
                System.out.println("Day " + (d + 1) + ":");
                QuickSimulateDay(websites);
            }

            // Tally one last time before determining the top 3.
            for (int r = 0; r < websites.size(); r++) {
                websites.get(r).tally();
            }

            // Sort the websites by total to determin the top 3.
            Website.SetSortMode(Website.SortMode.Total);
            Collections.sort(websites);

            // Print the top 3         
            System.out.println("Ranking: " + TOP_RANK + ":");
            for (int t = 0; t < 3; t++) {
                System.out.println((t + 1) + ": " + websites.get(t).getName());
            }

            // Close the input.
            input.close();

            // Tell the user goodbye.
            System.out.println("Simulation finished. Have a nice day!");
        } catch (Exception e) {
            // If something happened, print the error message.
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    // Handle the input of the user.
    static void HandleInput(Scanner input, ArrayList<Website> websiteList) {
        // Handle input as long as the necessary amount of websites have been added.
        while (websiteList.size() < MAX_WEBSITE_SIZE) {
            // Give the user their options.
            System.out.println("Please press 'i' to add a website. Or input 'r' to populate the rest of the list by reading a file.");
            System.out.println("To input a file, please configure your data by lines of two. Example: First line = Website Name, Next Line = Website URL, and so on...");
            // Read the user's command.
            String command = input.nextLine();
            if (command.toLowerCase().startsWith("i")) {
                // If the user wants to input the website manually, then...
                // Prompt the user for the name of the website, then interpret it.
                System.out.println("Type the name of the website: ");
                String name = input.nextLine();
                // Prompt the user for the URL of the website, then interpet it.
                System.out.println("Type the URL of the website: ");
                String url = input.nextLine();

                // Add the website based on the interpretations, then let the user know it has been added.
                websiteList.add(new Website(name, url));
                System.out.println("Added: " + websiteList.get(websiteList.size() - 1).toString());
            } else if (command.toLowerCase().startsWith("r")) {
                // else if the user wants to populate the rest of the list based on a file...
                // Prompt for the filepath, then interpret the input.
                System.out.println("Please input the path and filename.");
                String filename = input.nextLine();
                
                String name = "";
                int turn = 0;
                // Try using the Bufferreader to read the file.
                try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                    String line;
                    // Let the user know it is reading the file.
                    System.out.println("Reading the file...");
                    // Do the following while reading and if the website list is not filled.
                    while ((line = br.readLine()) != null && websiteList.size() < MAX_WEBSITE_SIZE) {
                        // Use a switch to add by pairs.
                        switch (turn) {
                            // Assign the line to the name variable for later.
                            case 0:
                                name = line;
                                turn = 1; // for switching
                                break;
                            // At this point, it is later.
                            case 1:
                                // Add the website to the list then let the user know it has been added.
                                websiteList.add(new Website(name, line));
                                System.out.println("Added: " + websiteList.get(websiteList.size() - 1).toString());
                                turn = 0; // for switching
                                break;
                        }
                    }
                } catch (Exception e) {
                    // Most likely something happened with the bufferereader, let the user know that.
                    System.out.println("An error occured: " + e.getMessage());
                }
            } else {
                // Let the user know the application could not interperet the input.
                System.out.println("Incorrect input.");
            }

            // A blank space. :)
            System.out.println();

            // If the last website has been added, then let the user know input is finished.
            if (websiteList.size() >= MAX_WEBSITE_SIZE) {
                System.out.println("Finished adding websites.");
                System.out.println();
            }
        }
    }

    // Quickly simulate the day. It is simple and could improve for larger arrays.
    static void QuickSimulateDay(ArrayList<Website> referencedWebsites) {
        // If there are websites to visit, then...
        if (referencedWebsites.size() > 0) {
            // Create a copy of the list of websites and tally to clear any old hits.
            ArrayList<Website> websiteList = new ArrayList<>();
            for (int r = 0; r < referencedWebsites.size(); r++) {
                websiteList.add(referencedWebsites.get(r));
                websiteList.get(r).tally();
            }

            // Begin printing for the visits
            System.out.println("Visited...");

            // Loop through the website lists
            for (int w = 0; w < MAX_VISITING_LENGTH && w < MAX_WEBSITE_SIZE; w++) {
                // Randomly assign an index for the website array.
                int visit = (int) (Math.random() * websiteList.size());
                Website site = websiteList.get(visit);
                // Visit the website if they can visit.
                // Otherwise, remove the website and try to visit another one.
                if (site.canVisit()) {
                    site.visit();
                    System.out.println(site.toString());
                } else {
                    websiteList.remove(visit);
                    w--;
                }
            }

            // Rank the websites for the day and print them.
            Ranking(referencedWebsites);

            // End printing for the visits and ranking
            System.out.println("");
        }
    }

    // Ranking for the day.
    static void Ranking(ArrayList<Website> referencedWebsites) {
        // Begin printing the ranking
        System.out.print("Ranking: [");

        // Sort the websites
        Collections.sort(referencedWebsites);

        // Print each website that was just sorted by their daily rank
        for (int r = 0; r < referencedWebsites.size(); r++) {
            System.out.print(referencedWebsites.get(r).getName());
            if (r < referencedWebsites.size() - 1) {
                System.out.print(", ");
            }
        }

        // End printing the ranking
        System.out.println("]");
    }
}
