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

const val USERNAME: String = "username"
const val PASSWORD: String = "password"
const val PASSWORD2: String = "confirm"


class MainActivity : AppCompatActivity() {

    val username = findViewById(R.id.editText) as EditText
    val password = findViewById(R.id.editText2) as EditText
    val confirmation = findViewById(R.id.editText3) as EditText
    private lateinit var client: OkHttpClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        client = OkHttpClient()
    }

    override fun onResume() {
        super.onResume()

        val myUrl = "http://messenger.mattkennett.com/api-auth/v1/registration/"
        val formBody: RequestBody = FormBody.Builder()
                .add("action", "authentication")
                .build()
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

                val requestBody: String = response.body()!!.string()
                Log.d("MPK_UTILITY", requestBody)

                val quizList: List<QuizType> =
                        gson.fromJson(requestBody,
                                object : TypeToken<List<QuizType>>() {}.type)



            }
            else {
                updateText = "Network Error"
            }

            uiThread {
                val intent = Intent(this@MainActivity, Login::class.java)
                val newButton = Button(this@MainActivity)
                newButton.setOnClickListener {
                    intent.putExtra(USERNAME, username.text)
                    intent.putExtra(PASSWORD, password.text)
                    intent.putExtra(PASSWORD2, confirmation.text)
                    startActivity(intent)
                }

            }
        }



    }
}
