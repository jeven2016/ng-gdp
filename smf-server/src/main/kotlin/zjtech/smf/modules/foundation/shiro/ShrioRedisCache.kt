package zjtech.smf.modules.foundation.shiro

import org.apache.shiro.cache.Cache
import org.apache.shiro.cache.CacheException
import org.springframework.data.redis.core.RedisTemplate
import java.util.Optional

class ShrioRedisCache<K, V>(private val shiroRedisTemplate: RedisTemplate<ByteArray, V>) : Cache<K, V> {
    var prefix = "shiro_redis:"

    constructor(shiroRedisTemplate: RedisTemplate<ByteArray, V>, prefix: String) : this(shiroRedisTemplate) {
        this.prefix = prefix
    }

    @Throws(CacheException::class)
    override fun get(key: K?): V? {
        return if (key == null) {
            null
        } else {
            val bkey = getByteKey(key)
            shiroRedisTemplate.opsForValue().get(bkey)
        }
    }

    @Throws(CacheException::class)
    override fun put(key: K?, value: V?): V? {
        Optional.ofNullable(key).ifPresent {
            shiroRedisTemplate.opsForValue().set(getByteKey(it), value)
        }
        return value
    }

    @Throws(CacheException::class)
    override fun remove(key: K?): V? {
        return if (key == null) {
            null
        } else {
            val bkey = getByteKey(key)
            val vo = shiroRedisTemplate.opsForValue()
            val value = vo.get(bkey)
            shiroRedisTemplate.delete(bkey)
            value
        }
    }

    @Throws(CacheException::class)
    override fun clear() {
        shiroRedisTemplate.connectionFactory.connection.flushDb()
    }

    override fun size(): Int {
        val len = shiroRedisTemplate.connectionFactory.connection.dbSize()
        return len.toInt()
    }

    override fun keys(): Set<K> {
        val bkey = (prefix + "*").toByteArray()
        val set = shiroRedisTemplate.keys(bkey)

        return set.map { it as K }.toHashSet()
    }

    override fun values(): Collection<V> {
        return keys().map({
            val key = getByteKey(it)
            shiroRedisTemplate.opsForValue().get(key)
        }).toList()

    }

    private fun getByteKey(key: K): ByteArray {
        return if (key is String) {
            (this.prefix + key).toByteArray()
        } else {
            key.toString().toByteArray()
        }
    }
}