package zjtech.smf.modules.auth_mgnt

import mu.KLogging
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import zjtech.smf.common.constants.UriConst.Companion.URI_ROLE
import zjtech.smf.modules.global.action.CrudMappingAction
import zjtech.smf.modules.global.domain.RoleEntity
import zjtech.smf.modules.global.service.ICrudService

/**
 * UserEntity Action, maintain users in DB
 *
 */
@RestController
@RequestMapping(URI_ROLE)
class RoleAction : CrudMappingAction<RoleEntity, String>() {

    /**
     * Associated Service
     */
    @Autowired
    lateinit var roleService: RoleService

    companion object : KLogging()

    /**
     * Return the associated service
     */
    override fun associatedService(): ICrudService<RoleEntity, String> = roleService

    /**
     * Return the logger instance
     */
    override fun getLogger(): Logger = logger
}