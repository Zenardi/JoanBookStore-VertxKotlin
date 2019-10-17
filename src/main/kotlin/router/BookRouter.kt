package router
import handler.BookHandler
import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler

class BookRouter(private val vertx: Vertx, private val bookHandler: BookHandler) {
//
//    constructor(vertx: Vertx, bookHandler: BookHandler) {
//        this.vertx = vertx
//        this.bookHandler = bookHandler
//    }

     fun getRouter() : Router{
            val bookRouter: Router = Router.router(vertx)
            bookRouter.route("/api/v1/books*").handler( BodyHandler.create())
            bookRouter.get("/api/v1/books").handler(bookHandler::getAll)
            bookRouter.get("/api/v1/books/:id").handler(bookHandler::getOne)
            bookRouter.post("/api/v1/books").handler(bookHandler::insertOne)
            bookRouter.put("/api/v1/books/:id").handler(bookHandler::updateOne)

            return bookRouter
        }
}