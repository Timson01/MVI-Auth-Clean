package space.timur.mviauthclean.domain.use_case

import space.timur.mviauthclean.common.AuthResult
import space.timur.mviauthclean.domain.repository.AuthRepository
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    private suspend fun buildRequest(): AuthResult<Unit> {
        return repository.logOut()
    }

    suspend fun execute(): AuthResult<Unit> {
        return try {
            buildRequest()
        } catch (exception: Exception) {
            return AuthResult.UnknownError()
        }
    }
}