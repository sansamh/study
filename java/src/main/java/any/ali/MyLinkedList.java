package any.ali;

import java.util.LinkedList;
import java.util.List;

public class MyLinkedList<T> {

    static class Node<T> {
        private T val;
        private Node pre;
        private Node next;

        public Node(T val, Node pre, Node next) {
            this.val = val;
            this.pre = pre;
            this.next = next;
        }

        public Object getVal() {
            return val;
        }

        public void setVal(T val) {
            this.val = val;
        }

        public Node getPre() {
            return pre;
        }

        public void setPre(Node pre) {
            this.pre = pre;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    transient Node head;
    transient Node tail;

    public MyLinkedList() {
    }

    public void add(T val) {
        this.addFirst(val);
    }

    private void addFirst(T val) {
        Node f = head;
        Node newNode = new Node(val, null, f);
        head = newNode;
        if (f == null) {
            tail = newNode;
        }
        else {
            f.setPre(newNode);
        }
    }

    public T remove(){
        return removeLast();
    }

    private T removeLast() {
        if (tail == null) throw new RuntimeException("empty list");
        Node<T> t = tail;
        T val = t.val;
        Node pre = t.pre;
        if (pre == null) {
            head = null;
        } else {
            pre.next = null;
        }

        t.val = null;

        tail = pre;

        return val;

    }


    public static void main(String[] args) {
        MyLinkedList<String> list = new MyLinkedList<String>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");

        list.remove();
        list.remove();

        list.add("ddd");

        System.out.println(list);

    }

}
