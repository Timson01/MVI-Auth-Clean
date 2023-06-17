package space.timur.mviauthclean.data.repository

import android.content.SharedPreferences
import retrofit2.HttpException
import space.timur.mviauthclean.common.AuthResult
import space.timur.mviauthclean.data.remote.AuthAPI
import space.timur.mviauthclean.domain.entity.AuthRequest
import space.timur.mviauthclean.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val api: AuthAPI,
    private val prefs: SharedPreferences
    ) : AuthRepository {

    override suspend fun logIn(username: String, password: String): AuthResult<Unit> {
        return try{
            val response = api.logIn(
                request = AuthRequest(
                username = username,
                password = password
                )
            )
            prefs.edit().putString("jwt_access", response.accessToken).apply()
            prefs.edit().putString("jwt_refresh", response.refreshToken).apply()
            AuthResult.Authorized()
        } catch (e: HttpException) {
            if(e.code() == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }

    override suspend fun logOut(): AuthResult<Unit> {
        prefs.edit().remove("jwt_access").apply()
        prefs.edit().remove("jwt_refresh").apply()
        return AuthResult.Unauthorized()
    }
}