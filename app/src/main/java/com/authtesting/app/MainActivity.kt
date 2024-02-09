package com.authtesting.app

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.authtesting.app.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result ->
        if(result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            Toast.makeText(this, "sucessful", Toast.LENGTH_LONG).show()
            //return gmail
            if (task.isSuccessful){
                startActivity(Intent(this, HomeActivity::class.java))
            }else{
                Toast.makeText(this, "login failed", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Sometthing went wrong"+" ${result.data?.extras.toString()}",
                Toast.LENGTH_LONG).show()
            Log.d("checkResult",result.data?.toString()+"")
            //return error
        }
    }

    lateinit var signInRequest: GoogleSignInClient
    lateinit var auth: FirebaseAuth

    fun initializeGoogleSignIn(){
//        activity?:return
//        auth = Firebase.auth
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(this.resources.getString(R.string.client_id))
            .requestEmail()
            .build()
        Log.d("testing",resources.getString(R.string.client_id))
//        with(activity!!) {
        signInRequest = GoogleSignIn.getClient(this, googleSignInOptions)
//        startActivityForResult(signInRequest.signInIntent,202)
        launcher.launch(signInRequest.signInIntent)
//        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.signIn.setOnClickListener {
            initializeGoogleSignIn()
        }
    }
}