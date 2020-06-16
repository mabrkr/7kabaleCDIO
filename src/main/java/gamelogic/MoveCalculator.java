package gamelogic;

import gamelogic.strategies.DefaultStrategy;
import gamelogic.strategies.MoveCalculationStrategy;
import model.GameSnapshot;
import model.Move;

/**
 * This class serves as the Context in the Strategy design pattern.
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
}
