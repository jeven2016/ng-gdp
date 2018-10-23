package zjtech.smf.modules.auth_mgnt

import org.apache.shiro.crypto.SecureRandomNumberGenerator
import org.apache.shiro.crypto.hash.Md5Hash
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Service
import zjtech.smf.common.constants.BeanNameConst
import zjtech.smf.common.constants.HashConst.Companion.HASH_ITERATIONS
import zjtech.smf.modules.global.domain.UserEntity
import zjtech.smf.modules.global.service.CrudServiceImp

@Service
class UserService(val userRep: UserRep) : CrudServiceImp<UserEntity, String>(), IUserService {

    @Autowired
    @Qualifier(BeanNameConst.RANDOMN_UMBER_GENERATOR)
    lateinit var generator: SecureRandomNumberGenerator

    override fun getUserProfile(name: String): IUserService.UserProfile? {
        var userEntity = super.findByName(name)
        return if (userEntity == null) {
            null
        } else {
            IUserService.UserProfile(userEntity.name, userEntity.password, userEntity.salt)
        }
    }

    override fun getEntityClass(): Class<UserEntity> {
        return UserEntity::class.java
    }

    override fun getRepository(): MongoRepository<UserEntity, String> {
        return userRep
    }

    /**
     * 创建用户
     * 随机生成干扰字符串(salt)并使用MD5进行加密，覆盖最初的password,同时将salt一并保存
     */
    override fun save(entity: UserEntity) {
//        We'll use a Random Number Generator to generate salts.  This
//        is much more secure than using a username as a salt or not
//        having a salt at all.  Shiro makes this easy.
        val salt = generator.nextBytes()//生成16个Byte的随机数
        val saltBase64 = salt.toBase64()//将hash编码后的密码以base64格式的字符串进行保存

        val pwd = Md5Hash(entity.password, salt, HASH_ITERATIONS).toBase64()
        entity.password = pwd
        entity.salt = saltBase64

        super.save(entity)
    }
}