package zjtech.smf.modules.auth_mgnt

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import zjtech.smf.modules.global.domain.RoleEntity
import zjtech.smf.modules.global.domain.UserEntity
import zjtech.smf.modules.global.domain.UserGroupEntity

/**
 * authority management related repositories
 */
interface UserGroupRep : MongoRepository<UserGroupEntity, String> {}

interface UserRep : MongoRepository<UserEntity, String> {}

interface RoleRep : MongoRepository<RoleEntity, String> {}
