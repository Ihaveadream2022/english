package com.english.service;

import org.springframework.core.ParameterizedTypeReference;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

public interface RedisService
{
    public <T> void setValue(final String key, final T value);

    public <T> void setValue(final String key, final T value, final Integer timeout, final TimeUnit timeUnit);

    public <T> T getValue(final String key, ParameterizedTypeReference<T> typeReference);

    public <T> long setList(final String key, final List<T> list);

    public <T> List<T> getList(final String key);

    public boolean delete(final String key);

    public boolean delete(final Collection collection);
}
