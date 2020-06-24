package gamelogic.util;

import model.Card;

/**
 * Utility class for checking whether two cards form a sequence.
 *
 * @author Malte Brink Kristensen
 */
public class CardSequenceChecker {

    public boolean isAlternatingColorSequence(Card highCard, Card lowCard) {
        boolean isSequence = false;

        if (highCard.getValue() == lowCard.getValue() + 1) {
            if (highCard.getSuit() == Card.Suit.CLUB || highCard.getSuit() == Card.Suit.SPADE) {
                if (lowCard.getSuit() == Card.Suit.DIAMOND || lowCard.getSuit() == Card.Suit.HEART) {
                    isSequence = true;
                }
            } else if (highCard.getSuit() == Card.Suit.DIAMOND || highCard.getSuit() == Card.Suit.HEART) {
                if (lowCard.getSuit() == Card.Suit.CLUB || lowCard.getSuit() == Card.Suit.SPADE) {
                    isSequence = true;
                }
            }
        }

        return isSequence;
    }

    public boolean isSameSuitSequence(Card highCard, Card lowCard) {
        boolean isSequence = false;

        if (highCard.getValue() == lowCard.getValue() + 1) {
            switch (lowCard.getSuit()) {
                case CLUB:
                    if (highCard.getSuit() == Card.Suit.CLUB) {
                        isSequence = true;
                    }
                    break;
                case DIAMOND:
                    if (highCard.getSuit() == Card.Suit.DIAMOND) {
                        isSequence = true;
                    }
                    break;
                case HEART:
                    if (highCard.getSuit() == Card.Suit.HEART) {
                        isSequence = true;
                    }
                    break;
                case SPADE:
                    if (highCard.getSuit() == Card.Suit.SPADE) {
                        isSequence = true;
                    }
                    break;
            }
        }

        return isSequence;
    }
}
