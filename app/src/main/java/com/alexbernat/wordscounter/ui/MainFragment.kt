package com.alexbernat.wordscounter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.alexbernat.wordscounter.databinding.FragmentMainBinding
import com.alexbernat.wordscounter.ui.base.BaseFragment

class MainFragment : BaseFragment<FragmentMainBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMainBinding
        get() = FragmentMainBinding::inflate

    private val viewModel by viewModels<MainFragmentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCalculate.setOnClickListener {
            viewModel.calculateWordsCount()
        }
    }

    companion object {
        fun newInstance(): MainFragment = MainFragment()
    }
}