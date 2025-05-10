package com.example.pokemontest.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemontest.domain.usecases.GetAccessTokenUseCase
import com.example.pokemontest.domain.usecases.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCase: LoginUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase
) : ViewModel() {

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    private val _shouldSkipLogin = MutableLiveData<Boolean>()
    val shouldSkipLogin: LiveData<Boolean> = _shouldSkipLogin

    fun checkIfLoggedIn() {
        getAccessTokenUseCase()
            .onEach { token ->
                _shouldSkipLogin.value = !token.isNullOrEmpty()
            }
            .launchIn(viewModelScope)
    }

    fun performLogin(email: String, pass: String) {
        useCase(email = email, pass = pass)
            .onStart {
                _loginResult.value = LoginResult.Loading
            }
            .onEach { domainLogin ->
                _loginResult.value = LoginResult.Success
            }
            .catch { error ->
                _loginResult.value =
                    LoginResult.Error(error.localizedMessage ?: "Error de autenticación")
            }
            .launchIn(viewModelScope)
    }

    sealed class LoginResult {
        data object Loading : LoginResult()
        data object Success : LoginResult()
        data class Error(val message: String) : LoginResult()
    }
}
