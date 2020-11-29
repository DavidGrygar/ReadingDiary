package android.example.readingdiary.data.DAOs

import android.example.readingdiary.data.entities.Book
import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface BookDAO {

    @Delete
    suspend fun deleteBook(book: Book)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(book: Book)

    /**
     * Observes list of books.
     *
     * @return all books.
     */
    @Query("SELECT * FROM BOOK")
    fun observeBooks(): LiveData<List<Book>>

    /**
     * Observes a single book.
     *
     * @param givenId the Book ID.
     * @return the Book with ID.
     */
    @Query("SELECT * FROM BOOK WHERE ID =:givenID")
    fun getLiveDataBookById(givenID: Int): LiveData<Book?>

    /**
     * Select all books from the BOOK table.
     *
     * @return all books.
     */
    @Query("SELECT * FROM BOOK")
    suspend fun getBooks(): List<Book>

    /**
     * Select a book by id.
     *
     * @param givenId the Book ID.
     * @return the Book with ID.
     */

    @Query("SELECT * FROM BOOK WHERE ID = :givenID")
    suspend fun getBookById(givenID: Int): Book?

    @Query("SELECT * FROM BOOK ORDER BY ID DESC LIMIT 1")
    suspend fun getLastBook(): Book
}