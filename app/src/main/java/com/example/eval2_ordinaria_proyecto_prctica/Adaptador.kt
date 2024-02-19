package com.example.eval2_ordinaria_proyecto_prctica

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class Adaptador(private val photos: List <Int>, private val onItemClick: (Int) -> Unit) :
    RecyclerView.Adapter<Adaptador.ProfileOptionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileOptionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_aux, parent, false)
        return ProfileOptionViewHolder(view)
    }
    override fun onBindViewHolder(holder: ProfileOptionViewHolder, position: Int) {
        val photo = photos[position]
        holder.bind(photo)
        holder.itemView.setOnClickListener {
            onItemClick(photo)
        }
    }
    override fun getItemCount(): Int = photos.size
    inner class ProfileOptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageViewOption: ImageView = itemView.findViewById(R.id.ImagenAux)
        fun bind(photo: Int) {
            imageViewOption.setImageResource(photo)
        }
    }
}