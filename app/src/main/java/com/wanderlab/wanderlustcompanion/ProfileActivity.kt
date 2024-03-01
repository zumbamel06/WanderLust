package com.wanderlab.wanderlustcompanion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.wanderlab.wanderlustcompanion.data.model.LoggedInUser

class ProfileActivity : AppCompatActivity(), View.OnClickListener  {
    private var displayname: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        this.displayname = intent.getStringExtra("login_name")
        val dbHelper = DBHelper(this, "wanderlust.db", null, 1)
        val userInfo:LoggedInUser = dbHelper.user_Info(this.displayname.toString())
        /*
        Full name = Last Name
        Nick Name = First Name
        Email = Email
        Phone = Phone
        Gender = Address
        DOB = City
        */
        val editFullName:EditText = findViewById(R.id.LastNAmeEditText)
        val editNickName:EditText = findViewById(R.id.FirstNameEditText)
        val editEmail:EditText = findViewById(R.id.emailEditText)
        val editPhone:EditText = findViewById(R.id.phoneEditText)
        val editGender:EditText = findViewById(R.id.addressEditText)
        val editDOB:EditText = findViewById(R.id.cityEditText)
        editFullName.setText(userInfo.fullName)
        editNickName.setText(userInfo.nickName)
        editEmail.setText(displayname)
        editPhone.setText(userInfo.phoneNumber)
        editGender.setText(userInfo.gender)
        editDOB.setText(userInfo.dob)
        val button:Button = findViewById(R.id.bt_profile_signup)
        button.setOnClickListener(this)
    }
    override fun onClick(arg0: View) {
        val btn = arg0 as Button
        val dbHelper = DBHelper(this, "wanderlust.db", null, 1)
        val userInfo:LoggedInUser = dbHelper.user_Info(displayname.toString())
        /*
        Full name = Last Name
        Nick Name = First Name
        Email = Email
        Phone = Phone
        Gender = Address
        DOB = City
        */
        val editFullName:EditText = findViewById(R.id.LastNAmeEditText)
        val editNickName:EditText = findViewById(R.id.FirstNameEditText)
        val editEmail:EditText = findViewById(R.id.emailEditText)
        val editPhone:EditText = findViewById(R.id.phoneEditText)
        val editGender:EditText = findViewById(R.id.addressEditText)
        val editDOB:EditText = findViewById(R.id.cityEditText)
        dbHelper.user_Update(this.displayname.toString(),editFullName.text.toString(),
            editNickName.text.toString(),editDOB.text.toString(),editPhone.text.toString(),
            editGender.text.toString())
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("login_name", this.displayname)
        startActivity(intent)
    }
}