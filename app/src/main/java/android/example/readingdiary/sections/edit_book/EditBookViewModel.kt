package android.example.readingdiary.sections.edit_book

import android.app.Application
import android.example.readingdiary.sections.EditNewViewModel
import android.example.readingdiary.data.DAOs.BookDAO
import android.example.readingdiary.data.entities.Book
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*

class EditBookViewModel(application: Application, override val DAO: BookDAO, val bookID: Int) :  EditNewViewModel(application, DAO) {

    override suspend fun getBook(): Book? {
        return DAO.getBookById(bookID)
    }

    //region Navigation
    private val _navigateBack = MutableLiveData<Int>()
    val navigateBack: LiveData<Int>
        get() = _navigateBack

    fun doneNavigatingBack() {
        _navigateBack.value = null
    }
    //endregion

    //region ActionBarMenu
    fun onMenuItemDeleteBook() {
        _book.value?.let { deleteBook(it) }
        _navigateBack.value = bookID
    }

    fun deleteBook(book: Book) {
        uiScope.launch {
            DAO.deleteBook(book)
        }
    }
    //endregion

    override fun afterSave() {
        _navigateBack.value = bookID
    }
}