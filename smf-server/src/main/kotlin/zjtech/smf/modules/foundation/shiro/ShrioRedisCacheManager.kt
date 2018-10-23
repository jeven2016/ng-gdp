package zjtech.smf.modules.foundation.shiro

import org.apache.shiro.cache.AbstractCacheManager
import org.apache.shiro.cache.Cache
import org.apache.shiro.cache.CacheException
import org.springframework.data.redis.core.RedisTemplate

class ShrioRedisCacheManager(private val shiroRedisTemplate: RedisTemplate<ByteArray, ByteArray>) : AbstractCacheManager() {

    @Throws(CacheException::class) public
    override fun createCache(name: String): Cache<ByteArray, ByteArray> {
        return ShrioRedisCache(shiroRedisTemplate, name)
    }
}
