package handler

import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import io.vertx.reactivex.ext.web.RoutingContext
import model.Book
import service.BookService
import java.lang.IllegalArgumentException
import java.util.NoSuchElementException

class BookHandler(private val bookService: BookService) {
    fun getAll(rc: RoutingContext) {
        bookService.all
            .subscribe(
                { result -> onSuccessResponse(rc, 200, result) },
                { throwable -> onErrorResponse(rc, 400, throwable) })
    }

    fun getOne(rc: RoutingContext) {
        val id = rc.pathParam("id")

        bookService.getById(id)
            .subscribe(
                { result -> onSuccessResponse(rc, 200, result) },
                { throwable -> onErrorResponse(rc, 400, throwable) },
                { onErrorResponse(rc, 400, NoSuchElementException("No book with id $id")) })
    }

    fun insertOne(rc: RoutingContext) {
        val book = mapRequestBodyToBook(rc)

        bookService.insert(book)
            .subscribe(
                { result -> onSuccessResponse(rc, 201, result) },
                { throwable -> onErrorResponse(rc, 400, throwable) })
    }

    fun updateOne(rc: RoutingContext) {
        val id = rc.pathParam("id")
        val book = mapRequestBodyToBook(rc)

        bookService.update(id, book)
            .subscribe(
                { onSuccessResponse(rc, 204, null) },
                { throwable -> onErrorResponse(rc, 400, throwable) })
    }

    fun deleteOne(rc: RoutingContext) {
        val id = rc.pathParam("id")

        bookService.delete(id)
            .subscribe(
                { onSuccessResponse(rc, 204, null) },
                { throwable -> onErrorResponse(rc, 400, throwable) })
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
    private fun onSuccessResponse(rc: RoutingContext, status: Int, 'object': Any?) {
        rc.response()
            .setStatusCode(status)
            .putHeader("Content-Type", "application/json")
            .end(Json.encodePrettily('object'))
    }

    private fun onErrorResponse(rc: RoutingContext, status: Int, throwable: Throwable) {
        val error = JsonObject().put("error", throwable.message)

        rc.response()
            .setStatusCode(status)
            .putHeader("Content-Type", "application/json")
            .end(Json.encodePrettily(error))
    }
}