package com.example.etisalatassessment.service.network

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class ImageDownloadTask(private val callback: (Bitmap?) -> Unit) :
    AsyncTask<String, Void, Bitmap?>() {

    override fun doInBackground(vararg params: String): Bitmap? {
        val urlString = params[0]

        try {
            val url = URL(urlString)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream: InputStream = BufferedInputStream(connection.inputStream)
                return BitmapFactory.decodeStream(inputStream)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    override fun onPostExecute(result: Bitmap?) {
        callback(result)
    }
}