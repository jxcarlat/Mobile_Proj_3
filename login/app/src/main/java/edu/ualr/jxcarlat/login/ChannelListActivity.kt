package edu.ualr.jxcarlat.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_login_successful.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.lang.Exception


class ChannelListActivity : AppCompatActivity() {

    //If we hit this activity our login was successful and we grab the key from the previous login
    //activity to take with us. We set our variable theKey to our key being held in our
    //Shared Preferences KEY_AUTHORIZATION.
    lateinit var theKey: String
    lateinit var client: OkHttpClient
    lateinit var linearLayoutNames: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_successful)

        theKey = intent.getStringExtra(KEY_AUTHORIZATION)
        Log.d("MPK_UTILITY", theKey)
    }

    override fun onResume() {
        super.onResume()

        linearLayoutNames = findViewById(R.id.linearLayout2)
        linearLayoutNames.removeAllViews()

        val request: Request = Request.Builder()
                .url("http://messenger.mattkennett.com/api/v1/channels/")
                .header("Authorization", "Token " + theKey)
                .build()

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
                Log.d("MPK_UTILITY", "I'm here first")
                val channelId: List<ChatChannel>
                        = gson.fromJson(responseBody,
                        object : TypeToken<List<ChatChannel>>() {}.type)
                Log.d("MPK_UTILITY", "I'm here")
                //Log.d("MPK_UTILITY", channelId.name.text.toString())
                uiThread {
                        val intent = Intent(this@ChannelListActivity, ChatChannelActivity::class.java)
                        for(names in channelId) {
                            val newButton = Button(this@ChannelListActivity)
                            newButton.text = names.name
                            newButton.setOnClickListener{
                                intent.putExtra(PK_ID, names.pk)
                                Log.d("MPK_UTILITY", "The pk value is: " + PK_ID)
                                intent.putExtra(KEY_AUTHORIZATION, theKey)
                                Log.d("MPK_UTILITY", "The key value is: " + KEY_AUTHORIZATION)
                                Log.d("MPK_UTILITY", "The pk value is now: " + PK_ID)
                                startActivity(intent)
                                Log.d("MPK_UTILITY", "I've been pushed")
                            }
                            linearLayoutNames.addView(newButton)
                        }

                        Log.d("MPK_UTILITY", "You got me")
                    if(channelId[0].detail != null)
                    {
                        val linearLayoutMessage: LinearLayout = findViewById(R.id.linearLayout2)
                        val newTextView2 = TextView(this@ChannelListActivity)
                        var newTextViewString: String? = ""
                        newTextViewString = channelId[0].detail
                        newTextView2.text = newTextViewString
                        linearLayoutMessage.addView(newTextView2)
                    }
                }
            }
        }
    }
}



