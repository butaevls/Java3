import HomeWork6.ArrayTest.NewArray;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(value = Parameterized.class)
public class NewArrayTest extends NewArray {

    private static NewArray newArray;

    private int[] arrayInt;
    private int x;
    private int y;
    private int[] resArray;
    private boolean resBool;

    public NewArrayTest(int[] arrayInt, int x, int y, int[] resArray, boolean resBool) {
        this.arrayInt = arrayInt;
        this.x = x;
        this.y = y;
        this.resArray = resArray;
        this.resBool = resBool;
    }

    @Parameterized.Parameters
    public static Collection parameters() {
        return Arrays.asList(new Object[][]{
            {new int[]{1,2,3,4,5,6,7,8,9}, 4, 5, new int[]{5,6,7,8,9}, true},
                        {new int[]{1,7,3,4,6,8,9}, 2, 0, new int[]{}, false},
                        {new int[]{0,5,3,4,5,6,7,1}, 5, 4, new int[]{6,7,1}, true},
                        {new int[]{1,9,3,4,5,8,0}, 8, 9, new int[]{0}, true},
        }
        );
    }

    @Test
    public void testArray() {
        Assert.assertArrayEquals(newArray.newArrayInt(arrayInt,x),resArray);
    }

    @Test
    public void boolTest() {
        Assert.assertEquals(newArray.equalsInt(arrayInt,x,y),resBool);
    }

    @org.junit.Before
    public void init() {
        System.out.println("init new array");
        newArray = new NewArray();
    }

    @org.junit.After
    public void tearDown() {
        newArray = null;
    }
}