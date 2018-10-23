package zjtech

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import zjtech.smf.config.ShiroProperites
import zjtech.smf.modules.foundation.shiro.ShiroRedisProperties
import org.springframework.boot.Banner


//@Import(*arrayOf(MongodbConfig::class, WebConfig::class))
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableMongoRepositories//Enable 相关的注解要在root config类中添加，spring会以根目录进行扫描同级父目录下的类。如将此注解放置再MongodbConfig中，无法自动扫描到Repository
@SpringBootApplication(scanBasePackages = arrayOf("zjtech"))//可以设定扫描路径，这样可以不需显示地Import其他@configuration. You should only ever add one @EnableAutoConfiguration annotation. We generally recommend that you add it to your primary @Configuration class.
@EnableRedisHttpSession//Enable spring redis session
@EnableWebMvc
@EnableConfigurationProperties(*arrayOf(ShiroRedisProperties::class))
class WebApp {

    /* companion object {
         @JvmStatic fun zjtech.main(args: Array<String>) {
             SpringApplication.run(zjtech.WebApp::class.java, *args)
         }
     }*/
}

//Main console
fun main(args: Array<String>) {
    //1. way one
    /* If you don’t want to use the restart feature you can disable it using
     the spring.devtools.restart.enabled property. In most cases you can set this
     in your application.properties*/
//    System.setProperty("spring.devtools.restart.enabled", "false");
//    SpringApplication.run(WebApp::class.java, *args)

    //2. way two
//    If the SpringApplication defaults aren’t to your taste you can instead create a local instance and customize it. For example, to turn off the banner you would write:
    val app = SpringApplication(zjtech.WebApp::class.java)
    app.addListeners()
//    app.setBannerMode(Banner.Mode.OFF)
    app.run(*args)
}
