package service

import model.Book
import repository.BookRepository

class BookService(private val bookRepository: BookRepository) {
    val all: Single<List<Book>>
        get() = bookRepository.all

    fun getById(id: String): Maybe<Book> {
        return bookRepository.getById(id)
    }

    fun insert(book: Book): Maybe<Book> {
        return bookRepository.insert(book)
    }

    fun update(id: String, book: Book): Completable {
        return bookRepository.update(id, book)
    }

    fun delete(id: String): Completable {
        return bookRepository.delete(id)
    }
}