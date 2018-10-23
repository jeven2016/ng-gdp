package zjtech.smf.common.exceptions

import zjtech.smf.common.result.ISmfResult

/**
 * Exception, kotlin doesn't have checked exception
 */
class SmfException : Throwable {
    var result: ISmfResult<*>? = null
        private set

    constructor() {

    }

    constructor(result: ISmfResult<*>) {
        this.result = result
    }

    constructor(msg: String) : super(msg)

    constructor(msg: String, result: ISmfResult<*>) : super(msg) {
        this.result = result
    }

}
