package com.andresaftari.mod10.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.andresaftari.mod10.R
import com.andresaftari.mod10.data.KategoriBmi
import com.andresaftari.mod10.databinding.FragmentSaranBinding

class SaranFragment : Fragment() {
    private val args: SaranFragmentArgs by navArgs()
    private lateinit var binding: FragmentSaranBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSaranBinding.inflate(layoutInflater, container, false)

        updateUI(args.kategori)
        return binding.root
    }

    private fun updateUI(kategori: KategoriBmi) {
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar

        with(binding) {
            when (kategori) {
                KategoriBmi.KURUS -> {
                    actionBar?.title = getString(R.string.judul_kurus)
                    imageView.setImageResource(R.drawable.kurus)
                    textView.text = getString(R.string.saran_kurus)
                }
                KategoriBmi.IDEAL -> {
                    actionBar?.title = getString(R.string.judul_ideal)
                    imageView.setImageResource(R.drawable.ideal)
                    textView.text = getString(R.string.saran_ideal)
                }
                KategoriBmi.GEMUK -> {
                    actionBar?.title = getString(R.string.judul_gemuk)
                    imageView.setImageResource(R.drawable.gemuk)
                    textView.text = getString(R.string.saran_gemuk)
                }
            }
        }
    }
}