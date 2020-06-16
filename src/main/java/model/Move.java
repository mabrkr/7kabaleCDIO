package model;

/**
 * SKRIV NOGET HER
 *
 * @author Malte Brink Kristensen
 */
public class Move {

    private MoveType type;
    private Card card;
    private Card target;

    public Move(MoveType type) {
        this.type = type;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void setTarget(Card target) {
        this.target = target;
    }

    @Override
    public String toString() {
        switch (type) {
            case DRAW:
                return "Træk kort fra bunken.";
            case MOVE:
                return "Flyt " + card.toString() + " til " + target.toString() + ".";
            case SUIT_STACK_MOVE:
                return "Flyt " + card.toString() + " til grundbunke.";
            case KING_MOVE:
                return "Flyt " + card.toString() + " til tom plads.";
            case TURN:
                return "Vend kort.";
            default:
                return "Kan ikke foreslå træk.";
        }
    }

    public enum MoveType {
        MOVE, SUIT_STACK_MOVE, DRAW, KING_MOVE, TURN
    }

}
