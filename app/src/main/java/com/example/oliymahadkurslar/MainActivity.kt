package com.example.oliymahadkurslar

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.oliymahadkurslar.db.SavedDatabase
import com.example.oliymahadkurslar.db.SavedRep
import com.example.oliymahadkurslar.ui.vm.SavedVm
import com.example.oliymahadkurslar.ui.vm.SavedVmf
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: SavedVm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        val navController = Navigation.findNavController(this, R.id.fragment_container_view)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
        val sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("USER_NAME", "Javohir")!![0].uppercase()
        val userLetter = findViewById<TextView>(R.id.name_first)
        userLetter.text = userName

        val db = SavedDatabase(this)
        val repo = SavedRep(db)
        val vmf = SavedVmf(repo)
        viewModel = ViewModelProvider(this, vmf)[SavedVm::class.java]


        val toggle = findViewById<CardView>(R.id.image_button)
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
//        val navigationView = findViewById<NavigationView>(R.id.my_nav_layout)
//        val headerView = navigationView.getHeaderView(0)
//        val logOut = findViewById<TextView>(R.id.log_out_btn)
        toggle.setOnClickListener {
            drawer.openDrawer(Gravity.LEFT)
        }
    }
}