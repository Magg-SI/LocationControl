package com.example.locationcontrol.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.example.locationcontrol.data.model.Location
import com.example.locationcontrol.R

class LocationsAdapter(val list: MutableList<Location>, val listener : BBListener): RecyclerView.Adapter<LocationViewHolder>() {

    var selectedItem : Location? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val contentView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_location, parent,false)
        contentView.layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT)

        val locationViewHolder  = LocationViewHolder(contentView)

        locationViewHolder.bbButton.setOnClickListener {
            if(!locationViewHolder.bbEt.text.isNullOrBlank()){
            val location = list[locationViewHolder.adapterPosition]
            location.bb = locationViewHolder.bbEt.text.toString().toInt()
            listener.onBBChanged(location)
            location.sendingState = true
            locationViewHolder.progressBar.visibility = View.VISIBLE
            locationViewHolder.bbButton.isEnabled = false
            }
        }

        locationViewHolder.mItemView.setOnClickListener{
            val location = list[locationViewHolder.adapterPosition]
            selectedItem = location
            notifyDataSetChanged()
        }

        return locationViewHolder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int){
        val item = list[position]

        holder.titleTv.text = item.title

        if (item.bb != null){
            holder.bbEt.setText(item.bb.toString())
        }else{
            holder.bbEt.text.clear()
        }

        when(item.compatible) {
            true -> {
                holder.imageView.visibility = View.VISIBLE
                holder.imageView.setImageResource(R.drawable.ic_round_check)
            }
            false -> {
                holder.imageView.visibility = View.VISIBLE
                holder.imageView.setImageResource(R.drawable.ic_round_close)
            }

            null -> {

                if (item.bb != null){
                    holder.imageView.visibility = View.VISIBLE
                    holder.imageView.setImageResource(R.drawable.ic_round_unknown)
                }else{
                    holder.imageView.visibility = View.INVISIBLE
                }
            }
        }

        if(item.sendingState){
            holder.progressBar.visibility = View.VISIBLE
            holder.bbButton.isEnabled = false
        }
        else{
            holder.progressBar.visibility = View.GONE
            holder.bbButton.isEnabled = true
        }


        if(item == selectedItem){
            holder.bbEt.visibility = View.VISIBLE
            holder.bbButton.visibility = View.VISIBLE
            holder.bbTv.visibility = View.INVISIBLE
        }else {
            holder.bbEt.visibility = View.INVISIBLE
            holder.bbButton.visibility = View.INVISIBLE
            if (item.bb != null) {
                holder.bbTv.visibility = View.VISIBLE
                holder.bbTv.text = item.bb.toString()
            } else {
                holder.bbTv.visibility = View.INVISIBLE
            }
        }
    }

    interface BBListener{
       fun onBBChanged(location : Location)
    }
}
