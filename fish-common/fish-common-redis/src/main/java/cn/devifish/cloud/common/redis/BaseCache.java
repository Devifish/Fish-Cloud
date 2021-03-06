package cn.devifish.cloud.common.redis;

import cn.devifish.cloud.common.core.constant.CommonConstant;
import cn.devifish.cloud.common.core.exception.UtilException;
import cn.devifish.cloud.common.redis.constant.RedisConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.time.Duration;
import java.util.Objects;
import java.util.function.Function;

/**
 * BaseCache
 * 公共缓存基础类
 *
 * @author Devifish
 * @date 2020/6/30 15:47
 */
public abstract class BaseCache<V, ID extends Serializable> implements InitializingBean {

    @Autowired
    protected RedisTemplate<String, Object> redisTemplate;

    @Value("${spring.application.name}")
    private String applicationName;

    protected ValueOperations<String, Object> valueOperations;
    private String baseCacheKey;
    private String cacheKeyPrefix;

    @Override
    public void afterPropertiesSet() {
        valueOperations = redisTemplate.opsForValue();
    }

    /**
     * 默认获取参数实现类名称
     * 如不使用可重写该方法
     *
     * @return 基本KEY名（不包含前后缀及ID）
     */
    @SuppressWarnings("unchecked")
    public String getBaseCacheKey() {
        if (baseCacheKey == null) {
            var genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
            var actualType = (Class<V>) genericSuperclass.getActualTypeArguments()[0];
            baseCacheKey = StringUtils.lowerCase(actualType.getSimpleName());
        }
        return baseCacheKey;
    }

    /**
     * 默认获取当前服务名称
     * 如不使用可重写该方法
     *
     * @return 缓存前缀
     */
    public String getCacheKeyPrefix() {
        if (cacheKeyPrefix == null) {
            if (!StringUtils.endsWithIgnoreCase(applicationName, CommonConstant.APPLICATION_NAME_SUFFIX))
                throw new UtilException("服务名称不符合规范, 生成cacheKeyPrefix失败");

            //处理服务名称分隔符
            var redisKeyPrefix = StringUtils.lowerCase(applicationName);
            redisKeyPrefix = redisKeyPrefix.replaceAll(CommonConstant.APPLICATION_NAME_SEPARATOR, RedisConstant.KEY_NAME_SEPARATOR);
            this.cacheKeyPrefix = redisKeyPrefix;
        }
        return cacheKeyPrefix;
    }

    /**
     * 缓存Key后缀
     * 如不使用可重写该方法
     *
     * @return 缓存前缀
     */
    public String getCacheKeySuffix() {
        return null;
    }

    /**
     * 获取缓存的超时时间
     * 可重写该方法，自定义缓存时间
     *
     * @return 缓存时间
     */
    public Duration getTimeout() {
        return RedisConstant.DEFAULT_CACHE_TIMEOUT;
    }

    /**
     * 根据key和主键id值生成缓存 key
     * 带前后缀
     *
     * @param key Redis Key
     * @param id 主键ID值
     * @param prefix 前缀
     * @param suffix 后缀
     * @return key
     */
    public String generatorCacheKey(String key, Serializable id, CharSequence prefix, CharSequence suffix) {
        Objects.requireNonNull(key);
        var builder = new StringBuilder();

        //拼接Key Prefix
        if (StringUtils.isNotEmpty(prefix))
            builder.append(prefix).append(RedisConstant.KEY_SEPARATOR);

        //拼接Key Name
        builder.append(key);

        //拼接Key Suffix
        if (StringUtils.isNotEmpty(suffix))
            builder.append(RedisConstant.KEY_SEPARATOR).append(suffix);

        //拼接Key ID
        String keyId;
        if (id != null && StringUtils.isNotEmpty(keyId = String.valueOf(id)))
            builder.append(RedisConstant.KEY_SEPARATOR).append(keyId);

        return builder.toString();
    }

    /**
     * 生成缓存KEY
     *
     * @param id ID
     * @return Key
     */
    public String generatorCacheKey(ID id) {
        return generatorCacheKey(getBaseCacheKey(), id, getCacheKeyPrefix(), getCacheKeySuffix());
    }

    /**
     * 生成缓存KEY
     *
     * @param id ID
     * @return Key
     */
    public String generatorCacheKey(ID id, String suffix) {
        return generatorCacheKey(getBaseCacheKey(), id, getCacheKeyPrefix(), suffix);
    }

    /**
     * 设置缓存对象
     *
     * @param id ID
     * @param value Value
     */
    public void set(ID id, V value) {
        valueOperations.set(generatorCacheKey(id), value, getTimeout());
    }

    /**
     * 设置缓存对象
     *
     * @param id ID
     * @param value Value
     * @param suffix 后缀
     */
    public void set(ID id, String suffix, V value) {
        valueOperations.set(generatorCacheKey(id, suffix), value, getTimeout());
    }

    /**
     * 获取缓存数据
     *
     * @param id ID
     * @return Value
     */
    @SuppressWarnings("unchecked")
    public V get(ID id) {
        return (V) valueOperations.get(generatorCacheKey(id));
    }

    /**
     * 获取缓存数据
     *
     * @param id ID
     * @param suffix 后缀
     * @return Value
     */
    @SuppressWarnings("unchecked")
    public V get(ID id, String suffix) {
        return (V) valueOperations.get(generatorCacheKey(id, suffix));
    }

    public V getIfAbsent(ID id, Function<ID, ? extends V> mappingFunction) {
        var value = get(id);
        if (value == null && mappingFunction != null) {
            value = mappingFunction.apply(id);
            if (value != null) set(id, value);
        }
        return value;
    }

    public V getIfAbsent(ID id, String suffix, Function<ID, ? extends V> mappingFunction) {
        var value = get(id, suffix);
        if (value == null && mappingFunction != null) {
            value = mappingFunction.apply(id);
            if (value != null) set(id, suffix, value);
        }
        return value;
    }

    /**
     * 删除缓存数据
     *
     * @param id ID
     */
    public void delete(ID id) {
        redisTemplate.delete(generatorCacheKey(id));
    }

    /**
     * 删除缓存数据
     *
     * @param id ID
     * @param suffix 后缀
     */
    public void delete(ID id, String suffix) {
        redisTemplate.delete(generatorCacheKey(id, suffix));
    }

    /**
     * 删除全部缓存数据
     * （包含带ClientID）
     */
    public void deleteAll() {
        var pattern = generatorCacheKey(getBaseCacheKey(), "*", getCacheKeyPrefix(), "*");
        var keys = redisTemplate.keys(pattern);
        if (!CollectionUtils.isEmpty(keys)) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 获取缓存过期时间
     *
     * @param id ID
     * @return 过期时间
     */
    public Long getExpire(ID id) {
        return redisTemplate.getExpire(generatorCacheKey(id));
    }

    /**
     * 获取缓存过期时间
     *
     * @param id ID
     * @param suffix 后缀
     * @return 过期时间
     */
    public Long getExpire(ID id, String suffix) {
        return redisTemplate.getExpire(generatorCacheKey(id, suffix));
    }

}
