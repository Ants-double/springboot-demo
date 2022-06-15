package com.ants.dirtuibutionlock.redission.sample.util;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisLockUtil {

    private final Logger logger = LoggerFactory.getLogger(RedisLockUtil.class);

    @Autowired
    private RedissonClient redissonClient;

   /* @Autowired
    public RedisLockUtil(@Qualifier("customRedisson") RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }*/

    /**
     * 加锁
     * @param key 锁的 key
     * @param value  value （ key + value 必须保证唯一）
     * @param expire key 的过期时间，单位 ms
     * @param retryTimes 重试次数，即加锁失败之后的重试次数
     * @param retryInterval 重试时间间隔，单位 ms
     * @return 加锁 true 成功
     */
    public RLock lock(String key, String value, long expire, int retryTimes, long retryInterval) {
        logger.info("locking... redisK = {}", key);
        RLock fairLock = redissonClient.getFairLock(key + ":" +  value);
        try {
            boolean tryLock = fairLock.tryLock(0, expire, TimeUnit.MILLISECONDS);
            if (tryLock) {
                logger.info("locked... redisK = {}", key);
                return fairLock;
            } else {
                //重试获取锁
                logger.info("retry to acquire lock: [redisK = {}]", key);
                int count = 0;
                while(count < retryTimes) {
                    try {
                        Thread.sleep(retryInterval);
                        tryLock = fairLock.tryLock(0, expire, TimeUnit.MILLISECONDS);
                        if(tryLock) {
                            logger.info("locked... redisK = {}", key);
                            return fairLock;
                        }
                        logger.warn("{} times try to acquire lock", count + 1);
                        count++;
                    } catch (Exception e) {
                        logger.error("acquire redis occurred an exception", e);
                        break;
                    }
                }

                logger.info("fail to acquire lock {}", key);
            }
        } catch (Throwable e1) {
            logger.error("acquire redis occurred an exception", e1);
        }

        return fairLock;
    }

    /**
     * 加锁
     * @param key 锁的 key
     * @param value  value （ key + value 必须保证唯一）
     * @param expire key 的过期时间，单位 ms
     * @param retryTimes 重试次数，即加锁失败之后的重试次数
     * @param retryInterval 重试时间间隔，单位 ms
     * @return 加锁 true 成功
     */
    public boolean lock2(String key, String value, long expire, int retryTimes, long retryInterval) {
        logger.info("locking... redisK = {}", key);
        RLock fairLock = redissonClient.getFairLock(key + ":" +  value);
        try {
            boolean tryLock = fairLock.tryLock(0, expire, TimeUnit.MILLISECONDS);
            if (tryLock) {
                logger.info("locked... redisK = {}", key);
                return true;
            } else {
                //重试获取锁
                logger.info("retry to acquire lock: [redisK = {}]", key);
                int count = 0;
                while(count < retryTimes) {
                    try {
                        Thread.sleep(retryInterval);
                        tryLock = fairLock.tryLock(0, expire, TimeUnit.MILLISECONDS);
                        if(tryLock) {
                            logger.info("locked... redisK = {}", key);
                            return true;
                        }
                        logger.warn("{} times try to acquire lock", count + 1);
                        count++;
                    } catch (Exception e) {
                        logger.error("acquire redis occurred an exception", e);
                        break;
                    }
                }

                logger.info("fail to acquire lock {}", key);
                return false;
            }
        } catch (Throwable e1) {
            logger.error("acquire redis occurred an exception", e1);
            return false;
        }
    }


    /**
     * 加锁
     * @param key 锁的 key
     * @param value  value （ key + value 必须保证唯一）
     * @param expire key 的过期时间，单位 ms
     * @return 加锁 true 成功
     */
    public boolean lockCheck(String key, String value, long expire) {
        logger.info("locking... redisK = {}", key);
        RLock fairLock = redissonClient.getFairLock(key + ":" +  value);
        boolean tryLock = false;
        try {
            tryLock = fairLock.tryLock(0, expire, TimeUnit.MILLISECONDS);
        } catch (Throwable e1) {
            logger.error("acquire redis occurred an exception", e1);
        }
        return tryLock;
    }

    /**
     * 释放KEY
     * @return 释放锁 true 成功
     */
    public boolean unlock(String key, String value) {
        RLock fairLock = redissonClient.getFairLock(key + ":" +  value);
        try {
            //如果这里抛异常，后续锁无法释放
            if (fairLock.isLocked()) {
                fairLock.unlock();
                logger.info("release lock success");

                return true;
            }
        } catch (Throwable e) {
            logger.error("release lock occurred an exception", e);
        }

        return false;
    }
}
