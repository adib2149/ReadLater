package me.riddhimanadib.readlater.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import me.riddhimanadib.readlater.R

class AddLinkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_link)

        println(intent.data)

    }
}
