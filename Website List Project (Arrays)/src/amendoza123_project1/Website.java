package amendoza123_project1;

/**
 *
 * @author Tony Mendoza
 */
// Website class is used to hold data regarding its visits.
// It implements the Comparable interface.
public class Website implements Comparable {
    // A bunch of boring variables for maximum fun!
    public static final int MAX_TOTAL_HITS = 15;
    public static final int MAX_DAILY_HITS = 3;
    // A bunch of boring variables sheltered by the word "protected"
    protected String name;
    protected String url;
    protected int totalHits;
    protected int dailyHits;

    // Used to determine how to sort when comparing.
    // It is a class variable because it is used across all websites.
    public static enum SortMode {
        Daily, Total
    }
    protected static SortMode sortMode = SortMode.Total;

    // Its default constructor.
    public Website(String name, String url) {
        this.name = name;
        this.url = url;
    }

    // CanVisit() returns true if it has not reached its daily hits nor maximum total hits.
    public boolean canVisit() {
        return totalHits != MAX_TOTAL_HITS && dailyHits != MAX_DAILY_HITS;
    }

    // Adds to the daily hits.
    public void visit() {
        dailyHits++;
    }

    // Adds the daily hits to the total then resets. Set to public to allow manual tally.
    public void tally() {
        totalHits += dailyHits;
        dailyHits = 0;
    }

    // Returns daily hits.
    public int getDailyHits() {
        return dailyHits;
    }

    // Returns total hits.
    public int getTotalHits() {
        return totalHits;
    }

    // Returns name.
    public String getName() {
        return name;
    }

    // Returns URL.
    public String getURL() {
        return url;
    }

    // Overrides default toString to return name and url.
    @Override
    public String toString() {
        return name + ", " + url;
    }

    // Returns the sorting mode
    public static SortMode GetSortMode() {
        return sortMode;
    }

    // Set the sorting mode
    public static void SetSortMode(SortMode sortMode) {
        Website.sortMode = sortMode;
    }

    // Compares two websites.
    @Override
    public int compareTo(Object object) {
        Website website = (Website) object;

        // Tally the hits for both websites.
        
        int compare = this.dailyHits;
        int reference = website.dailyHits;
        // if comparing the websites based on total hits, assign new comparing values.
        if (Website.sortMode == SortMode.Total) {
            compare = this.totalHits;
            reference = website.totalHits;
        }

        // Compare based on the variables assigned.
        if (compare < reference) {
            return 1;
        } else if (compare > reference) {
            return -1;
        }
        return 0;
    }
}