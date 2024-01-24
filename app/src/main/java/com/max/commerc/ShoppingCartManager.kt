import com.max.commerc.ItemModel

// ShoppingCartManager.kt
object ShoppingCartManager {
    private val cartItems = mutableListOf<ItemModel>()
    private var itemCount = 0

    // Add a listener to notify when the item count changes
    private val itemCountListeners = mutableListOf<OnItemCountChangeListener>()

    fun addToCart(item: ItemModel) {
        cartItems.add(item)
        itemCount++
        notifyItemCountChange() // Notify listeners when the item count changes
    }

    fun getCartItems(): List<ItemModel> {
        return cartItems
    }

    fun getItemCount(): Int {
        return itemCount
    }

    // Add a method to register listeners
    fun registerOnItemCountChangeListener(listener: OnItemCountChangeListener) {
        itemCountListeners.add(listener)
    }

    // Add a method to unregister listeners
    fun unregisterOnItemCountChangeListener(listener: OnItemCountChangeListener) {
        itemCountListeners.remove(listener)
    }

    // Notify all registered listeners when the item count changes
    private fun notifyItemCountChange() {
        for (listener in itemCountListeners) {
            listener.onItemCountChange(itemCount)
        }
    }

    // Interface for listeners
    interface OnItemCountChangeListener {
        fun onItemCountChange(newCount: Int)
    }
}
