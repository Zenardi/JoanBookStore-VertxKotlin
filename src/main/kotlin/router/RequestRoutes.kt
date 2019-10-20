package router

import controllers.Requests
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import model.Book
import org.bson.BsonType


///TODO -Filter: Only show books with stock >0
///TODO -implement order

class RequestRoutes(vertx: Vertx) {
    var router: Router = Router.router(vertx)

    init {
        router.get("/books").handler { routingContext ->
            Requests.getAllBooks(routingContext, "Books")
        }

        router.get("/books/:isbn").handler { routingContext ->
            //Requests.index(routingContext)
            //val  findJson  =JsonObject().put("ISBN", routingContext.request().getParam("isbn").toBigInteger())

            val findJson = JsonObject().put("ISBN", routingContext.request().getParam("isbn").toLong())
            print(findJson)
            Requests.getByIsbn(routingContext, "Books", findJson)


        }

        router.patch("/books/:isbn").handler { routingContext ->
            var isbnJson : JsonObject = JsonObject()
            isbnJson.put("ISBN", routingContext.request().getParam("isbn").toLong())

            val  body = routingContext.bodyAsJson
            val qtyValue = body.getString("Qty")
            val fieldsToUpdate = JsonObject().put(
                "\$set", JsonObject()
                    .put("Qty", qtyValue)
            )

            Requests.updateIsbn(routingContext, "Books", fieldsToUpdate , isbnJson)
        }

        router.post("/book/new").handler { routingContext ->
            val newBook = routingContext.bodyAsJson
            print(newBook)
            Requests.addBook(routingContext, "Books", newBook)
        }

        router.get("/book/search").handler{
            routingContext ->

            //Get parameters
            val author = routingContext.request().getParam("author")
            val genre = routingContext.request().getParam("genre")
            val title = routingContext.request().getParam("title")
            var findJson : String = "{ "
            var jsonObj = JsonObject()
            if(!author.isNullOrBlank() && !author.isNullOrEmpty())
            {
                findJson += "\"Author\"" + ":" + "\"" + author  + "\"" + ", "
                jsonObj.put("Author", author)
            }

            if(!genre.isNullOrBlank() && !genre.isNullOrEmpty())
            {
                findJson += "\"Genre\"" + ":" + "\"" + genre  + "\"" + ", "
                jsonObj.put("Genre", genre )

            }

            if(!title.isNullOrBlank() && !title.isNullOrEmpty())
            {
                findJson += "\"Title\"" + ":" + "\"" + title  + "\"" + ", "
                jsonObj.put("Title", title)

            }

            //remove last comma
            findJson += "\"Quantity\"" +" :  {"+ "$"+ "gte : 60} }"
            //findJson = findJson.substring(0, findJson.length - 2)
            //print("JSON: " + findJson)

            jsonObj.put("Qty", JsonObject().put("\$gte", 1));
            print(jsonObj.toString())
//
//            jsonObj.put(
//                        "\$gte", JsonObject()
//                    .put("Qty", 60))
            Requests.getAllBooks(routingContext, "Books", jsonObj)
        }


    }

    protected fun getBsonType(value: Any?): BsonType? {
        return if (value == null) {
            BsonType.NULL
        } else if (value is Boolean) {
            BsonType.BOOLEAN
        } else if (value is Double) {
            BsonType.DOUBLE
        } else if (value is Int) {
            BsonType.INT32
        } else if (value is Long) {
            BsonType.INT64
        } else if (value is String) {
            BsonType.STRING
        }
//        else if (isObjectIdInstance(value)) {
//            BsonType.OBJECT_ID
//        } else if (isObjectInstance(value)) {
//            BsonType.DOCUMENT
//        } else if (isArrayInstance(value)) {
//            BsonType.ARRAY
//        }
        else {
            null
        }
    }

}
