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
    //We will initialize client later in the program
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
                    .add("username", usernameEditText.text.toString())
                    .add("password", passwordEditText.text.toString())
                    .build()
            Log.d("MPK_UTILITY", usernameEditText.text.toString())
            Log.d("MPK_UTILITY", passwordEditText.text.toString())
            val request: Request = Request.Builder()
                    .url(myUrl)
                    .post(formBody)
                    .build()


            //If everything is successful than we should have a successful key message in our
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




                //If login is successful we grab the authorization key from the Json object and save
                    //it in our sharedPreferences KEY_AUTHORIZATION. Then we switch over to our
                    //Login Successful page.
                uiThread {
                    if (myUser.key != null) {
                        val intent = Intent(this@Login, LoginSuccessful::class.java)
                        intent.putExtra(KEY_AUTHORIZATION, myUser.key)
                        startActivity(intent)
                    }
                    //If login is unsuccessful we print out an error message and allow the user
                    //to try again.
                    else
                    {
                        val linearLayoutMessage: LinearLayout = findViewById(R.id.linearLayout2)
                        linearLayoutMessage.removeAllViews()
                        val newTextView = TextView(this@Login)
                        val newTextView2 = TextView(this@Login)
                        var newTextViewString: String = ""
                        if(myUser.password != null) {
                            for (text in myUser.password) {
                                newTextViewString += text + "\n"
                            }
                        }
                        newTextView.text = newTextViewString
                        linearLayoutMessage.addView(newTextView)
                        newTextViewString = ""
                        if(myUser.non_field_errors != null)
                        {
                            for(text1 in myUser.non_field_errors)
                            {
                                newTextViewString += text1 + "\n"
                            }
                        }
                        newTextView2.text = newTextViewString
                        linearLayoutMessage.addView(newTextView2)
                    }

                }

                }
            }
        }

    }

    }
