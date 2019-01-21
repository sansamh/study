package others;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * @version 1.0
 * @description:
 * @author: 侯春兵
 * @Date: 10:02 2018/12/21
 */
public class BloomFilterDemo {
	/**
	 * 布隆过滤器
	 * 对写入的数据做 H 次 hash 运算定位到数组中的位置，同时将数据改为 1 。
	 * 当有数据查询时也是同样的方式定位到数组中。 一旦其中的有一位为 0 则认为数据肯定不存在于集合，否则数据可能存在于集合中。
	 * 使用这个参数 -XX:+PrintHeapAtGC
	 */

	public static void test(int[] target) {
		long start = System.currentTimeMillis();
		BloomFilter<Integer> filter = BloomFilter.create(Funnels.integerFunnel(), 10000000, 0.01);
		for (int i = 0; i < 10000000; i++) {
			filter.put(i);
		}
		long end = System.currentTimeMillis();
		System.out.println("Filter装满数据耗时：" + (end - start));

		for (int i : target) {
			System.out.println(String.format("filter contain i = [%s] = [%s]",new Object[] {i, filter.mightContain(i)}));
		}
	}

	public static void main(String[] args) {
		test(new int[] {1,9999,12580,8787878});
	}
}
