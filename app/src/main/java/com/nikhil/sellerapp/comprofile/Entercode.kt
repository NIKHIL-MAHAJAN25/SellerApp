package com.nikhil.sellerapp.comprofile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import com.nikhil.sellerapp.R
import com.nikhil.sellerapp.databinding.ActivityEntercodeBinding
import com.nikhil.sellerapp.home.HomeActivity

class Entercode : AppCompatActivity() {
    lateinit var binding: ActivityEntercodeBinding
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    val auid=auth.currentUser?.uid
    val db= Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEntercodeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        binding.btnVerifyOtp.setOnClickListener {
            val d1=binding.etOtp1.text.toString()
            val d2=binding.etOtp2.text.toString()
            val d3=binding.etOtp3.text.toString()
            val d4=binding.etOtp4.text.toString()
            val d5=binding.etOtp5.text.toString()
            val d6=binding.etOtp6.text.toString()
            val code=d1+d2+d3+d4+d5+d6
            if (auid != null) {
                fetchcode(auid){securecode->
                    if(securecode!=null && securecode==code)
                    {
                        db.collection("Users").document(auid).update("approved",true)
                        fetchname(auid){name->
                            val lancer= mapOf(
                                "name" to name,
                                "uid" to auid,
                                "profcomp" to false
                            )
                            db.collection("Freelancers").document(auid).set(lancer, SetOptions.merge())


                        }
                        Log.d("otp","otp verified")
                        startActivity(Intent(this,HomeActivity::class.java))
                    }
                    else {
                        Log.d("otp", "Invalid OTP ❌")
                    }
                }

            }
        }
    }

    fun fetchcode(auid:String,onResult:(String?)->Unit){
        db.collection("Users").document(auid).get().addOnSuccessListener {document->
            if(document!=null && document.exists()){

                val securecode=document.getString("approvalCode")
                Log.d("code","Code:$securecode")
                onResult(securecode)
            }
            else {
                onResult(null)
            }
        }
    }
    fun fetchname(auid:String,onResult: (String?) -> Unit){
        db.collection("Users").document(auid).get().addOnSuccessListener { document->
            if (document!=null && document.exists()){
                val name=document.getString("fullName")
                onResult(name)
            }
            else{
                onResult(null)
            }
        }
    }

}