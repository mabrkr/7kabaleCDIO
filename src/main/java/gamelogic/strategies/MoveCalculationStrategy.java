package gamelogic.strategies;

import model.GameSnapshot;
import model.Move;

/**
 * @author Malte Brink Kristensen
 */
public interface MoveCalculationStrategy {

    Move execute(GameSnapshot gameSnapshot);
}
