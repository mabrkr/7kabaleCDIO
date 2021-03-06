package model;

import model.Card;
import org.apache.commons.lang3.ArrayUtils;

import java.sql.SQLOutput;
import java.util.*;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

/**
 * Provides a GameSnapshot from a list of cards and their positions.
 *
 * @author Neal Patrick Norman
 */

public final class GameSnapshotFactory {

    // public GameSnapshot(boolean isDrawPileEmpty, Card cardFromDrawPile, Card[][] buildStacks,
    //                     Card[] topCardsOfSuitStacks, double[] heightsOfFaceDownSequences)

    /**
     * Static method to generate new GameSnapshot object from the relative positions of the cards passed to it.
      * @param positionCards - complete list of all cards
     * @return GameSnapshot
     */
    public static GameSnapshot fromPositionCards(List<Card> positionCards) {

        int Yseperator = getYSeparatorCoordinate(positionCards);
//        System.out.println(Yseperator);
        //int Yseperator = 600;
        List<Card> cardsBelowY = getCardsBelowY(Yseperator, positionCards);
        List<Card> cardsAboveY = getCardsAboveY(Yseperator, positionCards);
//        System.out.println("cardsAboveY");
//        System.out.println(cardsAboveY);
//        System.out.println("cardsBelowY");
//        System.out.println(cardsBelowY);
//        System.out.println("----------");
        Card cardFromDrawPile = guessDrawpileCard(cardsAboveY);
        List<Card> topCardsOfSuitStacks = new ArrayList<>();
        cardsAboveY.sort(comparatorX());
        if (cardFromDrawPile != null) {
            for (int i = 1; i < cardsAboveY.size(); i++) {
                topCardsOfSuitStacks.add(cardsAboveY.get(i));
            }
        } else {
            topCardsOfSuitStacks = cardsAboveY;
        }
//        System.out.println("Cards in Suit Stacks");
//        System.out.println(topCardsOfSuitStacks);

        List<List<Card>> buildStacks = getColumns(cardsBelowY);
//        System.out.println("Build Stacks");
        List<Double> heightsOfFaceDownSequences = new ArrayList<>();
        for (List<Card> stack : buildStacks) {
//            System.out.println(stack);
            double height = getUnturnedCardsColumnLength(Yseperator, stack);
            heightsOfFaceDownSequences.add(height);
        }

        // Conversion
        //is DrawPileEmpty? I don't know what this card looks like.
        boolean _isDrawPileEmpty = false;
        // DrawpileCard
        Card _cardFromDrawPile = cardFromDrawPile;

        Card[][] _buildStacks = buildStacks.stream().map(u -> u.toArray(new Card[0])).toArray(Card[][]::new);
        double[] _heightsOfFaceDownSequences = ArrayUtils.toPrimitive(heightsOfFaceDownSequences.toArray(new Double[0]));

        //drop the tomme arrays.
        Card[] _topCardsOfSuitStacks = topCardsOfSuitStacks.stream().toArray(n -> new Card[n]);

//        //is DrawPileEmpty? I don't know what this card looks like.
//        boolean _isDrawPileEmpty = false;
//        // DrawpileCard
//        Card _cardFromDrawPile = cardFromDrawPile;
//        //Build stack and heights
//        int index = 0;
//        Card[][] _buildStacks = new Card[7][];
//        double[] _heightsOfFaceDownSequences = new double[7];
//        for (List<Card> column : buildStacks) {
//            int size = buildStacks.get(0).size();
//            Card[] _column = new Card[size];
//            int columnIndex = 0;
//            for (Card card : column) {
//                _column[columnIndex] = card;
//                columnIndex++;
//            }
//            _buildStacks[index] = _column;
//            _heightsOfFaceDownSequences[index] = (double) heightsOfFaceDownSequences.get(index);
//            index++;
//        }
//        //drop the tomme arrays.
//        int suitStackCount = topCardsOfSuitStacks.size();
//        Card[] _topCardsOfSuitStacks = new Card[suitStackCount];
//        int suitIndex = 0;
//        for (Card positionCard : topCardsOfSuitStacks) {
//            _topCardsOfSuitStacks[suitIndex] = positionCard;
//        }

        //TODO: Tjek om følgende løkke er unødvendig
        for (Card[] stack : _buildStacks) {
            ArrayUtils.reverse(stack);
        }


        GameSnapshot currentGamestate = new GameSnapshot(_isDrawPileEmpty, _cardFromDrawPile, _buildStacks, _topCardsOfSuitStacks,
                _heightsOfFaceDownSequences);
        return currentGamestate;
        //throw new UnsupportedOperationException();

    }

    /**
     * Get the top-left card in a list of PositionCards.
     * If the topmost card isn't the same as the leftmost card, pick the topmost of the two.
     * <p>
     * @param positionCards - complete list of all cards
     * @return the top-left card (with top as tie-breaker)
     */
    public static Card topLeftCard(List<Card> positionCards) {
        List<Card> sorted =
                positionCards
                        .stream()
                        .sorted(comparatorX())
                        .sorted(comparatorY())
                        .collect(Collectors.toList());
//        List<Card> sorted =
//                positionCards
//                        .stream()
//                        .sorted(comparatorY())
//                        .collect(Collectors.toList());
//        System.out.println("topLeftCard");
//        System.out.println(sorted.get(0));
        return sorted.get(0);
    }

    /**
     *
     * @param f
     * @return
     */
    private static Comparator<Card> comparatorBy(ToIntFunction<Card> f) {
        return Comparator.comparingInt(f);
    }

    /**
     *
     * @return
     */
    private static Comparator<Card> comparatorX() {
        ToIntFunction<Card> fx = (positionCard) -> positionCard.x;
        return comparatorBy(fx);
    }

    /**
     *
     * @return
     */
    private static Comparator<Card> comparatorY() {
        ToIntFunction<Card> fy = (positionCard) -> positionCard.y;
        return comparatorBy(fy);
    }

    /**
     * Uses the topLeftCard function to determine the dividing line between the buildstacks
     * and the draw pile and suitstacks.
     * @param positionCards - complete list of all cards
     * @return Y coordinate separator
     */
    public static int getYSeparatorCoordinate(List<Card> positionCards) {
        Card topCard = topLeftCard(positionCards);
        int bottomOftopCardY = topCard.y + topCard.rectangle.height;
        List<Card> cardsBelowY = getCardsBelowY(bottomOftopCardY, positionCards);
        Card topBelowtopLeft = topLeftCard(cardsBelowY);
        int separatorCoordinateY = ((bottomOftopCardY - topBelowtopLeft.y) / 2) + bottomOftopCardY;
        return separatorCoordinateY;
    }


    /**
     * Uses the separatorCoordinateY to return a list of cards below this point, which will be the buildstacks
     *
     * @param separatorCoordinateY - coordinate generated by the getYSeparatorCoordinate method
     * @param positionCards - complete list of all cards
     * @return the unsorted buildstacks
     */
    public static List<Card> getCardsBelowY(int separatorCoordinateY, List<Card> positionCards) {
        //insert check if no cards below, or above.
        List<Card> cardsBelowY = new ArrayList<>();
        for (Card card : positionCards) {
            if (card.y > separatorCoordinateY) {
                cardsBelowY.add(card);
            }
        }
        return cardsBelowY;
    }

    /**
     * Uses the separatorCoordinateY to return a list of cards above this point,
     * which will be the drawpile and suitstacks
     *
     * @param YCoordinate - coordinate generated by the getYSeparatorCoordinate method
     * @param positionCards - complete list of all cards
     * @return the unsorted drawpile and suitstacks
     */
    public static List<Card> getCardsAboveY(int YCoordinate, List<Card> positionCards) {
        //insert check if no cards below, or above.
        List<Card> cardsAboveY = new ArrayList<>();
        for (Card card : positionCards) {
            if (card.y < YCoordinate) {
                cardsAboveY.add(card);
            }
        }
        return cardsAboveY;
    }



    /**
     * Sorts the passed list of positionCards into columns based on whether the overlap on the x axis.
     * Is used to sort the "cardsbelowY" into columns which are the buildstacks
     * @param positionCards - list of cards from cardsbelowY method
     * @return columns of buildstacks
     */
    public static List<List<Card>> getColumns(List<Card> positionCards) {
        List<List<Card>> columnLists = new ArrayList<>();
        List<Card> sorted =
                positionCards
                        .stream()
                        .sorted(comparatorX())
                        .collect(Collectors.toList());

//        System.out.println("sorted columns");
//        System.out.println(sorted);
        List<Card> column = new ArrayList<>();
        column.add(sorted.get(0));
        for (int index = 0; index < sorted.size() - 1; index++) {
//            System.out.print("Index ");
//            System.out.println(index);
//            System.out.print("Card at Index: ");
//            System.out.println(sorted.get(index));
            Card card = sorted.get(index);
            Card nextCard = sorted.get(index + 1);

            if (cardsAreOverlappingX(card, nextCard)) {
//                System.out.print("Adding Card: ");
//                System.out.println(nextCard);
//                System.out.print("Column: ");
//                System.out.println(columnLists.size());
                column.add(nextCard);
            } else {
                column.sort(comparatorY());
                columnLists.add(column);

                column = new ArrayList<>();
                column.add(nextCard);
            }
            if (index == sorted.size() - 2) {

                column.sort(comparatorY());
                columnLists.add(column);
            }
        }
//        System.out.println(columnLists);


//        for (int index = 0; index < sorted.size()-1; index++) {
//            System.out.print("Index ");
//            System.out.println(index);
//            System.out.print("Card at Index: ");
//            System.out.println(sorted.get(index));
//            if (index == 0) {
//                System.out.println("Adding Card: 0");
//                column.add(sorted.get(index));
//            }
//
//            Card card = sorted.get(index);
//            Card nextCard = sorted.get(index + 1);
//            if (cardsAreOverlappingX(card, nextCard)) {
//                    System.out.print("Adding Card: ");
//                    System.out.println(nextCard);
//                    column.add(nextCard);
//            } else {
//                    columnLists.add(column.stream()
//                            .sorted(comparatorY())
//                            .collect(Collectors.toList()));
//                    column.add(card);
//                    column = new ArrayList<>();
//
//                    if (index == sorted.size() - 2) {
//                        System.out.print("Adding Card: ");
//                        System.out.println(nextCard);
//                        column.add(nextCard);
//                        columnLists.add(column.stream()
//                                .sorted(comparatorY())
//                                .collect(Collectors.toList()));
//                    }
//                }
//            }

        return columnLists;
    }



//    public static List<List<Card>> getColumns_deprecated(List<Card> cards) {
//        Card topCard = cards.stream().max(comparatorX()).get();
//        List<Card> above = new ArrayList<>();
//        List<Card> below = new ArrayList<>();
//
//        for (Card card : cards) {
//            (isCompletelyAbove(topCard, card) ? below : above).add(card);
//        }
//
//        return List.of(above, below);
//    }

    /**
     * Unused function - could be used more completely map the cards to the board if needed in future.
     *
     * @return
     */
    public static List<List<Card>> getRows(List<Card> cards) {
        Card topCard = cards.stream().max(comparatorY()).get();
        List<Card> above = new ArrayList<>();
        List<Card> below = new ArrayList<>();

        for (Card card : cards) {
            (isCompletelyAbove(topCard, card) ? below : above).add(card);
        }

        return List.of(above, below);
    }

    /* +--+ <- above.getPosition().getY()
     * |  |
     * |  | +--+ <- below.getPosition().getY()
     * +--+ |  | <- above.getPosition().getY() - above.getOffset().getY()
     *      |  |
     *      +--+
     */

    /**
     * Unused function - could be used more completely map the cards to the board if needed in future.
     * @param above
     * @param below
     * @return
     */
    public static boolean isCompletelyAbove(Card above, Card below) {
        return above.y - above.rectangle.height >= below.y;
    }

    /**
     * Checks if 2 cards overlap on the X axis.
     * @param cardA - single card
     * @param cardB - single card
     * @return boolean value corresponding to whether cards are overlapping
     */
    public static boolean cardsAreOverlappingX(Card cardA, Card cardB) {
        int xA0 = cardA.x;
        int xA1 = cardA.x + cardA.rectangle.width;
        int xB0 = cardB.x;
        int xB1 = cardB.x + cardB.rectangle.width;
        if ((xB0 >= xA0 && xB0 <= xA1) || (xB1 >= xA0 && xB1 <= xA1)) {
            return true;
        }
        return false;
    }


//    public static boolean areCardsTouching(PositionCard cardA, PositionCard cardB){
//        //Get all intervals.
//        int xA0 = cardA.position.getX();
//        int xA1 = cardA.position.getX() + cardA.offset.getX();
//        int yA0 = cardA.position.getY();
//        int yA1 = cardA.position.getY() + cardA.offset.getY();
//
//        int xB0 = cardB.position.getX();
//        int xB1 = cardB.position.getX() + cardB.offset.getX();
//        int yB0 = cardB.position.getY();
//        int yB1 = cardB.position.getY() + cardB.offset.getY();
//
//        if((xB0 >= xA0 && xB0 <= xA1) || (xB1 >= xA0 && xB1 <= xA1)){
//            if((yB0 >= yA0 && yB0 <= yA1) || (yB1 >= yA0 && yB1 <= yA1)){
//                return true;
//            }
//        }
//        return true;
//    }

    /**
     * Unused function - could be used more completely map the cards to the board if needed in future.
     * @param columns - list of list of columns from card buildstacks
     * @return
     */
    public static int countColumns(List<List<Card>> columns) {
        return columns.size();
    }

    /**
     * Guesses the position of the Drawpile
     * @param positionCards - list of cards from cardsAboveY method
     * @return Drawpile Card
     */
    public static Card guessDrawpileCard(List<Card> positionCards) {
        List<Card> sorted =
                positionCards
                        .stream()
                        .sorted(comparatorX())
                        .collect(Collectors.toList());
        if (sorted.size() == 4) {
            int card0farX = sorted.get(0).x + sorted.get(0).rectangle.width;
            int card1nearX = sorted.get(1).x;
            int width = sorted.get(0).rectangle.width;
            if (card0farX - card1nearX > width) {
                return null;
            }
        }
        return sorted.get(0);
    }

    /*****
     *      Possibilities
     *
     *      [] [Qs]       [Ah] [] [Ac] []
     *      []  []   []   []  []  []  []
     *
     *      [] [Qs]       [Ah] [] [] [As]
     *      []  []   []   []  []  []  []
     *
     *      [] [Qs]       [Ah] [] [Ac] []
     *      []  []   []   []  []  []  []
     *
     *      []  []       [Ah] [] [Ac] [As]
     *      []  []   []   []  []  []  []
     *
     *      []  []       [Ah] [] [] []
     *      []  []   []   []  []  []  []
     *
     *      []  []        [] [] [] [As]
     *      []  []   []   []  []  []  []
     *
     *
     *      easiest solution
     *      always topleft, unless there are 4 rows, and distance between rows is less than a card.
     *
     *
     */

    /**
     * Provides the distance of the unturned cards in a column of cards from the Yseparator to first faceup card
     * @param Yline - coordinate generated by the getYSeparatorCoordinate method
     * @param positionCards - list of cards from cardsbelowY method
     * @return distance
     */
    public static double getUnturnedCardsColumnLength(int Yline, List<Card> positionCards) {
//        List<Card> sorted = positionCards
//                .stream()
//                .sorted(comparatorY())
//                .collect(Collectors.toList());
        positionCards.sort(comparatorY());
        int distance = positionCards.get(0).y - Yline;
        if (distance < 120){
            distance = 0;
        }
        return distance;
    }

}
