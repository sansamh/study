package any;

public class TestStatic {

    public static void main(String[] args) {
        A a = new B();
        a.foo();
    }
}

class A {
    public static void foo() {
        System.out.println("any.A");
    }
}

class B extends A {
    public static void foo() {
        System.out.println("any.B");
    }
}