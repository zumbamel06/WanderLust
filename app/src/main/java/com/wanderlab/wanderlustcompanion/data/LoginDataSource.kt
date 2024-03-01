package com.wanderlab.wanderlustcompanion.data

import com.wanderlab.wanderlustcompanion.DBHelper
import com.wanderlab.wanderlustcompanion.data.model.LoggedInUser
import java.io.IOException


/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(dbHelper: DBHelper, username: String, password: String): Result<LoggedInUser> {
        if (dbHelper.user_IsUsernameFree(username)) {
            dbHelper.user_Insert(username, password)
        }
        return if (dbHelper.user_Login(username, password)) {
            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), username)
            Result.Success(fakeUser)
        } else {
            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "LoggedOut")
            Result.Fail(fakeUser)
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}