package model

import com.fasterxml.jackson.annotation.JsonInclude
import io.vertx.core.json.JsonObject

@JsonInclude(JsonInclude.Include.NON_NULL)
class Book {

    var id: String? = null
    var author: String? = null
    var country: String? = null
    var imageLink: String? = null
    var language: String? = null
    var link: String? = null
    var pages: Int? = null
    var title: String? = null
    var year: Int? = null

    constructor() {}

    constructor(jsonObject: JsonObject) {
        this.id = jsonObject.getString("_id")
        this.author = jsonObject.getString("author")
        this.country = jsonObject.getString("country")
        this.imageLink = jsonObject.getString("imageLink")
        this.language = jsonObject.getString("language")
        this.link = jsonObject.getString("link")
        this.pages = jsonObject.getInteger("pages")
        this.title = jsonObject.getString("title")
        this.year = jsonObject.getInteger("year")
    }
}