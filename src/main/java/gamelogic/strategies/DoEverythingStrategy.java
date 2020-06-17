package gamelogic.strategies;

import model.BuildStack;
import model.GameSnapshot;
import model.Move;

/**
 * Strategy for finding the best possible move for a given game state of a game of Klondike. This strategy should be
 * used after the default strategy no longer leads to anything but drawing cards.
 *
 * The rules are described in detail here:
 * https://dtudk.sharepoint.com/:b:/r/sites/F20CDIOGruppe14/Shared%20Documents/CDIO_Version2_7Kabale/Strategies.pdf
 *
 * @author Malte Brink Kristensen
 */
public class DoEverythingStrategy extends DefaultStrategy implements MoveCalculationStrategy {

    @Override
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

        // Any move that makes sense
        bestPossibleMove = findAnyPotentialMove();
        if (bestPossibleMove != null) {
            return bestPossibleMove;
        }

        // Rule 6
        return new Move(Move.MoveType.DRAW);
    }

    protected Move findAnyPotentialMove() {
        Move move;

        move = findCardToBuildStackMove(gameSnapshot.getCardFromDrawPile());
        if (move != null) {
            return move;
        }

        move = findCardToSuitStackMove(gameSnapshot.getCardFromDrawPile());
        if (move != null) {
            return move;
        }

        for (BuildStack stack : gameSnapshot.getBuildStacks()) {
            move = findCardToSuitStackMove(stack.getBottomFaceUpCard());

            if (move != null) {
                return move;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return "DoEverythingStrategy";
    }
}
