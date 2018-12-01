package edu.ualr.jxcarlat.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
const val PK_ID = "edu.ualr.jxcarlat.login.PK_ID"
const val KEY_AUTHORIZATION = "edu.ualr.jxcarlat.login.KEY_AUTHORIZATION"
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val registerButton: Button = findViewById(R.id.button3)
        val loginButton: Button = findViewById(R.id.button4)
        //In our MainActivity We want our users to have the ability to decide whether they want
        //to go to the register activity to register a user or go to the login activity
        //and login as a pre-existing user. We provide two buttons in this activity called login
        //and register to help the transition.
        registerButton.setOnClickListener{
            val intent = Intent(this@MainActivity, Register::class.java)
            startActivity(intent)
        }
        loginButton.setOnClickListener{
            val intent = Intent(this@MainActivity, Login::class.java)
            startActivity(intent)
        }
    }
}