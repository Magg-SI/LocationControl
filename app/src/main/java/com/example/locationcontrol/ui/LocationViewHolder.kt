package com.example.locationcontrol.ui

import android.view.View
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.locationcontrol.R

class LocationViewHolder(val mItemView: View) : RecyclerView.ViewHolder(mItemView) {
    val titleTv = mItemView.findViewById<TextView>(R.id.title_tv)
    val bbEt = mItemView.findViewById<EditText>(R.id.bb_et)
    val bbButton = mItemView.findViewById<Button>(R.id.bb_button)
    val imageView = mItemView.findViewById<ImageView>(R.id.compatibility_tv)
    val progressBar = mItemView.findViewById<ProgressBar>(R.id.progressBar)
    val bbTv = mItemView.findViewById<TextView>(R.id.bb_tv)
}