package android.example.readingdiary.sections.detail_book

import android.app.Application
import android.example.readingdiary.data.DAOs.BookDAO
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DetailBookViewModelFactory (
    private val application: Application,
    private val dataSource: BookDAO,
    private val selectedBookID: Int
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailBookViewModel::class.java)) {
            return DetailBookViewModel(application, dataSource, selectedBookID) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}