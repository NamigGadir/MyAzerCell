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
import com.azercell.myazercell.core.databinding.BaseWarningDialogBinding

class BaseWarningDialog(
    var title: String?,
    var subTitle: String?,
    var onAccept: (() -> Unit)?,
) : BaseFragmentDialog() {

    private lateinit var binding: BaseWarningDialogBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }
        binding = BaseWarningDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.title.text = title ?: getString(R.string.are_you_sure)
        binding.desc.text = subTitle ?: ""
        binding.makeCashButtonAccept.setOnClickListener {
            onAccept?.invoke()
            dismiss()
        }
    }

    class Builder {
        var title: String? = null
        var subTitle: String? = null
        var onAccept: (() -> Unit)? = null

        inline fun title(title: () -> String?) {
            this.title = title()
        }

        inline fun subTitle(subTitle: () -> String?) {
            this.subTitle = subTitle()
        }

        fun build() = BaseWarningDialog(
            title,
            subTitle,
            onAccept,
        )
    }
}

fun baseWarningDialog(lambda: BaseWarningDialog.Builder.() -> Unit) =
    BaseWarningDialog.Builder().apply(lambda).build()
