package android.example.readingdiary.utils

import java.util.*

class GeneralHelper {
    companion object {
        fun getCurrentYear(): Int{
            return Calendar.getInstance().get(Calendar.YEAR)
        }
    }
}