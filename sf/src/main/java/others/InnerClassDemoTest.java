package others;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * <p>
 * InnerClassDemoTest
 * </p>
 *
 * @author houcb
 * @since 2019-07-23 09:32
 */
public class InnerClassDemoTest {

    public static void main(String[] args) throws Exception {
        InnerClassDemo demo = new InnerClassDemo();

        Class clz = null;

        try {
            // 内部类 应该使用 $
//            clz = Class.forName("others.InnerClassDemo.StaticInner");
            clz = Class.forName("others.InnerClassDemo$NormalInner");
            System.out.println("clz name: " + clz.getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
        // private others.InnerClassDemo$NormalInner(others.InnerClassDemo)
        Constructor[] constructors = clz.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            System.out.println("constructor name: " + constructor);
        }
        // 因为构造函数是private 且包含参数InnerClassDemo 所以newInstance时需要设置可访问 并且 带上参数
        Constructor normal = clz.getDeclaredConstructor(InnerClassDemo.class);
        normal.setAccessible(true);
        System.out.println(normal.newInstance(demo));


        System.out.println("=======================");

        try {
            clz = Class.forName("others.InnerClassDemo$StaticInner");
            System.out.println("clz name: " + clz.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Constructor[] declaredConstructors = clz.getDeclaredConstructors();
        for (Constructor constructor : declaredConstructors) {
            System.out.println("constructor name: " + constructor);
        }

        Constructor declaredConstructor = clz.getDeclaredConstructor();
        Object o = declaredConstructor.newInstance();
        System.out.println(o);
        Field[] declaredFields = clz.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            System.out.println(field.get(o));
        }

    }
}
