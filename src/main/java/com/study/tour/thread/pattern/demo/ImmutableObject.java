package com.study.tour.thread.pattern.demo;

import com.study.tour.thread.pattern.entity.MMSCInfo;
import com.study.tour.thread.pattern.service.MMSCRouter;
import com.study.tour.thread.pattern.service.OMCAgent;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 不可变对象模式
 * 在多线程共享变量的情况下,往往需要对这些变量进行加锁处理,但是这样就有一个弊端,那就是锁本身的引入会带来一定的问题和开销
 * immutableObject可以让我们在不使用锁的情况下保证共享变量访问的线程安全,同时可以避免引入锁的开销
 * 一个严格意义上的不可变对象要满足以下所有条件:
 *  1. 类本身使用final修饰: 防止其子类改变其定义的行为
 *  2. 所与字段都是使用final修饰的: 使用final修饰不仅仅是从语义上说明改修饰字段的引用不可改变. 更重要的是这个语义
 *      在多线程环境下由JMM(java memory model)保证了被修饰字段所引用对象的初始化安全, 即final修饰的字段在其他线程
 *      可见时,它必定是已经初始化完成了的. 相反,非final修饰的字段缺少这个保证,所以会产生一些不可预料的结果.
 *  3. 在对象的创建过程中, this关键子没有泄漏给其他类: 防止其他类(如该类的内部匿名类)在对象创建过程中修改其状态.
 *  4. 任何字段如果引用了其他状态可变的对象(如集合,数组等), 则这些字段必须是private的, 并且不可以对外暴露,如果有
 *      相关方法需要这些字段, 应该进行防御性复制.
 */
public class ImmutableObject {


    public static void main(String[] args) {
        getLotteryCenterByRoute();
    }


    /**
     * 获取车辆位置，车辆位置是实时会有变更的,这里就可以使用immutableOjbect模式来实现
     * 更新车辆位置的时候,直接是更新整个location对象即可
     */
    private static void getCarLocation() {

    }


    /**
     * 业务场景:
     *  客户手机号获取 --> 路由网关 --> 彩信中心
     *  路由网关维护了一张路由表, 会因为新增彩信中心有所变动, 虽然路由表在系统中是多线程共享的数据, 但是因为路由表
     *  改变不会很频繁, 所以为了线程安全, 也不希望对这些数据加锁处理; 所以这个时候就可以使用immutable object模式
     *  这个时候路由表就是一个不可见对象了,
     */
    private  static void getLotteryCenterByRoute() {
        final CountDownLatch begin = new CountDownLatch(1);  //为0时开始执行
        final ExecutorService exec = Executors.newFixedThreadPool(100);
        new OMCAgent().start();
        for (int j=0; j<10; j++) {
            try {
                Thread.sleep(1000 * 3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 10; i++) {
                final int NO = i;
                Runnable runnable = new Runnable() {
                    public void run() {
                        try {
                            //如果当前计数为零，则此方法立即返回。
                            //如果当前计数大于零，则当前线程将被禁用以进行线程调度，并处于休眠状态，直至发生两件事情之一：
                            //或调用countDown（）方法，计数达到零;
                            //或一些其他线程中断当前线程。
                            //等待直到 CountDownLatch减到1
                            //begin.await();
                            MMSCInfo info = MMSCRouter.getInstance().getMMSC(NO + "");
                            System.out.println("deviceID:" + info.getDeviceID() + "  url:" + info.getUrl());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                exec.submit(runnable);
            }
        }

        System.out.println("开始执行");
        //减少锁存器的计数，如果计数达到零，释放所有等待的线程。
        // begin减一，开始并发执行
        //begin.countDown();
        //此方法不等待先前提交的任务完成执行
        exec.shutdown();
        //为了保证先前提交的任务完成执行 使用此方法


    }

}
