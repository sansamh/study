package concurrenthashmap;

import com.google.common.collect.Maps;

import java.util.HashMap;

public class MapComputeTest {

    /**
     * to test jdk 8 Map#computeIfAbsent
     * @param args
     */
    public static void main(String[] args) {
        HashMap<Integer, Integer> map = Maps.newHashMap();
        // 如果指定的key不存在，则通过指定的K -> V计算出新的值设置为key的值
        map.put(0 ,0);
        map.computeIfAbsent(0, k -> genValue(0));

        map.put(1, 1);
        map.computeIfAbsent(1, k -> genValue(1));

        // 如果指定的key不存在，则使用新值替换
        map.put(2,2);
        map.putIfAbsent(2, 22);

        map.putIfAbsent(3, 3);

        // 如果指定的key存在，则根据旧的key和value计算新的值newValue, 如果newValue不为null，则设置key新的值为newValue,
        // 如果newValue为null, 则删除该key的值
        map.put(4,4);
        map.computeIfPresent(4, (k, v) -> genValue(v));

        // {0=0, 1=1, 2=2, 3=3, 4=104}
        System.out.println(map);

    }

    public static int genValue(int i) {
        return i + 100;
    }

}
