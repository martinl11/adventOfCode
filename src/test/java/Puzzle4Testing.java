import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitQuickcheck.class)
public class Puzzle4Testing {

    @Test
    public void testSuccess() {

        String input = Puzzle4Testcase.inputLvl2;

        int[] res = Puzzle4.run(input);
        int[] expected = new int[]{278221, 267};

        Assert.assertArrayEquals(expected, res);
    }

    @Test
    public void testSuccess2() {

        String input = "ghkmaihex-hucxvm-lmhktzx-267[hmxka]\n" +
                "aaaaa-bbb-z-y-x-123[abxyz]\n" +
                "jchipqat-uadltg-hidgpvt-375[kcnop]\n";

        int[] res = Puzzle4.run(input);
        int[] expected = new int[]{390, 267};

        Assert.assertArrayEquals(expected, res);

    }

    @Test
    public void testFail() {

        String input = "totally-real-room-200[decoy]";

        int[] res = Puzzle4.run(input);
        int[] expected = new int[]{0, 0};

        Assert.assertArrayEquals(expected, res);
    }

    @Test
    public void testUnexpected() {
        String input = "0";

        int[] res = Puzzle4.run(input);
        int[] expected = new int[]{0, 0};

        Assert.assertArrayEquals(expected, res);
    }
}
