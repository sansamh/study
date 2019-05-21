package io.sansam;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;
import io.sansam.spi.DemoService;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * <p>
 * Sample
 * </p>
 *
 * @author houcb
 * @since 2019-05-21 17:42
 */
public class Sample {

    public static void main(String[] args) {
        ServiceLoader<DemoService> loader = ServiceLoader.load(DemoService.class);
        Iterator<DemoService> iterator = loader.iterator();
        while (iterator.hasNext()) {
            DemoService next = iterator.next();
            System.out.println(next.getClass().getName() + " " + next.sayHi("张三"));
        }
    }
}
