import model.Card;
import model.GameSnapshot;
import model.Move;
import model.MoveCalculator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class DefaultStrategyTest {

    private static MoveCalculator moveCalculator;

    @BeforeAll
    static void setUp() {
        moveCalculator = new MoveCalculator();
    }

    @Test
    void drawPileIsEmpty_ShouldReturnDrawMove() {
        Card[] firstBuildStack = {
                new Card(9, Card.Suit.SPADE)
        };

        Card[] secondBuildStack = {
                new Card(7, Card.Suit.SPADE)
        };

        Card[] thirdBuildStack = {
                new Card(4, Card.Suit.CLUB)
        };

        Card[] fourthBuildStack = {
                new Card(12, Card.Suit.SPADE)
        };

        Card[] fifthBuildStack = {
                new Card(12, Card.Suit.CLUB)
        };

        Card[] sixthBuildStack = {
                new Card(11, Card.Suit.SPADE)
        };

        Card[] seventhBuildStack = {
                new Card(9, Card.Suit.DIAMOND)
        };

        Card[][] buildStacks = {
                firstBuildStack,
                secondBuildStack,
                thirdBuildStack,
                fourthBuildStack,
                fifthBuildStack,
                sixthBuildStack,
                seventhBuildStack
        };

        Card[] suitStacks = null;

        double[] faceDownHeights = {0, 1, 2, 3, 4, 5, 6};

        GameSnapshot gameSnapshot = new GameSnapshot(false, null, buildStacks, suitStacks, faceDownHeights);

        Move move = new Move(Move.MoveType.DRAW);

        assertEquals(move.toString(), moveCalculator.calculateBestPossibleMove(gameSnapshot).toString());
    }

    @Test
    void aceIsCardFromDrawPile_ShouldReturnSuitStackMove() {
        Card[] firstBuildStack = {
                new Card(9, Card.Suit.SPADE)
        };

        Card[] secondBuildStack = {
                new Card(7, Card.Suit.SPADE)
        };

        Card[] thirdBuildStack = {
                new Card(4, Card.Suit.CLUB)
        };

        Card[] fourthBuildStack = {
                new Card(12, Card.Suit.SPADE)
        };

        Card[] fifthBuildStack = {
                new Card(12, Card.Suit.CLUB)
        };

        Card[] sixthBuildStack = {
                new Card(11, Card.Suit.SPADE)
        };

        Card[] seventhBuildStack = {
                new Card(9, Card.Suit.DIAMOND)
        };

        Card[][] buildStacks = {
                firstBuildStack,
                secondBuildStack,
                thirdBuildStack,
                fourthBuildStack,
                fifthBuildStack,
                sixthBuildStack,
                seventhBuildStack
        };

        Card[] suitStacks = null;

        double[] faceDownHeights = {0, 1, 2, 3, 4, 5, 6};

        Card cardFromDrawPile = new Card(1, Card.Suit.CLUB);

        GameSnapshot gameSnapshot = new GameSnapshot(false, cardFromDrawPile, buildStacks, suitStacks, faceDownHeights);

        Move move = new Move(Move.MoveType.SUIT_STACK_MOVE);
        move.setCard(new Card(1, Card.Suit.CLUB));

        assertEquals(move.toString(), moveCalculator.calculateBestPossibleMove(gameSnapshot).toString());
    }

    @Test
    void aceIsBottomCardOfBuildStack_ShouldReturnSuitStackMove() {
        Card[] firstBuildStack = {
                new Card(1, Card.Suit.CLUB)
        };

        Card[] secondBuildStack = {
                new Card(7, Card.Suit.SPADE)
        };

        Card[] thirdBuildStack = {
                new Card(4, Card.Suit.CLUB)
        };

        Card[] fourthBuildStack = {
                new Card(12, Card.Suit.SPADE)
        };

        Card[] fifthBuildStack = {
                new Card(12, Card.Suit.CLUB)
        };

        Card[] sixthBuildStack = {
                new Card(11, Card.Suit.SPADE)
        };

        Card[] seventhBuildStack = {
                new Card(9, Card.Suit.DIAMOND)
        };

        Card[][] buildStacks = {
                firstBuildStack,
                secondBuildStack,
                thirdBuildStack,
                fourthBuildStack,
                fifthBuildStack,
                sixthBuildStack,
                seventhBuildStack
        };

        Card[] suitStacks = null;

        double[] faceDownHeights = {0, 1, 2, 3, 4, 5, 6};

        Card cardFromDrawPile = new Card(10, Card.Suit.CLUB);

        GameSnapshot gameSnapshot = new GameSnapshot(false, cardFromDrawPile, buildStacks, suitStacks, faceDownHeights);

        Move move = new Move(Move.MoveType.SUIT_STACK_MOVE);
        move.setCard(new Card(1, Card.Suit.CLUB));

        assertEquals(move.toString(), moveCalculator.calculateBestPossibleMove(gameSnapshot).toString());
    }

    @Test
    void twoIsBottomCardOfBuildStackAndAceInSuitStack_ShouldReturnSuitStackMove() {
        Card[] firstBuildStack = {
                new Card(2, Card.Suit.CLUB)
        };

        Card[] secondBuildStack = {
                new Card(7, Card.Suit.SPADE)
        };

        Card[] thirdBuildStack = {
                new Card(4, Card.Suit.CLUB)
        };

        Card[] fourthBuildStack = {
                new Card(12, Card.Suit.SPADE)
        };

        Card[] fifthBuildStack = {
                new Card(12, Card.Suit.CLUB)
        };

        Card[] sixthBuildStack = {
                new Card(11, Card.Suit.SPADE)
        };

        Card[] seventhBuildStack = {
                new Card(9, Card.Suit.DIAMOND)
        };

        Card[][] buildStacks = {
                firstBuildStack,
                secondBuildStack,
                thirdBuildStack,
                fourthBuildStack,
                fifthBuildStack,
                sixthBuildStack,
                seventhBuildStack
        };

        Card[] suitStacks = {
                new Card(1, Card.Suit.CLUB)
        };

        double[] faceDownHeights = {0, 1, 2, 3, 4, 5, 6};

        Card cardFromDrawPile = new Card(10, Card.Suit.CLUB);

        GameSnapshot gameSnapshot = new GameSnapshot(false, cardFromDrawPile, buildStacks, suitStacks, faceDownHeights);

        Move move = new Move(Move.MoveType.SUIT_STACK_MOVE);
        move.setCard(new Card(2, Card.Suit.CLUB));

        assertEquals(move.toString(), moveCalculator.calculateBestPossibleMove(gameSnapshot).toString());
    }

    @Test
    void twoIsBottomCardOfBuildStackButAceNotInSuitStack_ShouldNotReturnSuitStackMove() {
        Card[] firstBuildStack = {
                new Card(2, Card.Suit.CLUB)
        };

        Card[] secondBuildStack = {
                new Card(7, Card.Suit.SPADE)
        };

        Card[] thirdBuildStack = {
                new Card(4, Card.Suit.CLUB)
        };

        Card[] fourthBuildStack = {
                new Card(12, Card.Suit.SPADE)
        };

        Card[] fifthBuildStack = {
                new Card(12, Card.Suit.CLUB)
        };

        Card[] sixthBuildStack = {
                new Card(11, Card.Suit.SPADE)
        };

        Card[] seventhBuildStack = {
                new Card(9, Card.Suit.DIAMOND)
        };

        Card[][] buildStacks = {
                firstBuildStack,
                secondBuildStack,
                thirdBuildStack,
                fourthBuildStack,
                fifthBuildStack,
                sixthBuildStack,
                seventhBuildStack
        };

        Card[] suitStacks = null;

        double[] faceDownHeights = {0, 1, 2, 3, 4, 5, 6};

        Card cardFromDrawPile = new Card(10, Card.Suit.CLUB);

        GameSnapshot gameSnapshot = new GameSnapshot(false, cardFromDrawPile, buildStacks, suitStacks, faceDownHeights);

        Move move = new Move(Move.MoveType.SUIT_STACK_MOVE);
        move.setCard(new Card(2, Card.Suit.CLUB));

        assertNotEquals(move.toString(), moveCalculator.calculateBestPossibleMove(gameSnapshot).toString());
    }

    @Test
    void kingIsMoveableAndEmptyBuildStackExists_ShouldReturnKingMove() {
        Card[] firstBuildStack = {
                new Card(11, Card.Suit.SPADE)
        };

        Card[] secondBuildStack = {
                new Card(7, Card.Suit.SPADE)
        };

        Card[] thirdBuildStack = {
                new Card(4, Card.Suit.CLUB)
        };

        Card[] fourthBuildStack = {
                new Card(12, Card.Suit.SPADE)
        };

        Card[] fifthBuildStack = {
                new Card(12, Card.Suit.CLUB)
        };

        Card[] sixthBuildStack = {
                new Card(13, Card.Suit.CLUB)
        };

        Card[][] buildStacks = {
                firstBuildStack,
                secondBuildStack,
                thirdBuildStack,
                fourthBuildStack,
                fifthBuildStack,
                sixthBuildStack,
        };

        Card[] suitStacks = null;

        double[] faceDownHeights = {0, 1, 2, 3, 4, 5};

        Card cardFromDrawPile = new Card(10, Card.Suit.CLUB);

        GameSnapshot gameSnapshot = new GameSnapshot(false, cardFromDrawPile, buildStacks, suitStacks, faceDownHeights);

        Move move = new Move(Move.MoveType.KING_MOVE);
        move.setCard(new Card(13, Card.Suit.CLUB));

        assertEquals(move.toString(), moveCalculator.calculateBestPossibleMove(gameSnapshot).toString());
    }

    @Test
    void kingIsMoveableAndEmptyBuildStackCreationCanBeSetUp_ShouldReturnMove() {
        Card[] firstBuildStack = {
                new Card(10, Card.Suit.CLUB)
        };

        Card[] secondBuildStack = {
                new Card(6, Card.Suit.DIAMOND)
        };

        Card[] thirdBuildStack = {
                new Card(4, Card.Suit.DIAMOND)
        };

        Card[] fourthBuildStack = {
                new Card(8, Card.Suit.SPADE),
                new Card(9, Card.Suit.HEART)
        };

        Card[] fifthBuildStack = {
                new Card(12, Card.Suit.HEART)
        };

        Card[] sixthBuildStack = {
                new Card(7, Card.Suit.CLUB),
                new Card(8, Card.Suit.HEART)
        };

        Card[] seventhBuildStack = {
                new Card(13, Card.Suit.DIAMOND)
        };

        Card[][] buildStacks = {
                firstBuildStack,
                secondBuildStack,
                thirdBuildStack,
                fourthBuildStack,
                fifthBuildStack,
                sixthBuildStack,
                seventhBuildStack
        };

        Card[] suitStacks = {
                new Card(1, Card.Suit.SPADE),
                new Card(1, Card.Suit.DIAMOND)
        };

        double[] faceDownHeights = {0, 0, 1, 3, 4, 5, 4};

        Card cardFromDrawPile = new Card(12, Card.Suit.CLUB);

        GameSnapshot gameSnapshot = new GameSnapshot(false, cardFromDrawPile, buildStacks, suitStacks, faceDownHeights);

        Move move = new Move(Move.MoveType.MOVE);
        move.setCard(new Card(6, Card.Suit.DIAMOND));
        move.setTarget(new Card(7, Card.Suit.CLUB));

        assertEquals(move.toString(), moveCalculator.calculateBestPossibleMove(gameSnapshot).toString());
    }

    @Test
    void faceDownCardCanBeFreed_ShouldReturnMove() {
        Card[] firstBuildStack = {
                new Card(11, Card.Suit.CLUB)
        };

        Card[] secondBuildStack = {
                new Card(13, Card.Suit.DIAMOND)
        };

        Card[] thirdBuildStack = {
                new Card(4, Card.Suit.DIAMOND)
        };

        Card[] fourthBuildStack = {
                new Card(8, Card.Suit.SPADE),
                new Card(9, Card.Suit.HEART)
        };

        Card[] fifthBuildStack = {
                new Card(12, Card.Suit.HEART)
        };

        Card[] sixthBuildStack = {
                new Card(5, Card.Suit.CLUB),
                new Card(6, Card.Suit.DIAMOND),
                new Card(7, Card.Suit.CLUB),
                new Card(8, Card.Suit.HEART)
        };

        Card[] seventhBuildStack = {
                new Card(4, Card.Suit.CLUB)
        };

        Card[][] buildStacks = {
                firstBuildStack,
                secondBuildStack,
                thirdBuildStack,
                fourthBuildStack,
                fifthBuildStack,
                sixthBuildStack,
                seventhBuildStack
        };

        Card[] suitStacks = {
                new Card(2, Card.Suit.SPADE),
                new Card(1, Card.Suit.DIAMOND),
                new Card(1, Card.Suit.CLUB),
                new Card(1, Card.Suit.HEART)
        };

        double[] faceDownHeights = {0, 0, 1, 3, 4, 5, 3};

        Card cardFromDrawPile = new Card(12, Card.Suit.CLUB);

        GameSnapshot gameSnapshot = new GameSnapshot(false, cardFromDrawPile, buildStacks, suitStacks, faceDownHeights);

        Move move = new Move(Move.MoveType.MOVE);
        move.setCard(new Card(4, Card.Suit.DIAMOND));
        move.setTarget(new Card(5, Card.Suit.CLUB));

        assertEquals(move.toString(), moveCalculator.calculateBestPossibleMove(gameSnapshot).toString());
    }

    @Test
    void twoFaceDownCardsCanBeFreed_ShouldReturnMoveFromBiggestStack() {
        Card[] firstBuildStack = {
                new Card(10, Card.Suit.CLUB)
        };

        Card[] secondBuildStack = {
                new Card(13, Card.Suit.DIAMOND)
        };

        Card[] thirdBuildStack = {
                new Card(4, Card.Suit.DIAMOND)
        };

        Card[] fourthBuildStack = {
                new Card(8, Card.Suit.SPADE),
                new Card(9, Card.Suit.HEART)
        };

        Card[] fifthBuildStack = {
                new Card(12, Card.Suit.HEART)
        };

        Card[] sixthBuildStack = {
                new Card(5, Card.Suit.CLUB),
                new Card(6, Card.Suit.DIAMOND),
                new Card(7, Card.Suit.CLUB),
                new Card(8, Card.Suit.HEART)
        };

        Card[] seventhBuildStack = {
                new Card(4, Card.Suit.CLUB)
        };

        Card[][] buildStacks = {
                firstBuildStack,
                secondBuildStack,
                thirdBuildStack,
                fourthBuildStack,
                fifthBuildStack,
                sixthBuildStack,
                seventhBuildStack
        };

        Card[] suitStacks = {
                new Card(2, Card.Suit.SPADE),
                new Card(1, Card.Suit.DIAMOND),
                new Card(1, Card.Suit.CLUB),
                new Card(1, Card.Suit.HEART)
        };

        double[] faceDownHeights = {0, 0, 1, 3, 4, 5, 3};

        Card cardFromDrawPile = new Card(12, Card.Suit.CLUB);

        GameSnapshot gameSnapshot = new GameSnapshot(false, cardFromDrawPile, buildStacks, suitStacks, faceDownHeights);

        Move move = new Move(Move.MoveType.MOVE);
        move.setCard(new Card(9, Card.Suit.HEART));
        move.setTarget(new Card(10, Card.Suit.CLUB));

        assertEquals(move.toString(), moveCalculator.calculateBestPossibleMove(gameSnapshot).toString());
    }

    @Test
    void cardFromDrawPileCanSetUpFreeingOfFaceDownCard_ShouldReturnMove() {
        Card[] firstBuildStack = {
                new Card(11, Card.Suit.HEART),
                new Card(12, Card.Suit.SPADE),
                new Card(13, Card.Suit.HEART)
        };

        Card[] secondBuildStack = {
                new Card(9, Card.Suit.SPADE),
                new Card(10, Card.Suit.HEART),
                new Card(11, Card.Suit.CLUB),
                new Card(12, Card.Suit.DIAMOND),
                new Card(13, Card.Suit.SPADE)
        };

        Card[] thirdBuildStack = {
                new Card(3, Card.Suit.HEART),
                new Card(4, Card.Suit.CLUB),
                new Card(5, Card.Suit.HEART),
                new Card(6, Card.Suit.CLUB),
                new Card(7, Card.Suit.HEART),
                new Card(8, Card.Suit.CLUB),
                new Card(9, Card.Suit.DIAMOND)
        };

        Card[] fourthBuildStack = {
                new Card(12, Card.Suit.HEART)
        };

        Card[] fifthBuildStack = {
                new Card(3, Card.Suit.CLUB),
                new Card(4, Card.Suit.HEART)

        };

        Card[] sixthBuildStack = {
                new Card(6, Card.Suit.DIAMOND),
                new Card(7, Card.Suit.CLUB)
        };

        Card[] seventhBuildStack = {
                new Card(7, Card.Suit.CLUB)
        };

        Card[][] buildStacks = {
                firstBuildStack,
                secondBuildStack,
                thirdBuildStack,
                fourthBuildStack,
                fifthBuildStack,
                sixthBuildStack,
                seventhBuildStack
        };

        Card[] suitStacks = {
                new Card(2, Card.Suit.SPADE),
                new Card(2, Card.Suit.DIAMOND),
                new Card(2, Card.Suit.CLUB),
                new Card(2, Card.Suit.HEART)
        };

        double[] faceDownHeights = {0, 0, 2, 1, 4, 1, 5};

        Card cardFromDrawPile = new Card(10, Card.Suit.SPADE);

        GameSnapshot gameSnapshot = new GameSnapshot(false, cardFromDrawPile, buildStacks, suitStacks, faceDownHeights);

        Move move = new Move(Move.MoveType.MOVE);
        move.setCard(new Card(10, Card.Suit.SPADE));
        move.setTarget(new Card(11, Card.Suit.HEART));

        assertEquals(move.toString(), moveCalculator.calculateBestPossibleMove(gameSnapshot).toString());
    }

    @Test
    void cardFromDrawPileCanSetUpFreeingOfFaceDownCard2_ShouldReturnSuitStackMove() {
        Card[] firstBuildStack = {
                new Card(3, Card.Suit.HEART),
                new Card(4, Card.Suit.CLUB),
                new Card(5, Card.Suit.HEART),
                new Card(6, Card.Suit.CLUB),
                new Card(7, Card.Suit.HEART),
                new Card(8, Card.Suit.CLUB),
                new Card(9, Card.Suit.DIAMOND),
                new Card(10, Card.Suit.SPADE),
                new Card(11, Card.Suit.HEART),
                new Card(12, Card.Suit.SPADE),
                new Card(13, Card.Suit.HEART)
        };

        Card[] secondBuildStack = {
                new Card(9, Card.Suit.SPADE),
                new Card(10, Card.Suit.HEART),
                new Card(11, Card.Suit.CLUB),
                new Card(12, Card.Suit.DIAMOND),
                new Card(13, Card.Suit.SPADE)
        };

        Card[] thirdBuildStack = {
                new Card(4, Card.Suit.SPADE)
        };

        Card[] fourthBuildStack = {
                new Card(12, Card.Suit.HEART)
        };

        Card[] fifthBuildStack = {
                new Card(3, Card.Suit.CLUB),
                new Card(4, Card.Suit.HEART)

        };

        Card[] sixthBuildStack = {
                new Card(6, Card.Suit.DIAMOND),
                new Card(7, Card.Suit.CLUB)
        };

        Card[] seventhBuildStack = {
                new Card(7, Card.Suit.CLUB)
        };

        Card[][] buildStacks = {
                firstBuildStack,
                secondBuildStack,
                thirdBuildStack,
                fourthBuildStack,
                fifthBuildStack,
                sixthBuildStack,
                seventhBuildStack
        };

        Card[] suitStacks = {
                new Card(2, Card.Suit.SPADE),
                new Card(2, Card.Suit.DIAMOND),
                new Card(2, Card.Suit.CLUB),
                new Card(2, Card.Suit.HEART)
        };

        double[] faceDownHeights = {0, 0, 1, 1, 4, 1, 5};

        Card cardFromDrawPile = new Card(3, Card.Suit.SPADE);

        GameSnapshot gameSnapshot = new GameSnapshot(false, cardFromDrawPile, buildStacks, suitStacks, faceDownHeights);

        Move move = new Move(Move.MoveType.SUIT_STACK_MOVE);
        move.setCard(new Card(3, Card.Suit.SPADE));

        assertEquals(move.toString(), moveCalculator.calculateBestPossibleMove(gameSnapshot).toString());
    }

    @Test
    void suitStackMoveCanSetUpFreeingOfFaceDownCard_ShouldReturnSuitStackMove() {
        Card[] firstBuildStack = {
                new Card(9, Card.Suit.HEART),
                new Card(10, Card.Suit.CLUB),
                new Card(11, Card.Suit.HEART),
                new Card(12, Card.Suit.SPADE),
                new Card(13, Card.Suit.HEART)
        };

        Card[] secondBuildStack = {
                new Card(3, Card.Suit.CLUB),
                new Card(4, Card.Suit.HEART),
                new Card(5, Card.Suit.CLUB),
                new Card(6, Card.Suit.HEART),
                new Card(7, Card.Suit.SPADE),
                new Card(8, Card.Suit.DIAMOND),
                new Card(9, Card.Suit.SPADE),
                new Card(10, Card.Suit.DIAMOND),
                new Card(11, Card.Suit.CLUB),
                new Card(12, Card.Suit.DIAMOND),
                new Card(13, Card.Suit.SPADE)
        };

        Card[] thirdBuildStack = {
                new Card(6, Card.Suit.SPADE),
                new Card(7, Card.Suit.HEART),
                new Card(8, Card.Suit.SPADE),
                new Card(9, Card.Suit.DIAMOND),
                new Card(10, Card.Suit.SPADE),
                new Card(11, Card.Suit.DIAMOND),
                new Card(12, Card.Suit.CLUB),
                new Card(13, Card.Suit.DIAMOND)
        };

        Card[] fourthBuildStack = {
                new Card(10, Card.Suit.HEART),
                new Card(11, Card.Suit.SPADE),
                new Card(12, Card.Suit.HEART),
                new Card(13, Card.Suit.CLUB)
        };

        Card[] fifthBuildStack = {
                new Card(3, Card.Suit.SPADE),
                new Card(4, Card.Suit.DIAMOND)

        };

        Card[] sixthBuildStack = {
                new Card(8, Card.Suit.HEART),
                new Card(9, Card.Suit.CLUB)
        };

        Card[] seventhBuildStack = {
                new Card(3, Card.Suit.HEART),
                new Card(4, Card.Suit.CLUB),
                new Card(5, Card.Suit.HEART),
                new Card(6, Card.Suit.CLUB),
                new Card(7, Card.Suit.DIAMOND)
        };

        Card[][] buildStacks = {
                firstBuildStack,
                secondBuildStack,
                thirdBuildStack,
                fourthBuildStack,
                fifthBuildStack,
                sixthBuildStack,
                seventhBuildStack
        };

        Card[] suitStacks = {
                new Card(2, Card.Suit.SPADE),
                new Card(3, Card.Suit.DIAMOND),
                new Card(2, Card.Suit.CLUB),
                new Card(2, Card.Suit.HEART)
        };

        double[] faceDownHeights = {0, 0, 0, 0, 2, 0, 2};

        Card cardFromDrawPile = new Card(3, Card.Suit.SPADE);

        GameSnapshot gameSnapshot = new GameSnapshot(false, cardFromDrawPile, buildStacks, suitStacks, faceDownHeights);

        Move move = new Move(Move.MoveType.SUIT_STACK_MOVE);
        move.setCard(new Card(3, Card.Suit.SPADE));

        assertEquals(move.toString(), moveCalculator.calculateBestPossibleMove(gameSnapshot).toString());
    }

}
