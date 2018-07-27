data class Shop(val name: String, val customers: List<Customer>)

data class Customer(val name: String, val city: City, val orders: List<Order>) {
    override fun toString() = "$name from ${city.name}"
}
fun Shop.getCitiesCustomersAreFrom(): Set<City> {
    return customers.map { it.city }.toSet()
}
fun Shop.getCustomersFrom(city: City): List<Customer> {
    return customers.filter { it.city == city }
}

data class Order(val products: List<Product>, val isDelivered: Boolean)

data class Product(val name: String, val price: Double) {
    override fun toString() = "'$name' for $price"
}

data class City(val name: String) {
    override fun toString() = name
}

