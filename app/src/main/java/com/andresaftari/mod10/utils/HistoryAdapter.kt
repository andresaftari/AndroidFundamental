package com.andresaftari.mod10.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andresaftari.mod10.R
import com.andresaftari.mod10.data.HitungBmi
import com.andresaftari.mod10.databinding.ItemHistoryBinding
import com.andresaftari.mod10.db.BmiEntity
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    private val data = mutableListOf<BmiEntity>()

    fun updateData(newData: List<BmiEntity>) {
        data.apply {
            clear()
            addAll(newData)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HistoryViewHolder(
        ItemHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
    )

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) =
        holder.bind(data[position])

    override fun getItemCount() = data.size

    class HistoryViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val dateFormatter = SimpleDateFormat(
            "dd MMMM yyyy", Locale("id", "ID")
        )

        fun bind(item: BmiEntity) = with(binding) {
            tvTanggal.text = dateFormatter.format(Date(item.tanggal))

            val hasilBmi = HitungBmi.hitung(item)

            tvKategori.text = hasilBmi.kategori.toString()
            bmiTextView.text = root.context.getString(R.string.bmi_x, hasilBmi.bmi)
            tvBerat.text = root.context.getString(R.string.berat_x, item.berat)
            tvTinggi.text = root.context.getString(R.string.tinggi_x, item.tinggi)

            val stringRes = if (item.isMale) R.string.pria else R.string.wanita
            tvGender.text = root.context.getString(stringRes)
        }
    }
}