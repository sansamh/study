package design.observer;

import java.util.ArrayList;
import java.util.List;

public class Publisher {

    List<Subject> observers = new ArrayList<>(16);

    private String data;

    public void doPublish() {
        for (Subject observer : observers) {
            observer.updateNew(data);
        }
    }

    public List<Subject> getObservers() {
        return observers;
    }

    public void setObservers(List<Subject> observers) {
        this.observers = observers;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public static void main(String[] args) {
        Publisher publisher = new Publisher();
        String news = "观察者模式简易版已经实现啦，快来围观！";

        Subject subject1 = new Observer1();
        Subject subject2 = new Observer2();

        List<Subject> observers = new ArrayList<>(2);
        observers.add(subject1);
        observers.add(subject2);

        publisher.setObservers(observers);
        publisher.setData(news);

        publisher.doPublish();

    }
}
