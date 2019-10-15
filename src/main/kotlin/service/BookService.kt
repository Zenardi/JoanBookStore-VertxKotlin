package service


import io.reactivex.Completable
import io.reactivex.Single
import io.vertx.ext.mongo.MongoClient
import model.Book
import repository.BookRepository
import java.util.ArrayList

class BookService(bookRepository: BookRepository) {

    private var bookRepository: BookRepository? = null

    fun BookService(bookRepository: BookRepository) {
        this.bookRepository = bookRepository
    }

    fun getAll(): Single<ArrayList<Book>>? {
        return bookRepository?.getAll()
    }

    fun getById(id: String): Single<ArrayList<Book>>? {
        return bookRepository?.getById(id)
    }

//    fun insert(book: Book): Maybe<Book> {
//        return bookRepository.insert(book)
//    }
//
    fun update(id: String, book: Book): Completable {
        return bookRepository.update(id, book)
    }
}