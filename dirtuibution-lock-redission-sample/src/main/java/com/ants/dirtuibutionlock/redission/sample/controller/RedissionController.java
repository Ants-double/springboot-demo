package com.ants.dirtuibutionlock.redission.sample.controller;

import com.ants.dirtuibutionlock.redission.sample.util.RedisLockUtil;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("redission")
public class RedissionController {

    private static final String product = "MoonCake";

    @Autowired
    private RedisLockUtil redisLockUtil;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("lockAdd")
    public void lockAdd() throws Exception {
        //对数据进行加锁
        RLock value = redisLockUtil.lock(product, "value", 300, 3, 200);

        //加锁

        System.out.println(Thread.currentThread().getName());
        String stocks = stringRedisTemplate.opsForValue().get("stock");
        int stock = Integer.parseInt(stocks);
        if (stock > 0) {
            //下单
            stock -= 1;
            stringRedisTemplate.opsForValue().set("stock", String.valueOf(stock));
            System.out.println("扣减成功，库存stock：" + stock);
            Thread.sleep(5000);
        } else {
            //没库存
            System.out.println("扣减失败，库存不足");
        }
        //解锁
       redisLockUtil.unlock(product,"value");

    }
}
