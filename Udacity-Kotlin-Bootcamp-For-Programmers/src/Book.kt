import kotlin.random.Random


object Constants {
    const val MAX_BOOKS = 100
    const val BASE_URL = "https://www.mylibrary.com"
}

open class Book (val title: String, val author: String, var pages: Int = 0) {
    private var currentPage = 0

    open fun readPage() { currentPage + 1 }

    fun canBorrow(currentBookCount: Int):Boolean {
        return currentBookCount < Constants.MAX_BOOKS
    }

    fun printUrl() = "${BASE_URL}/${title}/.html"

    companion object {
        const val BASE_URL = Constants.BASE_URL
    }
}

class eBook(title: String,
            author: String,
            val format: String = "text") : Book(title, author) {

    private var wordCount = 0
    override fun readPage() {
        wordCount += 250
    }

    init {
        println("Created eBook: '${title}', by ${author}.")
    }
}


// Practice 5.9: Extension functions:

fun Book.weight(): Double = pages * 1.5

//fun Book.tornPages(tornedPages: Int) {
//    pages = tornedPages
//}

fun Book.tornPages(torn: Int) = if (pages >= torn) pages -= torn else pages = 0

class Puppy(title: String, author: String, pages: Int): Book(title, author, pages) {
    fun playWithBook()  {
        //pages = (pages - (0..50).random())
        tornPages((0..50).random())
    }
}

fun practice5_9() {
    val puppy = Puppy("C++", "Bjorn Stroupstroup", 500)
    do {
        println("playing with book. Pages left: ${puppy.pages}.")
        puppy.playWithBook()
    } while(puppy.pages > 0)
}

fun main() {
    val myEBook = eBook("C++", "Bjarne Stroustroup")

    practice5_9()
}