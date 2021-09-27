package com.sonyliv.app

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sonyliv.app.model.Language
import com.sonyliv.app.utility.LocaleManager
import kotlinx.android.synthetic.main.activity_main_layout.btnBangali
import kotlinx.android.synthetic.main.activity_main_layout.btnEnglish
import kotlinx.android.synthetic.main.activity_main_layout.btnHindi
import kotlinx.android.synthetic.main.activity_main_layout.txtHelloWorld
import kotlinx.android.synthetic.main.activity_main_layout.txtWelcome
import java.util.Locale

class MainActivity : AppCompatActivity(), View.OnClickListener {

  private lateinit var locale: Locale
  private lateinit var localeManager: LocaleManager
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main_layout)

    setUpLocaleDataFromFile()
    //setting up button click event listener
    btnBangali.setOnClickListener(this)
    btnHindi.setOnClickListener(this)
    btnEnglish.setOnClickListener(this)

  }

  /**
   * Method to initialize LocaleManager class
   * Pass this file name that has translation strings
   */
  private fun setUpLocaleDataFromFile() {
    localeManager = LocaleManager(this, "localization.json")
  }

  override fun onClick(v: View) {
    var lang = Language.en.name
    when (v.id) {
      R.id.btnBangali -> {
        lang = Language.bn.name
      }
      R.id.btnHindi -> {
        lang = Language.hi.name
      }
      R.id.btnEnglish -> lang = Language.en.name
    }
    updateTexts(lang)
  }

  /**
   * Method to update resource configuration based on language code
   * @param lang -> selected language code
   */
  private fun changeLang(lang: String) {
    if (lang.equals("", ignoreCase = true)) return
    locale = Locale(lang)
    Locale.setDefault(locale)
    val config = Configuration()
    config.locale = locale
    baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
  }

  /**
   * Method to update the text based on language code
   * Read translation string from localization file{@link LocaleManager} if not available then fallback to default android string
   * @param lang -> selected language code
   */
  private fun updateTexts(lang: String) {
    changeLang(lang)
    txtHelloWorld.text = localeManager.getStringByKey(getString(R.string.key_hello_world), "")
      ?: getString(R.string.txt_hello_world)
    txtWelcome.text = localeManager.getStringByKey(getString(R.string.key_welcome), lang)
      ?: getString(R.string.txt_welcome)
  }

  override fun onConfigurationChanged(newConfig: Configuration) {
    super.onConfigurationChanged(newConfig)
    if (this::locale.isInitialized) {
      newConfig.locale = locale
      Locale.setDefault(locale)
      baseContext.resources.updateConfiguration(newConfig, baseContext.resources.displayMetrics)
    }
  }

}