package com.smartaccounting.core.presentation.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartaccounting.core.domain.model.User
import com.smartaccounting.core.domain.usecase.user.GetUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class SettingsUiState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val isDarkMode: Boolean = false,
    val isBiometricEnabled: Boolean = false,
    val isNotificationEnabled: Boolean = true,
    val autoAccountingEnabled: Boolean = false,
    val error: String? = null
)

class SettingsViewModel(
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()
    
    fun loadUser(userId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            getUserUseCase(userId)
                .onSuccess { user ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        user = user
                    )
                }
                .onFailure { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
        }
    }
    
    fun setDarkMode(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(isDarkMode = enabled)
    }
    
    fun setBiometricEnabled(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(isBiometricEnabled = enabled)
    }
    
    fun setNotificationEnabled(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(isNotificationEnabled = enabled)
    }
    
    fun setAutoAccountingEnabled(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(autoAccountingEnabled = enabled)
    }
}
