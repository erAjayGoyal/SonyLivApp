package com.sonyliv.app.utility

import android.content.Context
import android.os.Environment
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.nio.charset.Charset

class LocaleManager(private var context: Context, fileName: String) {
  private var localeMap: JSONObject? = null

  init {
    val folder: File =
      Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    val file = File(folder, fileName)
    val response = readData(file)
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

  // readData() is the method which reads the data from a file
  // the data that is saved in byte format in the file
  private fun readData(myFile: File): String {

    var fileInputStream: FileInputStream? = null
    try {
      fileInputStream = FileInputStream(myFile)
      val charset: Charset = Charsets.UTF_8
      var i = -1
      val buffer = ByteArray(fileInputStream.available())
      fileInputStream.read(buffer)
      return String(buffer, charset)
    } catch (e: Exception) {
      e.printStackTrace()
    } finally {
      if (fileInputStream != null) {
        try {
          fileInputStream.close()
        } catch (e: IOException) {
          e.printStackTrace()
        }
      }
    }
    return ""
  }
}