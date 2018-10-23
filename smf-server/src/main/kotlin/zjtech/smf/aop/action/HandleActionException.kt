package zjtech.smf.aop.action

import com.fasterxml.jackson.databind.ObjectMapper
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import zjtech.smf.common.constants.ACTION_EXCEPTION
import zjtech.smf.common.constants.INTERNAL_EXCEPTION
import zjtech.smf.common.constants.UNSERIALIZED_CLASSES
import zjtech.smf.common.result.SmfResult
import zjtech.smf.modules.global.action.CrudMappingAction
import javax.annotation.Resource


/**
 * 允许对用户进行操作日志的记录
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class HandleActionException(
        /**
         * The returned http status code while exception occurs
         */
        val httpStatusCode: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,

        /**
         * The error message should be inflected in log while exception occurs
         */
        val errorMessage: String
)

@Component
@Aspect
class ActionExceptionHandler {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger((CrudMappingAction::class.java))
    }

    fun getLogger(): Logger {
        return ActionExceptionHandler.logger
    }

    @Autowired
    lateinit var objMapper: ObjectMapper

    @Resource(name = UNSERIALIZED_CLASSES)
    lateinit var unserializedClasses: List<Class<Any>>

    /**
     * 拦截BaseAction及其子类下的public方法，并且该方法的返回类型是ResponseEntity，同时该方法使用了@HandleActionException注解
     * 如果被拦截的方法中抛出异常，则构造一个默认的ResponseEntity返回
     */
    @Around(ACTION_EXCEPTION)
    fun catchException(joinPoint: ProceedingJoinPoint): Any {
        try {
            return joinPoint.proceed(joinPoint.args)
        } catch (throwable: Throwable) {
            return handleException(throwable, joinPoint)
        }
    }

    private fun handleException(throwable: Throwable, joinPoint: ProceedingJoinPoint): Any {
        //获取对应注解类的的信息
        val args = joinPoint.args
        val methods = joinPoint.target.javaClass.methods

        val currentMethodName = joinPoint.signature.name

        val correspondingMethod = methods.filter {
            it.name.equals(currentMethodName) && it.parameterTypes.size == args.size
        }.first()

        val annotation = correspondingMethod!!.getAnnotation(HandleActionException::class.java)

        var filteredArgs = discardUnsupportedParams(args);
        val argsJsonInfo = objMapper.writeValueAsString(filteredArgs)

        val msgBuilder = StringBuilder(annotation.errorMessage).append(" (").append(argsJsonInfo).append(")")
        val httpStatusCode = annotation.httpStatusCode

        //log the exception
        getLogger().warn(msgBuilder.toString(), throwable)

        val result = SmfResult<Any>(false).addGlobalError("global", INTERNAL_EXCEPTION)

        return ResponseEntity(result, httpStatusCode)
    }

    private fun discardUnsupportedParams(args: Array<*>): List<Any?> {
        return args.filter { arg ->
            arg == null || !unserializedClasses.any {
                it.isAssignableFrom(arg.javaClass)
            }
        }.toList()
    }

}