package amendoza123_project3_v2;

/**
 *
 * @author Tony Mendoza
 */
public class Player {
    protected TonyoQueue hand; // the hand the player has for this game
    String name; // the name of the player
    int score; // used for keeping track of a player's potential score

    // constructor requires to give the player a name
    public Player(String name) {
        hand = new TonyoQueue();
        this.name = name;
        score = 0;
    }

    // Add a card to the player's hand
    public void addCard(Card card) {
        if (card != null) {
            hand.offer(card);
            score += card.getValue(); // increment the player's score
        }
    }

    // return the card the player plays, remove it from the player's hand
    public Card playCard() {
        Card card = (Card) hand.poll();
        if (card != null) {
            score -= card.getValue(); // increment the player's score.
        }
        return card;
    }

    // return the hand of a player
    public TonyoQueue getHand() {
        return hand;
    }

    // return name of player
    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
    
    // Compare the score of two players.
    //@Override
    public int compareTo(Player p) {
        if (this.getScore()< p.getScore()) {
            return -1;
        }
        if (this.getScore() > p.getScore()) {
            return 1;
        }
        return 0;
    }
}