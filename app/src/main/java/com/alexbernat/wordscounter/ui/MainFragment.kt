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
import com.alexbernat.wordscounter.ui.base.BaseFragment
import com.alexbernat.wordscounter.ui.model.CalculationResult
import com.alexbernat.wordscounter.ui.model.SortingOption
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
            spinnerFilterOption.onItemSelectedListener  = object : OnItemSelectedListener {
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
        when (val data = state.result) {
            is CalculationResult.Success -> {
                resultAdapter.submitList(data.data) {
                    if (data.data.isNotEmpty()) {
                        binding.recyclerViewResultList.scrollToPosition(0)
                    }
                }
                binding.textViewLabel.visibility = View.GONE
                binding.btnCalculate.visibility = View.GONE
                binding.spinnerFilterOption.visibility = View.VISIBLE
                binding.recyclerViewResultList.visibility = View.VISIBLE
            }

            is CalculationResult.Error -> {
                binding.textViewLabel.text = data.message
            }

            else -> {
                binding.textViewLabel.text = getString(R.string.main_label)
            }
        }
    }

    companion object {
        fun newInstance(): MainFragment = MainFragment()
    }
}