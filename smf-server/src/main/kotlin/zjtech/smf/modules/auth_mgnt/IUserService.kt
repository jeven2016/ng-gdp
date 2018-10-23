package zjtech.smf.modules.auth_mgnt

interface IUserService {
    fun getUserProfile(name: String): UserProfile?

    data class UserProfile(
            var name: String,
            var password: String,
            var salt: String?
    )
}
