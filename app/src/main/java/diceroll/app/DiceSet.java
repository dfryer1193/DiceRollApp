package diceroll.app;

import java.util.LinkedList;

/**
 * This class represents a set of n-sided dice.
 * Created by David on 3/26/14.
 */
public class DiceSet {

    private String name;
    private LinkedList<Dice> dieTypes;
    private LinkedList<Integer> numDice;
    private int addToRoll;

    public DiceSet() {
        this.dieTypes = new LinkedList<Dice>();
        this.numDice = new LinkedList<Integer>();
        this.name = "0d0";
    }

    public DiceSet(String diceString) {
        this.name = diceString;
        this.dieTypes = new LinkedList<Dice>();
        this.numDice = new LinkedList<Integer>();

        this.parseDiceString(diceString);
    }

    public DiceSet(String diceString, String name) {
        this.name = name;
        this.name = diceString;
        this.dieTypes = new LinkedList<Dice>();
        this.numDice = new LinkedList<Integer>();

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
        diceString.toLowerCase();
        diceString.trim();

        for (char c : diceString.toCharArray()) {
            if (c == 'd') {
                diceGroups++;
            }
        }

        for (int i = 0; i + 1 < diceString.length(); i++) {
            char curChar = diceString.charAt(i);
            char nextChar = diceString.charAt(i + 1);
            if (groupCount < diceGroups) {
                if (Character.isDigit(curChar)) {
                    tempVal *= 10;
                    tempVal += Character.getNumericValue(diceString.charAt(i));
                }

                if (nextChar == 'd') {
                    this.numDice.add(tempVal);
                    tempVal = 0;
                }

                if (nextChar == '+') {
                    this.dieTypes.add(new Dice(tempVal));
                    tempVal = 0;
                    groupCount++;
                }
            } else {
                if (i + 1 == diceString.length()) {
                    tempVal *= 10;
                    tempVal += Character.getNumericValue(diceString.charAt(i));
                    this.addToRoll = tempVal;
                } else if (Character.isDigit(curChar)) {
                    tempVal *= 10;
                    tempVal += Character.getNumericValue(diceString.charAt(i));
                }
            }
        }
    }

    public String toString(){
        String outStr = "";
        for(int i = 0; i < this.dieTypes.size(); i++) {
            outStr+=this.numDice.get(i)+"+"+this.dieTypes.get(i).toString()+"+";
        }
        return outStr+"+"+addToRoll;
    }
}
