package edu.ualr.jxcarlat.login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.jetbrains.anko.doAsync
import java.lang.Exception

class ChatChannelActivity : AppCompatActivity() {
    lateinit var theKey: String
    lateinit var pkValue: String
    lateinit var client: OkHttpClient
    lateinit var linearLayoutNames: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_channel)
        pkValue = intent.getStringExtra(PK_ID)
        theKey = intent.getStringExtra(KEY_AUTHORIZATION)
        Log.d("MPK_UTILITY", theKey)

    }

    override fun onResume() {
        super.onResume()

        val request: Request = Request.Builder()
                .url("http://messenger.mattkennett.com/api/v1/channel-messages/" + pkValue + "/")
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
                val channelId: List<ChatChannel> = gson.fromJson(responseBody,
                        object : TypeToken<List<ChatChannel>>() {}.type)
                Log.d("MPK_UTILITY", "I'm here")

            }
        }
    }
}
