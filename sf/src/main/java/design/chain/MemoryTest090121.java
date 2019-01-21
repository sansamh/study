package design.chain;

import java.util.ArrayList;
import java.util.List;

public class MemoryTest090121 {

    interface Invoker {
        int invoke();
    }

    interface Filter {
        int doFilter(Invoker invoker);
    }

    static class MyFilter1 implements Filter {

        @Override
        public int doFilter(Invoker invoker) {
            System.out.println(this.getClass() + " is running doFilter Method");
            return invoker.invoke();
        }
    }
    static class MyFilter2 implements Filter {

        @Override
        public int doFilter(Invoker invoker) {
            System.out.println(this.getClass() + " is running doFilter Method");
            return invoker.invoke();
        }
    }
    static class MyFilter3 implements Filter {

        @Override
        public int doFilter(Invoker invoker) {
            System.out.println(this.getClass() + " is running doFilter Method");
            return invoker.invoke();
        }
    }

    public static void main(String[] args) {
        List<Filter> chain = new ArrayList<>(3);
        chain.add(new MyFilter1());
        chain.add(new MyFilter2());
        chain.add(new MyFilter3());

        Invoker last = new Invoker() {
            @Override
            public int invoke() {
                System.out.println(this.getClass() + " invoker#invoke");
                return 0;
            }
        };

        for (int len = chain.size() - 1; len >=0; len --) {
            final Filter filter = chain.get(len);
            final Invoker next = last;
            last = new Invoker() {
                @Override
                public int invoke() {
                    return filter.doFilter(next);
                }
            };
        }

        System.out.println(last.invoke());
    }
}
