package zjtech.smf.common.constants


/**
 * 属性名称常量
 */
interface AttributesConstants {

    /**
     * 其他业务模块使用的常量
     */
    interface UserInfo {
        companion object {
            val USER = "user"
        }
    }

    companion object {
        /**
         * Application Context 范围的系统变量
         */
        val WEB_CONTEXT_PATH = "WEB_CONTEXT_PATH"
        val DEFAULT_THEME = "DEFAULT_THEME"
        val ENABLE_VERIFYCODE_FORADMINLOGIN = "enableVerifyCodeForAdminLogin" //是否启用管理员

        /**
         * 默认的主题名称
         */
        /*通过Servlet加载的spring容器在ServletContext中关联的属性名称*/
        val WebServletContextName = "org.springframework.web.servlet.FrameworkServlet.CONTEXT.smf"


        /**
         * Request范围的变量
         */
        val DEFAULT_LANGUAGE = "lan"
        val RESULT = "result"
        val CURRENT_OPER = "currentOper"//当前操作类型
        val LIST = "list"


        /**
         * Session范围的常量
         */
        val CURRENT_THEME = "CURRENT_THEME"
        val CURRENT_USER = "CURRENT_USER"
    }

}
