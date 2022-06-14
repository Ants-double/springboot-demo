package com.ants.dirtributionlock.zookeeper.samples.lock;

/**
 * @author ants_
 */
public interface ZLock {
    boolean lock();
    boolean unlock();
}
