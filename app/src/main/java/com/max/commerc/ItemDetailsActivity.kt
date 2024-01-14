// ItemDetailsActivity.kt
package com.max.commerc

import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import android.widget.ImageButton
import android.widget.Toast


class ItemDetailsActivity : AppCompatActivity() {

    private lateinit var itemImage: ImageView
    private lateinit var itemTitle: TextView
    private lateinit var itemCategory: TextView
    private lateinit var itemDescription: TextView
    private lateinit var itemPrice: TextView
    private lateinit var itemRating: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_details)

        val backButton: ImageButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }
        itemImage = findViewById(R.id.itemImage)
        itemTitle = findViewById(R.id.itemTitle)
        itemCategory = findViewById(R.id.itemCategory)
        itemDescription = findViewById(R.id.itemDescription)
        itemPrice = findViewById(R.id.itemPrice)
        itemRating = findViewById(R.id.itemRating)

        // Retrieve the selected item's ID from the Intent
        val itemId = intent.getIntExtra("item_id", -1)


        if (itemId != -1) {
            FetchItemDetailsTask().execute("https://fakestoreapi.com/products/$itemId")
        }
    }

    private inner class FetchItemDetailsTask : AsyncTask<String, Void, ItemModel>() {
        override fun doInBackground(vararg params: String?): ItemModel? {
            val url = URL(params[0])
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection

            try {
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val data = StringBuilder()
                var line: String?

                while (reader.readLine().also { line = it } != null) {
                    data.append(line)
                }

                val gson = Gson()
                return gson.fromJson(data.toString(), ItemModel::class.java)
            } finally {
                connection.disconnect()
            }
        }

        override fun onPostExecute(result: ItemModel?) {
            super.onPostExecute(result)
            if (result != null) {
                // Populate the UI with detailed item data
                itemTitle.text = result.title
                itemCategory.text = "Category: ${result.category}"
                itemDescription.text = result.description
                itemPrice.text = "Price: $${result.price}"
                itemRating.text = "Rating: ${result.rating.rate} (${result.rating.count} reviews)"

                // Load image using Glide
                Glide.with(itemImage.context).load(result.image).into(itemImage)
            }
        }
    }
    fun onBuyNowClick(view: View) {
        Toast.makeText(this, "It will be delivered soon", Toast.LENGTH_SHORT).show()
    }

}
