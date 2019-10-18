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

        router.put("/books/:isbn").handler { routingContext ->
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

        router.get("/book/search").handler{
            routingContext ->

//            val params = routingContext.queryParams()
//            val authorParam = params.getAll("author")
//            val titleParam = params.getAll("title")
//            val genreParam = params.getAll("genre")

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

            print(findJson)
            Requests.getAllBooks(routingContext, "Books", findJson)
            //create a Json object based on not null parameters
        }

    }
}
