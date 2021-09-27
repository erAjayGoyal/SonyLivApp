package com.sonyliv.app.utility

import android.content.Context
import org.json.JSONException
import org.json.JSONObject

class LocaleManager(private var context: Context, fileName: String) {
  private var localeMap: JSONObject? = null

  init {
    val response = Utils.loadJSONFromAsset(context, fileName)
    if (response.isNotEmpty()) {
      try {
        localeMap = JSONObject(response)
      } catch (e: JSONException) {
        e.printStackTrace()
      }
    }
  }

  /**
   * @return :: This method will return translation value of the 'translationKey' supplied
   * <p>
   * sample code
   * <p>
   * String valueOfKey = LocaleManager.getInstance().getStringByKey(getString(R.string.translationKey));
   * Note: This method is specific for DisplayLanguage only.
   */
  fun getStringByKey(translationKey: String, displayLanguageCode: String): String? {
    if (localeMap != null) {
      try {
        if (localeMap!!.has(translationKey)) {
          val languageArray = localeMap!!.getJSONArray(translationKey)
          if (languageArray.length() > 0) {
            val languageObject = languageArray.getJSONObject(0)
            if (displayLanguageCode.isNotEmpty() && languageObject.has(displayLanguageCode)) {
              return languageObject.getString(displayLanguageCode)
            }
          }
        }
      } catch (e: JSONException) {
        e.printStackTrace()

      }

    }
    return null
  }
}