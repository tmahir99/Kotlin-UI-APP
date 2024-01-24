import com.max.commerc.ItemModel

object ShoppingCartManager {
    private val cartItems = mutableListOf<ItemModel>()
    private var itemCount = 0

    private val itemCountListeners = mutableListOf<OnItemCountChangeListener>()

    fun addToCart(item: ItemModel) {
        cartItems.add(item)
        itemCount++
        notifyItemCountChange()
    }

    fun getCartItems(): List<ItemModel> {
        return cartItems
    }

    fun getItemCount(): Int {
        return itemCount
    }

    fun registerOnItemCountChangeListener(listener: OnItemCountChangeListener) {
        itemCountListeners.add(listener)
    }

    fun unregisterOnItemCountChangeListener(listener: OnItemCountChangeListener) {
        itemCountListeners.remove(listener)
    }

    private fun notifyItemCountChange() {
        for (listener in itemCountListeners) {
            listener.onItemCountChange(itemCount)
        }
    }

    interface OnItemCountChangeListener {
        fun onItemCountChange(newCount: Int)
    }
}
