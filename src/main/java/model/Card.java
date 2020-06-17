package model;

import org.opencv.core.Point;
import org.opencv.core.Rect;

/**
 * Represents a face-up card.
 * Also contains information relating to position in 2D space - used for image recognition purposes.
 *
 * @author Malte Brink Kristensen, Jeppe Kaare Larsen, Neal Patrick Norman, Mads Martin Dickmeiss Hemer
 */
public class Card {

    public int x;
    public int y;

    public Rect rectangle;

    private int value;
    private Suit suit;

    public Card(int value, Suit suit) {
        this.value = value;
        this.suit = suit;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public Point getPosition() {
        return new Point(x, y);
    }

    public Point getOffset() {
        return new Point(rectangle.width, rectangle.height);
    }

    public int getValue() {
        return value;
    }

    public Suit getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        String suit;
        String value;

        switch (this.suit) {
            case CLUB:
                suit = "klør";
                break;
            case DIAMOND:
                suit = "ruder";
                break;
            case HEART:
                suit = "hjerter";
                break;
            case SPADE:
                suit = "spar";
                break;
            default:
                suit = "ukendt kulør";
                break;
        }

        switch (this.value) {
            case 1:
                value = "es";
                break;
            case 11:
                value = "knægt";
                break;
            case 12:
                value = "dame";
                break;
            case 13:
                value = "konge";
                break;
            default:
                value = String.valueOf(this.value);
                break;
        }

        return suit + " " + value;
    }

    public String toSimpleString() {
        String suit;
        String value = String.valueOf(this.value);

        switch (this.suit) {
            case CLUB:
                suit = "K";
                break;
            case DIAMOND:
                suit = "R";
                break;
            case HEART:
                suit = "H";
                break;
            case SPADE:
                suit = "S";
                break;
            default:
                suit = "-";
                break;
        }

        return suit + value;
    }

    public enum Suit {
        SPADE, CLUB, HEART, DIAMOND
    }

}