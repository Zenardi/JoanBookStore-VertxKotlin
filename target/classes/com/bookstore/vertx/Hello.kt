//package com.bookstore.vertx
//
//import io.vertx.core.Vertx
//import io.vertx.core.Vertx.vertx
//import io.vertx.core.json.Json
//import io.vertx.ext.web.Router
//
//fun main(args: Array<String>) {
//    println("Hello, World")
//
//    val vertx = vertx()
//    val httpServer = vertx.createHttpServer()
//
//
//    val router = Router.router(vertx)
//    router.get("/")
//            .handler(
//                {
//                    routingContext ->
//                    val response = routingContext.response()
//                    response.putHeader("content-type", "text/plain")
//                            .setChunked(true)
//                            .write("hi \n")
//                            .end("ended")
//            }
//            )
//
//    router.get("/json/:name").handler(
//            {
//                routingContext ->
//                val request = routingContext.request()
//                val name = request.getParam("name")
//                val response = routingContext.response()
//                response.putHeader("content-type", "application/json")
//                        .setChunked(true)
//                        .write(Json.encodePrettily(ResponseObject(name)))
//                        .end()
//            }
//    )
//
//
//    httpServer
//            .requestHandler(router::accept)
//            .listen(8080)
//}
//
//data class ResponseObject(var name:String = "")