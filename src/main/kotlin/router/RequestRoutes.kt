package router

import controllers.Requests
import io.vertx.core.Vertx
import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router


class RequestRoutes(vertx: Vertx) {
    var router: Router = Router.router(vertx)

    init {
        router.get("/books").handler { routingContext ->
            Requests.getAllBooks(routingContext, "Books")
        }

        router.get("/books/:isbn").handler { routingContext ->
            //Requests.index(routingContext)
            val  jsonQuery = JsonObject().put("ISBN", routingContext.request().getParam("isbn"))
            Requests.getByIsbn(routingContext, "Books", jsonQuery)

        }

        router.patch("/books/:isbn").handler { routingContext ->
            val  jsonIsbn = JsonObject().put("ISBN", routingContext.request().getParam("isbn"))
            print(jsonIsbn)

            val  fieldsToUpdate = routingContext.bodyAsJson
            print(fieldsToUpdate)
            Requests.updateIsbn(routingContext, "Books", fieldsToUpdate , jsonIsbn)
        }

        router.post("/book/new").handler { routingContext ->
            val newBook = routingContext.bodyAsJson
            print(newBook)
            Requests.addBook(routingContext, "Books", newBook)
        }

    }
}
