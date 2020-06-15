import model.BuildStack;
import model.Card;
import model.GameSnapshot;

public class MaltesMain {

    public static void main(String[] args) {
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

        printGameSnapshotToConsole(gameSnapshot);
    }

    // TODO: Flyt til et rart sted.
    public static void printGameSnapshotToConsole(GameSnapshot gameSnapshot) {

        // Draw pile and suit stacks.

        String drawPile = gameSnapshot.isDrawPileEmpty() ? "0" : "#";
        String cardFromDrawPile = gameSnapshot.getCardFromDrawPile() != null
                ? gameSnapshot.getCardFromDrawPile().toSimpleString() : "-";
        String suitStack1 = gameSnapshot.getTopCardsOfSuitStacks().length > 0
                ? gameSnapshot.getTopCardsOfSuitStacks()[0].toSimpleString() : "-";
        String suitStack2 = gameSnapshot.getTopCardsOfSuitStacks().length > 1
                ? gameSnapshot.getTopCardsOfSuitStacks()[1].toSimpleString() : "-";
        String suitStack3 = gameSnapshot.getTopCardsOfSuitStacks().length > 2
                ? gameSnapshot.getTopCardsOfSuitStacks()[2].toSimpleString() : "-";
        String suitStack4 = gameSnapshot.getTopCardsOfSuitStacks().length == 4
                ? gameSnapshot.getTopCardsOfSuitStacks()[3].toSimpleString() : "-";

        System.out.printf("%-1s %-10s %-3s %-3s %-3s %-3s\n",
                drawPile, cardFromDrawPile, suitStack1, suitStack2, suitStack3, suitStack4);


        // Build stacks.

        BuildStack[] buildStacks = gameSnapshot.getBuildStacks();

        int longestLength = 0;
        for (BuildStack stack : buildStacks) {
            int length = stack.getFaceUpCards().length;
            longestLength = length > longestLength ? length : longestLength;
        }

        for (int i = 0; i < longestLength; i++) {
            String buildStacksString = "";

            for (BuildStack stack : buildStacks) {
                if (i == 0) {
                    if (stack.getHeightOfFaceDownCards() != 0) {
                        buildStacksString += "#" + stack.getHeightOfFaceDownCards() + " ";
                    } else {
                        buildStacksString += stack.getFaceUpCards()[i].toSimpleString() + " ";
                    }
                } else if (stack.getFaceUpCards().length < i){
                    buildStacksString += stack.getFaceUpCards()[i].toSimpleString() + " ";
                }
            }

            System.out.println(buildStacksString);
        }
    }
}
