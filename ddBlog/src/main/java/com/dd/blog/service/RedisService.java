package com.dd.blog.service;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.Map;
import java.util.Set;

/**
 * redis操作
 * @author DD
 * @date 2022/4/5 10:52
 */

public interface RedisService {

    /**
     * 获取属性
     * @param key
     * @return
     */
    Object get(String key);

    void set(String key, Object value);

    void set(String key, Object value, long time);

    void zIncr(String articleViewsCount, Integer articleId, double score);

    Double zScore(String key, Integer value);

    /**
     * 获取hash结构中的属性
     * @param key
     * @param hashKey
     * @return
     */
    Object hGet(String key, String hashKey);

    /**
     * 判断是不是set里面的属性
     * @param key
     * @param value
     * @return
     */
    Boolean sIsMember(String key, Object value);

    /**
     * hash中递增
     * @param key
     * @param hashKey
     * @param delta
     */
    Long hIncr(String key, String hashKey, long delta);

    /**
     * 按delta递增
     * @param key
     * @param delta
     */
    Long incr(String key, long delta);

    /**
     * 向Set中添加属性
     * @param key
     * @param value
     */
    Long sAdd(String key, Object... value);

    /**
     * 获取相应key的set
     * @param key
     * @return
     */
    Set<Object> getSet(String key);

    /**
     * 增加过期时间
     * @param key
     * @param seconds
     * @return
     */
    Long incrExpire(String key, long seconds);

    /**
     * 获取key下所有hash值
     * @param key
     * @return
     */
    Map<String, Object> hashGetAll(String key);

    /**
     * set减少相应元素
     * @param key
     * @param value
     * @return
     */
    Long setRemove(String key, Object... value);

    /**
     * 减少相应key的hash里的键值相应的值
     * @param key
     * @param hashKey
     * @param delta
     * @return
     */
    Long hashDecr(String key, String hashKey, Long delta);

    /**
     * set增加元素
     * @param key
     * @param value
     * @return
     */
    Long setAdd(String key, Object... value);

    /**
     * 增加相应key的hash里的键值相应的值
     * @param key
     * @param hashKey
     * @param delta
     * @return
     */
    Long hashIncr(String key, String hashKey, Long delta);

    /**
     * zset根据分数排名获取指定元素信息
     * @param key
     * @param start
     * @param end
     * @return
     */
    Map<Object, Double> zSetReversRangeWithScore(String key, int start, int end);

    /**
     * 获取相应key的set的长度
     * @param key
     * @return
     */
    Long setSize(String key);

    Boolean del(String key);

    /**
     * 获取zSet所有分数
     * @param key
     * @return
     */
    Map<Object, Double> zSetAllScore(String key);
}
