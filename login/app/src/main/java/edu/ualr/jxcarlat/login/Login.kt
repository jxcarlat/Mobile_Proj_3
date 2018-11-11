package edu.ualr.jxcarlat.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.google.gson.Gson
import okhttp3.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.lang.Exception

//We'll grab the key from the Json object here
const val KEY_AUTHORIZATION = ""

class Login : AppCompatActivity() {
    //We will initialize client and theKey later in the program
     lateinit var client: OkHttpClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

    }

    override fun onResume() {
        super.onResume()
        val submitButton: Button = findViewById(R.id.button2)
        //Much like the MainActivity this activity will take our user input information and
        //on submit, will write our information to a FormBody and send it to the other endpoint
        // of the REST API.
        submitButton.setOnClickListener {
            val myUrl = "http://messenger.mattkennett.com/api-auth/v1/login/"
            val usernameEditText: EditText = findViewById(R.id.editText4)
            val passwordEditText: EditText = findViewById(R.id.editText5)
            val formBody: RequestBody = FormBody.Builder()
                    .add("username", usernameEditText.toString())
                    .add("password1", passwordEditText.toString())
                    .build()
            Log.d("MPK_UTILITY", usernameEditText.toString())
            Log.d("MPK_UTILITY", passwordEditText.toString())
            val request: Request = Request.Builder()
                    .url(myUrl)
                    .post(formBody)
                    .build()


            //If everything is successful than we should have a successful login message in our
            //returned Json object.
            doAsync {
                var response: Response? = null
                client = OkHttpClient()

                try {
                    response = client.newCall(request).execute()
                } catch (e: Exception) {
                    Log.d("MPK_UTILITY", "Network Error")
                }

                if (response != null) {
                    val responseBody: String = response.body()!!.string()

                    val gson = Gson()

                    val myUser: GithubUser = gson.fromJson(responseBody, GithubUser::class.java)





                uiThread {
                    if (myUser.key != null) {
                        val intent = Intent(this@Login, LoginSuccessful::class.java)
                        intent.putExtra(KEY_AUTHORIZATION, myUser.key)
                        startActivity(intent)
                    }
                    else
                    {
                        val loginText: TextView = findViewById(R.id.textView)
                        loginText.text = "login unsuccessful, user is not recognized."
                    }

                }

                }
            }
        }

    }

    }
