//MainActivity.kt
package com.max.commerc

import android.os.AsyncTask
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        FetchDataTask().execute("https://fakestoreapi.com/products")
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
