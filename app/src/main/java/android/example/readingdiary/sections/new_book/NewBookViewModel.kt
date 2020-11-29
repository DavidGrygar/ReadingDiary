package android.example.readingdiary.sections.new_book

import android.app.Application
import android.example.readingdiary.sections.EditNewViewModel
import android.example.readingdiary.data.DAOs.BookDAO
import android.example.readingdiary.data.entities.Book
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class NewBookViewModel(application: Application, override val DAO: BookDAO) : EditNewViewModel(application, DAO) {

    override suspend fun getBook(): Book? {
        return Book()
    }

    //region Navigation
    private val _navigateAfterSave = MutableLiveData<Boolean?>()
    val navigateAfterSave: LiveData<Boolean?>
        get() = _navigateAfterSave

    fun doneNavigateAfterSave() {
        _navigateAfterSave.value = null
    }
    //endregion

    override fun afterSave() {
        _navigateAfterSave.value = true
    }
}