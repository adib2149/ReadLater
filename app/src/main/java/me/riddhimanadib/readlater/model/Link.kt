package me.riddhimanadib.readlater.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * Created by Adib on 27-May-17.
 */
class Link {

    lateinit var title: String
    lateinit var url: String
    var timeStamp: Long = System.currentTimeMillis()
    var description: String = ""
    var imageUrl: String = ""
    var isRead: Boolean = false

    constructor()

    constructor(title: String, url: String) {
        this.title = title
        this.url = url
        this.timeStamp = System.currentTimeMillis()
        this.description = ""
        this.imageUrl = ""
        this.isRead = false
    }

    fun save() {
        FirebaseDatabase.getInstance()
                .reference
                .child("links")
                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                .push()
                .setValue(this)
    }

    override fun toString(): String {
        return "title: " + this.title + ", url: " + this.url + ", description: " + this.description + ", imageUrl: " + this.imageUrl + ", isRead: " + this.isRead + ", timeStamp: " + this.timeStamp;
    }

    companion object {

        fun getAll(/*page: Int, */listener: ValueEventListener) {

            // val limit = 2
            // val offset = 2 * page

            val query = FirebaseDatabase.getInstance()
                    .reference
                    .child("links")
                    .child(FirebaseAuth.getInstance().currentUser!!.uid)
                    .orderByChild("timestamp")
                    // .startAt(offset)
                    // .limitToLast(limit)

            query.addValueEventListener(listener)
        }

    }

}