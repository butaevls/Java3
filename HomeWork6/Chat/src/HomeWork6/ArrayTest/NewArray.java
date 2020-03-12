package HomeWork6.ArrayTest;

import java.util.Arrays;
import java.util.stream.IntStream;

public class NewArray {

    public NewArray (){

    }

    public int[] newArrayInt(int[] arrayInt, int x) {
        int count = 0;
        int length = arrayInt.length;
        for (int i = arrayInt.length-1; i >-1 ; i--) {
            if (arrayInt[i] == x){
                break;
            }
            count++;
        }

        int[] newArrayInt = new int[count];
        if (count != arrayInt.length){
            for (int i = 0; i < count; i++) {
                newArrayInt[i] = arrayInt[length-count+i];
            }
        }
        else {throw new RuntimeException("not in array is "+x);}
        return newArrayInt;
    }

    public boolean equalsInt(int[] arrayInt, int x, int y){
        for (int i = arrayInt.length-1; i >-1 ; i--) {
            if (arrayInt[i] == x || arrayInt[i] == y){
                return true;
            }
        }
        return false;
    }
}
