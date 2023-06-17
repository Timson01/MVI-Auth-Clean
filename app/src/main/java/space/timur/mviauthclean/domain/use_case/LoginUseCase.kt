package space.timur.mviauthclean.domain.use_case

import space.timur.mviauthclean.common.AuthResult
import space.timur.mviauthclean.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository,
) {

    private suspend fun buildRequest(username: String?, password: String?):AuthResult<Unit> {
        if(username == null || password == null){
            return AuthResult.UnknownError()
        }
        return repository.logIn(username = username, password = password)
    }

    suspend fun execute(username: String?, password: String?): AuthResult<Unit> {
        return try {
            buildRequest(username, password)
        } catch (exception: Exception) {
            return AuthResult.UnknownError()
        }
    }
}