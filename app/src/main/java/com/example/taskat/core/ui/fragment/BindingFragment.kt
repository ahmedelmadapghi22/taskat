package com.example.taskat.core.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.AndroidEntryPoint

abstract class BindingFragment<Binding : ViewBinding>(
    private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> Binding
) : Fragment() {

    private var _binding: Binding? = null;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return bindingInflater(inflater, container, false).apply {
            _binding = this
        }.root

    }
    //To prevent memory leak

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun requireBinding(): Binding = _binding
        ?: throw IllegalStateException("You used the binding before onCreateView() or after onDestroyView()")

    protected fun useBinding(bindingUse: (Binding) -> Unit) {
        bindingUse(requireBinding())
    }

    protected fun makeToastFromStr(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    protected fun makeToastFromRes(msgId: Int) {
        try {
            Toast.makeText(requireContext(), getString(msgId), Toast.LENGTH_SHORT).show()
        }catch (ex:Exception){
            makeToastFromStr(ex.message.toString())
        }
    }
}