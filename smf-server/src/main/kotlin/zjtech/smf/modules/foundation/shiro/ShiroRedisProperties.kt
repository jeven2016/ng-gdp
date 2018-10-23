package zjtech.smf.modules.foundation.shiro

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

/**
 * Shiro Redis Cache配置

 * @author Frank.Zeng
 */
@Configuration
@ConfigurationProperties(prefix = "shiro.redis")
class ShiroRedisProperties {

    val database = 0

    val hostName = "10.113.12.77"

    val password: String? = null

    val port = 5803

    val timeout = 0

    val expire = 1800
}
