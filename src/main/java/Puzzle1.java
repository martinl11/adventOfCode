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

    private static List<Point> visitedPoint= new ArrayList<>();
    private static Point visitedTwice = null;

    public static int[] run(String input) {

        List<String> instructions = new ArrayList<>();
        String curDirection = NORTH;
        Point initPoint = new Point(0,0);
        Point curPoint = new Point(0,0);

        String[] lines = input.split("\n");

        for(String line : lines) {
            instructions.addAll(Arrays.asList(line.split((", "))));
        }

        for (String instruction : instructions) {

            if(instruction.contains("L") || instruction.contains("R")) {

                String direction = instruction.contains("L") ? "L" : "R";
                try {
                    int steps = Integer.parseInt(instruction.substring(instruction.indexOf(direction) + 1));
                    move(steps, curPoint, direction, curDirection);
                    curDirection = updateDirection(direction, curDirection);

                } catch (NumberFormatException e) {
                    System.err.println("Parse Error");
                    System.exit(-1);
                }
            }
        }

        // Taxicab formula d(AB)= (y2 - y1) + (x2 - x1);
        // In this case x1 = 0 ; y1 = 0

        int distance = Math.abs(curPoint.y - initPoint.y) + Math.abs(curPoint.x - initPoint.x);
        System.out.println("Final Point: (" + curPoint.x + ", " + curPoint.y + ")");
        System.out.println("Distance: " + distance);
        visitedPoint.clear();

        int distanceToVisitedTwice = 0;
        if(visitedTwice != null) {
            distanceToVisitedTwice = Math.abs(visitedTwice.y - initPoint.y) + Math.abs(visitedTwice.x - initPoint.x);
            System.out.println("Visited Twice Point: (" + visitedTwice.x + ", " + visitedTwice.y + ")");
            System.out.println("Distance: " + distanceToVisitedTwice);
            visitedTwice = null;
        }

        return new int[]{distance, distanceToVisitedTwice};
    }

    private static String updateDirection(String direction, String curDirection){
        return direction.equals("L") ? leftSwitch.get(curDirection) : rightSwitch.get(curDirection);
    }

    private static void move(int steps, Point curPoint, String direction, String curDirection){
        int[] factor = direction.equals("L") ? leftFactor.get(curDirection) : rightFactor.get(curDirection);
        int dx = steps * factor[0];
        int dy = steps * factor[1];

        addVisited(curPoint, dx, dy);
        curPoint.translate(dx, dy);
    }

    private static boolean isVisited(Point curCoordinate){
        for (Point visited : visitedPoint) {
            if(visited.x == curCoordinate.x && visited.y == curCoordinate.y){
                return true;
            }
        }
        return false;
    }

    private static void addVisited(Point curPoint, int dx, int dy) {

        int curX = curPoint.x;
        int curY = curPoint.y;

        int lastX = curX + dx;
        int lastY = curY + dy;

        if(dx != 0){
            if(dx > 0){
                for (int i = curX + 1; i <= lastX ; i++) {
                    addPoint(new Point(i, curY));
                }
            } else {
                for (int i = curX - 1; i >= lastX ; i--) {
                    addPoint(new Point(i, curY));
                }
            }
        } else if(dy != 0){
            if(dy > 0){
                for (int i = curY + 1; i <= lastY ; i++) {
                    addPoint(new Point(curX, i));
                }
            } else {
                for (int i = curY - 1; i >= lastY ; i--) {
                    addPoint(new Point(curX, i));
                }
            }
        }

    }

    private static void addPoint(Point point) {
        if(visitedTwice == null && isVisited(point)){
            visitedTwice = new Point(point.x , point.y);
        }
        visitedPoint.add(point);
    }

}
