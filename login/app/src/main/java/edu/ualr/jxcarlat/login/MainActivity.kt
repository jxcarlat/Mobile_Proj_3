package edu.ualr.jxcarlat.login

import android.content.Intent
import android.net.Network
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.EditText
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

const val KEY_AUTHORIZATION = ""


class MainActivity : AppCompatActivity() {
    private lateinit var client: OkHttpClient

    lateinit var username: String
    lateinit var password: String
    lateinit var password2: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val submitButton: Button = findViewById(R.id.button)

        submitButton.setOnClickListener {
            Log.d("MPK_UTILITY", "setOnClickListener pressed")
            val usernameEditText: EditText = findViewById(R.id.editText)
            val passwordEditText: EditText = findViewById(R.id.editText2)
            val confirmationEditText: EditText = findViewById(R.id.editText3)
            client = OkHttpClient()
            val myUrl = "http://messenger.mattkennett.com/api-auth/v1/registration/"
            val formBody: RequestBody = FormBody.Builder()
                    .add("username", usernameEditText.text.toString())
                    .add("password1", passwordEditText.text.toString())
                    .add("password2", confirmationEditText.text.toString())
                    .build()
            val request: Request = Request.Builder()
                    .url(myUrl)
                    .post(formBody)
                    .build()


            doAsync {
                var response: Response? = null
                Log.d("MPK_UTILITY", "doAsync called")
                try {
                    response = client.newCall(request).execute()
                } catch (e: Exception) {
                    Log.d("MPK_UTILITY", "Network Error")
                }

                if (response != null) {
                    val responseBody: String = response.body()!!.string()

                    val gson = Gson()
                    Log.d("MPK_UTILITY", responseBody)
                    val myUser: GithubUser = gson.fromJson(responseBody, GithubUser::class.java)
                    val updateText: String = myUser.messages


                    // else {
                    //  Log.d("MPK_UTILITY", "Network Error")
                    //  }

                    uiThread {

                    }
                }

            }
        }
    }
}

