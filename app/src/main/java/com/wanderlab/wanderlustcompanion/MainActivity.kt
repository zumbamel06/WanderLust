package com.wanderlab.wanderlustcompanion


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wanderlab.wanderlustcompanion.data.model.LoggedInUser
import com.wanderlab.wanderlustcompanion.databinding.ActivityMainBinding
import com.wanderlab.wanderlustcompanion.ui.home.HomeFragment


class MainActivity : AppCompatActivity() {

    private var displayname: String? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_details_dashboard, R.id.navigation_maps_search, R.id.navigation_recommendation
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        this.displayname = intent.getStringExtra("login_name")

        val dbHelper = DBHelper(this, "wanderlust.db", null, 1)
        val userInfo: LoggedInUser = dbHelper.user_Info(this.displayname.toString())

        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()

        val fragment: HomeFragment = HomeFragment.newInstance(this.displayname.toString(),
            userInfo.nickName,userInfo.fullName,userInfo.gender,userInfo.dob,userInfo.email)
        fragmentTransaction.add(R.id.frament_home, fragment)

        fragmentTransaction.commit()

    }
}