package others;

/**
 * <p>
 * InnerClassDemo
 * </p>
 *
 * @author houcb
 * @since 2019-07-23 09:26
 */
public class InnerClassDemo {

    int i = 1;

    private static class StaticInner {
        int j = 2;

        StaticInner() {
        }

        void print() {
            System.out.println("in static inner");
        }
    }

    private class NormalInner {
        int k = 3;

        void print() {
            System.out.println("in normalInner");
        }
    }

}
