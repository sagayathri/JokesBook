package com.gayathri.jokesbook.repository

import com.gayathri.jokesbook.data.api.IApiService
import timber.log.Timber
import javax.inject.Inject

class JokesRepo @Inject constructor(
   private val api : IApiService
) {
   suspend fun getJokes(type: String): String {
      var jokes = ""
      return try {
         val response = api.getJokesByType(type)
         if (response.isSuccessful) {
              val responseToJsonSting = response.body()?.string()
              // Convert to parsable JSON format
              jokes = "{ \"jokes\": $responseToJsonSting }"
         }
         jokes
      } catch (exception: Exception) {
         Timber.d("ERROR >>> $exception")
         "ERROR $exception"
      }
   }
}