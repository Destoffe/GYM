package com.stoffe.gym.dashboard.bmi

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import com.stoffe.gym.BmiViewModel

class BmiGraphActivity : AppCompatActivity() {
    private lateinit var bmiViewModel: BmiViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            bmiViewModel = ViewModelProvider(this)[BmiViewModel::class.java]
            val item by bmiViewModel.allBMI.collectAsState(initial = listOf())
            BmiGraphScreen(
                data = item,
                onExit = { finish() }
            )
        }
    }
}