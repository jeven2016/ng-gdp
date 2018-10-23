package zjtech.smf.modules.foundation.shiro

import org.apache.el.stream.Optional
import org.apache.shiro.authc.AuthenticationInfo
import org.apache.shiro.authc.AuthenticationToken
import org.apache.shiro.authc.SimpleAuthenticationInfo
import org.apache.shiro.authc.UnknownAccountException
import org.apache.shiro.authc.UsernamePasswordToken
import org.apache.shiro.authz.AuthorizationInfo
import org.apache.shiro.authz.SimpleAuthorizationInfo
import org.apache.shiro.codec.Base64
import org.apache.shiro.realm.AuthorizingRealm
import org.apache.shiro.subject.PrincipalCollection
import org.apache.shiro.util.SimpleByteSource
import org.springframework.beans.factory.annotation.Autowired
import zjtech.smf.common.constants.BeanNameConst.Companion.REALM
import zjtech.smf.modules.auth_mgnt.IUserService
import java.io.Serializable


/**
 * Shiro 相关的配置， session dao, cache manager 使用mongodb
 * 数据存储相关的realm 使用mongodb
 */
//refer to https://github.com/TensorWrench/shiro-mongodb-realm/blob/master/src/main/java/com/tensorwrench/shiro/realm/MongoUserPasswordRealm.java
class MongoRealm : AuthorizingRealm() {

    @Autowired
    lateinit var userService: IUserService

    /**
     * 判断是否是支持的类型： 必须为用户名密码类型
     */
    override fun supports(token: AuthenticationToken?): Boolean {
        return token is UsernamePasswordToken
    }

    override fun doGetAuthenticationInfo(token: AuthenticationToken?): AuthenticationInfo {
        //find a user by name
        val userNamePwdToken = token as UsernamePasswordToken
        val userProfile: IUserService.UserProfile = userService.getUserProfile(userNamePwdToken.username) ?:
                throw UnknownAccountException("the user(${userNamePwdToken.username}) doesn't exist.")

        val pwd = Base64.decode(userProfile.password)  //hashed password stored in db
        val salt = Base64.decode(userProfile.salt)

        //返回AuthenticationInfo,提供hash后的password 和hash值
        val authInfo: AuthenticationInfo = SimpleAuthenticationInfo(userNamePwdToken.principal, pwd,
                SimpleByteSource(salt), REALM)
        return authInfo
    }

    override fun doGetAuthorizationInfo(principals: PrincipalCollection?): AuthorizationInfo {
//        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
/*
		DBCursor cursor=collection.find(
				new BasicDBObject("_id",
						new BasicDBObject("$in",principals.asList())
				)
		);

		for(DBObject p : cursor){
			Object rolesObj=p.get("roles");
			if(rolesObj !=null && rolesObj instanceof List<?>) {
				for(Object r: (List<Object>) rolesObj) {
					info.addRole(r.toString());
				}
			}

			Object permissionsObj=p.get("permissions");
			if(permissionsObj !=null && permissionsObj instanceof List<?>) {
				for(Object r: (List<Object>) permissionsObj) {
					info.addStringPermission(r.toString());
				}
			}
		}

		return info;*/
        return SimpleAuthorizationInfo()
    }
}


/**
 * Seriable byte source
 * The class SimpleByteSource didn't implement Serializable interface, then it would cause a exception while the cache
 * is enabled to support Authentication/Authenriation.
 * for more details, refer to http://www.guyangxizhao.com/guan-yu-authentication/
 */
class MySimpleByteSource : SimpleByteSource, Serializable {
    constructor(bytes: ByteArray) : super(bytes)
}