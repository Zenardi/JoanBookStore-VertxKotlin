package router
import handler.BookHandler
import io.vertx.core.Vertx
import io.vertx.reactivex.core.Vertx
import io.vertx.reactivex.ext.web.Router
import io.vertx.reactivex.ext.web.handler.BodyHandler

class BookRouter(private val vertx: Vertx, private val bookHandler: BookHandler) {
    val router: Router
        get() {
            val bookRouter = Router.router(vertx)

            bookRouter.route("/api/v1/books*").handler(BodyHandler.create())
            bookRouter.get("/api/v1/books").handler(Handler<RoutingContext> { bookHandler.getAll(it) })
            bookRouter.get("/api/v1/books/:id").handler(Handler<RoutingContext> { bookHandler.getOne(it) })
            bookRouter.post("/api/v1/books").handler(Handler<RoutingContext> { bookHandler.insertOne(it) })
            bookRouter.put("/api/v1/books/:id").handler(Handler<RoutingContext> { bookHandler.updateOne(it) })
            bookRouter.delete("/api/v1/books/:id").handler(Handler<RoutingContext> { bookHandler.deleteOne(it) })

            return bookRouter
        }
}