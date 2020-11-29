package android.example.readingdiary.sections.new_book

import android.app.Application
import android.example.readingdiary.data.DAOs.BookDAO
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NewBookViewModelFactory(
    private val application: Application,
    private val dataSource: BookDAO,
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewBookViewModel::class.java)) {
            return NewBookViewModel(application, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}