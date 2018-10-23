package zjtech.smf.config

import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.credential.HashedCredentialsMatcher
import org.apache.shiro.crypto.SecureRandomNumberGenerator
import org.apache.shiro.crypto.hash.Md5Hash
import org.apache.shiro.realm.AuthorizingRealm
import org.apache.shiro.spring.LifecycleBeanPostProcessor
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
import org.apache.shiro.spring.web.ShiroFilterFactoryBean
import org.apache.shiro.web.mgt.DefaultWebSecurityManager
import org.apache.shiro.web.session.mgt.ServletContainerSessionManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.web.filter.DelegatingFilterProxy
import zjtech.smf.common.constants.BeanNameConst.Companion.LIFECYCLE_BEAN_POSTPR_OCESSOR
import zjtech.smf.common.constants.BeanNameConst.Companion.RANDOMN_UMBER_GENERATOR
import zjtech.smf.common.constants.BeanNameConst.Companion.REALM
import zjtech.smf.common.constants.BeanNameConst.Companion.SECURITY_MANAGER
import zjtech.smf.common.constants.HashConst.Companion.HASH_ITERATIONS
import zjtech.smf.modules.foundation.KryoRedisSerializer
import zjtech.smf.modules.foundation.shiro.MongoRealm
import zjtech.smf.modules.foundation.shiro.ShiroRestFilter
import zjtech.smf.modules.foundation.shiro.ShrioRedisCacheManager
import zjtech.smf.modules.foundation.shiro.URLPermissionsFilter
import javax.servlet.DispatcherType
import javax.servlet.Filter


@Configuration
open class ShiroConfig {

    /*
     * Redis connection factory defined in RedisConfig
     */
    private lateinit var connectionFactory: RedisConnectionFactory

    constructor()

    @Autowired
    constructor(factory: RedisConnectionFactory) {
        connectionFactory = factory
    }

    /**
     * Mongo Realm
     */
    @Bean(name = arrayOf(REALM))
    @DependsOn(value = *arrayOf(LIFECYCLE_BEAN_POSTPR_OCESSOR, "shrioRedisCacheManager"))
    fun realm(): AuthorizingRealm {
        var hashMatcher = HashedCredentialsMatcher(Md5Hash.ALGORITHM_NAME) //using md5 hash

        //we create a random salt and perform 1024 hash iterations for strong security
        hashMatcher.hashIterations = HASH_ITERATIONS;

        var realm = MongoRealm()
        realm.cacheManager = redisCacheManager()
        realm.isCachingEnabled = true
//        realm.isAuthenticationCachingEnabled = true   //关闭Authentication的缓存功能，因为byteSource类无法被序列号，会出异常
        realm.isAuthorizationCachingEnabled = true

        realm.credentialsMatcher = hashMatcher
        return realm
    }

    /**
     * Post Processor
     */
    @Bean
    fun lifecycleBeanPostProcessor() = LifecycleBeanPostProcessor()

    /**
     * Security Manager
     */
    @Bean(name = arrayOf(SECURITY_MANAGER))
    fun securityManager(): org.apache.shiro.mgt.SecurityManager {
        val securityManager = DefaultWebSecurityManager()
        securityManager.setRealm(realm())

        //enable/disable shiro session manager function because we use spring session with redis to maintain http session
//        var subjectDao = securityManager.subjectDAO as DefaultSubjectDAO
//        subjectDao.setSessionStorageEvaluator { false }

        // for session
        securityManager.sessionManager = sessionManager()

        // for cache
        securityManager.cacheManager = redisCacheManager()

        //attach to local thread
        SecurityUtils.setSecurityManager(securityManager)

        return securityManager
    }

    /**
     * Random Generator
     */
    @Bean(name = arrayOf(RANDOMN_UMBER_GENERATOR))
    fun randomNumberGenerator() = SecureRandomNumberGenerator()

    /**
     *  Enable shiro annotations
     */
    @Bean
    fun authorizationAttributeSourceAdvisor(): AuthorizationAttributeSourceAdvisor {
        val advisor = AuthorizationAttributeSourceAdvisor()
        advisor.securityManager = securityManager()
        return advisor
    }


    /**
     * FilterRegistrationBean

     * @return
     */
    @Bean
    fun filterRegistrationBean(): FilterRegistrationBean {
        val filterRegistration = FilterRegistrationBean()
        filterRegistration.filter = DelegatingFilterProxy("shiroFilter")
        filterRegistration.isEnabled = true
        filterRegistration.addUrlPatterns("/*")
        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST)
        return filterRegistration
    }

    @Bean
    fun shiroRestFilter(): ShiroRestFilter = ShiroRestFilter()

    @Bean(name = arrayOf("shiroFilter"))
    fun shiroFilter(): ShiroFilterFactoryBean {
        val bean = ShiroFilterFactoryBean()
        bean.loginUrl = null

        bean.securityManager = securityManager()

        var filters = mutableMapOf("rest" to ShiroRestFilter())

        var filterChains = mutableMapOf(
                "/login" to "anon",
                "/logout" to "anon",
                "/**" to "rest"
        )
        bean.filters = filters as Map<String, Filter>?
        bean.filterChainDefinitionMap = filterChains
        return bean
    }

    /**
     * leverage http session to manage http session
     * @return
     */
    @Bean(name = arrayOf("sessionManager"))
    fun sessionManager(): ServletContainerSessionManager {
        val sessionManager = ServletContainerSessionManager()
        return sessionManager
    }

    /**
     * Shiro ACL filter to handle all rest request
     */
    @Bean
    fun urlPermissionsFilter(): URLPermissionsFilter {
        return URLPermissionsFilter()
    }

    @Bean(name = arrayOf("shrioRedisCacheManager"))
    @DependsOn(value = "shiroRedisTemplate")
    fun redisCacheManager(): ShrioRedisCacheManager {
        val cacheManager = ShrioRedisCacheManager(shiroRedisTemplate())
        cacheManager.createCache("shiro_redis:")
        return cacheManager
    }

    @Bean(name = arrayOf("shiroRedisTemplate"))
    fun shiroRedisTemplate(): RedisTemplate<ByteArray, ByteArray> {
        val template = RedisTemplate<ByteArray, ByteArray>()
        template.connectionFactory = connectionFactory
        return template
    }


    /**
     * 设置redisTemplate的存储格式（在此与Session没有什么关系）

     * @return
     */
    @Bean
            //    @Profile("test")
    fun sessionRedisSerializer(): RedisSerializer<*> {
//        return Jackson2JsonRedisSerializer(Any::class.java)
        return KryoRedisSerializer<Any>()
    }
}

