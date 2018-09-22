package za.ac.sun.cs.search.adversarialagent;

import org.junit.Assert;
import org.junit.Test;
import za.ac.sun.cs.adversarial.agent.DigitsOfPiAgent;

public class TestDoPAgent {

    @Test
    public void pruningF0() {
        DigitsOfPiAgent playerOne = new DigitsOfPiAgent("F0");
        playerOne.requestMove();

        Assert.assertEquals(81, playerOne.getSampledDigits().size());

        System.out.println(playerOne.getSampledDigits());
        System.out.println(playerOne.getSampledIndices());

    }

    @Test
    public void pruningF1() {
        DigitsOfPiAgent playerOne = new DigitsOfPiAgent("F1");
        playerOne.requestMove();

        Assert.assertEquals(36, playerOne.getSampledDigits().size());

        System.out.println(playerOne.getSampledDigits());
        System.out.println(playerOne.getSampledIndices());

    }

    @Test
    public void pruningF2() {
        DigitsOfPiAgent playerOne = new DigitsOfPiAgent("F2");
        playerOne.requestMove();

        Assert.assertEquals(31, playerOne.getSampledDigits().size());

        System.out.println(playerOne.getSampledDigits());
        System.out.println(playerOne.getSampledIndices());

    }
}
