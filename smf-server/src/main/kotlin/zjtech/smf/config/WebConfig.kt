package zjtech.smf.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.validation.BindingResult
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import zjtech.smf.common.constants.UNSERIALIZED_CLASSES


/**
 *  Spring MVC config class
 */
@Configuration
class WebConfig : WebMvcConfigurerAdapter() {
    @Bean
    fun objectMapper(): ObjectMapper {
        val mapper = ObjectMapper()
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
//        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true)
        return mapper
    }

    /**
     * Jackson 只会对public属性，或有getter, setter的属性进行序列号/反序列化。当action里面的参数是BindingResult及其某个子类时，
     * 将无法进行反序列化，因此会抛出异常。因此，需要将此类对象排除。
     * @see ActionExceptionHandler
     */
    @Bean(UNSERIALIZED_CLASSES)
    fun getUnserializedClasses(): List<Class<*>> = listOf(BindingResult::class.java)
}
