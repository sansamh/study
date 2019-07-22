package io.sansam.service.async;

import io.sansam.pojo.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * AsyncTaskService
 * </p>
 *
 * @author houcb
 * @since 2019-05-28 10:35
 */
@Service
@Slf4j
public class AsyncTaskService {

    @Async("taskExecutor")
    public String doTask1() {
        try {
            log.info(Thread.currentThread().getName() + " 线程开始睡眠1S");
            Thread.sleep(1000);
            log.info(Thread.currentThread().getName() + " 线程结束睡眠");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "doTask1";
    }

    @Async("taskExecutor")
    public int doTask2() {
        try {
            log.info(Thread.currentThread().getName() + " 线程开始睡眠2S");
            Thread.sleep(2000);
            log.info(Thread.currentThread().getName() + " 线程结束睡眠");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 2;
    }

    @Async("taskExecutor")
    public List<Order> doTask3() {
        try {
            log.info(Thread.currentThread().getName() + " 线程开始睡眠2S");
            Thread.sleep(2000);
            log.info(Thread.currentThread().getName() + " 线程结束睡眠");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Order> list = new ArrayList<>();
        Order order = Order.builder().orderId("id001").orderStatus(2).orderName("订单一号").build();
        list.add(order);
        return list;
    }
}
