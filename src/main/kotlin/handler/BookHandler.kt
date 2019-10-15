package handler

import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext
import model.Book
import service.BookService
import java.lang.IllegalArgumentException
import java.util.NoSuchElementException


class BookHandler(bookService: BookService) {


    private var bookService: BookService? = null

    fun BookHandler(bookService: BookService) {
        this.bookService = bookService
    }

    fun getAll(rc: RoutingContext) {

        bookService?.getAll()?.subscribe(
            { result -> onSuccessResponse(rc, 200, result)  },
            { throwable -> onErrorResponse(rc, 400, throwable) })
    }

    fun getOne(rc: RoutingContext) {
        val id = rc.pathParam("id")

        bookService?.getById(id)?.subscribe(
            { result -> onSuccessResponse(rc, 200, result) },
            { throwable -> onErrorResponse(rc, 400, throwable) })
    }

    fun insertOne(rc: RoutingContext) {
        val book = mapRequestBodyToBook(rc)

        bookService?.insert(book)
    }

    fun updateOne(rc: io.vertx.ext.web.RoutingContext) {
        val id = rc.pathParam("id")
        val book = mapRequestBodyToBook(rc)

        bookService?.update(id, book)
    }

    // Mapping between book class and request body JSON object
    private fun mapRequestBodyToBook(rc: RoutingContext): Book {
        var book = Book()

        try {
            book = rc.bodyAsJson.mapTo(Book::class.java)
        } catch (ex: IllegalArgumentException) {
            onErrorResponse(rc, 400, ex)
        }

        return book
    }

    // Generic responses
    private fun onSuccessResponse(rc: RoutingContext, status: Int, obj: Any?) {
        rc.response()
            .setStatusCode(status)
            .putHeader("Content-Type", "application/json")
            .end(Json.encodePrettily("obj"))
    }

    private fun onErrorResponse(rc: RoutingContext, status: Int, throwable: Throwable) {
        val error = JsonObject().put("error", throwable.message)

        rc.response()
            .setStatusCode(status)
            .putHeader("Content-Type", "application/json")
            .end(Json.encodePrettily(error))
    }
}