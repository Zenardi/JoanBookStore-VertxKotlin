Take Home Programming Task
====

The goal of this task is to test your knowledge of the fundamentals of development and the processes involved.  
The topics that we will be reviewing are:
* The design of the solution provided
* The code quality
* The unit testing
* Adherence to the requirements

# Preamble

Joan Harn plans to open a bookstore on the upper west side of Chicago. For the last three months she has been buying books in bulk and storing them.  Her plan is to stock her shelves with these books when she finally opens her bookstore later this month.

One of her friends recently suggested that she take orders for books via phone and email until she closes on her lease.  Joan decided this would be great because then when she opened her store she would have some presence.

Which leads into Joan's first problem.

# Joan's First Problem

For the last three months Joan has been ordering books in bulk and she doesn't currently know what she has in stock.  The wholesalers that she purchased these boxes of books from do provide her with a manifest.

These Manifests usually contain the following information:

### Figure 1
|Key|Description|
|---|---|
|Title|The title of the book|
|Description| A short description of the book|
|ISBN| The unique identifier for the book|
|Author| The first name and last name of the Author|
|Genre| The suggested genre of the Author|
|Pages| The number of pages in the book|
|Age range| dash separated age range of the book `15-17`|
|Price| The suggested price for the book.  Provided by the publisher|
|Qty| The number of books purchased|

Joan's first problem is that the different wholesalers do not use the same format when giving her the manifests.  

|Wholesaler|format|extra info|
|:--|:--|:--|
|Dave's Wholesale Books|CSV| Titles of books sometimes contain special characters such as comma or double quote you can assume that any field with this content is surrounded by double quotes.  CSV's will pass this linter.[https://csvlint.io/about](https://csvlint.io/about) |
|Harold's Wholesale|JSON||
|Fera's Fantastic Bulk Books| Tab Separated|You can assume no tabs will be in the fields|

## Requirements 
Joan would like you to read in the three different manifests and write the data to a unified CSV.

* The application should be a command line application
    * The user provides the fully qualified path to the files as arguments to the app
* The CSV should contain all data fields in [Figure 1](#Figure-1)
* Identical Books should have their Quantities summed
    * NOTE: The ISBN is the unique identifier for a given edition of a title
* The CSV should be sorted by Alphabetical in Ascending Order by Genre, Last Name, First Name
* The CSV should be able to be opened by Microsoft Office or Numbers on MacOS
    * It should pass the linter [https://csvlint.io/about](https://csvlint.io/about)


# Joan's Second Problem

Joan's Bookshop opened two months ago and business has been great.  She is happy with with the software you wrote but she realized that she needs to have an online presence to compete with the other Chicago books stores.  Before she can move forward, she needs a REST API instead of CSV for her book inventory.

## Requirements

Build a REST API where you POST the files that were provided for Joan's First Problem and you insert that data into a database. 

* Manifests will be POST to a single endpoint and those Books will be inserted into a database (Preferably MongoDB)
* Joan can retrieve the list of books via GET request
    * Default Sorting is Alphabetical in Ascending Order by Genre, Last Name, First Name
    * return values should be in JSON format
* Joan can update the quantity of a given book via patch request utilizing the books ISBN as an identifier


# Joan's Final Problem
Joan has run into a bit of a pickle since she last talked to you.  It turns out that the more unique books in the service you wrote for her the harder it is to find the books she is looking for. Joan now wants you to implement the ability to search her stock.

Joan wants to search for books via the following fields:

|Fields|
|---|
|Genre|
|Full Name (first and last name)|
|Title|

She would like to only show books in stock and she would like to mix and match the criteria provided.

Example:
Joan wants to find the book `Name of the Wind` by Patrick Rothfuss but she can't remember the exact title of the book so she wants to search for books by Patrick Rothfuss.

She should be able to combine her search terms in any number of ways she wants.  Results should be sorted in the standard format stated in the previous problems.
