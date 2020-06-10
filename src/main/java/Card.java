import org.opencv.core.Rect;
import com.google.gson.Gson;

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

    @Override
    public String toString() {
//        return "Card{" +
//                "x=" + x +
//                ", y=" + y +
//                ", number=" + number +
//                ", suit=" + suit +
//                ", rectangle=" + rectangle +
//                '}';
        return new Gson().toJson(new Card(x, y, number, suit, rectangle));
    }
}
