package android.example.readingdiary.sections.edit_book

import android.app.Application
import android.example.readingdiary.data.DAOs.BookDAO
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EditBookViewModelFactory (
    private val application: Application,
    private val dataSource: BookDAO,
    private val selectedBookID: Int
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditBookViewModel::class.java)) {
            return EditBookViewModel(application, dataSource, selectedBookID) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}