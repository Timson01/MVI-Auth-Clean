package space.timur.mviauthclean.domain.repository

import space.timur.mviauthclean.common.AuthResult

interface AuthRepository {

    suspend fun logIn(username: String, password: String): AuthResult<Unit>

    suspend fun logOut(): AuthResult<Unit>

}