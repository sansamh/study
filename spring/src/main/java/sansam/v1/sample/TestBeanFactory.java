package sansam.v1.sample;

/**
 * @version 1.0
 * @description:
 * @author: 侯春兵
 * @Date: 16:37 2018/11/27
 */
public class TestBeanFactory {
    public static TestBean getStaticTestBean() {
        return new TestBean();
    }

    public TestBean getTestBean() {
        return new TestBean();
    }
}
