package uk.co.jordanrobinson.marsRover;

/**
 * RoverCommand Class - represents a command to be given to a rover.
 *
 * @author Jordan Robinson - me@jordanrobinson.co.uk
 */
public class RoverCommand {

    private String initialPosition;
    private String instructions;

    /**
     * Simple constructor for a RoverCommand.
     *
     * @param initialPosition The initial position for the rover.
     * @param instructions The instructions for the rover to follow.
     */
    public RoverCommand(String initialPosition, String instructions) {
        this.initialPosition = initialPosition;
        this.instructions = instructions;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getInitialPosition() {
        return initialPosition;
    }

    @Override
    public String toString() {
        return getInstructions() + "\n" + getInitialPosition();
    }

}