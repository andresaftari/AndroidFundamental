package com.andresaftari.mod10.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.andresaftari.mod10.R
import com.andresaftari.mod10.data.KategoriBmi
import com.andresaftari.mod10.databinding.FragmentHitungBinding

@SuppressLint("QueryPermissionsNeeded")
class HitungFragment : Fragment() {
    private lateinit var binding: FragmentHitungBinding
    private lateinit var kategoriBmi: KategoriBmi

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_about) {
            findNavController().navigate(
                R.id.action_hitungFragment_to_aboutFragment
            )
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHitungBinding.inflate(layoutInflater, container, false)


        with(binding) {
            button.setOnClickListener { hitungBmi() }
            buttonReset.setOnClickListener { resetForm() }
            saranButton.setOnClickListener { view: View ->
                view.findNavController().navigate(
                    HitungFragmentDirections.actionHitungFragmentToSaranFragment(kategoriBmi)
                )
            }
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
        if (TextUtils.isEmpty(berat)) {
            Toast.makeText(context, R.string.tinggi_invalid, Toast.LENGTH_LONG).show()
            return
        }

        val tinggiCm = tinggi.toFloat() / 100

        val selectedId = binding.radioGroup.checkedRadioButtonId
        if (selectedId == -1) {
            Toast.makeText(context, R.string.gender_invalid, Toast.LENGTH_LONG).show()
            return
        }

        val isMale = selectedId == R.id.priaRadioButton
        val bmi = berat.toFloat() / (tinggiCm * tinggiCm)
        val kategori = getKategori(bmi, isMale)

        with(binding) {
            bmiTextView.text = getString(R.string.bmi_x, bmi)
            kategoriTextView.text = getString(R.string.kategori_x, kategori)
            buttonGroup.visibility = View.VISIBLE
        }
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

    //Get Kategori
    private fun getKategori(bmi: Float, isMale: Boolean): String {
        kategoriBmi = if (isMale) when {
            bmi < 20.5 -> KategoriBmi.KURUS
            bmi >= 27.0 -> KategoriBmi.GEMUK
            else -> KategoriBmi.IDEAL
        }
        else when {
            bmi < 18.5 -> KategoriBmi.KURUS
            bmi >= 25.0 -> KategoriBmi.GEMUK
            else -> KategoriBmi.IDEAL
        }

        val stringRes = when (kategoriBmi) {
            KategoriBmi.KURUS -> R.string.kurus
            KategoriBmi.IDEAL -> R.string.ideal
            KategoriBmi.GEMUK -> R.string.gemuk
        }

        return getString(stringRes)
    }
}