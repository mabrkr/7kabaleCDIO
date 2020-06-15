import model.GameSnapshot;
import model.Move;
import strategies.DefaultStrategy;
import strategies.MoveCalculationStrategy;

// This class serves as the Context in the Strategy design pattern.
public class MoveCalculator {

    private MoveCalculationStrategy strategy = new DefaultStrategy();

    public Move calculateBestPossibleMove(GameSnapshot gameSnapshot) {
        return strategy.execute(gameSnapshot);
    }

    public void setStrategy(MoveCalculationStrategy strategy) {
        this.strategy = strategy;
    }
}