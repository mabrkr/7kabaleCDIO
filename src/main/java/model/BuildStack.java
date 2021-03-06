package model;

/**
 * Represents a build stack in a game of Klondike. A build stack consists of a number of face-down and face-up cards.
 *
 * @author Malte Brink Kristensen
 */
public class BuildStack implements Comparable<BuildStack> {

    private final Card[] faceUpCards;
    private final double heightOfFaceDownCards;

    public BuildStack(Card[] faceUpCards, double heightOfFaceDownCards) {
        this.faceUpCards = faceUpCards;
        this.heightOfFaceDownCards = heightOfFaceDownCards;
    }

    public Card[] getFaceUpCards() {
        return faceUpCards;
    }

    public Card getBottomFaceUpCard() {
        return faceUpCards[0];
    }

    public Card getTopFaceUpCard() {
        return faceUpCards[faceUpCards.length - 1];
    }

    public double getHeightOfFaceDownCards() {
        return heightOfFaceDownCards;
    }

    public int compareTo(BuildStack otherBuildStack) {
        if (heightOfFaceDownCards > otherBuildStack.heightOfFaceDownCards) {
            return 1;
        } else if (heightOfFaceDownCards < otherBuildStack.heightOfFaceDownCards) {
            return -1;
        } else {
            return 0;
        }
    }
}
