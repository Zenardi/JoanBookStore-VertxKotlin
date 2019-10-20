package model

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book {

//    var id: String? = null
    var title: String? = null
    var description: String? = null
    var ISBN: Long = 0
    var author: String? = null
    var genre: String? = null
    var pages: Int? = null
    var ageRange: String? = null
    var price: Float? = null
    var Qty: String = ""


    constructor() {}

//    constructor(jsonObject: JsonObject) {
//        this.id = jsonObject.getString("_id")
//        this.title = jsonObject.getString("title")
//        this.description = jsonObject.getString("description")
//        this.isbn = jsonObject.getString("isbn")
//        this.author = jsonObject.getString("author")
//        this.genre = jsonObject.getString("genre")
//        this.pages = jsonObject.getInteger("pages")
//        this.ageRange = jsonObject.getString("ageRange")
//        this.price = jsonObject.getFloat("price")
//        this.Qty = jsonObject.getString("quantity")
//
//    }

    constructor(book: Book) {
//        this.id = book.id
        this.title = book.title
        this.description = book.description
        this.ISBN = book.ISBN
        this.author = book.author
        this.genre = book.genre
        this.pages = book.pages
        this.ageRange = book.ageRange
        this.price = book.price
        this.Qty = book.Qty

    }

    constructor(book: Map.Entry<String, Any>)
}