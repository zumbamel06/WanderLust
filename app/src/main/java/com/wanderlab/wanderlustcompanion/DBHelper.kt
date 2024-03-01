package com.wanderlab.wanderlustcompanion

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper
import com.wanderlab.wanderlustcompanion.data.model.LoggedInUser
import com.wanderlab.wanderlustcompanion.ui.home.HomeFragment


class DBHelper(context: Context?, name: String?, factory: CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, name, factory, version) {
    var imageHelper: ImageHelper = ImageHelper()
    override fun onCreate(db: SQLiteDatabase) {
        //create table
        db.execSQL("CREATE TABLE USER( _id INTEGER PRIMARY KEY AUTOINCREMENT, userID TEXT, password TEXT, fullName TEXT, nickName TEXT, dob TEXT, email TEXT, phone TEXT, gender TEXT);")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
    fun user_Insert(userid: String, password: String) {
        // open read and write database
        val db = this.writableDatabase
        // execute insert query
        db.execSQL("INSERT INTO USER VALUES(null, '$userid', '$password', '$userid', '$userid', '$userid', '$userid', '$userid', '$userid');")
        db.close()
    }

    fun user_Update(
        userId: String,
        fullname: String,
        nickname: String,
        dob: String,
        phone: String,
        gender: String
    ) {
        // Update user info
        val db = this.writableDatabase
        db.execSQL("UPDATE USER SET fullName = '$fullname', nickName='$nickname', dob='$dob', phone='$phone', gender='$gender' WHERE userID='$userId';")
        db.close()
    }

    fun user_Info(userId: String): LoggedInUser {
        // Update user info
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM USER WHERE userID='$userId';", null)
        var userData = LoggedInUser(userId, userId)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                userData = LoggedInUser(
                    userId = cursor.getString(1),
                    fullName = cursor.getString(3),
                    displayName = cursor.getString(1),
                    nickName = cursor.getString(4).toString(),
                    dob = cursor.getString(5),
                    email = cursor.getString(6),
                    phoneNumber = cursor.getString(7),
                    gender = cursor.getString(8)
                )
                break
            }
        }
        cursor.close()
        db.close()
        return userData
    }

    //registration check function
    fun user_IsUsernameFree(userId: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM USER WHERE userID = '$userId'", null)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                return false
            }
        }
        cursor!!.close()
        db.close()
        return true
    }

    //login function
    fun user_Login(userId: String, password: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM USER WHERE userID = '$userId' AND password = '$password'",
            null
        )
        if (cursor != null) {
            while (cursor.moveToNext()) {
                return true
            }
        }
        cursor!!.close()
        db.close()
        return false
    }

    fun user_Allcount(): Int {
        // Open available reading database
        val db = this.readableDatabase
        var count = 0
        // Get all recipes data
        val cursor = db.rawQuery("SELECT COUNT(*) FROM USER", null)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                count = cursor.getInt(0)
            }
        }
        cursor!!.close()
        db.close()
        return count
    }
}