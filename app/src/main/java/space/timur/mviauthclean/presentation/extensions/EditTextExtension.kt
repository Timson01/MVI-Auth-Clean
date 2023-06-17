package space.timur.mviauthclean.presentation.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import space.timur.mviauthclean.presentation.contract.LoginContract
import space.timur.mviauthclean.presentation.login.LogInViewModel

fun EditText.addTextChangedListenerAndSetEvent(eventSetter: (String) -> LoginContract.AuthUiEvent, viewModel: LogInViewModel) {
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val newText = s?.toString()
            viewModel.setEvent(eventSetter(newText ?: ""))
        }
        override fun afterTextChanged(s: Editable?) {}
    })
}