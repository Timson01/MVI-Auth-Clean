package space.timur.mviauthclean.presentation.contract

import com.ysfcyln.base.UiEvent
import com.ysfcyln.base.UiState

class LoginContract {

    data class AuthState(
        val isLoading: Boolean = false,
        val logInUsername: String = "",
        val logInPassword: String = ""
    ): UiState

    sealed class AuthUiEvent: UiEvent {
        data class LogInUsernameChanged(val value: String): AuthUiEvent()
        data class LogInPasswordChanged(val value: String): AuthUiEvent()
        object LogIn: AuthUiEvent()
    }

}