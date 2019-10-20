package controllers

import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import io.vertx.ext.mongo.FindOptions
import io.vertx.ext.web.RoutingContext
import utils.MongoUtil

class Requests {
    companion object {
        fun getAllBooks(routingContext: RoutingContext, collectionName: String) {
            val json = JsonObject()
            //val requestData = routingContext.bodyAsJson

            val options = FindOptions().setSort(JsonObject().put("Genre", 1).put("Author", 1))
            print(options.toJson())
            MongoUtil.getClient().findWithOptions(collectionName, json, options) { result ->
                if (result.succeeded()) {
                    val book = result.result()
                    val response = routingContext.response()
                    response
                        .putHeader("content-type", "application/json")
                        .end(Json.encode(book))
                } else {
                    val response = routingContext.response()
                    response.putHeader("content-type", "application/json").setStatusCode(400).end(Json.encode(result.cause().message))
                    println("Update Error: " + result.cause())
                }
            }
        }




        fun getByIsbn(routingContext: RoutingContext, collectionName: String, jsonQuery: JsonObject) {
            //print(jsonQuery)
            MongoUtil.getClient().find(collectionName, jsonQuery) { result ->
                if (result.succeeded()) {
                    //println("vim")
                    val book = result.result()
                    val response = routingContext.response()
                    response
                        .putHeader("content-type", "application/json").setStatusCode(200)
                        .end(Json.encode(book))
                } else {
                    val response = routingContext.response()
                    response.putHeader("content-type", "application/json").setStatusCode(400).end(Json.encode(result.cause().message))

                }
            }
        }



        fun updateIsbn(routingContext: RoutingContext, collectionName: String, fieldsToUpdate: JsonObject, isbnJson: JsonObject) {

            MongoUtil.getClient().updateCollection(collectionName, isbnJson, fieldsToUpdate) { result ->
                if (result.succeeded()) {
                    val book = result.result()
                    val response = routingContext.response()
                    response
                        .putHeader("content-type", "application/json")
                        .end(Json.encodePrettily(book.toJson()))
                } else {
                    val response = routingContext.response()
                    response.putHeader("content-type", "application/json").setStatusCode(400).end(Json.encode(result.cause().message))
                    println("Update Error: " + result.cause())
                }
            }
        }


        fun addBook(routingContext: RoutingContext, collectionName: String, newBook : JsonObject) {

            MongoUtil.getClient().insert(collectionName, newBook) { res ->
                if (res.succeeded()) {
                    val book = res.result()
                    val response = routingContext.response()
                    response
                        .putHeader("content-type", "application/json")
                        .end(Json.encode(newBook))

                } else {
                    val response = routingContext.response()
                    response.putHeader("content-type", "application/json").setStatusCode(400).end(Json.encode(res.cause().message))
                    println("Update Error: " + res.cause())
                }
            }
        }

        fun getAllBooks(routingContext: RoutingContext, collectionName: String, findJson: JsonObject) {

            val options = FindOptions().setSort(JsonObject().put("Genre", 1).put("Author", 1))

            MongoUtil.getClient().findWithOptions(collectionName, findJson, options) { result ->
                if (result.succeeded()) {
                    val book = result.result()
                    val response = routingContext.response()
                    response
                        .putHeader("content-type", "application/json")
                        .end(Json.encode(book))
                } else {
                    val response = routingContext.response()
                    response.putHeader("content-type", "application/json").setStatusCode(400).end(Json.encode(result.cause().message))
                    println("Update Error: " + result.cause())
                }
            }
        }


    }
}