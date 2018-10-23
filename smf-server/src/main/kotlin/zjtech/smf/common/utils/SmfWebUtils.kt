package zjtech.smf.common.utils;

import org.springframework.web.context.WebApplicationContext
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.context.support.WebApplicationContextUtils
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import org.springframework.web.servlet.support.RequestContextUtils
import org.springframework.web.util.WebUtils
import zjtech.smf.common.constants.AttributesConstants
import zjtech.smf.common.constants.LanguageEnum
import java.util.*
import javax.servlet.ServletContext
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

/**
 * 获取web相关对象的工具类
 */
open class SmfWebUtils {

    companion object {


        /**
         * 获取ServletContext
         *
         * @return ServletContext
         */
        fun getServletContext(): ServletContext = getRequest().getServletContext()


        /**
         * 获取HttpSession
         *
         * @param allowNew 当Session不存在时，是否允许创建一个新的session
         * @return 获取HttpSession
         */
        fun getSession(allowNew: Boolean): HttpSession? = getRequest().getSession(allowNew)

        /**
         * 从HttpSession中获取储存的对象
         *
         * @param attribute 属性
         * @return 存储在HttpSession中的对象
         */
        fun getValueFromSession(attribute: String) = WebUtils.getSessionAttribute(getRequest(), attribute);

        /**
         * Get HttpServletRequest
         *
         * @return HttpServletRequest
         */
        fun getRequest(): HttpServletRequest {
            var attr: ServletRequestAttributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes;
            return attr.getRequest();
        }


        /**
         * 获取当前国际化对应的Locale对象
         */
        fun getCurrentLocale(): Locale {
            var request: HttpServletRequest = getRequest();

            var locale: Locale = WebUtils.getSessionAttribute(request, SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME) as Locale;
            if (locale == null) {
                //获取默认Locale
                locale = LanguageEnum.Chinese.localeInfo;
            }
            return locale;
        }

        /**
         * 从Session重获取Local对象
         */
        fun getLocalFromSession(): Locale {

            var locale: Locale = WebUtils.getSessionAttribute(getRequest(), SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME) as Locale;
            return locale;
        }

        /**
         * 通过Servlet，从ServletContext中获取Spring容器
         *
         * @param context ServletContext
         * @return WebApplicationContext
         */
        fun getWebAppCtxFromServletContext(context: ServletContext): WebApplicationContext {
            var webApplicationContext = context.getAttribute(AttributesConstants.WebServletContextName) as WebApplicationContext
            return webApplicationContext
        }


        /**
         * 通过全局配置加载，则通过此方式获取Spring容器
         *
         * @param context ServletContext
         * @return WebApplicationContext
         */
        fun getWebAppCtxFromGlobalContext(context: ServletContext): WebApplicationContext {
            var webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(context) as WebApplicationContext
            return webApplicationContext
        }


        /**
         * 根据语言信息切换语言(只在request范围生效)
         *
         * @param response HttpServletResponse
         */
        fun setLocaleForRequestScope(response: HttpServletResponse) {
            var req = getRequest()

            //用户未登录时切换语言将设置wei范围的国际化信息
            var localeResolver = RequestContextUtils.getLocaleResolver(req);
            if (localeResolver != null) {
                var locale: Locale = getCurrentLocale();
                localeResolver.setLocale(req, response, locale);
            }
        }

        /**
         * 判断当前请求对应的用户是否已经登录
         *
         * @return 用户是否已经登录
         */
        fun isUserLoggedIn(): Boolean {
            var request = getRequest();
            var userObj = WebUtils.getSessionAttribute(request, AttributesConstants.CURRENT_USER);
            return userObj != null
        }
    }

}
