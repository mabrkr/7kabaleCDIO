package model;

import model.Card;

import java.util.Arrays;
import java.util.Collections;

/**
 * A GameSnapshot represents a certain game state of a game of Klondike. The relevant parameters are the state of the
 * draw pile, the moveable cards, and the number of face-down cards in each build stack.
 *
 *  @author Malte Brink Kristensen
 */

public class GameSnapshot {

    private boolean isDrawPileEmpty;
    private Card cardFromDrawPile;
    private BuildStack[] buildStacks;
    private Card[] topCardsOfSuitStacks;

    /**
     * @param isDrawPileEmpty Is the draw pile empty or not?
     * @param cardFromDrawPile The top card of the draw pile.
     * @param buildStacks Multidimensional array of Card-objects. In case of empty stacks, the length of the array is
     *                    simply < 7. It is expected that the first element in each stack is the bottom card.
     * @param topCardsOfSuitStacks The top cards of each suit stack. In case of empty stacks the length of the array is
     *                             simply < 4.
     * @param heightsOfFaceDownSequences The number of face-down cards in each build stack. Can be approximated
     *                                   as unitless relative heights. It is expected that a build stack with zero face-
     *                                   down cards has a height of 0. After construction, the build stacks are sorted
     *                                   from highest to lowest number of face-down cards. This ensures that the stacks
     *                                   are traversed in prioritized order when looking for potential moves.
     */
    public GameSnapshot(boolean isDrawPileEmpty, Card cardFromDrawPile, Card[][] buildStacks,
                        Card[] topCardsOfSuitStacks, double[] heightsOfFaceDownSequences) {
        if (buildStacks.length > 7) {
            throw new IllegalArgumentException("There can't be more than seven build stacks.");
        }

        if (buildStacks.length != heightsOfFaceDownSequences.length) {
            throw new IllegalArgumentException("The number face-down heights must match the number of build stacks.");
        }

        this.isDrawPileEmpty = isDrawPileEmpty;
        this.cardFromDrawPile = cardFromDrawPile;
        this.topCardsOfSuitStacks = topCardsOfSuitStacks;

        this.buildStacks = new BuildStack[buildStacks.length];
        for (int i = 0; i < buildStacks.length; i++) {
            BuildStack buildStackWithHeight = new BuildStack(buildStacks[i], heightsOfFaceDownSequences[i]);
            this.buildStacks[i] = buildStackWithHeight;
        }

        Arrays.sort(this.buildStacks, Collections.reverseOrder());
    }

    public boolean isDrawPileEmpty() {
        return isDrawPileEmpty;
    }

    public Card getCardFromDrawPile() {
        return cardFromDrawPile;
    }

    public BuildStack[] getBuildStacks() {
        return buildStacks;
    }

    public Card[] getTopCardsOfSuitStacks() {
        return topCardsOfSuitStacks;
    }

}
