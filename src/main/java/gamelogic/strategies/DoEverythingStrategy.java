package gamelogic.strategies;

import model.BuildStack;
import model.GameSnapshot;
import model.Move;

/**
 * SKRIV NOGET HER
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
