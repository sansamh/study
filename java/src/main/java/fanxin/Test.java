package fanxin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * Test
 * </p>
 *
 * @author houcb
 * @since 2019-05-20 16:35
 */
public class Test {

    public static void main(String[] args) {
//        extendsTest();
        superTest();
    }

    public static void extendsTest() {
        // ? extends Animal 无法往里存数据（可以存null） 可以用另外一个list复制给他操作
        List<? extends Animal> list = new ArrayList<>();
        Animal animal = new Animal();
        animal.setName("动物");
        animal.setAge(3);
//        list.add(animal);

        Cat cat = new Cat();
        cat.setName("猫咪");
        cat.setAge(3);
        cat.setColor("橘色");
//        list.add(cat);

        list.add(null);

        List<Animal> list1 = new ArrayList<>();
        list1.add(animal);
        list1.add(cat);

        List<? extends Animal> list3 = list1;
        System.out.println(list3.get(0));
    }

    public static void superTest() {
        // ? extends Animal 无法往里存数据（可以存null） 可以用另外一个list复制给他操作
        List<? super Animal> list = new ArrayList<>();
        Animal animal = new Animal();
        animal.setName("动物");
        animal.setAge(3);
        list.add(animal);

        Cat cat = new Cat();
        cat.setName("猫咪");
        cat.setAge(3);
        cat.setColor("橘色");
        list.add(cat);

        list.add(null);
        Animal animal1 = (Animal) list.get(1);
        System.out.println("aaa "+animal1.getName());
        System.out.println(list.get(1));


        List<Animal> list1 = new ArrayList<>();
        list1.add(animal);
        list1.add(cat);

        List<? extends Animal> list3 = list1;
        System.out.println(list3.get(0));
    }


    public static <T> T[] toArray(T[] t) {
        return (T[]) Arrays.copyOf(t, t.length, t.getClass());
    }
}
