package android.example.readingdiary.sections

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

abstract class EditNewViewModel(application: Application, open val DAO: BookDAO) : AndroidViewModel(application) {

    protected var viewModelJob = Job()
    protected val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    protected val _book = MutableLiveData<Book?>()
    val book: LiveData<Book?>
        get() = _book

    init {
        uiScope.launch {
            _book.value = getBook()
        }
    }

    abstract suspend fun getBook(): Book?

    abstract fun afterSave()

    fun onMenuItemSaveBook() {
        uiScope.launch {
            _book.value?.let { DAO.insert(it) }
        }
        afterSave()
    }

    fun bookTypeClicked(bookTypeInt: Int) {
        _book.value = _book.value?.also {
            it.BOOK_TYPE = bookTypeInt
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}