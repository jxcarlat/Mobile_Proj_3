package edu.ualr.jxcarlat.login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log


class LoginSuccessful : AppCompatActivity() {

//If we hit this activity our login was successful and we grab the key from the previous login
    //activity to take with us. We set our variable theKey to our key being held in our
    //Shared Preferences KEY_AUTHORIZATION.
    lateinit var theKey: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_successful)

        theKey = intent.getStringExtra(KEY_AUTHORIZATION)
        Log.d("MPK_UTILITY", theKey)
    }

}
