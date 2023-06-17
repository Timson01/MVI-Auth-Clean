package space.timur.mviauthclean.presentation.login

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import space.timur.mviauthclean.base.BaseViewModel
import space.timur.mviauthclean.common.AuthResult
import space.timur.mviauthclean.domain.use_case.LoginUseCase
import space.timur.mviauthclean.presentation.contract.LoginContract
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val logInUseCase: LoginUseCase
) : BaseViewModel<LoginContract.AuthUiEvent, LoginContract.AuthState>() {

    private val resultChannel = Channel<AuthResult<Unit>>()
    val authResults = resultChannel.receiveAsFlow()

    override fun createInitialState(): LoginContract.AuthState {
        return LoginContract.AuthState()
    }

    override fun handleEvent(event: LoginContract.AuthUiEvent) {
        when(event){
            is LoginContract.AuthUiEvent.LogInUsernameChanged -> {
                setState { copy(logInUsername = event.value) }
            }
            is LoginContract.AuthUiEvent.LogInPasswordChanged -> {
                setState { copy(logInPassword = event.value) }
            }
            is LoginContract.AuthUiEvent.LogIn -> {
                logIn()
            }
        }
    }

    private fun logIn(){
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            val result = logInUseCase.execute(currentState.logInUsername, currentState.logInPassword)
            resultChannel.send(result)
            setState { copy(isLoading = false) }
        }
    }

}