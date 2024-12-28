import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.example.valyrianvisions.model.CartItem

class CartViewModel (private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _cartItems = mutableStateListOf<CartItem>()
    val cartItems: List<CartItem> get() = _cartItems

    val shippingCost = 5.00

    val subTotal: Double
        get() = _cartItems.sumOf { it.price * it.quantity } + shippingCost

//    Add items to cart
    fun addItemToCart(cartItem: CartItem) {
        _cartItems.add(cartItem)
    }
//    Remove an Item from Cart
    fun removeItem(cartItem: CartItem) {
        _cartItems.remove(cartItem)
    }

//    Clears whole cart list
    fun clearCart() {
        _cartItems.clear()
    }
}

