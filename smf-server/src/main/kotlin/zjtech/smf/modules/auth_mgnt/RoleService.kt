package zjtech.smf.modules.auth_mgnt

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Service
import zjtech.smf.modules.global.domain.RoleEntity
import zjtech.smf.modules.global.service.CrudServiceImp

@Service
class RoleService(val roleRep: RoleRep) : CrudServiceImp<RoleEntity, String>() {
    override fun getEntityClass(): Class<RoleEntity> {
        return RoleEntity::class.java
    }

    override fun getRepository(): MongoRepository<RoleEntity, String> {
        return roleRep
    }
}