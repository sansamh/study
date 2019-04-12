package any;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.Funnels;
import com.google.common.hash.PrimitiveSink;

public class BloomFilterTest {

    public static class User {
        private String id;
        private String name;

        public User(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static void main(String[] args) {
        int size = 1000;
        BloomFilter<User> filter = BloomFilter.create(new Funnel<User>() {
            @Override
            public void funnel(User from, PrimitiveSink into) {
            }
        }, size);

        int objectSize = 10;

        User u = null;
        for (int i = 0; i < objectSize; i++) {
            u = new User(i + "", i + "name");
            filter.put(u);
        }

        for (int i = 8; i <= 15; i++) {
            u = new User(i + "", i + "name");
            System.out.println(filter.mightContain(u) + u.toString());
        }
    }
}
