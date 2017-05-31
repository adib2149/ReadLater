package me.riddhimanadib.readlater.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import me.riddhimanadib.readlater.R
import me.riddhimanadib.readlater.ReadLaterApplication
import me.riddhimanadib.readlater.model.Link
import org.jetbrains.anko.toast


class HomeActivity : AppCompatActivity(), ValueEventListener {

    val REQ_CODE_ADD_LINK: Int = 1

    lateinit var urlFromIntent: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        urlFromIntent = intent.getStringExtra(ReadLaterApplication.KEY_URL)

        if (urlFromIntent.equals("")) {
            Link.getAll(this)
        } else {
            AddLinkActivity.startForResult(this, urlFromIntent, REQ_CODE_ADD_LINK)
        }

    }

    override fun onDataChange(dataSnapshot: DataSnapshot?) {
        if (dataSnapshot == null) {
            toast("No data found")
        } else {
            dataSnapshot.children.forEach {
                dataSnapshot: DataSnapshot? ->
                    if (dataSnapshot != null) {
                        val link = dataSnapshot.getValue(Link::class.java)
                        Log.d(ReadLaterApplication.LOG_TAG, link.title)
                    }
            }
        }
    }

    override fun onCancelled(databaseError: DatabaseError?) {
        toast("Failed to read value.")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if ((resultCode == Activity.RESULT_OK) && (requestCode == REQ_CODE_ADD_LINK)) {
            Link.getAll(this)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        fun start(context: Context, url: String) {
            var intent = Intent(context, HomeActivity::class.java)
            intent.putExtra(ReadLaterApplication.KEY_URL, url)
            context.startActivity(intent)
        }
    }

}
