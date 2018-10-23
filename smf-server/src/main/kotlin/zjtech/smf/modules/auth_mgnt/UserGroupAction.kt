package zjtech.smf.modules.auth_mgnt

import mu.KLogging
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import zjtech.smf.common.constants.UriConst.Companion.URI_USER_GROUP
import zjtech.smf.modules.global.action.CrudMappingAction
import zjtech.smf.modules.global.domain.UserGroupEntity
import zjtech.smf.modules.global.service.ICrudService

/**
 * UserGroupEntity Action, maintain users in DB
 *
 */
@RestController
@RequestMapping(URI_USER_GROUP)
class UserGroupAction : CrudMappingAction<UserGroupEntity, String>() {

    /**
     * Associated Service
     */
    @Autowired
    lateinit var userGroupService: UserGroupService

    companion object : KLogging()

    /**
     * Return the associated service
     */
    override fun associatedService(): ICrudService<UserGroupEntity, String> = userGroupService

    /**
     * Return the logger instance
     */
    override fun getLogger(): Logger = logger
}