package com.alexbernat.wordscounter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.alexbernat.wordscounter.R
import com.alexbernat.wordscounter.databinding.FragmentMainBinding
import com.alexbernat.wordscounter.domain.model.SortingOption
import com.alexbernat.wordscounter.domain.model.Word
import com.alexbernat.wordscounter.ui.base.BaseFragment
import com.alexbernat.wordscounter.ui.model.UiState
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment<FragmentMainBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMainBinding
        get() = FragmentMainBinding::inflate

    private val viewModel: MainFragmentViewModel by viewModel()

    private val filterOptionsAdapter by lazy {
        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            SortingOption.values()
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
    }
    private val resultAdapter by lazy {
        ResultAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeState()
        with(binding) {
            recyclerViewResultList.adapter = resultAdapter
            spinnerFilterOption.adapter = filterOptionsAdapter
            spinnerFilterOption.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, v: View?, pos: Int, id: Long) {
                    (parent?.getItemAtPosition(pos) as? SortingOption)?.let { option ->
                        viewModel.applySortingOption(option)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
            btnCalculate.setOnClickListener {
                viewModel.calculateWordsCount()
            }
        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    renderState(uiState)
                }
            }
        }
    }

    private fun renderState(state: UiState) {
        when (state) {
            is UiState.Idle -> binding.showInitialUi()
            is UiState.Loading -> binding.showLoading()
            is UiState.Data -> binding.showData(state.result)
            is UiState.Error -> binding.showError(getString(state.exception.msgResId))
        }
    }

    private fun FragmentMainBinding.showInitialUi(){
        progressBar.visibility = View.GONE
        textViewLabel.text = getString(R.string.main_label)
    }

    private fun FragmentMainBinding.showLoading(){
        progressBar.visibility = View.VISIBLE
        textViewLabel.visibility = View.GONE
        recyclerViewResultList.visibility = View.GONE
        btnCalculate.visibility = View.GONE
    }

    private fun FragmentMainBinding.showError(msg: String) {
        textViewLabel.text = msg
        progressBar.visibility = View.GONE
        recyclerViewResultList.visibility = View.GONE

    }

    private fun FragmentMainBinding.showData(words: List<Word>) {
        resultAdapter.submitList(words) {
            if (words.isNotEmpty()) {
                recyclerViewResultList.scrollToPosition(0)
            }
        }
        progressBar.visibility = View.GONE
        textViewLabel.visibility = View.GONE
        btnCalculate.visibility = View.GONE
        spinnerFilterOption.visibility = View.VISIBLE
        recyclerViewResultList.visibility = View.VISIBLE
    }

    companion object {
        fun newInstance(): MainFragment = MainFragment()
    }
}