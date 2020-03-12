package HomeWork7;

import java.lang.reflect.Method;
import java.lang.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class MainClass {
    static final int COUNT_PRIORITY= 10;
    static boolean usedBS, usedAS;
    static Map<Integer, List> map = new TreeMap<Integer, List>();
    static MyTest myTest = new MyTest();

    public static void start(Class<?> className) {
        List<String> causeList = new ArrayList<String>();
        int countTrue = 0;
        for (Method method : className.getDeclaredMethods()) {
            if (method.getAnnotation(BeforeSuite.class) != null) {
                if (!usedBS) {
                    PutObject(0, method);
                    usedBS = true;
                }
                else {throw new RuntimeException("Duplicate @BeforeSuite");}
            }
            if (method.getAnnotation(AfterSuite.class) != null) {
                if (!usedAS) {
                    PutObject(COUNT_PRIORITY+1, method);
                    usedAS = true;
                }
                else {throw new RuntimeException("Duplicate @AfterSuite");}
            }
            if (method.getAnnotation(Tester.class) != null) {
                Annotation annotation = method.getAnnotation(Tester.class);
                Tester test = method.getAnnotation(Tester.class);
                PutObject(test.priority(), method);
            }
        }
        String[] args = {" "};
        for (Integer key : map.keySet()) {
            ArrayList<Method> listMethod = (ArrayList<Method>) map.get(key);
            for (Method m: listMethod) {
                    System.out.println(m.getName() + " по очереди " + key);
                try {
                    m.invoke(myTest);
                    countTrue++;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    Throwable cause = e.getCause();
                    causeList.add(cause + " in " + m.getName());
                }
            }
        }
        System.out.println("Count of annotations is good = " + countTrue);
        System.out.println("Illegal test:");
        if (causeList.size()>0) {
            for (String str : causeList) {
                System.out.println(str);
            }
        }
    }

    public static void PutObject(int i, Method method) {
        List<Method> arrayList = new ArrayList<Method>();
        if (map.get(i)==null){
            arrayList.add(method);
            map.put(i, arrayList);
        }
        else {
            arrayList = map.get(i);
            arrayList.add(method);
        }
    }

    public static void main(String[] args) {
        System.out.println("Annotations analyze:");
        start(MyTest.class);
    }
}
