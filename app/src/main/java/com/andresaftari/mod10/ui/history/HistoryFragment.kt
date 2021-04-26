package com.andresaftari.mod10.ui.history

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.andresaftari.mod10.R
import com.andresaftari.mod10.databinding.FragmentHistoryBinding
import com.andresaftari.mod10.db.BmiDb
import com.andresaftari.mod10.utils.HistoryAdapter
import com.andresaftari.mod10.utils.HistoryViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var historyAdapter: HistoryAdapter

    private val viewModel: HistoryViewModel by lazy {
        val db = BmiDb.getInstance(requireContext())
        val factory = HistoryViewModelFactory(db.dao)
        ViewModelProvider(this, factory).get(HistoryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        historyAdapter = HistoryAdapter()

        with(binding.rvHistori) {
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            adapter = historyAdapter
            setHasFixedSize(true)
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.data.observe(viewLifecycleOwner, {
            binding.emptyView.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            historyAdapter.updateData(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.history_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_hapus) {
            hapusData()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun hapusData() = MaterialAlertDialogBuilder(requireContext())
        .setMessage(R.string.delete_confirmation)
        .setPositiveButton(getString(R.string.delete)) { _, _ -> viewModel.hapusData() }
        .setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.cancel() }
        .show()

    companion object {
        private const val TAG = "HistoryFragment"
    }
}