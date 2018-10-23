package zjtech.smf.common.result

import java.util.*

/**
 * SMF Result
 */
class SmfResult<E> : ISmfResult<E> {
    var fieldError = mutableMapOf<String, MutableList<String>>()
        private set

    var globalError = mutableMapOf<String, MutableList<String>>()
        private set

    override var ok: Boolean = true

    override var attachedObj: E? = null

    constructor()

    constructor(ok: Boolean) {
        this.ok = ok
    }

    constructor(attachedObj: E) {
        this.attachedObj = attachedObj
    }

    constructor(ok: Boolean, attachedObj: E) {
        this.ok = ok
        this.attachedObj = attachedObj
    }

    override fun addFieldError(fieldName: String, errorCode: String): ISmfResult<E> {
        addError(fieldError, fieldName, errorCode)
        return this
    }

    override fun addGlobalError(key: String, errorCode: String): ISmfResult<E> {
        addError(globalError, key, errorCode)
        return this;
    }

    override fun addGlobalError(key: String, throwable: Throwable): ISmfResult<E> {
        addError(globalError, key, throwable.message!!)
        return this;
    }

    private fun addError(map: MutableMap<String, MutableList<String>>, key: String, errorCode: String) {
        var list = map.get(key)
        Optional.ofNullable(list).ifPresent({ it -> it.add(errorCode) })
        if (list == null) {
            list = mutableListOf(errorCode)
            map.put(key, list)
        } else {
            list.add(errorCode)
        }
    }

}