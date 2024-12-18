package com.thezayin.presentation.utils

import com.thezayin.domain.model.BirthdayModel
import com.thezayin.values.R
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

fun calculateDaysLeft(birthday: BirthdayModel): Int {
    val today = LocalDate.now()
    val currentYearBirthday = LocalDate.of(today.year, birthday.month, birthday.day)

    // If the birthday has passed for this year, move it to the next year
    val finalDate = if (currentYearBirthday.isBefore(today)) {
        currentYearBirthday.plusYears(1)
    } else {
        currentYearBirthday
    }

    return ChronoUnit.DAYS.between(today, finalDate).toInt()
}

// Helper Function to Format Date
fun getFormattedBirthdayDate(birthday: BirthdayModel): String {
    val monthName = getMonthName(birthday.month)
    val year = birthday.year ?: java.time.LocalDate.now().year
    return "Friday, ${birthday.day} $monthName $year" // Adjust formatting dynamically if needed
}

fun getMonthName(month: Int): String {
    return when (month) {
        1 -> "January"
        2 -> "February"
        3 -> "March"
        4 -> "April"
        5 -> "May"
        6 -> "June"
        7 -> "July"
        8 -> "August"
        9 -> "September"
        10 -> "October"
        11 -> "November"
        12 -> "December"
        else -> ""
    }
}

fun getZodiacSign(day: Int, month: Int): String {
    return when (month) {
        1 -> if (day < 20) "Capricorn" else "Aquarius"
        2 -> if (day < 19) "Aquarius" else "Pisces"
        3 -> if (day < 21) "Pisces" else "Aries"
        4 -> if (day < 20) "Aries" else "Taurus"
        5 -> if (day < 21) "Taurus" else "Gemini"
        6 -> if (day < 21) "Gemini" else "Cancer"
        7 -> if (day < 23) "Cancer" else "Leo"
        8 -> if (day < 23) "Leo" else "Virgo"
        9 -> if (day < 23) "Virgo" else "Libra"
        10 -> if (day < 23) "Libra" else "Scorpio"
        11 -> if (day < 22) "Scorpio" else "Sagittarius"
        12 -> if (day < 22) "Sagittarius" else "Capricorn"
        else -> "Unknown"
    }
}


fun getMonthColor(month: String): Int {
    return when (month) {
        "January" -> R.color.green
        "February" -> R.color.brown
        "March" -> R.color.sun_yellow
        "April" -> R.color.orange
        "May" -> R.color.purple_200
        "June" -> R.color.red
        "July" -> R.color.tealish
        "August" -> R.color.pink
        "September" -> R.color.brown
        "October" -> R.color.light_blue
        "November" -> R.color.lochmara
        "December" -> R.color.gold_yellow
        else -> R.color.greyish // Default color
    }
}

// Helper Function to Format Notification Time
fun getFormattedNotificationTime(notifyAt: Long): String {
    val instant = Instant.ofEpochMilli(notifyAt)
    val dateTime = instant.atZone(ZoneId.systemDefault())
    val formatter = DateTimeFormatter.ofPattern("hh:mm a, dd MMMM yyyy")
    return dateTime.format(formatter)
}