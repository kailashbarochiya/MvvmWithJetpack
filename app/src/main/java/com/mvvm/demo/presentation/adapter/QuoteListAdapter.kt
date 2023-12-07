package com.mvvm.demo.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mvvm.demo.R
import com.mvvm.demo.databinding.ItemQuoteRateBinding
import com.mvvm.demo.domain.model.CoinsRateDataResponse


class QuoteListAdapter(
    arrayList: MutableList<CoinsRateDataResponse>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var quotesList: MutableList<CoinsRateDataResponse>? = null;
    private var onItemClickListener: OnItemClickListener? = null


    init {
        this.quotesList = arrayList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {


        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_quote_rate,
                parent,
                false
            )
        )


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = quotesList!!.get(position)


        if (holder is ViewHolder) {
            holder.bind(model, position)
        }


    }

    override fun getItemCount(): Int {
        return quotesList!!.size
    }

    interface OnItemClickListener {
        fun onItemShopClick(model: CoinsRateDataResponse?)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }

    fun addData(list: List<CoinsRateDataResponse>) {
        this.quotesList = list as MutableList<CoinsRateDataResponse>?
        this.notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var binding: ItemQuoteRateBinding? = null

        init {
            binding = DataBindingUtil.bind(itemView)

        }

        fun bind(model: CoinsRateDataResponse, position: Int) {

            binding?.model = model

        }


    }


}
