package com.azercell.myazercell.core.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.azercell.myazercell.core.R
import com.azercell.myazercell.core.base.models.ErrorModel
import com.azercell.myazercell.core.databinding.BaseErrorDialogBinding

class BaseErrorDialog(
    var errorModel: ErrorModel?,
    var onAccept: (() -> Unit)?,
) : BaseFragmentDialog() {

    private lateinit var binding: BaseErrorDialogBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }
        binding = BaseErrorDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        errorModel?.let {
            binding.errorTitle.text = getString(R.string.unknown_error)
            binding.errorDesc.text = errorModel?.error?.toString() ?: ""
        }
        binding.makeCashButtonAccept.setOnClickListener {
            onAccept?.invoke()
            dismiss()
        }
    }

    class Builder {
        var errorModel: ErrorModel? = null
        var onAccept: (() -> Unit)? = null

        inline fun errorModel(errorModel: () -> ErrorModel?) {
            this.errorModel = errorModel()
        }

        fun build() = BaseErrorDialog(
            errorModel,
            onAccept,
        )
    }
}

fun baseErrorDialog(lambda: BaseErrorDialog.Builder.() -> Unit) =
    BaseErrorDialog.Builder().apply(lambda).build()
