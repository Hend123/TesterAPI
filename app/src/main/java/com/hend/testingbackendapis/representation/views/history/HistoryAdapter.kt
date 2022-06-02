package com.hend.testingbackendapis.representation.views.history

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.hend.testingbackendapis.databinding.CustomListItemBinding
import com.hend.testingbackendapis.model.ResponseAndRequest


class HistoryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {
    private var responseAndRequestList: List<ResponseAndRequest>
    private lateinit var context: Context
    private lateinit var onItemClickListener: OnItemClickListener
    private  var responseAndRequestListFilter: List<ResponseAndRequest>

    init {
        responseAndRequestList = ArrayList()
        responseAndRequestListFilter = ArrayList()
    }

    interface OnItemClickListener {
        fun onClick(position: Int)

    }

    fun setOnItemClickListener(onItemClickListner: OnItemClickListener) {
        this.onItemClickListener = onItemClickListner
    }


    fun setDataAndContext(responseAndRequestList: List<ResponseAndRequest>, context: Context) {
        this.responseAndRequestList = responseAndRequestList
        this.responseAndRequestListFilter = responseAndRequestList
        this.context = context

    }

    fun update(responseAndRequestList: List<ResponseAndRequest>) {
        this.responseAndRequestList = responseAndRequestList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CustomListItemBinding.inflate(inflater, parent, false)
        return OutputItemsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return responseAndRequestListFilter.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as OutputItemsViewHolder
        viewHolder.bind(responseAndRequestListFilter[position])
    }


    inner class OutputItemsViewHolder(val binding: CustomListItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(item: ResponseAndRequest) {
            binding.outPut = item
            binding.root.setOnClickListener(this)
            binding.root.isClickable = true

            binding.executePendingBindings()
        }

        override fun onClick(v: View?) {
            onItemClickListener.onClick(adapterPosition)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                responseAndRequestListFilter = if (charString.isEmpty()) responseAndRequestList else {
                    val filteredList = ArrayList<ResponseAndRequest>()
                    responseAndRequestList.filter {
                        (it.methodType.contains(constraint!!))

                    }.forEach { filteredList.add(it) }
                    filteredList

                }
                return FilterResults().apply { values = responseAndRequestListFilter }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                responseAndRequestListFilter = if (results?.values == null) ArrayList()
                else
                    results.values as ArrayList<ResponseAndRequest>
                notifyDataSetChanged()
            }

        }
    }
}