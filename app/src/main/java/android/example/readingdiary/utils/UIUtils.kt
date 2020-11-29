package android.example.readingdiary.utils

import android.content.Context
import android.example.readingdiary.R
import android.example.readingdiary.SwipeToDeleteCallback
import android.example.readingdiary.adapters.BookAdapter
import android.example.readingdiary.data.enums.PeriodOfYear
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.NumberPicker
import android.widget.SimpleAdapter
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun setupYearNumberPicker(picker: NumberPicker)
{
    picker.minValue = Constants.YEAR_MIN_VALUE
    picker.maxValue = GeneralHelper.getCurrentYear()
}

fun setupYearPeriodNumberPicker(picker: NumberPicker, context: Context)
{
    picker.minValue = 1
    picker.maxValue = PeriodOfYear.values().size
    picker.displayedValues = getStringArrayFromPeriodOfYear(PeriodOfYear.values(), context)
}

fun setupMainList(recyclerView: RecyclerView, context: Context)
{
    recyclerView.layoutManager = LinearLayoutManager(context)

    val swipeHandler = object : SwipeToDeleteCallback(context) {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val adapter = recyclerView.adapter as BookAdapter
            adapter.remove(viewHolder.adapterPosition)
        }
    }
    val itemTouchHelper = ItemTouchHelper(swipeHandler)
    itemTouchHelper.attachToRecyclerView(recyclerView)
}

fun getStringArrayFromPeriodOfYear(array: Array<PeriodOfYear>, context: Context): Array<String>
{
    val result = mutableListOf<String>()
    array.forEach {
        result.add(context.resources.getString(it.stringResId))
    }
    return result.toTypedArray()
}
