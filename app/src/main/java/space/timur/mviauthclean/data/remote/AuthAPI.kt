package space.timur.mviauthclean.data.remote

import retrofit2.http.Body
import retrofit2.http.POST
import space.timur.mviauthclean.domain.entity.AuthRequest
import space.timur.mviauthclean.domain.entity.TokenEntityModel

interface AuthAPI {

    @POST("api/token/")
    suspend fun logIn(
        @Body request: AuthRequest
    ): TokenEntityModel

}