import io.reactivex.Completable
import io.vertx.core.Vertx
import io.vertx.core.Vertx.vertx
import io.vertx.core.json.Json
import io.vertx.ext.web.Router
//import io.vertx.reactivex.core.Vertx
import verticle.MainVerticle

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val vertx = Vertx.vertx()

        vertx.deployVerticle("verticle.MainVerticle", { res ->
            if (res.succeeded()) {

                println("Deployment id is: ${res.result()}")
            } else {
                println("Deployment failed!")
            }
        })
    }
}

data class ResponseObject(var name:String = "")
