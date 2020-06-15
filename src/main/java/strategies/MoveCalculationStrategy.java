package strategies;

import model.GameSnapshot;
import model.Move;

public interface MoveCalculationStrategy {

    Move execute(GameSnapshot gameSnapshot);
}
