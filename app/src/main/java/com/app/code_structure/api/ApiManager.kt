package com.app.code_structure.api

import android.webkit.MimeTypeMap
import androidx.annotation.NonNull
import com.app.code_structure.BuildConfig
import com.app.code_structure.utils.Utility
import com.app.code_structure.utils.log
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.ArrayList
import java.util.HashMap
import java.util.concurrent.TimeUnit


/**
 * zealous system
 */

class ApiManager {

    companion object {

        val instance: ApiRepo
            get() {
                val retrofit = retrofitInstance
                return retrofit.create(ApiRepo::class.java)
            }

        val retrofitInstance: Retrofit
            get() {
                val httpClient = OkHttpClient.Builder()
                    httpClient.connectTimeout(2, TimeUnit.MINUTES)
                if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor()
                    logging.level = HttpLoggingInterceptor.Level.BODY
                    httpClient.addInterceptor(logging)
                }

                val accessToken = DataManager.getAccessToken()

                if (!accessToken.equals(""))
                    httpClient.addInterceptor(addAccessToken())

                return Retrofit.Builder()
                    .baseUrl(ApiRepo.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(trustAllSslClient(httpClient.build()))
                    .build()
            }

        @NonNull
        private fun addAccessToken(): Interceptor {

            return Interceptor { chain ->
                val original = chain.request()
                val accessToken = DataManager.getAccessToken()
                val request = original.newBuilder()
                    .header("Accept", "application/json")
                    .header("Authorization", "Bearer $accessToken")
                    .method(original.method, original.body)
                    .build()
                Utility.log("Authorization \nBearer $accessToken")
                chain.proceed(request)
            }
        }

        fun prepareFilePart(fileMap: HashMap<String, File>): List<MultipartBody.Part> {
            val finalFileMap = ArrayList<MultipartBody.Part>()

            for (paramName in fileMap.keys) {
                val mimeType = getMimeType(fileMap[paramName]!!.path)
                log("" + fileMap[paramName]!!.totalSpace + " is exist = " + mimeType)
                val requestFile = fileMap[paramName]!!
                    .asRequestBody((mimeType ?: "image/*").toMediaTypeOrNull())
                finalFileMap.add(
                    MultipartBody.Part.createFormData(
                        paramName,
                        fileMap[paramName]!!.name,
                        requestFile
                    )
                )

                finalFileMap.add(
                    MultipartBody.Part.createFormData(
                        paramName,
                        fileMap[paramName]!!.name,
                        requestFile
                    )
                )
            }

            if (finalFileMap.size == 0) {
                val file = RequestBody.create(MultipartBody.FORM, "")
                finalFileMap.add(MultipartBody.Part.createFormData("dummy", "", file))
            }
            // MultipartBody.Part is used to send also the actual file name
            return finalFileMap
        }

        fun getMimeType(url: String): String? {
            var type: String? = null
            val extension = MimeTypeMap.getFileExtensionFromUrl(url)
            if (extension != null) {
                type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
            }
            return type
        }

        fun trustAllSslClient(client: OkHttpClient): OkHttpClient {
            val builder = client.newBuilder()
            builder.readTimeout(60, TimeUnit.SECONDS)
            builder.connectTimeout(60, TimeUnit.SECONDS)
            builder.hostnameVerifier { hostname, session -> true }
            return builder.build()
        }
    }
}