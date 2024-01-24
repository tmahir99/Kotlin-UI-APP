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
            finish()
        }

        val cartItems = ShoppingCartManager.getCartItems()

        val parentLayout: LinearLayout = findViewById(R.id.parentLayout)
        proceedToPaymentButton = findViewById(R.id.proceedToPaymentButton)

        for (item in cartItems) {
            val itemLayout = layoutInflater.inflate(R.layout.item_layout, null)

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
            Glide.with(imageView.context).load(item.image).into(imageView)

            parentLayout.addView(itemLayout)
        }

        val totalSum = cartItems.sumByDouble { it.price }

        updateProceedToPaymentButtonText(totalSum)
    }

    private fun updateProceedToPaymentButtonText(totalSum: Double) {
        val buttonText = "Proceed to payment ${totalSum.formatAsCurrency()} total"
        proceedToPaymentButton.text = buttonText
    }

    private fun Double.formatAsCurrency(): String {
        val currencyFormatter = NumberFormat.getCurrencyInstance()
        return currencyFormatter.format(this)
    }

    fun onProceedToPaymentClick(view: View) {
        Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show()
    }
}
