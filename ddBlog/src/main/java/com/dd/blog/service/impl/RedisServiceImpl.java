package com.dd.blog.service.impl;

import com.dd.blog.service.RedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Redis操作
 * @author DD
 * @date 2022/4/5 10:53
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Set<Object> getSet(String key) {
        return redisTemplate.boundSetOps(key).members();
    }

    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, Object value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    @Override
    public void zIncr(String articleViewsCount, Integer articleId, double score) {
        redisTemplate.opsForZSet().incrementScore(articleViewsCount, articleId, score);
    }

    @Override
    public Double zScore(String key, Integer value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    @Override
    public Object hGet(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    @Override
    public Boolean sIsMember(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    @Override
    public Long hIncr(String key, String hashKey, long delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    @Override
    public Long incr(String key, long delta) {
//        System.out.println("加一！！！！！！！！！！！！！！！！！！！！！");
        return redisTemplate.opsForValue().increment(key, delta);
    }

    @Override
    public Long sAdd(String key, Object... value) {
        return redisTemplate.opsForSet().add(key, value);
    }

    @Override
    public Long incrExpire(String key, long seconds) {
        Long count = redisTemplate.opsForValue().increment(key, 1);
        if (count != null && count == 1) {
            redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
        }
        return count;
    }

    @Override
    public Map hashGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    @Override
    public Long setRemove(String key, Object... value) {
        return redisTemplate.opsForSet().remove(key, value);
    }

    @Override
    public Long hashDecr(String key, String hashKey, Long delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, -delta);
    }

    @Override
    public Long setAdd(String key, Object... value) {
        return redisTemplate.opsForSet().add(key, value);
    }

    @Override
    public Long hashIncr(String key, String hashKey, Long delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    @Override
    public Map<Object, Double> zSetReversRangeWithScore(String key, int start, int end) {
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end)
                .stream()
                .collect(Collectors.toMap(ZSetOperations.TypedTuple::getValue, ZSetOperations.TypedTuple::getScore));
    }

    @Override
    public Long setSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    @Override
    public Boolean del(String key) {
        return redisTemplate.delete(key);
    }

    @Override
    public Map<Object, Double> zSetAllScore(String key) {
        return Objects.requireNonNull(redisTemplate.opsForZSet().rangeWithScores(key, 0, -1))
                .stream()
                .collect(Collectors.toMap(ZSetOperations.TypedTuple::getValue, ZSetOperations.TypedTuple::getScore));
    }

}
