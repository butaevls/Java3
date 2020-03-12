package HomeWork7;

import org.testng.Assert;

public class MyTest {
    private static ForTest forTest = new ForTest();

    @BeforeSuite
    public void methodBefore() {
        System.out.println("Before suite");
    }

    @AfterSuite
    public void methodAfter() {
        System.out.println("AfterSuite");
    }

    // Duplicate BeforeSuite & AfterSuite

/*    @BeforeSuite
    public void methodBefore2() {
        System.out.println("Before suite");
    }
*/

/*
    @AfterSuite
    public void duplicateAfterSuite2() {
        System.out.println("AfterSuite");
    }
*/

    @Tester(args = "", priority = 4)
    public void methodTest1() {
        Assert.assertEquals(7, forTest.Plus(4,3));
    }

    @Tester(args = "", priority = 3)
    public void methodTest2() {
        Assert.assertEquals(forTest.Minus(10,4), 6);
    }

    @Tester(args = "", priority = 4)
    public void methodTest3() {
        Assert.assertEquals(5, forTest.Div(17,3));
    }

    @Tester(args = "", priority = 1)
    public void methodTest4() {
        Assert.assertEquals("String", forTest.ChngChar("String", 'i', 'o'));
    }

    @Tester(args = "", priority = 2)
    public void methodTest5() {
        Assert.assertEquals("BasketBall", forTest.Cont("Basket", "Ball"));
    }

    @Tester(args = "", priority = 5)
    public void methodTest6() {
        Assert.assertEquals("Fiitball", forTest.ChngChar("Football", 'o', 'i'));
    }

    @Tester(args = "", priority = 9)
    public void methodTest7() {
        Assert.assertEquals(forTest.Minus(10,4), 4);
    }

    @Tester(args = "", priority = 7)
    public void methodTest8() {
        Assert.assertEquals(5, forTest.Div(17,3));
    }

    @Tester(args = "", priority = 10)
    public void methodTest9() {
        Assert.assertEquals("Strong", forTest.ChngChar("String", 'i', 'o'));
    }

    @Tester(args = "", priority = 3)
    public void methodTest10() {
        Assert.assertEquals(forTest.Cont("Before", "Suite"), "BeforeSuite");
    }

    @Tester(args = "", priority = 2)
    public void methodTest11() {
        Assert.assertEquals("Fiitball", forTest.ChngChar("Football", 'o', 'i'));
    }

}
