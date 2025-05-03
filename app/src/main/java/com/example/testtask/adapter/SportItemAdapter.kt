package com.example.testtask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.testtask.databinding.AdapterListItemBinding
import com.example.testtask.model.SportsTable


interface CallBackInterface{
    fun onDelete(data: SportsTable)
}
class SportItemAdapter(var data: List<SportsTable>, val callBackInterface: CallBackInterface) :
    RecyclerView.Adapter<SportItemAdapter.SportItemViewHolder>() {

    inner class SportItemViewHolder(val binding: AdapterListItemBinding) :
        RecyclerView.ViewHolder(binding.root){
            fun bind(data: SportsTable){
                binding.sportsName.text = data.sport_name
                binding.sportsStatus.text = data.status
                binding.sportsID.text = "${data.sport_id} / ${data.nsrs_sport_id}"
                binding.delete.setOnClickListener{
                    callBackInterface.onDelete(data)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SportItemViewHolder {
        return SportItemViewHolder(
            AdapterListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: SportItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun updateList(newList: List<SportsTable>) {
        data = newList
        notifyDataSetChanged()
    }

}