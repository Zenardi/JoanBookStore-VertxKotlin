package repository
import io.reactivex.Completable
import io.reactivex.Single
import io.vertx.core.json.JsonObject
import io.vertx.ext.mongo.MongoClient
import io.vertx.reactivex.redis.client.Response
import model.Book
import java.util.ArrayList
import java.util.NoSuchElementException


class BookRepository (client: MongoClient){

    private val COLLECTION_NAME = "Books"

    private var client: MongoClient? = null

    fun BookRepository(client: MongoClient) {
        this.client = client
    }

    fun getAll(): Single<ArrayList<Book>> {
        val query = JsonObject()
        val books = ArrayList<Book>()
        client?.find(COLLECTION_NAME, query) { res ->
            if (res.succeeded()) {
                print(res.result())
                for (json in res.result()) {
                    json.forEach { book -> books.add(Book(book)) }
                }

            } else {
                res.cause().printStackTrace()
            }
        }

        return Single.just(books);
    }


    fun getById(id: String): Single<ArrayList<Book>> {
        val query = JsonObject().put("_id", id)
        val books = ArrayList<Book>()
       client?.find(COLLECTION_NAME, query) { res ->
            if (res.succeeded()) {

                for (json in res.result()) {
                    json.forEach { book -> books.add(Book(book)) }
                }

            } else {
                res.cause().printStackTrace()
            }
        }
        return Single.just(books);
    }


    fun update(id :String, book: Book) {
         val query = JsonObject().put("_id", id);
        val books = ArrayList<Book>()

        client?.updateCollection(COLLECTION_NAME, query, JsonObject.mapFrom(book), { res ->
            if (res.succeeded()) {
                println("Book updated !")
                Completable.complete()
            } else {
                Completable.error(NoSuchElementException("No book with id " + id))
            }
        })

    }

    fun insert(book : Book){
        client?.insert(COLLECTION_NAME, JsonObject.mapFrom(book), { res ->
            if (res.succeeded()) {
                var id = res.result()
                println("Inserted book with id ${id}")
            } else {
                res.cause().printStackTrace()
            }
        })

    }
}