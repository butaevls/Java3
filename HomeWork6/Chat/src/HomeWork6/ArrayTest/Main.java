package HomeWork6.ArrayTest;

public class Main {

    public static void main(String[] args) {
        int[] arrayInt = {1, 2, 4, 4, 2, 3, 4, 1, 7};
        NewArray newArray = new NewArray();
        int[] arrayNew = newArray.newArrayInt(arrayInt,2);
        for (int i = 0; i < arrayNew.length; i++) {
            System.out.print(arrayNew[i]+" ");
        }
        System.out.println(newArray.equalsInt(arrayInt,8,9));
    }
}
