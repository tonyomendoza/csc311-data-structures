package amendoza123_project3_v2;

/**
 *
 * @author Tony Mendoza
 */
// This class holds data for client's account.
public class Card {

    protected int suit; // The integer value of a suit, used for comparing
    protected int denomination; // the int value of a denomiation, used for comparing
    protected static String[] Suits; // The string value of a suit
    protected static String[] Denominations; // the string value of denominations
    protected static int baseValue = 1; // where to start comparing values from denominations

    // Default constructor for an account, initializes all variables.
    public Card(int suit, int denomination) {
        this.suit = suit;
        this.denomination = denomination;
    }

    // Set denominations based on inputted string array
    public static void SetDenominations(String[] denominations) {
        Card.Denominations = denominations;
    }

    // return the denimination value at the base value
    public int getValue() {
        return denomination + baseValue;
    }

    // return the suit value
    public int getSuitValue() {
        return suit;
    }

    // set suits based on inputted string array
    public static void SetSuits(String[] suits) {
        Card.Suits = suits;
    }

    // set a generic deck, based on 52 cards, and the lowest card starts at 2
    public static void SetGenericDeck() {
        Card.Suits = GetGenericSuits();
        Card.Denominations = GetGenericDenominations();
        baseValue = 2;
    }

    // Returns a string based on a certain format
    @Override
    public String toString() {
        if (Card.Denominations != null && Card.Denominations.length > denomination) {
            if (Card.Suits != null && Card.Suits.length > suit) {
                return Card.Denominations[denomination] + " of " + Card.Suits[suit];
            }
            return Card.Denominations[denomination] + " of " + suit;
        }
        return (denomination + " of " + suit);
    }

    // Return generic suis of cards in a string array
    public static String[] GetGenericSuits() {
        return new String[]{
            "Spades", "Hearts", "Diamonds", "Clubs"
        };
    }

    // Return generic denominations of cards as a string array
    public static String[] GetGenericDenominations() {
        return new String[]{
            "Two", "Three", "Four", "Five", "Six", "Seven",
            "Eight", "Nine", "Ten", "Jack", "Queen", "King", "Ace"
        };
    }

    // Shuffle the deck
    public static void Shuffle(Card[] deck) {
        for (int i = 0; i < deck.length; i++) { // For every card
            int j = (int) (Math.random() * deck.length); // the position to be swapped
            if (i != j) { // if the card is not in the position to be swapped, then swap
                Card temp = deck[i];
                deck[i] = deck[j];
                deck[j] = temp;
            } else { // else try again
                i--;
            }
        }
    }

    // Compare the value of two cards. If they are the same, then compare the suits.
    //@Override
    public int compareTo(Card c) {
        if (this.getValue() < c.getValue()) {
            return -1;
        }
        if (this.getValue() > c.getValue()) {
            return 1;
        }
        if (this.getSuitValue() < c.getSuitValue()) {
            return -1;
        }
        if (this.getSuitValue() > c.getSuitValue()) {
            return 1;
        }
        return 0;
    }
}