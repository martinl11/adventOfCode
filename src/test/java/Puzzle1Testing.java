import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitQuickcheck.class)
public class Puzzle1Testing {

    @Test
    public void testSuccess() {

        String input = Puzzle1Testcase.inputLvl2;

        int[] res = Puzzle1.run(input);
        int[] expected = new int[]{231, 147};

        Assert.assertArrayEquals(expected, res);
    }

    @Test
    public void testSuccess2() {

        String input = "R8, R4, R4, R8";

        int[] res = Puzzle1.run(input);
        int[] expected = new int[]{8, 4};

        Assert.assertArrayEquals(expected, res);
    }

    @Test
    public void testFail() {

        String input = " ";

        int[] res = Puzzle1.run(input);
        int[] expected = new int[]{0, 0};

        Assert.assertArrayEquals(expected, res);
    }

    @Test
    public void testUnexpected() {

        String input = "1,1";

        int[] res = Puzzle1.run(input);
        int[] expected = new int[]{0, 0};

        Assert.assertArrayEquals(expected, res);
    }
}
