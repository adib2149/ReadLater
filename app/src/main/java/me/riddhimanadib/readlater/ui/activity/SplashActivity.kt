package me.riddhimanadib.readlater.ui.activity

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import me.riddhimanadib.readlater.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            finish()
            if (FirebaseAuth.getInstance().currentUser == null) SignupActivity.start(this, intent?.data.toString()) else HomeActivity.start(this, intent?.data.toString())
        }, 1500)

    }

}