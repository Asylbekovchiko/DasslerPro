package kg.sunrise.dasslerpro.utils.preference

import android.content.Context
import androidx.core.content.edit

private const val SETTING_STORAGE_NAME = "dasslerpro.sunrise"
private const val IS_FIRST_TIME = "IS_FIRST_TIME"
private const val TOKEN = "TOKEN"
const val DEFAULT_TOKEN_VALUE = "empty"
private const val DEVICE_TOKEN = "device_token"

private fun pref(context: Context) = context.getSharedPreferences(SETTING_STORAGE_NAME, Context.MODE_PRIVATE)

private fun editString(context: Context, key: String, value: String?) = pref(context).edit {
    putString(key, value)
}

private fun getString(context: Context, key: String, default : String) = pref(context).getString(key, default) ?: default

private fun editBoolean(context: Context, key: String, value: Boolean) = pref(context).edit {
    putBoolean(key, value)
}

private fun getBoolean(context: Context, key: String, default : Boolean) = pref(context).getBoolean(key, default)


fun setUserEntered(context : Context) = editBoolean(context, IS_FIRST_TIME, false)

fun isUserEnterFirstTime(context: Context) = getBoolean(context, IS_FIRST_TIME, true)

fun setToken(context: Context, token: String) = editString(context, TOKEN, token)

fun getToken(context: Context) = getString(context, TOKEN, DEFAULT_TOKEN_VALUE)

fun deleteToken(context: Context) {
    editString(context, TOKEN, DEFAULT_TOKEN_VALUE)
}

fun isAuthorized(context: Context) = getString(context, TOKEN, DEFAULT_TOKEN_VALUE) != DEFAULT_TOKEN_VALUE

fun setDeviceToken(context: Context, deviceToken: String) =
    editString(context, DEVICE_TOKEN, deviceToken)

fun getDeviceToken(context: Context) =
    getString(context, DEVICE_TOKEN, DEFAULT_TOKEN_VALUE)