package concurrenthashmap;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @version 1.0
 * @description:
 * @author: 侯春兵
 * @Date: 11:34 2019/1/28
 */
public class ConcurrentHashMapDemo {

	/**
	 * summary:
	 *  ConcurrentHash put方法的key val都不能为空
	 *  ConcurrentHash get方法的key 不能为空
	 *  因为put get都要获取key的hashCode -> spread(int hashCode) int h = spread(key.hashCode()); null.hashCode报异常
	 *
	 *  hashMap put方法的key val可以为空 但是key只有一个为null的 key为null时hashCode为0
	 *  hashMap get方法的key 可以为空 -> haspMap的hash(Object key)方法 return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
	 *
	 */

	public static void main(String[] args) {
		ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
		//异常 ConcurrentHash put 的 key val 都不能为null
//		map.put("1", null);
//		map.put(null, "1");
		//异常 ConcurrentHash get 的key 不能为null， key要进行hash计算 会报异常
		System.out.println(map.get(null)); 	// int h = spread(key.hashCode()); null.hashCode报异常

		HashMap<String, String> hashMap = new HashMap<>();
		hashMap.put(null, "1"); // 计算hash null = 0; return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
		hashMap.get(null);
	}
}
