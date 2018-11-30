package design.chain;

/**
 * @version 1.0
 * @description: 责任链模式 类servlet filter链
 * @author: 侯春兵
 * @Date: 15:28 2018/11/30
 */
public class ServletChain {
	//记录位置
	private int pos = 0;
	//filter的数量
	private int size;
	private Filter[] filters;
	//调用的结果
	private int ans;

	public ServletChain(Filter[] filters) {
		this.filters = filters;
		size = filters.length;
	}

	public void invoke() {
		if (pos < size) {
			Filter filter = filters[pos++];
			//调用过滤器
			Object invoke = filter.invoke();
			ans += Integer.valueOf(invoke.toString());
			//自身再调用
			invoke();
		} else {
			afterDoFilter(ans);
		}

	}

	public void afterDoFilter(int a) {
		System.out.println("调用链走完了，结果为：" + a);
	}

	public static void main(String[] args) {
		Filter[] filters = {new MyFilter1(), new MyFilter2()};
		new ServletChain(filters).invoke();
		/**
		 * MyFilter1被调用啦
		 * MyFilter2被调用啦
		 * 调用链走完了，结果为：3
		 */
	}

}

interface Filter {
	Object invoke();
}

class MyFilter1 implements Filter {

	@Override
	public Object invoke() {
		System.out.println("MyFilter1被调用啦");
		return 1;
	}
}

class MyFilter2 implements Filter {

	@Override
	public Object invoke() {
		System.out.println("MyFilter2被调用啦");
		return 2;
	}
}