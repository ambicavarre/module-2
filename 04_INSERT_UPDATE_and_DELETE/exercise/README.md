# INSERT, UPDATE, and DELETE

The purpose of this exercise is to practice inserting, updating, and deleting rows in database tables using Structured Query Language (SQL).

## Learning objectives

After completing this exercise, you'll understand:

* How to insert data using the `INSERT` statement.
* How to update data using the `UPDATE` statement.
* How to delete data using the `DELETE` statement.

## Evaluation criteria and functional requirements

* All of the queries run as expected.
* The unit tests pass as expected.
* The code is clean, concise, and readable.

To complete this exercise, you need to write SQL statements in the files that are in the `Exercises` folder. You'll use the `MovieDB` database for all these exercises.

In each file, there's a commented-out description of the change you must make to the database. Below it, write one or more `INSERT`, `UPDATE`, or `DELETE` statements to make the requested change. The value immediately after the description is the expected number of rows affected.

## Getting started

1. If you haven't done so already, create the `MovieDB` database. The script to do this is `resources/postgresql/MovieDB.sql` at the top of your repository.
    > Refer to the [Database setup](https://lms.techelevator.com/content_link/gitlab.com/te-curriculum/intro-to-tools-lms/postgresql/03-database-setup.md) lesson in the Intro to Tools unit for PostgreSQL if you need instructions on setting up the database.
2. Open the `Exercises` folder. The file numbering indicates the suggested completion order, but you can do them in any order you wish.
3. Launch pgAdmin and open each numbered exercise file one by one. Write and run the query for the individual exercise. Save the file before moving on to the next exercise.
4. The unit tests project is in the same directory as this README. You can open it in IntelliJ and run the tests as you did in earlier exercises.

> **Note**<br>
> Make sure to save your changes to the SQL files before running the unit tests.<br>
> The unit tests test your queries using a private *testing* database. Your query inserts, updates, and deletes will not be reflected in the *exercise* database during testing.

## Tips and tricks

* The `INSERT` statement adds rows of data (records) to a database table.
* The `UPDATE` statement updates existing data in a table.
* The `DELETE` statement deletes data from a table.
* IMPORTANT: Be sure to include a `WHERE` clause when you're updating or deleting data from a table unless you intend to update or delete *all* rows in the table.
* Using transactions allows you to quickly get a database back into the state it was in before you ran statements. Consider doing this as you work through the exercises to avoid having to restore your database. *If you do, be sure to remove the transaction-related statements before running the unit tests.*
 * If you need to restore the database, use the same script that created the `MovieDB` database to restore it to its original state.
