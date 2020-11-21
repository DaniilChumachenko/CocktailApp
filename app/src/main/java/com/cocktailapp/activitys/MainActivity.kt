package com.cocktailapp.activitys

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.cocktailapp.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    var flag: Boolean = false
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                return if (!flag) {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.firtdFragment)
                    flag = true
                    item.isVisible = false
                    toolbar.setNavigationIcon(R.drawable.ic_back_drinks)
                    toolbar.setNavigationOnClickListener {
                        findNavController(R.id.nav_host_fragment).navigate(R.id.FirstFragment)
                        flag = false
                        item.isVisible = true
                        toolbar.navigationIcon = null
                        toolbar.title = "Drinks"
                    }
                    toolbar.title = "Filters"
                    flag
                } else {
                    flag = false
                    flag
                }
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}