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

		Invoker last = new Invoker() {
			@Override
			public int invoke() {
				System.out.println("invoker");
				return 0;
			}
		};

		for (int i = filters.size() - 1; i >= 0; i-- ) {
			final Filter filter = filters.get(i);
			final Invoker next = last;
			last = new Invoker() {
				@Override
				public int invoke() {
					return filter.invoke(next);
				}
			};
		}

		last.invoke();
	}
}

