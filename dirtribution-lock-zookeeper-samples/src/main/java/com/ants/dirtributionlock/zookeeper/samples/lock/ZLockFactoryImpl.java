package com.ants.dirtributionlock.zookeeper.samples.lock;

import lombok.Data;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

@Data
public class ZLockFactoryImpl implements ZLockFactory {

    private String ZLOCK_ROOT_PATH = "/zlock/locks";

    private ZooKeeper zooKeeper;

    @Override
    public ZLock getLock(String key) {
        String path = getPath(key);
        try {
            zooKeeper.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            Stat stat = null;
            try {
                stat = zooKeeper.exists(path, false);
                if (stat == null) {
                    return null;
                }
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        ZLock lock = new ZLockImpl(path,key,zooKeeper);
        return lock;

    }

    private String getPath(String key) {
        return ZLOCK_ROOT_PATH + "/" + key;
    }

}
