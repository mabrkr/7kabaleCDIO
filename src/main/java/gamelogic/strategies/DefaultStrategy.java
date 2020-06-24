package gamelogic.strategies;

import model.BuildStack;
import model.Card;
import model.GameSnapshot;
import model.Move;
import gamelogic.util.CardSequenceChecker;

/**
 * Strategy for finding the best possible move for a given game state of a game of Klondike. This strategy should be
 * followed until it no longer leads to anything but drawing cards.
 *
 * The rules are described in detail here:
 * https://dtudk.sharepoint.com/:b:/r/sites/F20CDIOGruppe14/Shared%20Documents/CDIO_Version2_7Kabale/Strategies.pdf
 *
 * @author Malte Brink Kristensen
 */
public class DefaultStrategy implements MoveCalculationStrategy {

    protected GameSnapshot gameSnapshot;
    protected CardSequenceChecker sequenceChecker = new CardSequenceChecker();

    public Move execute(GameSnapshot gameSnapshot) {
        this.gameSnapshot = gameSnapshot;

        Move bestPossibleMove;

        // Rule 1
        bestPossibleMove = findPotentialEmptyDrawPileMove();
        if (bestPossibleMove != null) {
            return bestPossibleMove;
        }

        // Rule 2
        bestPossibleMove = findPotentialAceOrTwoMove();
        if (bestPossibleMove != null) {
            return bestPossibleMove;
        }

        // Rule 3
        bestPossibleMove = findPotentialKingToEmptyStackMove();
        if (bestPossibleMove != null) {
            return bestPossibleMove;
        }

        // Rule 4
        bestPossibleMove = findPotentialFaceDownCardFreeingMove();
        if (bestPossibleMove != null) {
            return bestPossibleMove;
        }

        // Rule 5
        bestPossibleMove = setupPotentialFaceDownCardFreeingMove();
        if (bestPossibleMove != null) {
            return bestPossibleMove;
        }

        // Rule 6
        return new Move(Move.MoveType.DRAW);
    }

    protected Move findCardToBuildStackMove(Card card) {
        Move move;

        for (BuildStack stack : gameSnapshot.getBuildStacks()) {
            Card bottomCard = stack.getBottomFaceUpCard();

            if (sequenceChecker.isAlternatingColorSequence(bottomCard, card)) {
                move = new Move(Move.MoveType.MOVE);
                move.setCard(card);
                move.setTarget(bottomCard);

                return move;
            }
        }

        return null;
    }

    protected Move findCardToSuitStackMove(Card card) {
        Move move;

        if (card.getValue() == 1) {
            move = new Move(Move.MoveType.SUIT_STACK_MOVE);
            move.setCard(card);

            return move;
        }

        if (gameSnapshot.getTopCardsOfSuitStacks() != null) {
            for (Card cardFromSuitStack : gameSnapshot.getTopCardsOfSuitStacks()) {
                if (sequenceChecker.isSameSuitSequence(card, cardFromSuitStack)) {
                    move = new Move(Move.MoveType.SUIT_STACK_MOVE);
                    move.setCard(card);

                    return move;
                }
            }
        }

        return null;
    }

    protected Move findPotentialEmptyDrawPileMove() {
        Move move;

        if (gameSnapshot.getCardFromDrawPile() == null && !gameSnapshot.isDrawPileEmpty()) {
            move = new Move(Move.MoveType.DRAW);

            return move;
        }

        return null;
    }

    protected Move findPotentialAceOrTwoMove() {
        Move move;

        // Check if top card of draw pile is an ace or a two.
        Card cardFromDrawPile = gameSnapshot.getCardFromDrawPile();

        if (cardFromDrawPile != null && (cardFromDrawPile.getValue() == 1 || cardFromDrawPile.getValue() == 2)) {
            move = findCardToSuitStackMove(cardFromDrawPile);

            if (move != null) {
                return move;
            }
        }

        // Check build stacks for aces or twos.
        for (BuildStack stack : gameSnapshot.getBuildStacks()) {
            Card bottomCard = stack.getBottomFaceUpCard();

            if (bottomCard.getValue() == 1 || bottomCard.getValue() == 2) {
                move = findCardToSuitStackMove(bottomCard);

                if (move != null) {
                    return move;
                }
            }
        }

        return null;
    }

    protected Move findPotentialKingToEmptyStackMove() {
        Move move;

        // Check if top face-up card of each build stack is a king.
        for (BuildStack stack : gameSnapshot.getBuildStacks()) {
            if (stack.getTopFaceUpCard().getValue() == 13 && stack.getHeightOfFaceDownCards() != 0) {
                move = findOrMakeEmptyStackForKing(stack.getTopFaceUpCard());

                if (move != null) {
                    return move;
                }
            }
        }

        // Check if card from draw pile is a king.
        if (gameSnapshot.getCardFromDrawPile().getValue() == 13) {
            move = findOrMakeEmptyStackForKing(gameSnapshot.getCardFromDrawPile());

            if (move != null) {
                return move;
            }
        }

        return null;
    }

    protected Move findOrMakeEmptyStackForKing(Card king) {
        Move move;

        // Check for empty stacks.
        if (gameSnapshot.getBuildStacks().length < 7) {
            move = new Move(Move.MoveType.KING_MOVE);
            move.setCard(king);

            return move;
        }

        // Check if an empty stack can be created...
        for (BuildStack stack : gameSnapshot.getBuildStacks()) {
            if (stack.getHeightOfFaceDownCards() == 0) {

                // ... by moving a card to another build stack...
                Card topFaceUpCard = stack.getTopFaceUpCard();

                move = findCardToBuildStackMove(topFaceUpCard);

                if (move != null) {
                    return move;
                }

                // ... or a suit stack.
                if (stack.getFaceUpCards().length == 1) {
                    move = findCardToSuitStackMove(topFaceUpCard);

                    if (move != null) {
                        return move;
                    }
                }
            }
        }

        return null;
    }

    protected Move findPotentialFaceDownCardFreeingMove() {
        Move move;

        for (BuildStack stack : gameSnapshot.getBuildStacks()) {
            if (stack.getHeightOfFaceDownCards() != 0) {
                Card topCard = stack.getTopFaceUpCard();

                move = findCardToBuildStackMove(topCard);

                if (move != null) {
                    return move;
                }

                if (stack.getFaceUpCards().length == 1) {
                    move = findCardToSuitStackMove(topCard);

                    if (move != null) {
                        return move;
                    }
                }
            }
        }

        return null;
    }

    protected Move setupPotentialFaceDownCardFreeingMove() {
        Move move;

        // Check if top face up card of suit stack can be moved next turn using card from draw pile or suit stack.
        for (BuildStack stack : gameSnapshot.getBuildStacks()) {
            if (stack.getHeightOfFaceDownCards() != 0) {
                Card topCard = stack.getTopFaceUpCard();
                Card cardFromDrawPile = gameSnapshot.getCardFromDrawPile();

                if (sequenceChecker.isAlternatingColorSequence(cardFromDrawPile, topCard)) {
                    move = findCardToBuildStackMove(cardFromDrawPile);

                    if (move != null) {
                        return move;
                    }
                }

                if (gameSnapshot.getTopCardsOfSuitStacks() != null) {
                    for (Card cardFromSuitStack : gameSnapshot.getTopCardsOfSuitStacks()) {
                        if (sequenceChecker.isAlternatingColorSequence(cardFromSuitStack, topCard)) {
                            move = findCardToBuildStackMove(cardFromSuitStack);

                            if (move != null) {
                                return move;
                            }
                        }
                    }
                }
            }
        }

        // Check if card from draw pile and a card from single face-up card build stack can be moved to a suit stack.
        for (BuildStack stack : gameSnapshot.getBuildStacks()) {
            if (stack.getHeightOfFaceDownCards() != 0 && stack.getFaceUpCards().length == 1) {
                if (sequenceChecker.isSameSuitSequence(stack.getBottomFaceUpCard(), gameSnapshot.getCardFromDrawPile())) {
                    move = findCardToSuitStackMove(gameSnapshot.getCardFromDrawPile());

                    if (move != null) {
                        return move;
                    }
                }
            }
        }

        // Check if a sequence of exactly two cards can be moved to relevant suit stacks.
        for (BuildStack stack : gameSnapshot.getBuildStacks()) {
            if (stack.getHeightOfFaceDownCards() != 0 && stack.getFaceUpCards().length == 2) {
                move = findCardToSuitStackMove(stack.getTopFaceUpCard());

                if (move != null) {
                    move = findCardToSuitStackMove(stack.getBottomFaceUpCard());

                    if (move != null) {
                        return move;
                    }
                }
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return "DefaultStrategy";
    }
}
