package zjtech.smf.config

import org.springframework.boot.context.properties.ConfigurationProperties
import java.util.*

@ConfigurationProperties(prefix = "shiro")
class ShiroProperites {
    /**
     * filter chain
     */

    var filterChainDefinitions: List<Map<String, String>> = mutableListOf(LinkedHashMap())


}