package com.example.etisalatassessment.service.network

import android.os.AsyncTask
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class NetworkTask(private val callback: (String?) -> Unit) : AsyncTask<String, Void, String>() {

    override fun doInBackground(vararg params: String): String? {
        val urlString = params[0]
        var connection: HttpURLConnection? = null
        var reader: BufferedReader? = null

        try {
            val url = URL(urlString)
            connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                reader = BufferedReader(InputStreamReader(connection.inputStream))
                val response = StringBuilder()
                var line: String? = reader.readLine()
                while (line != null) {
                    response.append(line)
                    line = reader.readLine()
                }
                return response.toString()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            connection?.disconnect()
            reader?.close()
        }

        return null
    }

    override fun onPostExecute(result: String?) {
        callback(result)
    }
}