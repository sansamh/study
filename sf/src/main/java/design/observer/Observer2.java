package design.observer;

public class Observer2 implements Subject {
    @Override
    public void updateNew(String data) {
        System.out.println(this + "获得新消息：" + data);
    }
}
