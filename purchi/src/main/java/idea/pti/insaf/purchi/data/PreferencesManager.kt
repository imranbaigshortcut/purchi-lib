package idea.pti.insaf.purchi.data

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "pti-app-pref"
    }

    fun saveString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String, defaultValue: String = ""): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    fun saveInt(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun getInt(key: String, defaultValue: Int = 0): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    // Add more methods for other data types as needed

    // Clear all preferences
    fun clearPreferences() {
        sharedPreferences.edit().clear().apply()
    }

    // Remove a specific preference
    fun removePreference(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }
}
