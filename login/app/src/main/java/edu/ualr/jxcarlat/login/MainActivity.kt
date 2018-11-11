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




class MainActivity : AppCompatActivity() {
    //We'll use OkHttpClient later in the program
    private lateinit var client: OkHttpClient



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val submitButton: Button = findViewById(R.id.button)
        //When we type in our username and password with confirmation, we begin the process of
        //submitting the information to the website.
        submitButton.setOnClickListener {
            Log.d("MPK_UTILITY", "setOnClickListener pressed")
            //Initialize all placeholders and prime client with OkHttpClient()
            val usernameEditText: EditText = findViewById(R.id.editText)
            val passwordEditText: EditText = findViewById(R.id.editText2)
            val confirmationEditText: EditText = findViewById(R.id.editText3)
            client = OkHttpClient()
            val myUrl = "http://messenger.mattkennett.com/api-auth/v1/registration/"
            //We will be posting a form to the registration endpoint of the REST API.
            //Post the username, password1, and confirmation to the formBody and request
            //The website to return an object
            val formBody: RequestBody = FormBody.Builder()
                    .add("username", usernameEditText.text.toString())
                    .add("password1", passwordEditText.text.toString())
                    .add("password2", confirmationEditText.text.toString())
                    .build()
            val request: Request = Request.Builder()
                    .url(myUrl)
                    .post(formBody)
                    .build()

            //In doAsync we grab the string object and set it to our response variable
            //if we have some sort of network error than we'll have to try again.
            doAsync {
                var response: Response? = null
                Log.d("MPK_UTILITY", "doAsync called")
                try {
                    response = client.newCall(request).execute()
                } catch (e: Exception) {
                    Log.d("MPK_UTILITY", "Network Error")
                }
                //Once we get our response string we go through the process here.
                //We use our data class of username, password1, password2, and key
                //to grab the authorization key upon a successful login. otherwise
                //we have an error that we can check in our Log
                if (response != null) {
                    val responseBody: String = response.body()!!.string()

                    val gson = Gson()

                    Log.d("MPK_UTILITY", responseBody)

                    val myUser: GithubUser = gson.fromJson(responseBody, GithubUser::class.java)



                    // else {
                    //  Log.d("MPK_UTILITY", "Network Error")
                    //  }
                    //If myUser.key is not null and contains a value, store this value
                    //in our sharedPreferences and go into the Login activity
                    uiThread {
                        if(myUser.key != null)
                        {
                            val intent = Intent(this@MainActivity, Login::class.java)
                            startActivity(intent)


                        }
                        else
                        {
                            val linearLayoutMessage: LinearLayout = findViewById(R.id.linearLayout)
                            val newTextView = TextView(this@MainActivity)
                            newTextView.text = "Error logging in, please ensure " +
                                    "that username is unique and passwords match. Password must " +
                                    "contain at least 8 characters and at least one number."
                            linearLayoutMessage.addView(newTextView)
                        }

                    }
                }

            }
        }
    }
}

