package space.timur.mviauthclean.presentation.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import space.timur.mviauthclean.R
import space.timur.mviauthclean.common.AuthResult
import space.timur.mviauthclean.databinding.FragmentLogInBinding
import space.timur.mviauthclean.presentation.contract.LoginContract
import space.timur.mviauthclean.presentation.extensions.addTextChangedListenerAndSetEvent

@AndroidEntryPoint
class LogInFragment : Fragment() {

    private lateinit var binding: FragmentLogInBinding
    private val viewModel: LogInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLogInBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            username.addTextChangedListenerAndSetEvent({ newText ->
                LoginContract.AuthUiEvent.LogInUsernameChanged(newText)
            }, viewModel)
            password.addTextChangedListenerAndSetEvent({ newText ->
                LoginContract.AuthUiEvent.LogInPasswordChanged(newText)
            }, viewModel)
            loginButton.setOnClickListener {
                viewModel.setEvent(LoginContract.AuthUiEvent.LogIn)
            }
        }
        initObservers()
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch{
            viewModel.authResults.collect { result ->
                when(result) {
                    is AuthResult.Authorized -> {
                        findNavController().navigate(R.id.action_logInFragment_to_mainPageFragment)
                    }
                    is AuthResult.Unauthorized -> {
                        Toast.makeText(
                            context,
                            "You're not authorized",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    is AuthResult.UnknownError -> {
                        Toast.makeText(
                            context,
                            "An unknown error occurred",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }
}
