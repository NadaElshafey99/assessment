package com.example.etisalatassessment.utils


sealed class UiState{
    object Loading: UiState()
    class Success <T>(val data: T): UiState()
    class Error(val error: String?): UiState()
}