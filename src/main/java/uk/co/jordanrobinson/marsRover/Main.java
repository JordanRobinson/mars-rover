package uk.co.jordanrobinson.marsRover;

/**
 * The entry point for the Mars Rover program.
 *
 * @author Jordan Robinson - me@jordanrobinson.co.uk
 */
public final class Main {

    /**
     * Private constructor since this is a utility class.
     */
    private Main() {

    }

    /**
     * Entry point for the program. Input should be specified, and bases created here.
     *
     * @param args The args given to the program. Not currently used.
     */
    public static void main(String[] args) {
        String input = "5 5\n" +
                "1 2 N\n" +
                "LMLMLMLMM\n" +
                "3 3 E\n" +
                "MMRMMRMRRM";

        int i=2; int j=0;


        int x = ((++i * ++i) % --i)* ++i;

        System.out.println(x);
        System.out.println(i);
        System.out.println(~i);
        System.out.println(~i + x);

        //System.out.println(~i + ((++i * ++i) % --i)* ++i);


        j = ~i + ((++i * ++i) % --i) * ++i;

        System.out.println(j % 5);
        System.out.println(-2 % 5);

        //System.out.println(new Base().processInstructions(input));
    }
}
