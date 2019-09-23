package io.sansam;

import io.sansam.generate.Generate;
import io.sansam.generate.RandomGenerate;
import io.sansam.test.EstateBuildingResponseParam;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello World!");
        test();
    }

    public static void test() throws Exception {
        Generate generate = new RandomGenerate();
        EstateBuildingResponseParam param =
                (EstateBuildingResponseParam) generate.product(EstateBuildingResponseParam.class);
        System.out.println(param);
    }
}
