package v1.sample;

/**
 * @version 1.0
 * @description:
 * @author: 侯春兵
 * @Date: 16:36 2018/11/27
 */
public class TestBean {

    public void doSomthing() {
        System.out.println(System.currentTimeMillis() + " " + this);
    }

    public void init() {
        System.out.println("TestBean.init() 执行了");
    }

    public void destroy() {
        System.out.println("TestBean.destroy() 执行了");
    }
}
