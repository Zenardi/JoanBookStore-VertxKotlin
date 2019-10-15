package router
import handler.BookHandler
import io.vertx.core.Handler
import io.vertx.core.Vertx
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler

class BookRouter(private val vertx: Vertx, private val bookHandler: BookHandler) {
    val router: Router

        get() {
            val bookRouter = Router.router(vertx)
            bookRouter.route("/api/v1/books*").handler(Handler<RoutingContext> {  BodyHandler.create()})
            bookRouter.get("/api/v1/books").handler( Handler<RoutingContext> { this.bookHandler.getAll(it) })
            bookRouter.get("/api/v1/books/:id").handler(Handler<RoutingContext> { bookHandler.getOne(it) })
            bookRouter.post("/api/v1/books").handler(Handler<RoutingContext> { bookHandler.insertOne(it) })
            bookRouter.put("/api/v1/books/:id").handler(Handler<RoutingContext> { bookHandler.updateOne(it) })
            //bookRouter.delete("/api/v1/books/:id").handler(Handler<RoutingContext> { bookHandler.deleteOne(it) })

            return bookRouter
        }
}