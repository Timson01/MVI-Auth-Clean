package space.timur.mviauthclean.presentation.main_page

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import space.timur.mviauthclean.base.BaseViewModel
import space.timur.mviauthclean.common.AuthResult
import space.timur.mviauthclean.domain.use_case.LogOutUseCase
import space.timur.mviauthclean.presentation.contract.LoginContract
import space.timur.mviauthclean.presentation.contract.MainPageContract
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel@Inject constructor(
    private val logOutUseCase: LogOutUseCase
) : BaseViewModel<MainPageContract.AuthUiEvent, LoginContract.AuthState>(){

    private val resultChannel = Channel<AuthResult<Unit>>()
    val logOutResults = resultChannel.receiveAsFlow()

    override fun createInitialState(): LoginContract.AuthState {
        return LoginContract.AuthState()
    }

    override fun handleEvent(event: MainPageContract.AuthUiEvent) {
        when(event){
            is MainPageContract.AuthUiEvent.LogOut -> {
                logOut()
            }
        }
    }

    private fun logOut(){
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            val result = logOutUseCase.execute()
            resultChannel.send(result)
            setState { copy(isLoading = false) }
        }
    }

}