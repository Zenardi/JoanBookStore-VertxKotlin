package verticle

import handler.BookHandler
import io.vertx.core.AbstractVerticle
import io.vertx.core.Vertx
import io.vertx.core.http.HttpServer
import io.vertx.core.json.JsonObject
import io.vertx.reactivex.ext.web.Router
import repository.BookRepository
import router.BookRouter
import service.BookService

class MainVerticle : AbstractVerticle() {

    override fun start() {
        val store = ConfigStoreOptions().setType("env")
        val options = ConfigRetrieverOptions().addStore(store)
        val retriever = ConfigRetriever.create(vertx, options)

        retriever.rxGetConfig()
            .flatMap { configurations ->
                val client = createMongoClient(vertx, configurations)

                val bookRepository = BookRepository(client)
                val bookService = BookService(bookRepository)
                val bookHandler = BookHandler(bookService)
                val bookRouter = BookRouter(vertx, bookHandler)

                createHttpServer(bookRouter.router, configurations)
            }
            .subscribe(
                { server -> println("HTTP Server listening on port "/* + server.actualPort()*/) },
                { throwable ->
                    println("Error occurred before creating a new HTTP server: "/* + throwable.message*/)
                    System.exit(1)
                })
    }

    // Private methods
    private fun createMongoClient(vertx: Vertx, configurations: JsonObject): MongoClient {
        val config = JsonObject()
            .put("host", configurations.getString("localhost"))
            .put("username", configurations.getString("user"))
            .put("password", configurations.getString("pwd"))
            //                .put("authSource", configurations.getString("AUTHSOURCE"))
            .put("db_name", configurations.getString("test"))
            .put("useObjectId", true)

        return MongoClient.createShared(vertx, config)
    }

    private fun createHttpServer(router: Router, configurations: JsonObject): HttpServer {
        return vertx
            .createHttpServer()
            .requestHandler(router)
            .rxListen(configurations.getInteger("HTTP_PORT")!!)
    }

}



