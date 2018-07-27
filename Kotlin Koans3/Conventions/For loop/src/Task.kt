import java.util.NoSuchElementException
class DateRange(val start: MyDate, val end: MyDate): Iterable<MyDate>{
    override fun iterator(): Iterator<MyDate> = object : Iterator<MyDate>{
        var current: MyDate = start
        override fun next(): MyDate {
            if (!hasNext()) {
                throw NoSuchElementException()
            }
            val result = current
            current = current.nextDay()
            return result
        }
        override fun hasNext(): Boolean{
            return current <= end
        }
    }
}

fun iterateOverDateRange(firstDate: MyDate, secondDate: MyDate, handler: (MyDate) -> Unit) {
    for (date in firstDate..secondDate) {
        handler(date)
    }
}
