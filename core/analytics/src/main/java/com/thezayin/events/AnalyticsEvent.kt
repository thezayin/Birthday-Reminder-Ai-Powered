package com.thezayin.events

import android.os.Bundle
import com.thezayin.utils.AnalyticsConstant.AD_REVENUE
import com.thezayin.utils.AnalyticsConstant.APP_OPEN_AD
import com.thezayin.utils.AnalyticsConstant.INTERSTITIAL_AD
import com.thezayin.utils.AnalyticsConstant.SCREEN_VIEW
import com.thezayin.utils.AnalyticsConstant.SETTINGS_CONTACT_US
import com.thezayin.utils.AnalyticsConstant.SETTINGS_FEEDBACK
import com.thezayin.utils.AnalyticsConstant.SETTINGS_MORE_APPS
import com.thezayin.utils.AnalyticsConstant.SETTINGS_PRIVACY_POLICY
import com.thezayin.utils.AnalyticsConstant.SETTINGS_RATE_US
import com.thezayin.utils.AnalyticsConstant.SETTINGS_TERMS_CONDITION

sealed class AnalyticsEvent(
    val event: String? = null,
    val args: Bundle?
) {
    /**
     * A key-value pair used to supply extra context to an
     * analytics event.
     *
     * @param key - the parameter key. Wherever possible use
     * one of the standard `ParamKeys`, however, if no suitable
     * key is available you can define your own as long as it is
     * configured in your backend analytics system (for example,
     * by creating a Firebase Analytics custom parameter).
     *
     * @param value - the parameter value.
     */

    // User Interaction Events
    class AddBirthdayStarted : AnalyticsEvent(
        "AddBirthdayStarted",
        null
    )

    class AddBirthdayInputChanged(
        val fieldName: String,
        val newValue: String
    ) : AnalyticsEvent(
        "AddBirthdayInputChanged",
        Bundle().apply {
            putString("field_name", fieldName)
            putString("new_value", newValue)
        }
    )

    class AddBirthdaySaveClicked : AnalyticsEvent(
        "AddBirthdaySaveClicked",
        null
    )

    class AddBirthdaySuccess : AnalyticsEvent(
        "AddBirthdaySuccess",
        null
    )

    class AddBirthdayFailure(
        val errorMessage: String
    ) : AnalyticsEvent(
        "AddBirthdayFailure",
        Bundle().apply {
            putString("error_message", errorMessage)
        }
    )

    class AddBirthdayDuplicateDetected : AnalyticsEvent(
        "AddBirthdayDuplicateDetected",
        null
    )

    class AddBirthdayDuplicateConfirmed : AnalyticsEvent(
        "AddBirthdayDuplicateConfirmed",
        null
    )

    class AddBirthdayDuplicateCancelled : AnalyticsEvent(
        "AddBirthdayDuplicateCancelled",
        null
    )

    class SendCustomMessageToggled(
        val enabled: Boolean
    ) : AnalyticsEvent(
        "SendCustomMessageToggled",
        Bundle().apply {
            putBoolean("enabled", enabled)
        }
    )

    class NotificationSettingsChanged(
        val settingName: String,
        val newValue: String
    ) : AnalyticsEvent(
        "NotificationSettingsChanged",
        Bundle().apply {
            putString("setting_name", settingName)
            putString("new_value", newValue)
        }
    )

    class AppOpenAdEvent(
        status: String
    ) : AnalyticsEvent(
        APP_OPEN_AD,
        Bundle().apply {
            putString("status", status)
        }
    )

    class InterstitialAdEvent(
        status: String
    ) : AnalyticsEvent(
        INTERSTITIAL_AD,
        Bundle().apply {
            putString("status", status)
        }
    )

    class AdFailedEvent(
        type: String,
        error: String,
        errorCode: Int
    ) : AnalyticsEvent(
        AD_REVENUE,
        Bundle().apply {
            putString("ad_type", type)
            putString("event", "Ad_Failed_Event")
            putString("Error", error)
            putInt("Error_Code", errorCode)
        }
    )

    class ScreenViewEvent(
        status: String
    ) : AnalyticsEvent(
        SCREEN_VIEW,
        Bundle().apply {
            putString("status", status)
        }
    )


    class AdImpressionEvent(
        event: String,
        provider: String,
        status: String
    ) : AnalyticsEvent(
        event,
        Bundle().apply {
            putString("ad_provider", provider)
            putString("status", status)
        }
    )

    class SettingsRateUs(
        status: String
    ) : AnalyticsEvent(
        SETTINGS_RATE_US,
        Bundle().apply {
            putString("status", status)
        }
    )

    class SettingsFeedback(
        status: String
    ) : AnalyticsEvent(
        SETTINGS_FEEDBACK,
        Bundle().apply {
            putString("status", status)
        }
    )

    class SettingsContactUs(
        status: String
    ) : AnalyticsEvent(
        SETTINGS_CONTACT_US,
        Bundle().apply {
            putString("status", status)
        }
    )

    class SettingsTermsConditions(
        status: String
    ) : AnalyticsEvent(
        SETTINGS_TERMS_CONDITION,
        Bundle().apply {
            putString("status", status)
        }
    )

    class SettingsPrivacyPolicy(
        status: String
    ) : AnalyticsEvent(
        SETTINGS_PRIVACY_POLICY,
        Bundle().apply {
            putString("status", status)
        }
    )

    class SettingMoreApps(
        status: String
    ) : AnalyticsEvent(
        SETTINGS_MORE_APPS,
        Bundle().apply {
            putString("status", status)
        }
    )

    // User Interaction Events
    class CalculatorScreenOpened : AnalyticsEvent(
        "CalculatorScreenOpened",
        null
    )

    class NameInputChanged(
        val newValue: String
    ) : AnalyticsEvent(
        "NameInputChanged",
        Bundle().apply {
            putString("new_value", newValue)
        }
    )

    class StartDateChanged(
        val newDate: String
    ) : AnalyticsEvent(
        "StartDateChanged",
        Bundle().apply {
            putString("new_date", newDate)
        }
    )

    class EndDateChanged(
        val newDate: String
    ) : AnalyticsEvent(
        "EndDateChanged",
        Bundle().apply {
            putString("new_date", newDate)
        }
    )

    class CalculateButtonClicked : AnalyticsEvent(
        "CalculateButtonClicked",
        null
    )

    class CalculationSuccess(
        val years: String,
        val months: String,
        val days: String
    ) : AnalyticsEvent(
        "CalculationSuccess",
        Bundle().apply {
            putString("years", years)
            putString("months", months)
            putString("days", days)
        }
    )

    class CalculationFailure(
        val errorMessage: String
    ) : AnalyticsEvent(
        "CalculationFailure",
        Bundle().apply {
            putString("error_message", errorMessage)
        }
    )

    class CopyButtonClicked : AnalyticsEvent(
        "CopyButtonClicked",
        null
    )

    class PlayButtonClicked : AnalyticsEvent(
        "PlayButtonClicked",
        null
    )

    class ShareButtonClicked : AnalyticsEvent(
        "ShareButtonClicked",
        null
    )

    class HistoryButtonClicked : AnalyticsEvent(
        "HistoryButtonClicked",
        null
    )

    // Ad Interaction Events
    class AdShown(
        val adType: String
    ) : AnalyticsEvent(
        "AdShown",
        Bundle().apply {
            putString("ad_type", adType)
        }
    )

    class AdClosed(
        val adType: String
    ) : AnalyticsEvent(
        "AdClosed",
        Bundle().apply {
            putString("ad_type", adType)
        }
    )

    class AdFailed(
        val adType: String,
        val errorMessage: String
    ) : AnalyticsEvent(
        "AdFailed",
        Bundle().apply {
            putString("ad_type", adType)
            putString("error_message", errorMessage)
        }
    )

    // Button State Events
    class CalculatorButtonEnabled : AnalyticsEvent(
        "CalculatorButtonEnabled",
        null
    )

    class CalculatorButtonDisabled : AnalyticsEvent(
        "CalculatorButtonDisabled",
        null
    )

    class CalculationHistoryAdded(
        val name: String,
        val years: String,
        val months: String,
        val days: String
    ) : AnalyticsEvent(
        "CalculationHistoryAdded",
        Bundle().apply {
            putString("name", name)
            putString("years", years)
            putString("months", months)
            putString("days", days)
        }
    )

    class CalculationHistoryFailure(
        val errorMessage: String
    ) : AnalyticsEvent(
        "CalculationHistoryFailure",
        Bundle().apply {
            putString("error_message", errorMessage)
        }
    )
}