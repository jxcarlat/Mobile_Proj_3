package edu.ualr.jxcarlat.login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.lang.Exception

class Login : AppCompatActivity() {

    private lateinit var client: OkHttpClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        client = OkHttpClient()
    }

    override fun onResume() {
        super.onResume()

        val myUrl = "http://messenger.mattkennett.com/api-auth/v1/login/"
        val request: Request = Request.Builder()
                .url(myUrl)
                .build()

        val linearLayoutResponse: LinearLayout = findViewById(R.id.linearLayout)

        val newTextView: TextView = TextView(this)

        var updateText: String = ""

        doAsync {
            var response: Response? = null

            try {
                response = client.newCall(request).execute()
            }catch (e: Exception) {
                Log.d("MPK_UTILITY", "Network Error")
            }

            if (response != null) {
                val responseBody: String = response.body()!!.string()

                val gson = Gson()

                val myUser: GithubUser = gson.fromJson(responseBody, GithubUser::class.java)

                //val myMessage: messenger =
                      //  gson.fromJson(responseBody, messenger::class.java)

                val responseCode: Int = response.code()

                Log.d("MPK_UTILITY", "Body: " + responseBody)
                Log.d("MPK_UTILITY", "Code: " + responseCode.toString())
            }
            else {
                updateText = "Network Error"
            }

            uiThread {
                newTextView.text = updateText
                linearLayoutResponse.removeAllViews()
                val newTextView = TextView(this@Login)

                var newTextViewString: String = ""


                }
            }
        }



    }
