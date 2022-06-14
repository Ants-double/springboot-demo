package com.ants.dirtributionlock.zookeeper.samples.lock;

public interface ZLockFactory {

    ZLock getLock(String key);
}
