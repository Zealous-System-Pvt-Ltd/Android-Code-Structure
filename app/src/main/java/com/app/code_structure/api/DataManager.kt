package com.app.code_structure.api



/**
 * Class contains all the necessary methods to interact with any data set.
 * there are mainly 3 data sources @[ApiManager] , @[SharedPreferenceManager] or @[SQLManager]
 * This class is responsible for deciding data source for required data
 * All the data operations must go through this class.
 */
object DataManager {


    /**
     * Calls @[ApiManager] to initiate Login request
     *
     * @param map        map containing email,password as parameters
     * @param onResponse @[OnResponse] listener to call on response received
     */

    fun getAccessToken(): String {
        return ""
    }





}