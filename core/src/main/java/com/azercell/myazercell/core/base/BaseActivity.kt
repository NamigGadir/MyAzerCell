package com.azercell.myazercell.core.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.azercell.myazercell.core.base.models.ErrorModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseActivity<ViewModel : BaseViewModel<State, Effect, Event>, VB : ViewBinding, State : UIState, Effect : UIEffect, Event : UIEvent> : AppCompatActivity() {

    protected lateinit var viewmodel: ViewModel

    lateinit var binding: VB

    protected abstract val onViewBinding: () -> VB

    protected abstract fun getViewModelClass(): Class<ViewModel>

    open fun onStateUpdate(state: State) {}

    open fun onEffectUpdate(effect: Effect) {}

    private fun initViewModel() {
        viewmodel = ViewModelProvider(this)[getViewModelClass()]
    }

    protected open val onViewInit: VB.() -> Unit = {
        //ignored this in base
    }

    open fun onInitView(binding: VB) {
        //ignored in base
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = onViewBinding()
        setContentView(binding.root)
        initViewModel()
        onInitView(binding)
        onViewInit(binding)
        startObserver()
    }


    private fun startObserver() {
        viewmodel.state
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> onStateUpdate(state = state) }
            .launchIn(lifecycleScope)

        viewmodel.effect
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { effect -> onEffectUpdate(effect) }
            .launchIn(lifecycleScope)

        viewmodel.errorHandler
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { error -> handleError(error) }
            .launchIn(lifecycleScope)
    }

    private fun handleError(error: ErrorModel) {

    }

    inline fun <R> withBinding(block: VB.() -> R): R {
        return binding.block()
    }
}