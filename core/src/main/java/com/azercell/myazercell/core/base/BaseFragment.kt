package com.azercell.myazercell.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.viewbinding.ViewBinding
import com.azercell.myazercell.core.base.models.ErrorModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseFragment<ViewModel : BaseViewModel<State, Effect, Event>, VB : ViewBinding, State : UIState, Effect : UIEffect, Event : UIEvent> : Fragment() {
    protected abstract val onViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> VB

    protected lateinit var viewmodel: ViewModel

    lateinit var binding: VB

    protected abstract fun getViewModelClass(): Class<ViewModel>
    protected open fun getViewModelScope(): ViewModelStoreOwner? = null


    open fun onStateUpdate(state: State) {}

    open fun onEffectUpdate(effect: Effect) {}

    protected fun postEvent(producer: () -> Event) {
        viewmodel.postEvent(producer.invoke())
    }

    private fun initViewModel() {
        viewmodel = ViewModelProvider(getViewModelScope() ?: requireActivity())[getViewModelClass()]
    }

    protected open val onViewInit: VB.() -> Unit = {
        //ignored this in base
    }

    open fun onInitView(binding: VB) {
        //ignored in base
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = onViewBinding(inflater, container, false)
        binding.root.findViewWithTag<Toolbar>("toolbar")?.let {
            NavigationUI.setupWithNavController(it, findNavController())
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    open fun handleError(error: ErrorModel) {}

    inline fun <R> withBinding(block: VB.() -> R): R {
        return binding.block()
    }


}