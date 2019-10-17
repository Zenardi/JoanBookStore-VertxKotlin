package configs

import io.vertx.core.json.JsonObject

object Config {
    private var config: JsonObject = JsonObject().put(
        "connection_string",
        "mongodb://localhost:27017/BookstoreDb"
    )

    fun getConfig(): JsonObject {
        return config
    }
}