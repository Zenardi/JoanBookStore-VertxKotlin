package service

import model.Book
import repository.BookRepository

class BookService(private val bookRepository: BookRepository) {
    val all: List<Book>
        get() = bookRepository.all

    fun getById(id: String): Book {
        return bookRepository.getById(id)
    }

    fun insert(book: Book): Book {
        return bookRepository.insert(book)
    }

    fun update(id: String, book: Book): Book {
        return bookRepository.update(id, book)
    }

    fun delete(id: String): Book {
        return bookRepository.delete(id)
    }
}