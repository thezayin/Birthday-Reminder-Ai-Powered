package com.thezayin.presentation.model

import androidx.compose.runtime.MutableState
import java.util.Calendar

fun isValidDayForMonth(day: Int, month: Int, year: Int?): Boolean {
    return when (month) {
        1, 3, 5, 7, 8, 10, 12 -> day in 1..31
        4, 6, 9, 11 -> day in 1..30
        2 -> {
            if (year != null) {
                if (isLeapYear(year)) {
                    day in 1..29
                } else {
                    day in 1..28
                }
            } else {
                // If year is not provided, assume non-leap year
                day in 1..28
            }
        }
        else -> false
    }
}

fun isLeapYear(year: Int): Boolean {
    return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
}

/**
 * Calculates the next occurrence of the birthday.
 * If the birthday has already occurred this year, it sets it to next year.
 *
 * @param day The day of the birthday.
 * @param month The month of the birthday (1-12).
 * @param year The optional year of the birthday. If null, calculates based on the current year.
 * @return A Calendar instance representing the next birthday date.
 */
fun calculateNextBirthday(day: Int, month: Int, year: Int? = null): Calendar {
    val today = Calendar.getInstance()
    val birthday = Calendar.getInstance().apply {
        set(Calendar.DAY_OF_MONTH, day)
        set(Calendar.MONTH, month - 1) // Calendar.MONTH is 0-based
        set(Calendar.YEAR, year ?: today.get(Calendar.YEAR))
    }

    // If the birthday has already occurred this year, set it to next year
    if (birthday.before(today)) {
        birthday.add(Calendar.YEAR, 1)
    }
    return birthday
}

/**
 * Calculates the notification timestamp based on the selected date and time.
 *
 * @param notificationDate The selected notification date.
 * @param hour The selected hour (1-12).
 * @param minute The selected minute (0-59).
 * @param period The period ("AM" or "PM").
 * @return The timestamp in milliseconds for the notification.
 */
fun calculateNotifyAt(
    notificationDate: NotificationDate,
    hour: Int,
    minute: Int,
    period: String
): Long {
    val day = notificationDate.day.value.text.toIntOrNull() ?: 1
    val month = notificationDate.month.value.text.toIntOrNull() ?: 1
    val year = notificationDate.year.value.text.toIntOrNull()

    val notifyCalendar = Calendar.getInstance().apply {
        set(Calendar.DAY_OF_MONTH, day)
        set(Calendar.MONTH, month - 1) // Calendar.MONTH is 0-based

        // Set Year: Use provided year or calculate next occurrence
        if (year != null) {
            set(Calendar.YEAR, year)
        } else {
            val nextBirthday = calculateNextBirthday(day, month)
            set(Calendar.YEAR, nextBirthday.get(Calendar.YEAR))
        }

        // Set Time
        set(Calendar.HOUR, if (hour == 12) 0 else hour % 12) // Adjust hour for AM/PM
        set(Calendar.MINUTE, minute)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
        set(Calendar.AM_PM, if (period.uppercase() == "AM") Calendar.AM else Calendar.PM)
    }
    return notifyCalendar.timeInMillis
}

/**
 * Helper function to update the Save button state based on validation.
 */
fun updateButtonState(
    name: String,
    day: String,
    month: String,
    year: String,
    isNameError: Boolean,
    isDayError: Boolean,
    isMonthError: Boolean,
    isYearError: Boolean,
    isButtonEnabled: MutableState<Boolean>
) {
    isButtonEnabled.value = name.isNotBlank() &&
            day.isNotBlank() &&
            month.isNotBlank() &&
            !isNameError &&
            !isDayError &&
            !isMonthError &&
            (year.isBlank() || !isYearError)
}