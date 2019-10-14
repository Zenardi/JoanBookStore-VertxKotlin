package service


import model.Book
import repository.BookRepository
import java.util.ArrayList

class BookService() {

    private var bookRepository: BookRepository? = null

    fun BookService(bookRepository: BookRepository) {
        this.bookRepository = bookRepository
    }

    fun getAll(): ArrayList<Book>? {
        return bookRepository?.getAll()
    }

    fun getById(id: String): ArrayList<Book>? {
        return bookRepository?.getById(id)
    }

//    fun insert(book: Book): Maybe<Book> {
//        return bookRepository.insert(book)
//    }
//
//    fun update(id: String, book: Book): Completable {
//        return bookRepository.update(id, book)
//    }
}