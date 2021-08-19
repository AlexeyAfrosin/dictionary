package com.afrosin.dictionary.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.afrosin.dictionary.R
import com.afrosin.dictionary.databinding.ActivityMainRecyclerviewItemBinding
import com.afrosin.dictionary.viewmodels.convertMeaningsToString
import com.afrosin.model.data.DataModel


class MainAdapter(
    private var onListItemClickListener: OnListItemClickListener
) :

    RecyclerView.Adapter<MainAdapter.RecyclerItemViewHolder>() {
    private var data: List<DataModel> = arrayListOf()
    fun setData(data: List<DataModel>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_main_recyclerview_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val vb: ActivityMainRecyclerviewItemBinding by viewBinding()
        fun bind(data: DataModel) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                vb.headerTextviewRecyclerItem.text = data.text
                vb.descriptionTextviewRecyclerItem.text = convertMeaningsToString(data.meanings!!)

                itemView.setOnClickListener { openInNewWindow(data) }
            }
        }
    }

    private fun openInNewWindow(listItemData: DataModel) {
        onListItemClickListener.onItemClick(listItemData)
    }

    interface OnListItemClickListener {
        fun onItemClick(data: DataModel)
    }
}
