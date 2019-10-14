package repository

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.vertx.core.json.JsonObject
import io.vertx.ext.mongo.MongoClient
import jdk.nashorn.internal.objects.NativeFunction.function
import model.Book
import jdk.nashorn.internal.objects.NativeArray.forEach
import java.util.ArrayList



class BookRepository{

    private val COLLECTION_NAME = "books"

    private var client: MongoClient? = null

    fun BookRepository(client: MongoClient) {
        this.client = client
    }

    fun getAll(): ArrayList<Book> {
        val query = JsonObject()
        val books = ArrayList<Book>()

        val r = client?.find(COLLECTION_NAME, query, { res ->
            if (res.succeeded()) {
                for (json in res.result()) {
                    println(json.toString())
                }
            } else {
                res.cause().printStackTrace()
            }
        })
        return books
    }

    fun getById(id: String): ArrayList<Book> {
        val query = JsonObject().put("_id", id)
        val books = ArrayList<Book>()

        val r =  client?.findOne(COLLECTION_NAME, query,null, { res ->
            if (res.succeeded()) {
                for (json in res.result()) {
                    println(json.toString())
                }

            } else {
                res.cause().printStackTrace()
            }
        })
        return books
    }


}