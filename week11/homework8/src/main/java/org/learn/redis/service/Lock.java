package org.learn.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Service
public class Lock {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public boolean getLock(String value) {
        Boolean result = this.redisTemplate.opsForValue()
                .setIfAbsent("lock", value, 30,TimeUnit.SECONDS);
        if (result == null) {
            System.out.println("获取锁失败");
            return false;
        }
        if(!result) {
            System.out.println("锁已被持有");
            return result;
        }
        System.out.println("成功获得锁");
        return result;
    }
    private static final String RELEASE_LOCK_LUA_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    public boolean unlock(String value) {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(RELEASE_LOCK_LUA_SCRIPT,Long.class);
        // 参数一：redisScript，参数二：key列表，参数三：arg（可多个）
        Long result = redisTemplate.execute(redisScript, Collections.singletonList("lock"),value);
        if (result == null) {
            System.out.println("释放锁失败");
            return false;
        }
        if(result == 0){
            System.out.println("已被其他线程持有,释放锁失败");
            return false;
        }
        System.out.println("释放成功");
        return true;
    }

}
