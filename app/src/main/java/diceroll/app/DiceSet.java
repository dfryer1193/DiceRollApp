package diceroll.app;

import java.util.LinkedList;

/**
 * This class represents a set of n-sided dice.
 * Created by David on 3/26/14.
 */
public class DiceSet {

    private String name;
    private String rollSet;
    private String diceString;
    private LinkedList<Dice> dieTypes;
    private LinkedList<Integer> numDice;
    private int addToRoll;

    /**
     * Empty constructor - not sure if needed
     */
    public DiceSet() {
        this.dieTypes = new LinkedList<Dice>();
        this.numDice = new LinkedList<Integer>();
        this.name = "";
        this.diceString = "";
        this.rollSet = "";
    }

    /**
     * constructor to use if no name is specified
     *
     * @param diceString - - the string representing the set of dice.
     */
    public DiceSet(String diceString) {
        this.name = diceString;
        this.dieTypes = new LinkedList<Dice>();
        this.numDice = new LinkedList<Integer>();
        this.rollSet = "";
        this.diceString = diceString;
        this.parseDiceString(diceString);
    }

    /**
     * The constructor to use if a name is specified.
     * @param diceString
     * @param name
     */
    public DiceSet(String diceString, String name) {
        this.name = name;
        this.name = diceString;
        this.dieTypes = new LinkedList<Dice>();
        this.numDice = new LinkedList<Integer>();
        this.rollSet = "";
        this.diceString = diceString;
        this.parseDiceString(diceString);
    }

    /**
     * This method parses a string in the form of
     * "1d2+2d3+3d4...+NdS+K" and adds elements to
     * the dieTypes and numDice lists.
     * @param diceString - the string representing
     *                   the set of dice
     */
    private void parseDiceString(String diceString) {
        int tempVal = 0;
        int diceGroups = 0;
        int groupCount = 0;
        diceString = diceString.toLowerCase();
        diceString = diceString.trim();

        //counts the number of types of dice (needed for constant at the end)
        for (char c : diceString.toCharArray()) {
            if (c == 'd') {
                diceGroups++;
            }
        }

        //parses the string
        for (int i = 0; i + 1 < diceString.length(); i++) {
            char curChar = diceString.charAt(i);
            char nextChar = diceString.charAt(i + 1);
            //check for constant
            if (groupCount < diceGroups) {
                /* if the current character is a digit, multiply tempVal by 10
                 * and add the number to tempVal.
                 */
                if (Character.isDigit(curChar)) {
                    tempVal *= 10;
                    tempVal += Character.getNumericValue(diceString.charAt(i));
                }

                /* if the current character is a d, add tempVal to numDice
                 */
                if (curChar == 'd') {
                    this.numDice.add(tempVal);
                    tempVal = 0;
                }

                /* if the current character is a '+', add a new die with tempVal sides,
                 * increment groupCount
                 */
                if (curChar == '+') {
                    this.dieTypes.add(new Dice(tempVal));
                    tempVal = 0;
                    groupCount++;
                }
            } else {
                /* if the next character is the last character in the string, this is a constant.
                 */
                if (i + 1 == diceString.length()) {
                    tempVal *= 10;
                    tempVal += Character.getNumericValue(diceString.charAt(i));
                    this.addToRoll = tempVal;
                } else if (Character.isDigit(curChar)) { //otherwise, if it's a number do this.
                    tempVal *= 10;
                    tempVal += Character.getNumericValue(diceString.charAt(i));
                }
            }
        }
    }

    /**
     * returns the name of the dice set
     * @return name - the name of the dice set
     */
    public String getName() {
        return name;
    }

    /**
     * rolls the dice set
     * @return the sum of the rolls plus the constant
     */
    public int roll() {
        int sum = 0;
        for (int i = 0; i < this.dieTypes.size(); i++) { //keeps track of which set we're on
            Dice d = this.dieTypes.get(i);
            for (int j = 0; j < this.numDice.get(i); j++) { //keeps track of the number of times we've rolled.
                int temp = d.roll();
                sum += temp;
                this.rollSet += (temp + "+");
            }
        }
        return sum + this.addToRoll;
    }

    /**
     * returns a string representation of the dice set.
     * @return the string representing the dice set.
     */
    public String toString() {
        return this.diceString + "\n(" + this.rollSet + ")";
    }
}
