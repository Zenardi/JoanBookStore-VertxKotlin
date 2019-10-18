package com.bookstore.vertx

//import io.vertx.codetrans.annotations.CodeTranslate
import controllers.Requests
import io.vertx.core.Vertx
import io.vertx.core.http.HttpClient
import io.vertx.core.http.HttpServer
import io.vertx.core.json.JsonObject
import io.vertx.ext.unit.Async
import io.vertx.ext.unit.TestOptions
import io.vertx.ext.unit.TestSuite
import io.vertx.ext.unit.report.ReportOptions
import io.vertx.reactivex.ext.web.codec.BodyCodec.json

class VertxUnitTest {

    internal var vertx= Vertx.vertx()

    fun run() {

        val options = TestOptions().addReporter(ReportOptions().setTo("console"))
        val suite = TestSuite.create("io.vertx.example.unit.test.VertxUnitTest")

        suite.before { context ->
            //vertx = Vertx.vertx()
            vertx.createHttpServer().requestHandler { req -> req.response().end("foo") }
                .listen(8888, context.asyncAssertSuccess())

        }

        suite.after { context -> vertx.close(context.asyncAssertSuccess()) }

        // Specifying the test names seems ugly...
        suite.test("GetAll") { context ->
            // Send a request and get a response
            val client = vertx.createHttpClient()
            val async = context.async()
            client.getNow(8888, "localhost", "/books") { resp ->
                resp.bodyHandler { body -> context.assertEquals(200, resp.statusCode()) }
                client.close()
                async.complete()
            }
        }

        suite.test("GetByIsbn") { context ->
            // Send a request and get a response
            val client = vertx.createHttpClient()
            val async = context.async()
            client.getNow(8888, "localhost", "/books/399226907") { resp ->
                resp.bodyHandler { body -> context.assertEquals(200, resp.statusCode()) }
                client.close()
                async.complete()
            }
        }

        suite.test("FindBook") { context ->
            // Send a request and get a response
            val client = vertx.createHttpClient()
            val async = context.async()
            client.getNow(8888, "localhost", "/book/search?author=Eric Carle&title=The Very Hungry Caterpillar&genre=Children") { resp ->
                resp.bodyHandler { body -> context.assertEquals(200, resp.statusCode()) }
                client.close()
                async.complete()
            }
        }



        suite.test("CreateBook") { context ->
            // Send a request and get a response
            val client = vertx.createHttpClient()
            val  jsonNewBook = JsonObject()
                .put("ISBN", "0")
                .put("Title", "Test")
                .put("Price", "99")


            val async = context.async()
            client.post(8888, "localhost", "/book/new") { resp ->
                resp.bodyHandler { body -> context.assertEquals(201, resp.statusCode()) }
                client.close()
                async.complete()
            }
        }


//        suite.test("GetByIsbn") { context ->
//            // Deploy and undeploy a verticle
//            vertx.deployVerticle(
//                "io.vertx.example.unit.SomeVerticle",
//                context.asyncAssertSuccess { deploymentID ->
//                    vertx.undeploy(
//                        deploymentID,
//                        context.asyncAssertSuccess()
//                    )
//                })
//        }

        suite.run(options)
    }

    companion object {


        @JvmStatic
        fun main(args: Array<String>) {
            VertxUnitTest().run()
        }
    }
}