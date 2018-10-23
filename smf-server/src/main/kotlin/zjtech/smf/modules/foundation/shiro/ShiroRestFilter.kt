package zjtech.smf.modules.foundation.shiro

import org.apache.shiro.web.filter.AccessControlFilter
import org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse

class ShiroRestFilter : AccessControlFilter() {
    var filter = HttpMethodPermissionFilter()

    override fun isAccessAllowed(req: ServletRequest?, resp: ServletResponse?, p2: Any?): Boolean {
        return false;
    }

    override fun onAccessDenied(req: ServletRequest?, resp: ServletResponse?): Boolean {
        println("denied request")

        return false
    }

}