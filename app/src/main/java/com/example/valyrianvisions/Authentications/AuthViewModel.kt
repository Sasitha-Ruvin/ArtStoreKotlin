package com.example.valyrianvisions.Authentications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class AuthViewModel: ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _authstate = MutableLiveData<AuthState>()
    val authstate: LiveData<AuthState> = _authstate

    init {
        checkAuthStatus()
    }

    fun checkAuthStatus() {
        if (auth.currentUser == null) {
            _authstate.value = AuthState.Unauthenticated
        } else {
            _authstate.value = AuthState.Authenticated
        }
    }

    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authstate.value = AuthState.Error("Please provide both email and password")
            return
        }
        _authstate.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authstate.value = AuthState.Authenticated
                } else {
                    val exception = task.exception
                    val errorMessage = when (exception) {
                        is FirebaseAuthInvalidUserException -> "Email or password is incorrect"
                        is FirebaseAuthInvalidCredentialsException -> "Email or password is incorrect"
                        else -> exception?.message ?: "Something went wrong"
                    }
                    _authstate.value = AuthState.Error(errorMessage)
                }
            }
    }

    fun signUp(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authstate.value = AuthState.Error("Please enter all the details")
            return
        }
        _authstate.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authstate.value = AuthState.Authenticated
                } else {
                    val exception = task.exception
                    val errorMessage = when (exception) {
                        is FirebaseAuthInvalidUserException -> "Email or password is incorrect"
                        is FirebaseAuthInvalidCredentialsException -> "Email or password is incorrect"
                        else -> exception?.message ?: "Something went wrong"
                    }
                    _authstate.value = AuthState.Error(errorMessage)
                }
            }
    }

    fun signOut() {
        auth.signOut()
        _authstate.value = AuthState.Unauthenticated
    }
}

sealed class AuthState {
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}
