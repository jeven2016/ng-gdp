package zjtech.smf.modules.auth_mgnt

import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.UsernamePasswordToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import zjtech.smf.common.constants.UriConst.Companion.URI_LOGIN
import zjtech.smf.config.SmfConfig
import zjtech.smf.modules.global.action.BaseAction

@RestController
@RequestMapping(URI_LOGIN)
class LoginAction : BaseAction() {

    /**
     * Associated Service
     */
    @Autowired
    lateinit var roleService: RoleService

    @Autowired
    lateinit var userService: UserService

    /* @Autowired()
     @Qualifier("securityManager")
     lateinit var securityManager: SecurityManager
 */
    @GetMapping("/login")
    fun login2(@RequestParam("name") name: String, @RequestParam("password") password: String) {
        var sub = SecurityUtils.getSubject()
        try {
            sub.login(UsernamePasswordToken(name, password))
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        var session = sub.getSession(true)
        session.setAttribute("who", mutableListOf("It's me"))
        println(sub.isAuthenticated())


    }


    @GetMapping("/get")
    fun login(): ResponseEntity<*>? {
        var subject = SecurityUtils.getSubject()
        var session = getSession()
        if (session == null) {
//            subject.login(UsernamePasswordToken("root", "123"))
            session = getValidSession()

            var user = userService.findByName("user2")
            session.setAttribute("currentUser", user)
        }
        session?.setAttribute("hello", "hello")
        return null
    }

    @GetMapping("/check")
    fun check(): Boolean {
        var sub = SecurityUtils.getSubject()
        var isAuth = sub.isAuthenticated
        if (isAuth) {
            var shiroSession = sub.session
        }
        return getSession() == null
    }


    @GetMapping("/clear")
            //    @RequiresAuthentication
//    @RequiresPermissions("clear")
    fun clear() {
        var session = getSession()
        var user = session?.getAttribute("currentUser")
        session!!.invalidate()
    }

    @Autowired
    lateinit var smfconfig: SmfConfig

    @GetMapping("test")
    fun test() {
        println(smfconfig.name)
    }


}