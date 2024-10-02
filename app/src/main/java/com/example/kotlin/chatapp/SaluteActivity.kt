package com.example.mychatapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin.chatapp.databinding.ActivitySaluteBinding

class SaluteActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySaluteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaluteBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}