package space.timur.mviauthclean.presentation.contract

import com.ysfcyln.base.UiEvent

class MainPageContract {
    sealed class AuthUiEvent: UiEvent {
        object LogOut: AuthUiEvent()
    }
}