package android.example.readingdiary.utils

import android.example.readingdiary.R
import android.example.readingdiary.data.entities.Book
import android.example.readingdiary.data.enums.BookType
import android.example.readingdiary.data.enums.PeriodOfYear
import android.view.View
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.TextView
import androidx.databinding.*

@BindingAdapter("bookIDString")
fun TextView.setBookIDString(item: Book?) {
    item?.let {
        val textFormatted = "ID: " + item.ID.toString()
        text = textFormatted
    }
}

@BindingAdapter("bookTypeImage")
fun ImageView.setBookTypeImage(item: Book?) {
    item?.let {
        setImageResource(
            when (item.BOOK_TYPE) {
                BookType.PAPERBACK.value -> R.drawable.ic_book
                BookType.AUDIO.value -> R.drawable.ic_audio
                BookType.EBOOK.value -> R.drawable.ic_ebook
                else -> R.drawable.ic_question
            }
        )
    }
}

@BindingAdapter("readingYearPeriodToString")
fun TextView.setReadingYearPeriodToString(period: Int)
{
    text = PeriodOfYear.fromInt(period)?.stringResId?.let { resources.getString(it) } ?: ""
}