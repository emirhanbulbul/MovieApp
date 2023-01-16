package com.emirhanbb.movieapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding>(
    private val bindingInflater: (inflater: LayoutInflater) -> T
) : Fragment() {
    private var _binding: T? = null
    val binding: T
        get() = _binding as T

    protected abstract fun initListeners()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater)
        initListeners()
        if (_binding == null) {
            throw IllegalArgumentException("Binding cannot be null")
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}


