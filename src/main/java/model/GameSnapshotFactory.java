package model;

import model.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public final class GameSnapshotFactory {

    // public GameSnapshot(boolean isDrawPileEmpty, Card cardFromDrawPile, Card[][] buildStacks,
    //                     Card[] topCardsOfSuitStacks, double[] heightsOfFaceDownSequences)

    public static GameSnapshot fromPositionCards(List<Card> positionCards) {

        int Yseperator = getYSeparatorCoordinate(positionCards);
        List<Card> cardsBelowY = getCardsBelowY(Yseperator, positionCards);
        List<Card> cardsAboveY = getCardsAboveY(Yseperator, positionCards);
        ;

        Card cardFromDrawPile = guessDrawpileCard(cardsAboveY);
        List<Card> topCardsOfSuitStacks = new ArrayList<>();
        if (cardFromDrawPile != null) {
            for (int i = 1; i < cardsAboveY.size(); i++) {
                topCardsOfSuitStacks.add(cardsAboveY.get(i));
            }
        } else {
            topCardsOfSuitStacks = cardsAboveY;
        }

        List<List<Card>> buildStacks = getColumns(cardsBelowY);
        List<Integer> heightsOfFaceDownSequences = new ArrayList<>();
        for (List<Card> stack : buildStacks) {
            System.out.println(stack);
//            int height = getUnturnedCardsColumnLength(Yseperator, stack);
//            heightsOfFaceDownSequences.add(height);
        }

        // Conversion
        //is DrawPileEmpty? I don't know what this card looks like.
        boolean _isDrawPileEmpty = false;
        // DrawpileCard
        Card _cardFromDrawPile = cardFromDrawPile;
        //Build stack and heights
        int index = 0;
        Card[][] _buildStacks = new Card[7][];
        double[] _heightsOfFaceDownSequences = new double[7];
        for (List<Card> column : buildStacks) {
            int size = buildStacks.get(0).size();
            Card[] _column = new Card[size];
            int columnIndex = 0;
            for (Card card : column) {
                _column[columnIndex] = card;
                columnIndex++;
            }
            _buildStacks[index] = _column;
            _heightsOfFaceDownSequences[index] = (double) heightsOfFaceDownSequences.get(index);
            index++;
        }
        //
        int suitStackCount = topCardsOfSuitStacks.size();
        Card[] _topCardsOfSuitStacks = new Card[suitStackCount];
        int suitIndex = 0;
        for (Card positionCard : topCardsOfSuitStacks) {
            _topCardsOfSuitStacks[suitIndex] = positionCard;
        }


        GameSnapshot test = new GameSnapshot(_isDrawPileEmpty, _cardFromDrawPile, _buildStacks, _topCardsOfSuitStacks,
                _heightsOfFaceDownSequences);
        throw new UnsupportedOperationException();
        //return GameSnapshot;
    }

    /**
     * Get the top-left card in a list of PositionCards.
     * <p>
     * If the topmost card isn't the same as the leftmost card, pick the topmost of the two.
     *
     * @param positionCards a list of PositionCards
     * @return the top-left card (with top as tie-breaker)
     */
    public static Card topLeftCard(List<Card> positionCards) {
        List<Card> sorted =
                positionCards
                        .stream()
                        .sorted(comparatorX())
                        .sorted(comparatorY())
                        .collect(Collectors.toList());

        return sorted.get(0);
    }

    private static Comparator<Card> comparatorBy(ToIntFunction<Card> f) {
        return Comparator.comparingInt(f);
    }

    private static Comparator<Card> comparatorX() {
        ToIntFunction<Card> fx = (positionCard) -> positionCard.x;
        return comparatorBy(fx);
    }

    private static Comparator<Card> comparatorY() {
        ToIntFunction<Card> fy = (positionCard) -> positionCard.x;
        return comparatorBy(fy);
    }

    public static List<Card> getCardsBelowY(int YCoordinate, List<Card> positionCards) {
        //insert check if no cards below, or above.
        List<Card> cardsBelowY = new ArrayList<>();
        for (Card card : positionCards) {
            if (card.y < YCoordinate) {
                cardsBelowY.add(card);
            }
        }
        return cardsBelowY;
    }

    public static List<Card> getCardsAboveY(int YCoordinate, List<Card> positionCards) {
        //insert check if no cards below, or above.
        List<Card> cardsAboveY = new ArrayList<>();
        for (Card card : positionCards) {
            if (card.y > YCoordinate) {
                cardsAboveY.add(card);
            }
        }
        return cardsAboveY;
    }

    public static int getYSeparatorCoordinate(List<Card> positionCards) {
        Card topCard = topLeftCard(positionCards);
        int bottomOftopCardY = topCard.y + topCard.rectangle.height;
        List<Card> cardsBelowY = getCardsBelowY(bottomOftopCardY, positionCards);
        Card topBelowtopLeft = topLeftCard(cardsBelowY);
        int separatorCoordinateY = ((bottomOftopCardY - topBelowtopLeft.y) / 2) + bottomOftopCardY;
        return separatorCoordinateY;
    }

    public static List<List<Card>> getColumns(List<Card> positionCards) {
        List<List<Card>> columnLists = new ArrayList<>();

        List<Card> sorted =
                positionCards
                        .stream()
                        .sorted(comparatorX())
                        .collect(Collectors.toList());
        List<Card> column = new ArrayList<>();
        for (int index = 0; index < sorted.size() - 1; index++) {
            if (index == 0) {
                column.add(sorted.get(index));
            }
            Card card = sorted.get(index);
            Card nextCard = sorted.get(index + 1);
            if (cardsAreOverlappingX(card, nextCard)) {
                column.add(nextCard);
            } else {
                columnLists.add(column.stream()
                        .sorted(comparatorY())
                        .collect(Collectors.toList()));
                column = new ArrayList<>();
                if (index == sorted.size() - 2) {
                    column.add(nextCard);
                    columnLists.add(column.stream()
                            .sorted(comparatorY())
                            .collect(Collectors.toList()));
                }
            }
        }
        return columnLists;
    }

    public static List<List<Card>> getColumns_noller(List<Card> cards) {
        Card topCard = cards.stream().max(comparatorX()).get();
        List<Card> above = new ArrayList<>();
        List<Card> below = new ArrayList<>();

        for (Card card : cards) {
            (isCompletelyAbove(topCard, card) ? below : above).add(card);
        }

        return List.of(above, below);
    }

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
    public static boolean isCompletelyAbove(Card above, Card below) {
        return above.y - above.rectangle.height >= below.y;
    }

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


    public static int countColumns(List<List<Card>> columns) {
        return columns.size();
    }

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
            if (card0farX - card1nearX < width) {
                return null;
            }
        }
        return sorted.get(0);
    }


    public static int getUnturnedCardsColumnLength(int Yline, List<Card> positionCards) {
//        List<Card> sorted = positionCards
//                .stream()
//                .sorted(comparatorY())
//                .collect(Collectors.toList());
        positionCards.sort(comparatorY());
        int distance = Yline - positionCards.get(0).y;
        return distance;
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


}