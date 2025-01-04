package com.example.myapplication.ui.base

abstract class BaseViewModel : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    protected fun launchWithLoadingState(
        block: suspend () -> Unit
    ) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                block()
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

// --Commented out by Inspection START (1/4/25, 2:11 PM):
//    fun clearError() {
//        _error.value = null
//    }
// --Commented out by Inspection STOP (1/4/25, 2:11 PM)
}