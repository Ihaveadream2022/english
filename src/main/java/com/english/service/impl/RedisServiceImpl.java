package com.english.service.impl;

import com.english.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@SuppressWarnings(value = { "unchecked", "rawtypes" })
public class RedisServiceImpl implements RedisService
{
    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 写入字符值. 无过期时间.
     * @param key
     * @param value
     * @param <T>
     */
    public <T> void setValue(final String key, final T value)
    {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 写入字符值. 设置过期时间.
     * @param key
     * @param value
     * @param timeout
     * @param timeUnit
     * @param <T>
     */
    public <T> void setValue(final String key, final T value, final Integer timeout, final TimeUnit timeUnit)
    {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 读取字符值
     * @param key
     * @param typeReference
     * @return
     * @param <T>
     */
    public <T> T getValue(final String key, ParameterizedTypeReference<T> typeReference)
    {
        ValueOperations<String, T> a = redisTemplate.opsForValue();

        return a.get(key);
    }

    /**
     * 写入列表
     * @param key
     * @param list
     * @return
     * @param <T>
     */
    public <T> long setList(final String key, final List<T> list)
    {
        Long count = redisTemplate.opsForList().rightPushAll(key, list);

        return count == null? 0: count;
    }

    /**
     * 读取列表
     * @param key
     * @return
     * @param <T>
     */
    public <T> List<T> getList(final String key)
    {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 删除
     *
     * @param key
     */
    public boolean delete(final String key)
    {
        return redisTemplate.delete(key);
    }

    /**
     * 批量删除
     *
     * @param collection
     * @return
     */
    public boolean delete(final Collection collection)
    {
        return redisTemplate.delete(collection) > 0;
    }
}
