/*
 * Copyright (c) 2015. ZJTech Inc. All Rights Reserved.
 */

package zjtech.smf.common.result

import java.util.HashMap

/**
 * SMF的结果类接口
 */
interface ISmfResult<E> {
    /**
     * 添加字段错误的信息
     * @fieldName  字段名，格式为"对象.字段名"
     * @errorCode  错误码信息
     * @return void
     */
    fun addFieldError(fieldName: String, errorCode: String): ISmfResult<E>

    /**
     * Getter and setter for attached object for GUI
     */
    var attachedObj: E?

    /**
     * 当前请求是否已成功处理
     */
    var ok: Boolean

    /**
     * 添加一条全局错误信息
     */
    fun addGlobalError(key: String, errorCode: String): ISmfResult<E>

    /**
     *  添加一条全局错误信息,同时附带上异常的相关信息
     */
    fun addGlobalError(key: String, throwable: Throwable): ISmfResult<E>
}
