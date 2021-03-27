package com.andresaftari.basicinput

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import com.andresaftari.basicinput.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    // NOTE: KHUSUS DROPDOWN, ADA DAFTAR VALUE
    val listDropdown = listOf("Pilih Value", "Value A", "Value B", "Value C")
    // NOTE: KHUSUS DROPDOWN, ADA DAFTAR VALUE


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Wajib ada adapter ini saat membuat dropdown
        val arrAdapter = ArrayAdapter(
                this@MainActivity,
                android.R.layout.simple_spinner_item,
                listDropdown
        )

        arrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.actionSpinner.adapter = arrAdapter
        // Batas kodingan inisialisasi dropdown dan adapternya


        // NOTE: KHUSUS DROPDOWN, KITA TIDAK PERLU MENYIMPAN FUNCTION KE DALAM BUTTON
        getDataFromDropdown()
        // NOTE: KHUSUS DROPDOWN, KITA TIDAK PERLU MENYIMPAN FUNCTION KE DALAM BUTTON


        binding.btnOutput.setOnClickListener {
            if (!TextUtils.isEmpty(binding.edtInput.text.toString())) {
                if (binding.edtInput.text.isDigitsOnly()) getDataFromEditTextInt()
                else getDataFromEditTextString()
            } else if (binding.rgValue.checkedRadioButtonId != 0) getDataFromRadioButton()
        }
    }


    // Mengambil data dalam bentuk String dari EditText
    private fun getDataFromEditTextString() {
        val dataString = binding.edtInput.text.toString()
        Log.d("EditText: String", "Check: $dataString")

        binding.tvHasil.text = dataString
    }

    // Mengambil data dalam bentuk String dari EditText
    @SuppressLint("SetTextI18n")
    private fun getDataFromEditTextInt() {
        val dataInt = binding.edtInput.text.toString().toInt()
        val hasil = dataInt * 10

        binding.tvHasil.text = "$dataInt * 10 = $hasil"
    }

    // Mengambil data dalam bentuk pilihan radio button
    private fun getDataFromRadioButton() {
        var data = ""

        with(binding) {
            when {
                rbA.isChecked -> {
                    data = "A"
                    // Apabila ada hitungan, buat kodingannya di sini
                }
                rbB.isChecked -> {
                    data = "B"
                    // Apabila ada hitungan, buat kodingannya di sini
                }
            }
            // Apabila tidak ada hitungan, proses langsung diluar when (tetap dalam with biar mudah)
            tvHasil.text = data
        }
    }

    // Mengambil data dalam bentuk pilihan dropdown
    private fun getDataFromDropdown() = with(binding) {
        actionSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(spinner: AdapterView<*>?, view: View?, position: Int, viewId: Long) {
                if (position != 0) tvHasil.text = listDropdown[position]
                // Apabila hitungan atau proses lain, silakan tambahkan di sini
            }

            override fun onNothingSelected(spinner: AdapterView<*>?) {
                // Fungsi ini gunanya memberi aksi apabila tidak ada yang dipilih, jarang digunakan, jadi boleh dikosongin
            }
        }
    }
}