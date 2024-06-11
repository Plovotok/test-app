package ru.plovotok.githubtest.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<State: UiState, Event: UiEvent, Effect: UiEffect>(initialState: State): ViewModel() {

    private val _state: MutableStateFlow<State> = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _effectFlow: MutableSharedFlow<Effect?> = MutableSharedFlow()
    val effect = _effectFlow.asSharedFlow()

    private val _eventFlow: MutableSharedFlow<Event> = MutableSharedFlow()

    init {
        subscribeEvents()
    }

    fun sendEvent(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    protected abstract fun handleEvent(event: Event)

    protected fun setState(state:State) {
        _state.value = state
    }

    protected fun sendEffect(effect: Effect) {
        viewModelScope.launch { _effectFlow.emit(effect) }
    }

    private fun subscribeEvents() {
        viewModelScope.launch {
            _eventFlow.collect { event ->
                handleEvent(event)
            }
        }
    }
}

interface UiState
interface UiEvent
interface UiEffect