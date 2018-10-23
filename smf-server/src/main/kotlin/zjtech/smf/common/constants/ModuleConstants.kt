package zjtech.smf.common.constants

const val UNSERIALIZED_CLASSES = "UnserializedClasses"


class UriConst {
    companion object {
        /**用户管理**/
        const val URI_LOGIN = "auth"//用户登录
        const val URI_USER_GROUP = "userGroup"//用户组
        const val URI_USER = "user"
        const val URI_ROLE = "role"

        /** user login identifier**/
        const val URI_USER_LOGIN = "/login"
        const val URI_USER_LOGOUT = "/logout"

        const val MONGODB_HOST = "10.113.12.77"
        const val MONGODB_NAME = "testDb"
    }
}


class BeanNameConst {
    companion object {
        /**Shiro related beans*/
        const val SECURITY_MANAGER = "securityManager"
        const val REALM = "realm"
        const val LIFECYCLE_BEAN_POSTPR_OCESSOR = "lifecycleBeanPostProcessor"
        const val RANDOMN_UMBER_GENERATOR = "randomNumberGenerator"

    }
}


class HashConst {
    companion object {
        const val MD5 = "md5"

        //we create a random salt and perform 2 hash iterations for strong security
        const val HASH_ITERATIONS = 1
    }
}
