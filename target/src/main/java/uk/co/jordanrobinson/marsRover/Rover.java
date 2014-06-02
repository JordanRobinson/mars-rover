package uk.co.jordanrobinson.marsRover;

import java.util.ArrayList;

/**
 * Class representing a mars rover that accepts commands.
 *
 * @author Jordan Robinson - me@jordanrobinson.co.uk
 */
public class Rover {

    private static final int NORTH = 0;
    private static final int EAST = 1;
    private static final int SOUTH = 2;
    private static final int WEST = 3;

    private int facing;
    private int positionX;
    private int positionY;
    private int platformSizeX;
    private int platformSizeY;

    private Base base; //we need a way to check where the other rovers are, so we have a reference to the base

    /**
     * Sets up a rover with the size, command and a reference to it's base.
     *
     * @param platformSize size of the platform the rover resides on, formatted like, for example: (5 5)
     * @param command The initial command to give the rover.
     * @param base A reference to the base so that the rover can ask for the other rover locations. By doing this
     *             it has a way to check if it will collide with another rover.
     */
    public Rover(String platformSize, RoverCommand command, Base base) {

        this.base = base;

        //setting up the platform size
        String[] platformCoords = platformSize.split(" ");

        int platformSizeX = Integer.parseInt(platformCoords[0]);
        int platformSizeY = Integer.parseInt(platformCoords[1]);

        //setting up the initial position of the rover
        String[] position = command.getInitialPosition().split(" ");

        int positionX = Integer.parseInt(position[0]);
        int positionY = Integer.parseInt(position[1]);

        //initial position needs to be within the platform size, for obvious reasons
        if (positionX > platformSizeX || positionY > platformSizeY) {
            System.err.println();
            throw new IllegalArgumentException("Starting position is not within the platform!");
        }
        else {
            setPlatformSizeX(platformSizeX);
            setPlatformSizeY(platformSizeY);

            setPositionX(positionX);
            setPositionY(positionY);
            setFacing(parseFacingChar(position[2].charAt(0)));

            processInstructions(command.getInstructions());
        }
    }

    /**
     * Processes instructions for a rover. In the format of M L or R repeating. This is called once from the
     * constructor, but new instructions can be passed into the rover at any point.
     *
     * @param instructions pattern denoting the actions the rover should take, built of M L and R
     *                     characters in any order.
     */
    public void processInstructions(String instructions) {
        for (int i = 0; i < instructions.length(); i++) {
            if (instructions.charAt(i) == 'M') {
                moveForward();
            }
            else {
                turn(instructions.charAt(i));
            }
        }
    }

    /**
     * Tells the rover to move forwards. Checks the current direction of the rover, and then moves in that direction.
     * Also checks that the position that the rover would move to is correct.
     */
    private void moveForward() {

        int newX = getPositionX();
        int newY = getPositionY();

        switch (getFacing()) {
            case NORTH:
                newY++;
                break;
            case EAST:
                newX++;
                break;
            case SOUTH:
                newY--;
                break;
            case WEST:
                newX--;
                break;
            default:
        }

        //we want to check we aren't going off a cliff, or into another rover
        if (checkValidPosition(newX, newY)) {
            setPositionX(newX);
            setPositionY(newY);
        }
        else {
            System.err.println("Invalid position! Can't move to " + positionX + " " + positionY + " so staying put.");
        }
    }

    /**
     * Checks that the position given is a valid one, as in does not currently contain a rover, and is within the
     * platform boundaries.
     *
     * @param positionX The X co-ordinate of the position to check
     * @param positionY The Y co-ordinate of the position to check
     * @return A boolean value denoting if the position is valid or not
     */
    private boolean checkValidPosition(int positionX, int positionY) {
        boolean ret;

        boolean xOutOfBounds = positionX > getPlatformSizeX() || positionX < 0;
        boolean yOutOfBounds = positionY > getPlatformSizeY() || positionY < 0;

        boolean collision = false;

        ArrayList<Rover> otherRovers = getBase().getRovers();

        //check we aren't going to hit another rover
        for (int i = 0; i < otherRovers.size(); i++) {
            if (positionX == otherRovers.get(i).getPositionX()
                    && positionY == otherRovers.get(i).getPositionY()) {
                collision = true;
                break;
            }
        }

        //all three failure conditions should be false for the position to be valid
        ret = !(xOutOfBounds || yOutOfBounds || collision);

        return ret;
    }

    /**
     * Turns the rover based on the input char given.
     *
     * @param turnDirection This should be either L or R, and input should be validated before this point.
     */
    private void turn(char turnDirection) {

        if (turnDirection == 'L') {
            setFacing(getFacing() - 1);
        }
        else if (turnDirection == 'R') {
            setFacing(getFacing() + 1);
        }

        //keeps the directional constants within the bounds
        if (getFacing() < NORTH) {
            setFacing(WEST);
        }
        else if (getFacing() > WEST) {
            setFacing(NORTH);
        }
    }

    /**
     * Converts the char given into the corresponding constant int value, so it can be used within the rover.
     *
     * @param facingChar Should be one of N, E, W, or S. This should be validated before input to this method.
     * @return The constant int value reflecting the directional char given
     */
    private int parseFacingChar(char facingChar) {

        int facingInt;

        switch (facingChar) {
            case 'N':
                facingInt = NORTH;
                break;
            case 'E':
                facingInt = EAST;
                break;
            case 'S':
                facingInt = SOUTH;
                break;
            case 'W':
                facingInt = WEST;
                break;
            default:
                //this shouldn't happen, but just in case
                throw new IllegalArgumentException("Incorrect argument when parsing direction char");
        }
        return facingInt;
    }

    /**
     * Converts the int given into the corresponding char value denoting direction, so it can be used for output.
     *
     * @param facingInt the constant int value as defined in Rover, that reflects a direction
     * @return The char denoting a direction, N, E, W or S
     */
    private char parseFacingInt(int facingInt) {

        char facingChar;

        switch (facingInt) {
            case NORTH:
                facingChar = 'N';
                break;
            case EAST:
                facingChar = 'E';
                break;
            case SOUTH:
                facingChar = 'S';
                break;
            case WEST:
                facingChar = 'W';
                break;
            default:
                //this shouldn't happen, but just in case
                throw new IllegalArgumentException("Incorrect argument when parsing direction int");
        }

        return facingChar;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getPlatformSizeX() {
        return platformSizeX;
    }

    public void setPlatformSizeX(int platformSizeX) {
        this.platformSizeX = platformSizeX;
    }

    public int getPlatformSizeY() {
        return platformSizeY;
    }

    public void setPlatformSizeY(int platformSizeY) {
        this.platformSizeY = platformSizeY;
    }

    public String getOutput() {
        return "" + positionX + " " + positionY + " " + parseFacingInt(getFacing());
    }

    public int getFacing() {
        return facing;
    }

    public void setFacing(int facing) {
        this.facing = facing;
    }

    public Base getBase() {
        return base;
    }
}