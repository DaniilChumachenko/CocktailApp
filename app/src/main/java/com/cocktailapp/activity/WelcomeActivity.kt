package com.cocktailapp.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cocktailapp.R
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_screen)
        supportActionBar?.hide()
        Observable.timer(1500, TimeUnit.MILLISECONDS)
            .subscribe {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }.isDisposed
    }
}