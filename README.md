# SonyLiveApp
SonyLiv repository that contains localization functionality from external storage

### List of modules

#### Application modules

| Module name | Description |
| --- | --- |
| **app** | SonyLiv application module. |

Currently app support English, Hindi & bangla language and localization system is completely dynamic
While adding a new text view in layout file. To support translation correspondence string should be added
in json file along with string.xml

On tapping a button English/Hindi/Bangla, first searching text view key for eg: txt_hello_world into json file along with lang code
If found then it get applied from localization file else fallback to string.xml


#### All the possible solution
1. Expose an API from server that provides all the text with all the supported languages.
   Format should be key = "key_text_view" in string.xml and it should contains an array
   that has all the text in supported language like "en": "Hello World"

2. Localization file push from server on regular interval using Localization MDM Server and on launch the app
   app will read that file and stores into LocaleManager

3. Any 3rd Party tool that expose response in json format in the form of an API. It should have key, value pair structure

#### Here I choose option 1 since json format is widely used in Mobile Application. Instead of exposing as an API
     I just stored localization.json file in external storage and read the data and stores into LocaleManager class
     An another option could be is CSV/TDV file where one column contains all the key and other tab have translated language text
     Key               English         Hindi       Bangla
     txt_hello_world   Hello World     नमस्ते दुनिया    Ōhē biśba



#### How to test the application
Pushed "localization.json" file in external storage
sdcard->download->localization.json (file name can be anything)

Launch the application and allow external storage read permission
if{
    permission granted then text will be translated using external file
  }
 else{
    fallback to android native string
 }

 A new text should be added in below format in json file
 "txt_hello_world": [
   {
    "en": "Hello World",
    "hi": "नमस्ते दुनिया",
    "bn": "Ōhē biśba"
   }
  ]
Where key = "txt_hello_world" that is an array & it should contains all the supported language text

Similar way it should be added into android native string.xml with the same key name



