package verticle

import handler.BookHandler
import io.vertx.core.AbstractVerticle
import io.vertx.core.Vertx
import io.vertx.core.http.HttpServer
import io.vertx.core.json.JsonObject
import io.vertx.ext.mongo.MongoClient
import io.vertx.ext.web.Router
import repository.BookRepository
import router.BookRouter
import service.BookService



class MainVerticle : AbstractVerticle() {

    override fun start() {
        val mongoconfig = JsonObject()
            .put("connection_string", "mongodb://localhost:27017")
            .put("db_name", "books")
        val client = createMongoClient(vertx, mongoconfig)
        val bookRepository = BookRepository(client)
        val bookService = BookService(bookRepository)
        val bookHandler = BookHandler(bookService)
        val bookRouter = BookRouter(vertx, bookHandler)
        createHttpServer(bookRouter.router)
    }

    // Private methods
    private fun createMongoClient(vertx: Vertx, configurations: JsonObject): MongoClient {
        val config = JsonObject()
            .put("host", configurations.getString("localhost"))
            //.put("username", configurations.getString("user"))
            //.put("password", configurations.getString("pwd"))
            //.put("authSource", configurations.getString("AUTHSOURCE"))
            .put("db_name", configurations.getString("books"))
            //.put("useObjectId", true)

        return MongoClient.createShared(vertx, config)
    }

    private fun createHttpServer(router: Router): HttpServer {
        return vertx
            .createHttpServer()
            .requestHandler(router::accept)
            .listen(8080)
    }

}



