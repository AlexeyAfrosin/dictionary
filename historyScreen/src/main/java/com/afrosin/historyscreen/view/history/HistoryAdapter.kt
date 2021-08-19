package com.afrosin.historyscreen.view.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.afrosin.historyscreen.R
import com.afrosin.historyscreen.databinding.HistoryWorldItemBinding
import com.afrosin.model.data.DataModel

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.RecyclerItemViewHolder>() {
    private var data: List<DataModel> = arrayListOf()

    fun setData(data: List<DataModel>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.history_world_item, parent, false) as View
        )
    }


    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }


    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val vb: HistoryWorldItemBinding by viewBinding()

        fun bind(data: DataModel) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                with(vb.headerHistoryTextviewRecyclerItem) {
                    text = data.text
                    setOnClickListener {
                        Toast.makeText(context, data.text, Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }
}