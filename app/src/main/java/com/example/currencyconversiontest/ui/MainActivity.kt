package com.example.currencyconversiontest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.currencyconversiontest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var _bi: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _bi = ActivityMainBinding.inflate(layoutInflater)
        //      val view = _bi.root
        setContentView(_bi.root)
    }
}