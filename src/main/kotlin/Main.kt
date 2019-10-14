import io.vertx.core.Vertx
import io.vertx.core.Vertx.vertx
import io.vertx.core.json.Json
import io.vertx.ext.web.Router
//import io.vertx.reactivex.core.Vertx
import verticle.MainVerticle

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
            val vertx = vertx()
    val httpServer = vertx.createHttpServer()


    val router = Router.router(vertx)
    router.get("/")
            .handler(
                {
                    routingContext ->
                    val response = routingContext.response()
                    response.putHeader("content-type", "text/plain")
                            .setChunked(true)
                            .write("hi \n")
                            .end("ended")
            }
            )

    router.get("/json/:name").handler(
            {
                routingContext ->
                val request = routingContext.request()
                val name = request.getParam("name")
                val response = routingContext.response()
                response.putHeader("content-type", "application/json")
                        .setChunked(true)
                        .write(Json.encodePrettily(ResponseObject(name)))
                        .end()
            }
    )


    httpServer
            .requestHandler(router::accept)
            .listen(8080)
    }

//        val vertx = Vertx.vertx()
//
//        vertx.deployVerticle(MainVerticle::class.objectInstance)
//            .subscribe(
//                { verticle -> println("New verticle started!") },
//                { throwable ->
//                    println("Error occurred before deploying a new verticle: "/* + throwable.messmessage*/)
//                    System.exit(1)
//                })
//    }
}

data class ResponseObject(var name:String = "")
