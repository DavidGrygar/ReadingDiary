package android.example.readingdiary.sections.main_list

import android.app.Application
import android.example.readingdiary.data.DAOs.BookDAO
import android.example.readingdiary.data.entities.Book
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*

class MainViewModel(val DAO: BookDAO, application: Application) : AndroidViewModel(application) {

    val books = DAO.observeBooks()
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    //region Navigation
    private val _navigateToDeatilBook = MutableLiveData<Int>()
    val navigateToDeatilBook: LiveData<Int>
        get() = _navigateToDeatilBook

    fun onBookClicked(id: Int) {
        _navigateToDeatilBook.value = id
    }

    fun doneDeatilBookNavigating() {
        _navigateToDeatilBook.value = null
    }
    //endregion

    fun deleteBook(book: Book) {
        uiScope.launch {
            DAO.deleteBook(book)
        }
    }
}