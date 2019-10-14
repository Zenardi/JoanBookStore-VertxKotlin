package repository

import io.vertx.core.json.JsonObject
import model.Book
import java.util.ArrayList
import java.util.NoSuchElementException

class BookRepository (private val client: MongoClient){
    val all: Single<List<Book>>
        get() {
            val query = JsonObject()

            return client.rxFind(COLLECTION_NAME, query)
                .flatMap { result ->
                    val books = ArrayList<Book>()
                    result.forEach { book -> books.add(Book(book)) }

                    Single.just<List<Book>>(books)
                }
        }

    fun getById(id: String): Maybe<Book> {
        val query = JsonObject().put("_id", id)

        return client.rxFindOne(COLLECTION_NAME, query, null)
            .flatMap { result ->
                val book = Book(result)

                Maybe.just(book)
            }
    }

    fun insert(book: Book): Maybe<Book> {
        return client.rxInsert(COLLECTION_NAME, JsonObject.mapFrom(book))
            .flatMap { result ->
                val jsonObject = JsonObject().put("_id", result)
                val insertedBook = Book(jsonObject)

                Maybe.just(insertedBook)
            }
    }

    fun update(id: String, book: Book): Completable {
        val query = JsonObject().put("_id", id)

        return client.rxReplaceDocuments(COLLECTION_NAME, query, JsonObject.mapFrom(book))
            .flatMapCompletable { result ->
                if (result.docModified == 1L) {
                    return@client.rxReplaceDocuments(COLLECTION_NAME, query, JsonObject.mapFrom(book))
                        .flatMapCompletable Completable . complete ()
                } else {
                    return@client.rxReplaceDocuments(COLLECTION_NAME, query, JsonObject.mapFrom(book))
                        .flatMapCompletable Completable . error NoSuchElementException("No book with id $id")
                }
            }
    }

    fun delete(id: String): Completable {
        val query = JsonObject().put("_id", id)

        return client.rxRemoveDocument(COLLECTION_NAME, query)
            .flatMapCompletable { result ->
                if (result.removedCount == 1L) {
                    return@client.rxRemoveDocument(COLLECTION_NAME, query)
                        .flatMapCompletable Completable . complete ()
                } else {
                    return@client.rxRemoveDocument(COLLECTION_NAME, query)
                        .flatMapCompletable Completable . error NoSuchElementException("No book with id $id")
                }
            }
    }

    companion object {

        private val COLLECTION_NAME = "books"
    }
}