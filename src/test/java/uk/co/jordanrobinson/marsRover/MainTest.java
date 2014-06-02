package uk.co.jordanrobinson.marsRover;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * The tests for the mars rover program.
 * @author Jordan Robinson - me@jordanrobinson.co.uk
 */
public class MainTest {

    /**
     * Basic test just to see that a simple case works.
     */
    @Test
    public void exampleRover() {
        final String commandInput = "5 5\n1 2 N\nLMLMLMLMM\n";
        assertTrue(new Base().processInstructions(commandInput).equals("1 3 N"));
    }

    /**
     * Test for multiple rovers.
     */
    @Test
    public void multipleTest() {
        final String commandInput = "5 5\n1 2 N\nLMLMLMLMM\n3 3 E\nMMRMMRMRRM";
        assertTrue(new Base().processInstructions(commandInput).equals("1 3 N\n5 1 E"));
    }

    /**
     * This should show that if a rover tries to enter a space where there is another rover, it will instead
     * give an error and then stay still.
     */
    @Test
    public void collisionTest() {
        final String commandInput = "5 5\n3 3 N\nLMLMLMLMM\n3 3 N\nLMLMLMLMM\n";
        assertTrue(new Base().processInstructions(commandInput).equals("3 4 N\n3 3 N"));
    }

    /**
     * This should show that if a rover tries to fall off the platform, they stay still instead and give an error.
     */
    @Test
    public void fallOffPlatformTest() {
        final String commandInput = "5 5\n5 5 N\nMM";
        assertTrue(new Base().processInstructions(commandInput).equals("5 5 N"));
    }

    /**
     * Test for incorrect directions input.
     */
    @Test(expected=IllegalArgumentException.class)
    public void incorrectDirectionsTest() {
        final String commandInput = "5 5\n3 3 N\nLMLMQMLMM";
        new Base().processInstructions(commandInput);
    }

    /**
     * Test for incorrectly sized platform
     */
    @Test(expected=IllegalArgumentException.class)
    public void incorrectSizeTest() {
        final String commandInput = "-5 5\n3 3 N\nLMLMLMM";
        new Base().processInstructions(commandInput);
    }

    /**
     * Test for checking for valid ordering of the arguments given.
     */
    @Test(expected=IllegalArgumentException.class)
    public void incorrectOrderTest() {
        final String commandInput = "5 5 N\n3 3\nLMLMLMLMM\n3 3 N\nLMLMLMLMM\n";
        new Base().processInstructions(commandInput);
    }

    /**
     * Test for checking that an incorrect starting position is discovered.
     */
    @Test(expected=IllegalArgumentException.class)
    public void incorrectStartingPositionTest() {
        final String commandInput = "2 2\n3 3 N\nLMLMLMLMM";
        new Base().processInstructions(commandInput);
    }

    /**
     * Tests a large platform number and starting size.
     */
    @Test
    public void largeNumbersTest() {
        final String commandInput = "25000 25000\n1299 3006 N\nMMMMM";
        assertTrue(new Base().processInstructions(commandInput).equals("1299 3011 N"));
    }

    /**
     * Tests having lots of rovers over a very large area.
     */
    @Test
    public void lotsOfRoversTest() {
        final String commandInput = "25000 25000\n" +
                "1299 3006 N\nMMMMM\n" +
                "1299 3006 N\nMRRRMRRMRRMRM\n" +
                "1299 3006 N\nLLLLLLL\n" +
                "100 6 N\nRRRRLMM\n" +
                "299 306 N\nMMMRLRLR\n" +
                "1299 3116 N\nLRRMM\n" +
                "1299 15888 N\nMRMRMRMRM\n" +
                "1299 1006 N\nMLMLMLMM\n" +
                "1200 306 N\nMMMLMM\n" +
                "1299 3016 N\nMMLMMM\n" +
                "1299 3306 N\nMMMLMM\n" +
                "1299 3506 N\nMMLMMM\n" +
                "1299 3606 N\nMLMMMM";

        assertTrue(new Base().processInstructions(commandInput).equals("1299 3011 N\n" +
                "1298 3008 N\n" +
                "1299 3006 E\n" +
                "98 6 W\n" +
                "299 309 E\n" +
                "1301 3116 E\n" +
                "1299 15889 N\n" +
                "1300 1006 E\n" +
                "1198 309 W\n" +
                "1296 3018 W\n" +
                "1297 3309 W\n" +
                "1296 3508 W\n" +
                "1295 3607 W"));
    }

}
