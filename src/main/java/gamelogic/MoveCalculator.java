package gamelogic;

import gamelogic.strategies.DefaultStrategy;
import gamelogic.strategies.MoveCalculationStrategy;
import model.GameSnapshot;
import model.Move;

/**
 * Calculates the best possible move for a given game state of a game of Klondike. The calculation is based on the
 * chosen strategy.
 *
 * @author Malte Brink Kristensen
 */
public class MoveCalculator {

    private MoveCalculationStrategy strategy = new DefaultStrategy();

    public Move calculateBestPossibleMove(GameSnapshot gameSnapshot) {
        return strategy.execute(gameSnapshot);
    }

    public void setStrategy(MoveCalculationStrategy strategy) {
        this.strategy = strategy;
    }

    public MoveCalculationStrategy getStrategy() {
        return strategy;
    }
}
