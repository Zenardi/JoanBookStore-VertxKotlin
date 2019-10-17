package verticle

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import router.RequestRoutes

fun main() {
    val vertx = Vertx.vertx()
    vertx.deployVerticle(MainVerticle())
}

class MainVerticle : AbstractVerticle() {

    override fun start(startFuture: Future<Void>) {
        // create a router object
        val router = Router.router(vertx)

        // create a body handler for all requests
        router.route().handler(BodyHandler.create())
        mapRoutes(router)

        // create server
        vertx
            .createHttpServer()
            .requestHandler(router::accept)
            .listen(8888) { http ->
                if (http.succeeded()) {
                    startFuture.complete()
                    println("HTTP server started on port 8888")
                } else {
                    startFuture.fail(http.cause())
                    println("Server couldn't be started due to: ${http.cause()}")
                }
            }
    }


    private fun mapRoutes(router: Router) {
        router.mountSubRouter("/", RequestRoutes(vertx).router)
    }

}



