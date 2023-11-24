package com.stoffe.gym.Helpers

import android.content.Context
import java.util.*

object UtilsNew {
    private const val PREF_NAME = "AppPreferences"
    private const val KEY_APP_OPEN_COUNT = "appOpenCount"
    private const val KEY_FIRST_OPEN_DATE = "firstOpenDate"
    private const val KEY_HAS_SEEN_CARD = "hasSeenCard"

    fun incrementAppOpenCount(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val count = sharedPreferences.getInt(KEY_APP_OPEN_COUNT, 0) + 1
        sharedPreferences.edit().putInt(KEY_APP_OPEN_COUNT, count).apply()
    }

    fun getAppOpenCount(context: Context): Int {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(KEY_APP_OPEN_COUNT, 0)
    }

    fun setFirstOpenDate(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val currentDate = Calendar.getInstance().timeInMillis
        sharedPreferences.edit().putLong(KEY_FIRST_OPEN_DATE, currentDate).apply()
    }

    fun getFirstOpenDate(context: Context): Long {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getLong(KEY_FIRST_OPEN_DATE, 0)
    }

    fun isAppOpenedMoreThanThreeTimes(context: Context): Boolean {
        val appOpenCount = getAppOpenCount(context)
        return appOpenCount > 5
    }

    fun isFirstAndLatestOpenDateDifferent(context: Context): Boolean {
        val firstOpenDate = getFirstOpenDate(context)
        val currentDate = Calendar.getInstance().timeInMillis
        return firstOpenDate != 0L && firstOpenDate != currentDate
    }

    fun setHasSeenCard(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean(KEY_HAS_SEEN_CARD, true).apply()
    }

    fun getHasSeenCard(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(KEY_HAS_SEEN_CARD, false)
    }
}