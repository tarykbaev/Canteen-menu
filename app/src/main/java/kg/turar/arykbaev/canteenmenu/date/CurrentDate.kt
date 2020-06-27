package kg.turar.arykbaev.canteenmenu.date

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.LocaleList
import kg.turar.arykbaev.canteenmenu.R
import java.text.SimpleDateFormat
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class CurrentDate {
    companion object {
        @SuppressLint("SimpleDateFormat")
        fun getCurrentDate(): String {
            return SimpleDateFormat("yyyy-MM-dd").format(Date())
        }

        @SuppressLint("SimpleDateFormat")
        fun getDate(context: Context, date: String): String {
            val calendar = Calendar.getInstance()
            calendar.time = SimpleDateFormat("yyyy-MM-dd").parse(date)

            val week = when (calendar.get(Calendar.DAY_OF_WEEK)) {
                Calendar.MONDAY -> context.getString(R.string.monday)
                Calendar.TUESDAY -> context.getString(R.string.tuesday)
                Calendar.WEDNESDAY -> context.getString(R.string.wednesday)
                Calendar.THURSDAY -> context.getString(R.string.thursday)
                Calendar.FRIDAY -> context.getString(R.string.friday)
                Calendar.SATURDAY -> context.getString(R.string.saturday)
                else -> context.getString(R.string.sunday)
            }

            val month = when (calendar.get(Calendar.MONTH)) {
                Calendar.JANUARY -> context.getString(R.string.january)
                Calendar.FEBRUARY -> context.getString(R.string.february)
                Calendar.MARCH -> context.getString(R.string.march)
                Calendar.APRIL -> context.getString(R.string.april)
                Calendar.MAY -> context.getString(R.string.may)
                Calendar.JUNE -> context.getString(R.string.june)
                Calendar.JULY -> context.getString(R.string.july)
                Calendar.AUGUST -> context.getString(R.string.august)
                Calendar.SEPTEMBER -> context.getString(R.string.september)
                Calendar.OCTOBER -> context.getString(R.string.october)
                Calendar.NOVEMBER -> context.getString(R.string.november)
                else -> context.getString(R.string.december)
            }

            return if (getSystemLocale() != "tr")
                "$week, ${calendar.get(Calendar.DAY_OF_MONTH)} $month"
            else
                "${calendar.get(Calendar.DAY_OF_MONTH)} $month, $week"
        }

        private fun getSystemLocale(): String {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                LocaleList.getDefault().get(0).language;
            } else {
                Locale.getDefault().language;
            }
        }
    }
}