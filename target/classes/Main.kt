import io.vertx.core.Vertx
import io.vertx.reactivex.core.Vertx
import verticle.MainVerticle

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val vertx = Vertx.vertx()

        vertx.rxDeployVerticle(MainVerticle::class.java!!.getName())
            .subscribe(
                { verticle -> println("New verticle started!") },
                { throwable ->
                    println("Error occurred before deploying a new verticle: " + throwable.message)
                    System.exit(1)
                })
    }
}