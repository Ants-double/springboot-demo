package com.ants.dirtributionlock.zookeeper.samples.lock;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
@Slf4j
public class ZLockImpl implements ZLock {


    private String path;
    private String name;
    private String lockPath;
    private ZooKeeper zooKeeper;
    private int state;

    public ZLockImpl(String path, String name, ZooKeeper zooKeeper) {
        this.path = path;
        this.name = name;
        this.zooKeeper = zooKeeper;
        this.state = 0;
    }

    @Override
    public boolean lock() {
        boolean flag = lockInternal();
        if (flag) {
            state++;
        }
        return flag;
    }

    private boolean lockInternal() {
        try {


            String result = zooKeeper.create(getPath(), "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            this.lockPath = result;
            List<String> waits = zooKeeper.getChildren(path, false);
            Collections.sort(waits);
            String[] paths = result.split("/");
            String curNodeName = paths[paths.length - 1];
            if (waits.get(0).equalsIgnoreCase(curNodeName)) {
                return true;
            }
            CountDownLatch latch = new CountDownLatch(1);
            for (int i = 0; i < waits.size(); i++) {
                String cur = waits.get(i);
                if (!cur.equalsIgnoreCase(curNodeName)) {
                    continue;
                }
                String perPath = path + "/" + waits.get(i - 1);
                zooKeeper.exists(perPath, new Watcher() {
                    @Override
                    public void process(WatchedEvent watchedEvent) {
                        latch.countDown();
                    }
                });
                break;
            }
            latch.await();
            return true;
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return false;

    }

    private String getPath() {
        return  path+"/"+name;
    }

    @Override
    public boolean unlock() {
        if (state > 1) {
            state--;
            return true;
        }

        try {
            Stat exists = zooKeeper.exists(lockPath, false);
            int version = exists.getVersion();
            zooKeeper.delete(lockPath, version);
            state--;
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
        return false;
    }
}
