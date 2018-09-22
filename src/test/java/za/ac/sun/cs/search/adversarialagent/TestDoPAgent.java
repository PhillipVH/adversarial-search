package za.ac.sun.cs.search.adversarialagent;

import org.junit.Assert;
import org.junit.Test;
import za.ac.sun.cs.adversarial.agent.Agent;
import za.ac.sun.cs.adversarial.agent.DigitsOfPiAgent;
import za.ac.sun.cs.adversarial.referee.Referee;

public class TestDoPAgent {

    @Test
    public void smokeTest() {
        DigitsOfPiAgent playerOne = new DigitsOfPiAgent();
        playerOne.requestMove();

        Assert.assertEquals(81, playerOne.getSampledDigits().size());

        System.out.println(playerOne.getSampledDigits());

    }
}
