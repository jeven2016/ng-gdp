package zjtech.smf.common.constants

/**
 * 拦截BaseAction及其子类下的public方法，并且该方法的返回类型是ResponseEntity，同时该方法使用了@HandleActionException注解
 * 如果被拦截的方法中抛出异常，则构造一个默认的ResponseEntity返回
 */
const val ACTION_EXCEPTION = "execution(public org.springframework.http.ResponseEntity zjtech.smf.modules.global.action.BaseAction+.*(..))" +
        " && @annotation(zjtech.smf.aop.action.HandleActionException)"
