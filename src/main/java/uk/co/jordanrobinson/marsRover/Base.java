package uk.co.jordanrobinson.marsRover;

import java.util.ArrayList;

/**
 * @author Jordan Robinson - me@jordanrobinson.co.uk
 *
 * Class representing a base of operations for communication and construction of the rovers.
 */
public class Base {

    private ArrayList<Rover> rovers;

    /**
     * Simple constructor for Base, that initialises the collection of rovers that it stores.
     */
    public Base() {
        rovers = new ArrayList<Rover>();
    }

    /**
     * Checks and then parses the commands given. Creates a rover for every command, lets the rover parse the
     * command and return output, and then collects the output from all rovers and returns it as a String.
     *
     * Note that the instructions need to be in a particular format to be accepted, an example is:
     * 5 5
     * 1 2 N
     * LMLMLMLMM
     *
     * The first line makes up the size of the platform. The second line is the initial position
     * followed by the initial direction. The third line makes up the commands to give the rover.
     *
     * @param instructions The instructions to be given to the mars rovers.
     * @return The output from the mars rovers.
     */
    public String processInstructions(String instructions) {

        /*regex to check if the input is valid.
        to break it down further: ^\d+ \d+\n checks for a number followed by a space and a number, then a line break.
        This is the platform size.
        Then, (\d+ \d+ [NESW] check is the same but followed by one of N E W or S, the initial position.
        The rest \n[LRM]+\n?)+$ checks that the last line is formed from L R and M, for the instructions.
        The command section that is not the platform size can also repeat as many times as necessary. */
        String inputPattern = "^\\d+ \\d+\\n(\\d+ \\d+ [NESW]\\n[LRM]+\\n?)+$";

        //if we don't match the pattern, there could be anything as input, so we stop here.
        if (!instructions.matches(inputPattern)) {
            throw new IllegalArgumentException("Input not recognised as valid!");
        }

        //instructions look good, so get commands and size from the text-based instructions
        ArrayList<RoverCommand> commands = getRoverCommands(instructions);
        String platformSize = getPlatformSize(instructions);
        for (int i = 0; i < commands.size(); i++) { //and add a rover for each command
            rovers.add(new Rover(platformSize, commands.get(i), this));
        }

        String ret = "";

        //then build up the output of each rover's final position
        for (int j = 0; j < rovers.size(); j++) {
            ret += rovers.get(j).getOutput() + "\n";
        }

        //and strip the last line break to conform with the brief
        if (ret.contains("\n")) {
            ret = ret.substring(0, ret.length() - 1);
        }

        return ret;
    }

    /**
     * Parses input and retrieves an ArrayList of RoverCommands from the input. Input should be validated before
     * it reaches this method.
     *
     * @param input The instructions given to the program.
     * @return An ArrayList of commands that can then be given to rovers.
     */
    private ArrayList<RoverCommand> getRoverCommands(String input) {

        String[] commandSnippets = input.split("\n");
        ArrayList<RoverCommand> ret = new ArrayList<RoverCommand>();

        //note we start at one since the first line is the platform size
        for (int i = 1; i < commandSnippets.length; i++) {
            RoverCommand roverCommand = new RoverCommand(commandSnippets[i], commandSnippets[++i]);
            ret.add(roverCommand);
        }

        return ret;
    }

    /**
     * Gets the size of the platform by parsing the instructions given to the program.
     *
     * @param input The instructions given to the program.
     * @return A string denoting the size of the platform, for example "5 5" or "1 44"
     */
    private String getPlatformSize(String input) {
        String[] commandSnippets = input.split("\n");
        String platformSize = null;

        if (commandSnippets.length >= 1) {
            platformSize = commandSnippets[0];
        }
        return platformSize;
    }

    public ArrayList<Rover> getRovers() {
        return rovers;
    }



}
