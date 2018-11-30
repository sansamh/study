package design.chain;


import java.util.Arrays;
import java.util.List;

/**
 * @version 1.0
 * @description:  类dubbo filter调用链
 * @author: 侯春兵
 * @Date: 15:46 2018/11/30
 */
public class DubboChain {

	interface Invoker {
		int invoke();
	}

	interface Filter {
		int invoke(Invoker invoker);
	}

	static class Filter1 implements Filter {


		@Override
		public int invoke(Invoker invoker) {
			System.out.println("Filter1");
			return invoker.invoke();
		}
	}
	static class Filter2 implements Filter {


		@Override
		public int invoke(Invoker invoker) {
			System.out.println("Filter2");
			return invoker.invoke();
		}
	}
	static class Filter3 implements Filter {


		@Override
		public int invoke(Invoker invoker) {
			System.out.println("Filter3");
			return invoker.invoke();
		}
	}

	public static void main(String[] args) {
		List<Filter> filters = Arrays.asList(new Filter1(), new Filter2(), new Filter3());
        //初始化的invoker对象
		Invoker last = new Invoker() {
			@Override
			public int invoke() {
				System.out.println("invoker");
				return 0;
			}
		};

		for (int i = filters.size() - 1; i >= 0; i-- ) {
			final Filter filter = filters.get(i);
			//第一次循环 将上面初始化的Invoker对象赋值给next，最后一个filter执行invoke(初始化的Invoker对象)的方法，然后封装成新的invoker对象 -> #1.Invoker.invoke(最后一个filter.invoke(初始化的Invoker对象))
            //第二次循环 last等于上面封装的对象，last赋值给next，生成新invoker对象，第一个filter执行invoke(next对象，即第一步封装的对象) -> last = Invoker.invoke(第一个filter.invoke(#1对象))
			final Invoker next = last;
			last = new Invoker() {
				@Override
				public int invoke() {
					return filter.invoke(next);
				}
			};
		}
        //输出结果第一个filter执行invoke(#1对象) -> 输出Filter1 调用#1对象的invoke方法 -> 输出Filter2 调用 初始化的invoker对象的invoke方法 -> 输出 invoker
		last.invoke();
	}
}

