package model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import io.vertx.core.json.JsonObject

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book {

    var id: String? = null
    var title: String? = null
    var description: String? = null
    var isbn: String? = null
    var author: String? = null
    var genre: String? = null
    var pages: Int? = null
    var ageRange: String? = null
    var price: Float? = null
    var quantity: Int? = null


    constructor() {}

    constructor(jsonObject: JsonObject) {
        this.id = jsonObject.getString("_id")
        this.title = jsonObject.getString("title")
        this.description = jsonObject.getString("description")
        this.isbn = jsonObject.getString("isbn")
        this.author = jsonObject.getString("author")
        this.genre = jsonObject.getString("genre")
        this.pages = jsonObject.getInteger("pages")
        this.ageRange = jsonObject.getString("ageRange")
        this.price = jsonObject.getFloat("price")
        this.quantity = jsonObject.getInteger("quantity")

    }

    constructor(book: Book) {
        this.id = book.id
        this.title = book.title
        this.description = book.description
        this.isbn = book.isbn
        this.author = book.author
        this.genre = book.genre
        this.pages = book.pages
        this.ageRange = book.ageRange
        this.price = book.price
        this.quantity = book.quantity

    }

    constructor(book: Map.Entry<String, Any>)
}