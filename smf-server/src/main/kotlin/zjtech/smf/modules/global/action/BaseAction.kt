/*
 * Copyright (c) 2015. ZJTech Inc. All Rights Reserved.
 */

package zjtech.smf.modules.global.action

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import zjtech.smf.common.utils.AllOpen
import zjtech.smf.common.utils.SmfWebUtils
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession

@AllOpen
open class BaseAction {
    @Autowired
    lateinit var objMapper: ObjectMapper

    /**
     * 获取HttpSession

     * @return 获取HttpSession
     */
    protected fun getSession(): HttpSession? {
        return SmfWebUtils.getSession(false)
    }

    /**
     * 获取HttpRequest

     * @return HttpRequest
     */
    protected fun getRequest(): HttpServletRequest? {
        return SmfWebUtils.getRequest()
    }


    /**
     * 获取当前国际化对应的Locale对象
     */
    protected fun getCurrentLocale(): Locale? {
        return SmfWebUtils.getCurrentLocale()
    }

    /**
     * 从Session重获取Local对象
     */
    protected fun getLocaleFromSession(): Locale? {
        return SmfWebUtils.getLocalFromSession()
    }


    /**
     * 获取HttpSession, 如果没有则创建新的session

     * @return 获取HttpSession
     */
    protected fun getValidSession(): HttpSession {

        return SmfWebUtils.getSession(true)!!
    }

    /**
     * 从HttpSession中获取储存的对象

     * @param attribute 属性
     */
    protected fun getFromSessionByAttr(attribute: String): Any {
        return SmfWebUtils.getValueFromSession(attribute)
    }

    protected fun ensureValidValue(currentPage: Int?, defaultValue: Int?, maxValue: Int?): Int {
        var currentPage = currentPage
        if (currentPage == null || currentPage.toInt() < defaultValue!!.toInt()) {
            currentPage = defaultValue
        }
        if (currentPage!!.toInt() > maxValue!!.toInt()) {
            currentPage = maxValue
        }
        return currentPage
    }

    /**
     * Deserialize an object to string
     */
    protected fun <T> deserialize(value: T): String {
        return objMapper.writeValueAsString(value)
    }
}
