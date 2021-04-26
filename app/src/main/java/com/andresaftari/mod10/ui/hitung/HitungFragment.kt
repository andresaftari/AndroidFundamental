package com.andresaftari.mod10.ui.hitung

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.andresaftari.mod10.R
import com.andresaftari.mod10.data.KategoriBmi
import com.andresaftari.mod10.databinding.FragmentHitungBinding
import com.andresaftari.mod10.db.BmiDb
import com.andresaftari.mod10.utils.HitungViewModelFactory

@SuppressLint("QueryPermissionsNeeded")
class HitungFragment : Fragment() {
    private val viewModel: HitungViewModel by lazy {
        val db = BmiDb.getInstance(requireContext())
        val factory = HitungViewModelFactory(db.dao)
        ViewModelProvider(this, factory).get(HitungViewModel::class.java)
    }
    private lateinit var binding: FragmentHitungBinding

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_about) {
            findNavController().navigate(R.id.action_hitungFragment_to_aboutFragment)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getNavigasi().observe(viewLifecycleOwner, {
            if (it == null) return@observe
            findNavController().navigate(
                HitungFragmentDirections.actionHitungFragmentToSaranFragment(it)
            )
            viewModel.selesaiNavigasi()
        })

        viewModel.getHasilBmi().observe(viewLifecycleOwner, {
            if (it == null) return@observe

            with(binding) {
                bmiTextView.text = getString(R.string.bmi_x, it.bmi)
                kategoriTextView.text = getString(R.string.kategori_x, getKategori(it.kategori))
                buttonGroup.visibility = View.VISIBLE
            }
        })

        viewModel.data.observe(viewLifecycleOwner, {
            if (it == null) return@observe
            Log.d(TAG, "Data tersimpan. ID = ${it.id}")
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHitungBinding.inflate(layoutInflater, container, false)

        with(binding) {
            button.setOnClickListener { hitungBmi() }
            buttonReset.setOnClickListener { resetForm() }
            saranButton.setOnClickListener { viewModel.mulaiNavigasi() }
            shareButton.setOnClickListener { shareData() }
        }
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun resetForm() = with(binding) {
        beratEditText.setText("")
        beratEditText.requestFocus()
        tinggiEditText.setText("")
        radioGroup.clearCheck()

        bmiTextView.text = ""
        kategoriTextView.text = ""
    }

    //Hitung BMI
    private fun hitungBmi() {
        val berat = binding.beratEditText.text.toString()
        if (TextUtils.isEmpty(berat)) {
            Toast.makeText(context, R.string.berat_invalid, Toast.LENGTH_LONG).show()
            return
        }

        val tinggi = binding.tinggiEditText.text.toString()
        if (TextUtils.isEmpty(tinggi)) {
            Toast.makeText(context, R.string.tinggi_invalid, Toast.LENGTH_LONG).show()
            return
        }

        val selectedId = binding.radioGroup.checkedRadioButtonId
        if (selectedId == -1) {
            Toast.makeText(context, R.string.gender_invalid, Toast.LENGTH_LONG).show()
            return
        }

        val isMale = selectedId == R.id.priaRadioButton
        viewModel.hitungBmi(berat, tinggi, isMale)
    }

    //Share Data
    private fun shareData() {
        val selectedId = binding.radioGroup.checkedRadioButtonId

        val gender = if (selectedId == R.id.priaRadioButton) getString(R.string.pria)
        else getString(R.string.wanita)

        val message = getString(
            R.string.bagikan_template,
            binding.beratEditText.text,
            binding.tinggiEditText.text,
            gender,
            binding.bmiTextView.text,
            binding.kategoriTextView.text
        )
        val shareIntent = Intent(Intent.ACTION_SEND)

        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, message)
        if (shareIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(shareIntent)
        }
    }

    private fun getKategori(kategoriBmi: KategoriBmi): String {
        val stringRes = when (kategoriBmi) {
            KategoriBmi.KURUS -> R.string.kurus
            KategoriBmi.IDEAL -> R.string.ideal
            KategoriBmi.GEMUK -> R.string.gemuk
        }
        return getString(stringRes)
    }

    companion object {
        private const val TAG = "HitungFragment"
    }
}