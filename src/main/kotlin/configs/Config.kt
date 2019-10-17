package configs

import io.vertx.core.json.JsonObject

object Config {
    private var config: JsonObject = JsonObject().put(
        "connection_string",
        //"mongodb+srv://Godric:dolfyn95**@ivr-db-n5wlx.mongodb.net/test?retryWrites=true&w=majority"
        "mongodb://localhost:27017/BookStoreDb"
    )

    fun getConfig(): JsonObject {
        return config
    }
}