package controllers

import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext
import model.Book
import utils.MongoUtil
import java.util.ArrayList

class Requests {
    companion object {
        fun getAllBooks(routingContext: RoutingContext, collectionName: String) {
            val json = JsonObject()
            //val requestData = routingContext.bodyAsJson

            MongoUtil.getClient().find(collectionName, json) { result ->
                if (result.succeeded()) {
                    //println("vim")
                    val book = result.result()
                    val response = routingContext.response()
                    response
                        .putHeader("content-type", "application/json")
                        .end(Json.encode(book))
                } else {
                    println("Nope")
                }
            }
        }




        fun getByIsbn(routingContext: RoutingContext, collectionName: String, jsonQuery: JsonObject) {
            //print(jsonQuery)
            MongoUtil.getClient().find(collectionName, jsonQuery, { result ->
                if (result.succeeded()) {
                    //println("vim")
                    val book = result.result()
                    val response = routingContext.response()
                    response
                        .putHeader("content-type", "application/json")
                        .end(Json.encode(book))
                } else {
                    println("Nope")
                }
            })
        }



        fun updateIsbn(routingContext: RoutingContext, collectionName: String, fieldsToUpdate: JsonObject, isbnJson : JsonObject) {

            MongoUtil.getClient().updateCollection("books", isbnJson, fieldsToUpdate, { res ->
                if (res.succeeded()) {
                    println("Book updated !")
                } else {
                    res.cause().printStackTrace()
                }
            })


//            MongoUtil.getClient().updateCollection(collectionName, isbnJson, fieldsToUpdate, { result ->
//                if (result.succeeded()) {
//                    //println("vim")
//                    val book = result.result()
//                    val response = routingContext.response()
//                    response
//                        .putHeader("content-type", "application/json")
//                        .end(Json.encode(book))
//                } else {
//                    println(result)
//                }
//            })
        }


        fun addBook(routingContext: RoutingContext, collectionName: String, newBook : JsonObject) {

            MongoUtil.getClient().insert(collectionName, newBook, { res ->
                if (res.succeeded()) {
                    val book = res.result()
                    val response = routingContext.response()
                    response
                        .putHeader("content-type", "application/json")
                        .end(Json.encode(newBook))

                } else {
                    println(res.cause())
                }
            })
        }




    }
}