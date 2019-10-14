package handler

import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext
import model.Book
import service.BookService
import java.lang.IllegalArgumentException
import java.util.NoSuchElementException
import service.BookService
import service.BookService



class BookHandler() {


    private var bookService: BookService? = null

    fun BookHandler(bookService: BookService) {
        this.bookService = bookService
    }

    fun getAll(rc: RoutingContext) {
        bookService.getAll()

            .subscribe(
                { result -> onSuccessResponse(rc, 200, result) },
                { throwable -> onErrorResponse(rc, 400, throwable) })
    }

    fun getOne(rc: io.vertx.ext.web.RoutingContext) {
        val id = rc.pathParam("id")

        bookService.getById(id)
            .subscribe(
                { result -> onSuccessResponse(rc, 200, result) },
                { throwable -> onErrorResponse(rc, 400, throwable) },
                { onErrorResponse(rc, 400, NoSuchElementException("No book with id $id")) })
    }

    fun insertOne(rc: io.vertx.ext.web.RoutingContext) {
        val book = mapRequestBodyToBook(rc)

        bookService.insert(book)
            .subscribe(
                { result -> onSuccessResponse(rc, 201, result) },
                { throwable -> onErrorResponse(rc, 400, throwable) })
    }

    fun updateOne(rc: io.vertx.ext.web.RoutingContext) {
        val id = rc.pathParam("id")
        val book = mapRequestBodyToBook(rc)

        bookService.update(id, book)
            .subscribe(
                { onSuccessResponse(rc, 204, null) },
                { throwable -> onErrorResponse(rc, 400, throwable) })
    }

    // Mapping between book class and request body JSON object
    private fun mapRequestBodyToBook(rc: io.vertx.ext.web.RoutingContext): Book {
        var book = Book()

        try {
            book = rc.bodyAsJson.mapTo(Book::class.java)
        } catch (ex: IllegalArgumentException) {
            onErrorResponse(rc, 400, ex)
        }

        return book
    }

    // Generic responses
    private fun onSuccessResponse(rc: io.vertx.ext.web.RoutingContext, status: Int, obj: Any?) {
        rc.response()
            .setStatusCode(status)
            .putHeader("Content-Type", "application/json")
            .end(Json.encodePrettily("obj"))
    }

    private fun onErrorResponse(rc: io.vertx.ext.web.RoutingContext, status: Int, throwable: Throwable) {
        val error = JsonObject().put("error", throwable.message)

        rc.response()
            .setStatusCode(status)
            .putHeader("Content-Type", "application/json")
            .end(Json.encodePrettily(error))
    }
}