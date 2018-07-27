class PropertyExample() {
    var counter = 0
    var propertyWithCounter: Int? = null
        set(a: Int?){
           field = a
            counter++
        }
}
