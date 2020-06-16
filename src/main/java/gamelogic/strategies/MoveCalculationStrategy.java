package gamelogic.strategies;

import model.GameSnapshot;
import model.Move;

/**
 * SKRIV NOGET HER
 *
 * @author Malte Brink Kristensen
 */
public interface MoveCalculationStrategy {

    Move execute(GameSnapshot gameSnapshot);
}
