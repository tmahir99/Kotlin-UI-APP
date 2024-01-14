// ItemAdapter.kt
package com.max.commerc

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ItemAdapter(private var items: List<ItemModel>) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val price: TextView = itemView.findViewById(R.id.price)
        val description: TextView = itemView.findViewById(R.id.description)
        val category: TextView = itemView.findViewById(R.id.category)
        val image: ImageView = itemView.findViewById(R.id.image)
        val rating: TextView = itemView.findViewById(R.id.rating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        // Populate data for the item
        holder.title.text = item.title
        holder.price.text = "Price: $${item.price}"
        holder.description.text = item.description
        holder.category.text = "Category: ${item.category}"
        Glide.with(holder.image.context).load(item.image).into(holder.image)
        holder.rating.text = "Rating: ${item.rating.rate} (${item.rating.count} reviews)"

        // Set click listener for the item
        holder.itemView.setOnClickListener {
            // Pass the selected item's ID to the new activity
            val intent = Intent(holder.itemView.context, ItemDetailsActivity::class.java)
            intent.putExtra("item_id", item.id)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
    fun setItems(items: List<ItemModel>) {
        this.items = items
        notifyDataSetChanged()
    }
}
