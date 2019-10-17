package utils

import configs.Config
import io.vertx.core.Vertx
import io.vertx.ext.mongo.MongoClient

class MongoUtil {
    // create a mongodb client
    companion object {
        // create a private mongo client
        private var client = MongoClient.createShared(Vertx.vertx(), Config.getConfig())

        // create a getter method for the private client
        fun getClient(): MongoClient {
            return client
        }
    }

}
