package com.max.commerc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import java.text.NumberFormat

class ShoppingCartActivity : AppCompatActivity() {

    private lateinit var proceedToPaymentButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart)

        val backButton: ImageView = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish() // Finish the activity when the back button is clicked
        }

        val cartItems = ShoppingCartManager.getCartItems()

        val parentLayout: LinearLayout = findViewById(R.id.parentLayout)
        proceedToPaymentButton = findViewById(R.id.proceedToPaymentButton)

        // Iterate through cart items and display them in your layout
        for (item in cartItems) {
            // Inflate the layout for each item
            val itemLayout = layoutInflater.inflate(R.layout.item_layout, null)

            // Find views in the layout and set their values based on the item data
            val titleTextView: TextView = itemLayout.findViewById(R.id.title)
            val priceTextView: TextView = itemLayout.findViewById(R.id.price)
            val descriptionTextView: TextView = itemLayout.findViewById(R.id.description)
            val categoryTextView: TextView = itemLayout.findViewById(R.id.category)
            val ratingTextView: TextView = itemLayout.findViewById(R.id.rating)
            val imageView: ImageView = itemLayout.findViewById(R.id.image)

            titleTextView.text = item.title
            priceTextView.text = "Price: $${item.price}"
            descriptionTextView.text = item.description
            categoryTextView.text = "Category: ${item.category}"
            ratingTextView.text = "Rating: ${item.rating.rate} (${item.rating.count} reviews)"
            // Load image using Glide
            Glide.with(imageView.context).load(item.image).into(imageView)

            // Add the inflated layout to the parent layout
            parentLayout.addView(itemLayout)
        }

        // Calculate the total sum of prices
        val totalSum = cartItems.sumByDouble { it.price }

        // Update the text of the "Proceed to payment" button
        updateProceedToPaymentButtonText(totalSum)
    }

    private fun updateProceedToPaymentButtonText(totalSum: Double) {
        val buttonText = "Proceed to payment ${totalSum.formatAsCurrency()} total"
        proceedToPaymentButton.text = buttonText
    }

    // Add this extension function to format double as currency
    private fun Double.formatAsCurrency(): String {
        val currencyFormatter = NumberFormat.getCurrencyInstance()
        return currencyFormatter.format(this)
    }

    fun onProceedToPaymentClick(view: View) {
        Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show()
    }
}
