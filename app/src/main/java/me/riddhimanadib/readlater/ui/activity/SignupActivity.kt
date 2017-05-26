package me.riddhimanadib.readlater.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_signup.*
import me.riddhimanadib.readlater.R
import org.jetbrains.anko.toast


class SignupActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {

    val RC_SIGN_IN: Int = 9001

    lateinit var mGoogleApiClient: GoogleApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()

        signInGoogle.setOnClickListener { signin() }
    }

    fun signin() {
        val signInIntent: Intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode === RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess) {
                // Google Sign In was successful, authenticate with Firebase
                val account = result.signInAccount
                firebaseAuthWithGoogle(account)
            } else {
                // Google Sign In failed, update UI appropriately
                toast("Google Sign In failed")
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?) {
        if (acct == null) {
            toast("Google Sign In failed")
        } else {
            val credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null)
            FirebaseAuth.getInstance().signInWithCredential(credential)
                    .addOnCompleteListener(this, {
                        task ->
                            if (task.isSuccessful)
                                toast(FirebaseAuth.getInstance().currentUser.toString())
                            else
                                toast("Authentication Failed!")
                    })
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        toast("Google Play Services error.")
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, SignupActivity::class.java))
        }
    }

}
