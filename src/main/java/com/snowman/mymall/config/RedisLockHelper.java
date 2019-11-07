package com.snowman.mymall.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.Expiration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/11/7 10:39
 * @Version 1.0
 **/
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisLockHelper {

    private static final String DELIMITER = "|";

    /**
     * 如果要求比较高可以通过注入的方式分配
     */
    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newScheduledThreadPool(10);

    private final StringRedisTemplate stringRedisTemplate;


    public RedisLockHelper(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }


    /**
     * 获取锁（存在死锁风险）
     *
     * @param lockKey
     * @param value
     * @param timeout
     * @param unit
     * @return
     */
    public boolean tryLock(final String lockKey, final String value, final long timeout,
                           final TimeUnit unit) {

        return stringRedisTemplate.execute((RedisCallback<Boolean>) connection -> connection.set(
                lockKey.getBytes(), value.getBytes(),
                Expiration.from(timeout, unit),
                RedisStringCommands.SetOption.SET_IF_ABSENT));

    }

    /**
     * 获取锁
     *
     * @param lockKey
     * @param uuid
     * @param timeout
     * @param unit
     * @return
     */
    public boolean lock(String lockKey, final String uuid, long timeout, final TimeUnit unit) {

        final long milliseconds = Expiration.from(timeout, unit).getExpirationTimeInMilliseconds();

        boolean success = stringRedisTemplate.opsForValue().setIfAbsent(lockKey,
                (System.currentTimeMillis() + milliseconds) + DELIMITER + uuid);

        if (success) {
            stringRedisTemplate.expire(lockKey, timeout, TimeUnit.SECONDS);
        } else {
            String oldVal = stringRedisTemplate.opsForValue().getAndSet(lockKey,
                    (System.currentTimeMillis() + milliseconds) + DELIMITER + uuid);

            final String[] oldValues = oldVal.split(Pattern.quote(DELIMITER));

            if (Long.parseLong(oldValues[0]) + 1 < System.currentTimeMillis()) {
                return true;
            }
        }
        return success;
    }


    /**
     * @param lockKey
     * @param value
     */
    public void unlock(String lockKey, String value) {
        unlock(lockKey, value, 0, TimeUnit.MILLISECONDS);
    }


    /**
     * 延迟unlock
     *
     * @param lockKey
     * @param uuid      最好是唯一键的
     * @param delayTime
     * @param unit
     */
    public void unlock(final String lockKey, final String uuid, long delayTime, TimeUnit unit) {
        if (StringUtils.isBlank(lockKey)) {
            return;
        }

        if (delayTime <= 0) {
            doUnlock(lockKey, uuid);
        } else {
            EXECUTOR_SERVICE.schedule(() -> doUnlock(lockKey, uuid), delayTime, unit);
        }
    }


    /**
     * 执行unlock
     *
     * @param lockKey
     * @param uuid    最好是唯一键的
     */
    public void doUnlock(final String lockKey, final String uuid) {
        String val = stringRedisTemplate.opsForValue().get(lockKey);
        final String[] values = val.split(Pattern.quote(DELIMITER));

        if (values.length <= 0) {
            return;
        }

        if (uuid.equals(values[1])) {
            stringRedisTemplate.delete(lockKey);
        }
    }
}
