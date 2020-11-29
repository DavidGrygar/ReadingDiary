package android.example.readingdiary.data.entities

import android.example.readingdiary.R
import android.example.readingdiary.data.enums.BookType
import android.example.readingdiary.data.enums.PeriodOfYear
import android.example.readingdiary.utils.GeneralHelper
import android.view.View
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Year
import java.time.YearMonth
import java.util.*

/**
 * Immutable model class for a Task. In order to compile with Room, we can't use @JvmOverloads to
 * generate multiple constructors.
 *
 * @param title title of the task
 * @param description description of the task
 * @param isCompleted whether or not this task is completed
 * @param id id of the task
 */
@Entity(tableName = "BOOK")
data class Book @JvmOverloads constructor(
    @ColumnInfo(name = "NAME") var NAME: String = "",
    @ColumnInfo(name = "BOOK_TYPE") var BOOK_TYPE: Int = BookType.PAPERBACK.value,
    @ColumnInfo(name = "NOTE") var NOTE: String = "",
    @ColumnInfo(name = "READING_YEAR") var READING_YEAR: Int = GeneralHelper.getCurrentYear(),
    @ColumnInfo(name = "READING_YEAR_PERIOD") var READING_YEAR_PERIOD: Int = PeriodOfYear.NON_SELECTED.value,
    @ColumnInfo(name = "READ_PERCENT") var READ_PERCENT: Int = 100,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "ID") var ID: Int = 0
)
{
        fun getReadInfoStatusVisibility(): Int
        {
                if(this.READ_PERCENT == 0 || this.READ_PERCENT == 100){
                        return View.VISIBLE
                }
                else{
                        return View.GONE
                }
        }

        fun getReadInfoStatus(): Int
        {
                if(this.READ_PERCENT == 0){
                        return R.string.read_info_status_unread
                }
                else if(this.READ_PERCENT == 100){
                        return R.string.read_info_status_read
                }
                else{
                        return R.string.empty
                }
        }
}
