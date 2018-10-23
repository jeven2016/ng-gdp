package zjtech.smf.modules.auth_mgnt

import mu.KLogging
import org.apache.shiro.authc.UsernamePasswordToken
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import zjtech.smf.common.constants.UriConst.Companion.URI_USER
import zjtech.smf.common.constants.UriConst.Companion.URI_USER_LOGIN
import zjtech.smf.common.constants.UriConst.Companion.URI_USER_LOGOUT
import zjtech.smf.common.result.ISmfResult
import zjtech.smf.modules.global.action.CrudMappingAction
import zjtech.smf.modules.global.domain.UserEntity
import zjtech.smf.modules.global.service.ICrudService

/**
 * UserEntity Action, maintain users in DB
 *
 */
@RestController
@RequestMapping(URI_USER)
class UserAction : CrudMappingAction<UserEntity, String>() {

    /**
     * Associated Service
     */
    @Autowired
    lateinit var userService: UserService

    companion object : KLogging()

    /**
     * Return the associated service
     */
    override fun associatedService(): ICrudService<UserEntity, String> = userService

    /**
     * Return the logger instance
     */
    override fun getLogger(): Logger = logger

    @PostMapping(URI_USER_LOGIN)
    fun login(@RequestParam("name") name: String,
              @RequestParam("password") password: String): ResponseEntity<Any> {
        val subject = getSubject()
        subject.login(UsernamePasswordToken(name, password))
        return ResponseEntity(HttpStatus.OK)
    }

    @PostMapping(URI_USER_LOGOUT)
    fun logout(): ResponseEntity<Any> {
        val subject = getSubject()
        subject.logout()
        return ResponseEntity(HttpStatus.OK)
    }
}