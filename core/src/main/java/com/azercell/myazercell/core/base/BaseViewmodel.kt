package com.azercell.myazercell.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azercell.myazercell.core.base.models.ErrorModel
import com.azercell.myazercell.domain.base.RemoteResponse
import com.azercell.myazercell.domain.base.BaseUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

abstract class BaseViewModel<State : UIState, Effect : UIEffect, Event : UIEvent> : ViewModel() {

    private val initialState: State by lazy { setInitialState() }
    abstract fun setInitialState(): State

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    private val _event = MutableSharedFlow<Event>()
    val event: SharedFlow<Event> = _event

    private val _errorHandler = Channel<ErrorModel>()
    val errorHandler = _errorHandler.receiveAsFlow()

    private val _handleProgressBar = MutableStateFlow(false)
    val handleProgressBar: StateFlow<Boolean> = _handleProgressBar

    val handler by lazy {
        CoroutineExceptionHandler { _, exception ->
            handleError(exception, 0)
        }
    }


    init {
        subscribeEvents()
    }

    protected fun postState(producer: State.() -> State) {
        viewModelScope.launch {
            val newState = state.value?.producer()
            newState?.let {
                _state.emit(it)
            }
        }
    }

    protected fun postEffect(producer: () -> Effect) {
        viewModelScope.launch { _effect.send(producer()) }
    }

    fun postEvent(event: Event) {
        viewModelScope.launch { _event.emit(event) }
    }

    private fun subscribeEvents() {
        viewModelScope.launch {
            _event.collect {
                handleEvents(it)
            }
        }
    }

    abstract fun handleEvents(event: Event)

    private fun handleError(exception: Throwable, errorCode: Int) {
        viewModelScope.launch {
            _errorHandler.send(ErrorModel(errorCode, exception))
        }
    }

    fun <T, R> BaseUseCase<T, R>.invokeRequest(
        params: T,
        onStart: (() -> Unit)? = null,
        onFinish: (() -> Unit)? = null,
        onHandleLoading: (Boolean) -> Unit = ::showLoading,
        onError: ((exception: Exception, errorCode: Int) -> Unit)? = null,
        onSuccess: ((result: R) -> Unit)? = null,
    ) {
        viewModelScope.launch(handler) {
            onHandleLoading(true)
            onStart?.invoke()
            when (val callResult = this@invokeRequest.invoke(params)) {
                is RemoteResponse.Success -> onSuccess?.let {
                    onSuccess(callResult.result)
                }
                is RemoteResponse.NetworkError ->
                    onError?.let {  //if error handler connected disable global error handler
                        onError(callResult.exception, callResult.errorCode)
                    } ?: handleError(callResult.exception, callResult.errorCode)
            }
            onFinish?.invoke()
            onHandleLoading(false)
        }
    }

    suspend operator fun <T, R> BaseUseCase<T, R>.invoke(input: T): RemoteResponse<R> {
        return try {
            RemoteResponse.Success(call(input))
        } catch (httpException: HttpException) {
            RemoteResponse.NetworkError(httpException.code(), httpException)
        } catch (e: Exception) {
            RemoteResponse.NetworkError(0, e)
        }
    }

    fun launch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(handler) {
            block()
        }
    }

    private fun showLoading(isShowing: Boolean) {
        viewModelScope.launch {
            _handleProgressBar.emit(isShowing)
        }
    }
}

interface UIState

interface UIEvent

interface UIEffect
