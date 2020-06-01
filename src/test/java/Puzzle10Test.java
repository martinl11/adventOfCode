import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitQuickcheck.class)
public class Puzzle10Test {

    @Test
    public void testSuccess() {
        String input = Puzzle10Testcase.inputLvl2;

        System.out.println("---------------------------");
        System.out.println("Test: default testing");
        System.out.println("---------------------------");

        int[] res = Puzzle10.run(input);
        int[] expected = new int[]{118, 143153};

        Assert.assertArrayEquals(expected, res);
    }

    @Test
    public void testSuccess2() {
        String input = "value 61 goes to bot 2\n" +
                "bot 2 gives low to bot 1 and high to bot 4\n" +
                "value 3 goes to bot 1\n" +
                "bot 1 gives low to output 1 and high to bot 4\n" +
                "bot 4 gives low to output 2 and high to output 0\n" +
                "value 17 goes to bot 2";

        System.out.println("---------------------------");
        System.out.println("Test: simple testing");
        System.out.println("---------------------------");

        int[] res = Puzzle10.run(input);
        int[] expected = new int[]{4, 3111};

        Assert.assertArrayEquals(expected, res);
    }

    @Test
    public void testFail() {
        String input = "bot 76 gives low to bot 191 and high to bot 21";

        System.out.println("---------------------------");
        System.out.println("Test: fail input testing");
        System.out.println("---------------------------");

        int[] res = Puzzle10.run(input);
        int[] expected = new int[]{-1, -1};

        Assert.assertArrayEquals(expected, res);
    }

    @Test
    public void testUnexpected() {
        String input = " ";

        System.out.println("---------------------------");
        System.out.println("Test: random input testing");
        System.out.println("---------------------------");

        int[] res = Puzzle10.run(input);
        int[] expected = new int[]{-1, -1};

        Assert.assertArrayEquals(expected, res);
    }
}
