package zjtech.smf.modules.global.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.annotation.Version
import org.springframework.data.mongodb.core.index.TextIndexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Entity类的每个字段都需要设置初始值，否则会抛出异常
 */


/**
 * User Group
 */
@Document(collection = "auth_user_group")
data class UserGroupEntity(
        @Id var id: String? = null,
        @TextIndexed var name: String = "",
        var description: String? = null,
        @Version var version: Long? = 0,

        @DBRef var users: MutableList<UserEntity> = mutableListOf()
) : IEntity

/**
 * UserEntity Entity
 */
@TypeAlias("auth_user")
@Document(collection = "auth_user")
data class UserEntity(
        @Id var id: String? = null,
        @TextIndexed var name: String = "",
        @JsonIgnore var password: String = "", //password
        var salt: String? = null, //a random salt
        var description: String? = null,
        @Version var version: Long? = 0,

        @DBRef var roles: MutableList<RoleEntity> = mutableListOf()
) : IEntity

/**
 * RoleEntity entity
 */
@TypeAlias("auth_role")
@Document(collection = "auth_role")
data class RoleEntity(
        @Id var id: String? = null,
        @TextIndexed var name: String = "",
        var description: String? = null,
        @Version var version: Long? = 0
) : IEntity
