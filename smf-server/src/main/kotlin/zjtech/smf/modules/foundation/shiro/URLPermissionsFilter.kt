package zjtech.smf.modules.foundation.shiro

import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.io.IOException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

@Component
class URLPermissionsFilter : PermissionsAuthorizationFilter() {


    @Throws(IOException::class)
    override fun isAccessAllowed(request: ServletRequest, response: ServletResponse, mappedValue: Any?): Boolean {
      return true;
    }

    /**
     * 获取当前URL+Parameter

     * @author lance
     * *
     * @since 2014年12月18日 下午3:09:26
     * *
     * @param request 拦截请求request
     * *
     * @return 返回完整URL
     */
    private fun getRequestUrl(request: ServletRequest): String {
        val req = request as HttpServletRequest
        var queryString = req.queryString

        queryString = if (StringUtils.isEmpty(queryString)) "" else "?" + queryString
        return req.requestURI + queryString
    }
}
