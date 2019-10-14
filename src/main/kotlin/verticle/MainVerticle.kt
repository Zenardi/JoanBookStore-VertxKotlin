package verticle

import handler.BookHandler
import io.vertx.config.ConfigRetriever
import io.vertx.config.ConfigRetrieverOptions
import io.vertx.config.ConfigStoreOptions
import io.vertx.core.AbstractVerticle
import io.vertx.core.Vertx
import io.vertx.core.http.HttpServer
import io.vertx.core.json.JsonObject
import io.vertx.ext.mongo.MongoClient
import io.vertx.reactivex.ext.web.Router
import repository.BookRepository
import router.BookRouter
import service.BookService



class MainVerticle : AbstractVerticle() {

    override fun start() {
        val store = ConfigStoreOptions().setType("env")
        val options = ConfigRetrieverOptions().addStore(store)
        val retriever = ConfigRetriever.create(vertx, options)

        retriever.getConfig({ configurations ->
                val client = createMongoClient(vertx, configurations)

                val bookRepository = BookRepository(client)
                val bookService = BookService(bookRepository)
                val bookHandler = BookHandler(bookService)
                val bookRouter = BookRouter(vertx, bookHandler)

                createHttpServer(bookRouter.router, configurations)
            })
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

    private fun createHttpServer(router: Router, configurations: JsonObject): HttpServer {
        return vertx
            .createHttpServer()
            .requestHandler(router)
            .listen(configurations.getInteger("HTTP_PORT")!!)
    }

}



