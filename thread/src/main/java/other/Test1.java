package other;

import java.util.ArrayList;
import java.util.List;

public class Test1 {
    // ... 代表变长数组 或者单个元素
    public static void test(String... s) {
        System.out.println(s.length);
        for (String s1 : s) {
            System.out.println(s1);
        }
    }

    public static void main(String[] args) {
        String t = "aaa";
        String x = "bbb";
        test(t,x);
        System.out.println("----------------------------");

        String [] arr = {"1","2","3"};
        test(arr);
        System.out.println("----------------------------");

        List<String> list = new ArrayList<>(3);
        list.add("l1");
        list.add("l2");
        list.add("l3");
//        test(list);
        /**
         * Error:(29, 9) java: 无法将类 other.Test1中的方法 test应用到给定类型;
         *         需要: java.lang.String[]
         *         找到: java.util.List<java.lang.String>
         *         原因: varargs 不匹配; java.util.List<java.lang.String>无法转换为java.lang.String
         */


    }
}
