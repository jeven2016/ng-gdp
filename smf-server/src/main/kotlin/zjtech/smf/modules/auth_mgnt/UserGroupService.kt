package zjtech.smf.modules.auth_mgnt

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Service
import zjtech.smf.modules.global.domain.UserGroupEntity
import zjtech.smf.modules.global.service.CrudServiceImp

@Service
class UserGroupService(val userGroupRep: UserGroupRep) : CrudServiceImp<UserGroupEntity, String>() {
    override fun getEntityClass(): Class<UserGroupEntity> {
        return UserGroupEntity::class.java
    }

    override fun getRepository(): MongoRepository<UserGroupEntity, String> {
        return userGroupRep
    }
}