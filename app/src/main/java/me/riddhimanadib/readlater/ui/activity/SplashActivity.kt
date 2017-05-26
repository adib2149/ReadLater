package me.riddhimanadib.readlater.ui.activity

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import me.riddhimanadib.readlater.R
import me.riddhimanadib.readlater.ReadLaterApplication
import org.jetbrains.anko.toast

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Log.d(ReadLaterApplication.LOG_TAG, FirebaseAuth.getInstance().currentUser.toString())
        Handler().postDelayed(Runnable {
            if (FirebaseAuth.getInstance().currentUser == null) {
                finish()
                SignupActivity.start(this)
            } else {
                toast("Logged in")
            }
        }, 1500)

    }

}