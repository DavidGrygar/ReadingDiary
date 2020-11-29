package android.example.readingdiary.sections.detail_book

import android.app.Application
import android.example.readingdiary.data.DAOs.BookDAO
import android.example.readingdiary.data.entities.Book
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailBookViewModel (application: Application, val DAO: BookDAO, val bookID: Int) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    //var book = DAO.getLiveDataBookById(bookID)
    private val _book = MutableLiveData<Book?>()
    val book: LiveData<Book?>
        get() = _book

    init {
        uiScope.launch {
            _book.value = DAO.getBookById(bookID)
        }
    }

    //region Navigation
    private val _navigateToMainFragment = MutableLiveData<Boolean?>()
    val navigateToMainFragment: LiveData<Boolean?>
        get() = _navigateToMainFragment

    fun doneNavigating() {
        _navigateToMainFragment.value = null
    }

    private val _navigateToEditBook = MutableLiveData<Int>()
    val navigateToEditBook: LiveData<Int>
        get() = _navigateToEditBook

    fun onMenuItemEditBookClicked() {
        _navigateToEditBook.value = bookID
    }

    fun doneEditBookNavigating() {
        _navigateToEditBook.value = null
    }
    //endregion

    //region ActionBarMenu
    fun onMenuItemDeleteBookClick() {
        book.value?.let { deleteBook(it) }
        _navigateToMainFragment.value = true
    }

    fun deleteBook(book: Book)
    {
        uiScope.launch {
            DAO.deleteBook(book)
        }
    }
    //endregion


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}