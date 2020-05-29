import java.awt.*;
import java.util.*;
import java.util.List;

public class Puzzle1 {

    private static String NORTH = "n";
    private static String SOUTH = "s";
    private static String EAST = "e";
    private static String WEST = "w";

    private static Map<String, String> leftSwitch = new HashMap<String, String>() {{
        put(NORTH, WEST);
        put(SOUTH, EAST);
        put(EAST, NORTH);
        put(WEST, SOUTH);
    }};

    private static Map<String, String> rightSwitch = new HashMap<String, String>() {{
        put(NORTH, EAST);
        put(SOUTH, WEST);
        put(EAST, SOUTH);
        put(WEST, NORTH);
    }};

    private static Map<String, int[]> leftFactor = new HashMap<String, int[]>() {{
        put(NORTH, new int[]{-1, 0});
        put(SOUTH, new int[]{1, 0});
        put(EAST, new int[]{0, 1});
        put(WEST, new int[]{0, -1});
    }};

    private static Map<String, int[]> rightFactor = new HashMap<String, int[]>() {{
        put(NORTH, new int[]{1, 0});
        put(SOUTH, new int[]{-1, 0});
        put(EAST, new int[]{0, -1});
        put(WEST, new int[]{0, 1});
    }};

    public static void main(String[] args) {
        String input = "R5, R4, R2, L3, R1, R1, L4, L5, R3, L1, L1, R4, L2, R1, R4, R4, L2, L2, R4, L4, R1, R3, L3, L1, L2, R1, R5, L5, L1, L1, R3, R5, L1, R4, L5, R5, R1, L185, R4, L1, R51, R3, L2, R78, R1, L4, R188, R1, L5, R5, R2, R3, L5, R3, R4, L1, R2, R2, L4, L4, L5, R5, R4, L4, R2, L5, R2, L1, L4, R4, L4, R2, L3, L4, R2, L3, R3, R2, L2, L3, R4, R3, R1, L4, L2, L5, R4, R4, L1, R1, L5, L1, R3, R1, L2, R1, R1, R3, L4, L1, L3, R2, R4, R2, L2, R1, L5, R3, L3, R3, L1, R4, L3, L3, R4, L2, L1, L3, R2, R3, L2, L1, R4, L3, L5, L2, L4, R1, L4, L4, R3, R5, L4, L1, L1, R4, L2, R5, R1, R1, R2, R1, R5, L1, L3, L5, R2";
        run(input);
    }

    public static void run(String input) {

        List<String> instructions = new ArrayList<String>();
        String curDirection = NORTH;
        Point initPoint = new Point(0,0);
        Point curPoint = new Point(0,0);

        String[] lines = input.split("\n");

        for(String line : lines) {
            instructions.addAll(Arrays.asList(line.split((", "))));
        }

        for (String instruction : instructions) {
            String direction = instruction.contains("L") ? "L" : "R";
            int steps = Integer.parseInt(instruction.substring(instruction.indexOf(direction) + 1));
            move(steps, curPoint, direction, curDirection);
            curDirection = updateDirection(direction, curDirection);
        }

        // Taxicab formula d(AB)= (y2 - y1) + (x2 - x1);
        // In this case x1 = 0 ; y1 = 0

        int distance = (curPoint.y - initPoint.y) + (curPoint.x - initPoint.x);

        System.out.println("distance: " + distance);
    }

    private static String updateDirection(String direction, String curDirection){
        return direction.equals("L") ? leftSwitch.get(curDirection) : rightSwitch.get(curDirection);
    }

    private static void move(int steps, Point curCoordinate, String direction, String curDirection){
        int[] factor = direction.equals("L") ? leftFactor.get(curDirection) : rightFactor.get(curDirection);
        curCoordinate.translate(steps * factor[0], steps * factor[1]);
    }

}