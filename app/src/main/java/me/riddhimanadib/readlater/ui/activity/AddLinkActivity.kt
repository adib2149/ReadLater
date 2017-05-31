package me.riddhimanadib.readlater.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import me.riddhimanadib.readlater.R
import me.riddhimanadib.readlater.ReadLaterApplication
import me.riddhimanadib.readlater.helper.HtmlParser
import me.riddhimanadib.readlater.model.Link


class AddLinkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_link)

        if (intent.data != null) {
            val task = GetUrlDataTask()
            task.execute(intent.data.toString())
        } else {
            Log.d(ReadLaterApplication.LOG_TAG, "link not found")
        }

    }


    private class GetUrlDataTask : AsyncTask<String, Unit, Link>() {

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg p0: String?): Link? {
            if (p0[0] == null) {
                Log.d(ReadLaterApplication.LOG_TAG, "no url found")
                return null
            } else {
                return HtmlParser.get(p0[0]!!)
            }
        }

        override fun onPostExecute(result: Link?) {
            result?.save()
        }

    }


    companion object {
        fun startForResult(context: Context, url: String, reqCode: Int) {
            var intent = Intent(context, AddLinkActivity::class.java)
            intent.putExtra(ReadLaterApplication.KEY_URL, url)
            val activity = context as Activity
            activity.startActivityForResult(intent, reqCode)
        }
    }

}
