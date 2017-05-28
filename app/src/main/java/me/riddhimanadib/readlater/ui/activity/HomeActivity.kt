package me.riddhimanadib.readlater.ui.activity

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // val link: Link = Link(title = "Awesome Article", url = "http://medium.com")
        // link.save()

        Link.getAll(this)
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

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, HomeActivity::class.java))
        }
    }

}
