package com.example.medicineremainder.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.medicineremainder.Model.MedicineType
import com.example.medicineremainder.R
import com.example.medicineremainder.Utilities.SharedPrefHelper
import com.example.medicineremainder.Utilities.ValidationUtils
import com.example.medicineremainder.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.example.medicineremainder.Model.User
import com.example.medicineremainder.Utilities.FirebaseManager

class RegisterActivity : ComponentActivity() {
    lateinit var firebase : FirebaseAuth
    lateinit var registerBinding: ActivityRegisterBinding
    lateinit var sharedPrefHelper: SharedPrefHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)
        firebase = FirebaseAuth.getInstance()
        sharedPrefHelper = SharedPrefHelper(this)

            registerBinding.registerBtn.setOnClickListener {

                val email = registerBinding.email.text.toString()
                val name = registerBinding.userName.text.toString()
                val age = registerBinding.age.text.toString()
                val password = registerBinding.password.text.toString()
                if (validateRegister(email,name,age,password)){
                    FirebaseManager.createUserWithEmailAndPassword(this,name,email,age.toInt(),password,registerBinding.progressBar,sharedPrefHelper)
               }
             }
    }
    //TODO: validate register
    fun validateRegister(email:String,name:String,age:String, password: String):Boolean{
        if (email.isEmpty() || password.isEmpty() || age.isEmpty() || name.isEmpty()){
            Toast.makeText(this, getString(R.string.please_fill_all_the_fields), Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.isEmpty()){
            Toast.makeText(this, getString(R.string.please_fill_all_the_fields), Toast.LENGTH_SHORT).show()
            return false
        }
        if (email.isEmpty()){
            Toast.makeText(this, getString(R.string.please_fill_all_the_fields), Toast.LENGTH_SHORT).show()
            return false
        }
        if (name.isEmpty()){
            Toast.makeText(this, getString(R.string.please_fill_all_the_fields), Toast.LENGTH_SHORT).show()
            return false
        }
        if (age.isEmpty()){
            Toast.makeText(this, getString(R.string.please_fill_all_the_fields), Toast.LENGTH_SHORT).show()
            return false
        }
        if (!ValidationUtils.isValidEmail(email)){
            Toast.makeText(this, getString(R.string.please_Enter_valid_email), Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}