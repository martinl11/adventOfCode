import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitQuickcheck.class)
public class Puzzle1Test {

    @Test
    public void testSuccess() {

        String input = Puzzle1Testcase.inputLvl2;

        System.out.println("---------------------------");
        System.out.println("Test: default testing");
        System.out.println("---------------------------");

        int[] res = Puzzle1.run(input);
        int[] expected = new int[]{231, 147};

        Assert.assertArrayEquals(expected, res);
    }

    @Test
    public void testSuccess2() {

        String input = "R8, R4, R4, R8";

        System.out.println("---------------------------");
        System.out.println("Test: simple testing");
        System.out.println("---------------------------");

        int[] res = Puzzle1.run(input);
        int[] expected = new int[]{8, 4};

        Assert.assertArrayEquals(expected, res);
    }

    @Test
    public void testFail() {

        String input = " ";

        System.out.println("---------------------------");
        System.out.println("Test: fail input testing");
        System.out.println("---------------------------");

        int[] res = Puzzle1.run(input);
        int[] expected = new int[]{0, 0};

        Assert.assertArrayEquals(expected, res);
    }

    @Test
    public void testUnexpected() {

        String input = "1,1";

        System.out.println("---------------------------");
        System.out.println("Test: random input testing");
        System.out.println("---------------------------");

        int[] res = Puzzle1.run(input);
        int[] expected = new int[]{0, 0};

        Assert.assertArrayEquals(expected, res);
    }
}
