import org.opencv.core.Rect;

public class Card {

    int x;
    int y;

    char number;
    char suit;

    Rect rectangle;

    public Card(int x, int y, char number, char suit, Rect rectangle) {
        this.x = x;
        this.y = y;
        this.number = number;
        this.suit = suit;
        this.rectangle = rectangle;
    }

}
