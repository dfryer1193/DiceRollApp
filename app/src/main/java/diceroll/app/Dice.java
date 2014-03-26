package diceroll.app;

import java.util.Random;

/**
 * Created by David Fryer on 3/26/14.
 */
public class Dice {
    private int numsides;

    public Dice(){
        this.numsides=0; //not a valid die
    }

    public Dice(int numsides){
        this.numsides=numsides;
    }

    public int roll(){
        Random rand = new Random();
        return rand.nextInt(this.numsides)+1;
    }

    public String toString(){
        return this.numsides + "";
    }
}
