package router

import controllers.Requests
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import org.bson.BsonType




class RequestRoutes(vertx: Vertx) {
    var router: Router = Router.router(vertx)

    init {
        router.get("/books").handler { routingContext ->
            Requests.getAllBooks(routingContext, "Books")
        }

        router.get("/books/:isbn").handler { routingContext ->
            //Requests.index(routingContext)
            //val  findJson  =JsonObject().put("ISBN", routingContext.request().getParam("isbn").toBigInteger())

            val findJson = JsonObject( "{ \"ISBN\"" + " : " + routingContext.request().getParam("isbn") + "}")
            print(findJson)
            Requests.getByIsbn(routingContext, "Books", findJson)


        }

        router.patch("/books/:isbn").handler { routingContext ->
            //val  findJson = JsonObject(BsonType.INT64).put("ISBN", routingContext.request().getParam("isbn").toBigInteger())
            //val findJson = JsonObject( "{ \"ISBN\"" + " : " + routingContext.request().getParam("isbn") + "}")
            val findJson = "{ \"ISBN\"" + " : " + routingContext.request().getParam("isbn") + "}"

            print(findJson)

            val  fieldsToUpdate = routingContext.bodyAsJson
            print(fieldsToUpdate)
            Requests.updateIsbn(routingContext, "Books", fieldsToUpdate , findJson)
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
            var findJson = "{ "

            if(!author.isNullOrBlank() && !author.isNullOrEmpty())
            {
                findJson += "\"Author\"" + ":" + "\"" + author  + "\"" + ", "
            }

            if(!genre.isNullOrBlank() && !genre.isNullOrEmpty())
            {
                findJson += "\"Genre\"" + ":" + "\"" + genre  + "\"" + ", "
            }

            if(!title.isNullOrBlank() && !title.isNullOrEmpty())
            {
                findJson += "\"Title\"" + ":" + "\"" + title  + "\"" + ", "
            }

            //remove last comma
            findJson = findJson.substring(0, findJson.length - 2)
            findJson += " }"

            //print(findJson)
            Requests.getAllBooks(routingContext, "Books", findJson)
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
