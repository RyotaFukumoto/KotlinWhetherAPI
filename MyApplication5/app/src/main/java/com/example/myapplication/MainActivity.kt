package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main()
    }
    fun main() {
        var connection: HttpURLConnection? = null
        var reader: BufferedReader? = null
        val buffer: StringBuffer
        try {
            //param[0]にはAPIのURI(String)を入れます(後ほど)。
            val API_URL = "http://weather.livedoor.com/forecast/webservice/json/v1?city=140010"
            val url = URL(API_URL)
            connection = url.openConnection() as HttpURLConnection
            connection.setConnectTimeout(1000)
            connection.connect()  //ここで指定したAPIを叩いてみてます。
            //アクセスする際のURL

            val stream = connection.inputStream
            reader = BufferedReader(InputStreamReader(stream))
            buffer = StringBuffer()
            var line: String?
            while (true) {
                line = reader.readLine()
                if (line == null) {
                    break
                }
                buffer.append(line)
                //Log.d("CHECK", buffer.toString())
            }
            val jsonText = buffer.toString()
            val parentJsonObj = JSONObject(jsonText)
        }catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        //finallyで接続を切断してあげましょう。
        finally {
            connection?.disconnect()
            try {
                reader?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        //失敗した時はnullやエラーコードなどを返しましょう
    }

}
