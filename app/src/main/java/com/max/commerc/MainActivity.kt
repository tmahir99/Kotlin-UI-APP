// MainActivity.kt
package com.max.commerc

import android.content.Intent
import android.os.AsyncTask
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), ShoppingCartManager.OnItemCountChangeListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter
    private lateinit var itemCountTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        itemCountTextView = findViewById(R.id.itemCountTextView)

        FetchDataTask().execute("https://fakestoreapi.com/products")

        val shoppingCartIcon: ImageView = findViewById(R.id.shoppingCartIcon)
        shoppingCartIcon.setOnClickListener {
            startActivity(Intent(this, ShoppingCartActivity::class.java))
        }

        // Register MainActivity as a listener
        ShoppingCartManager.registerOnItemCountChangeListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Unregister MainActivity as a listener to avoid memory leaks
        ShoppingCartManager.unregisterOnItemCountChangeListener(this)
    }

    // Implement the OnItemCountChangeListener interface
    override fun onItemCountChange(newCount: Int) {
        runOnUiThread {
            // Update the TextView on the UI thread
            itemCountTextView.text = newCount.toString()
        }
    }

    private inner class FetchDataTask : AsyncTask<String, Void, List<ItemModel>>() {
        override fun doInBackground(vararg params: String?): List<ItemModel> {
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
                return gson.fromJson(data.toString(), Array<ItemModel>::class.java).toList()
            } finally {
                connection.disconnect()
            }
        }

        override fun onPostExecute(result: List<ItemModel>?) {
            super.onPostExecute(result)
            if (result != null) {
                adapter = ItemAdapter(result)
                recyclerView.adapter = adapter
            }
        }
    }
}