package com.ants.dirtributionlock.zookeeper.samples;

import com.ants.dirtributionlock.zookeeper.samples.lock.ZLock;
import com.ants.dirtributionlock.zookeeper.samples.lock.ZLockFactory;
import com.ants.dirtributionlock.zookeeper.samples.lock.ZLockFactoryImpl;
import org.apache.curator.CuratorZookeeperClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.ZooKeeper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class DirtributionLockZookeeperSamplesApplicationTests {

    public final static Random random  = new Random();


    @Resource
    private CuratorFramework curatorFramework;

    @Test
    void contextLoads() throws Exception {
        CuratorZookeeperClient zookeeperClient = curatorFramework.getZookeeperClient();
        ZooKeeper zooKeeper = zookeeperClient.getZooKeeper();
        CountDownLatch latch = new CountDownLatch(100);
        ZLockFactoryImpl factory = new ZLockFactoryImpl();
        factory.setZooKeeper(zooKeeper);
        for(int i = 0;i<100;i++){
            int finalI = i;
            Thread t = new Thread(()->{
                exec(factory);
                System.out.println("Thread_"+ finalI +"释放锁完成");
                latch.countDown();
            },"Thread_"+i);
            t.start();
        }
        try {
            latch.await();
            TimeUnit.SECONDS.sleep(5);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("测试完成");
    }

    public  void exec(ZLockFactory factory){
        ZLock lock=factory.getLock("lock");
        System.out.println("Thread:"+Thread.currentThread().getName()+",尝试获取锁");
        boolean flag=lock.lock();
        System.out.println("Thread:"+Thread.currentThread().getName()+",尝试获取锁,结果："+flag);

        try {
            TimeUnit.MILLISECONDS.sleep(random.nextInt(30));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.unlock();
        System.out.println("Thread:"+Thread.currentThread().getName()+",释放锁锁");


    }

}
