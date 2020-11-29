package android.example.readingdiary.data.enums

import android.example.readingdiary.R

enum class PeriodOfYear(val value: Int, val stringResId: Int) {
    JANUARY(1, R.string.january),
    FEBRUARY(2, R.string.february),
    MARCH(3, R.string.march),
    APRIL(4, R.string.april),
    MAY(5, R.string.may),
    JUNE(6, R.string.june),
    JULY(7, R.string.july),
    AUGUST(8, R.string.august),
    SEPTEMBER(9, R.string.september),
    OCTOBER(10, R.string.october),
    NOVEMBER(11, R.string.november),
    DECEMBER(12, R.string.december),
    FIRST_QUARTER(13, R.string.first_quarter),
    SECOND_QUARTER(14, R.string.second_quarter),
    THIRD_QUARTER(15, R.string.third_quarter),
    FOURTH_QUARTER(16, R.string.fourth_quarter),
    NON_SELECTED(17, R.string.period_of_year_non_selected);

    companion object {
        private val map = PeriodOfYear.values().associateBy(PeriodOfYear::value)
        fun fromInt(type: Int) = map[type]
    }
}