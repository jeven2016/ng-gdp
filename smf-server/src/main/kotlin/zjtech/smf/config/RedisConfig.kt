package zjtech.smf.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.session.ExpiringSession
import zjtech.smf.modules.foundation.KryoRedisSerializer


@Configuration
class RedisConfig {

    @Primary
    @Bean
    fun redisTemplate(connectionFactory: RedisConnectionFactory): RedisTemplate<String, ExpiringSession> {
        val template = RedisTemplate<String, ExpiringSession>()

        template.keySerializer = StringRedisSerializer()
        template.hashKeySerializer = StringRedisSerializer()

        //使用kryo序列号/反序列化对象
        template.defaultSerializer = KryoRedisSerializer<Any>()
        template.isEnableDefaultSerializer = true

        template.connectionFactory = connectionFactory
        template.afterPropertiesSet()
        return template
    }
}