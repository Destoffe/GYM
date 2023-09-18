package com.stoffe.gym.dashboard

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.stoffe.gym.database.entities.BMI
import java.time.LocalDateTime

class BmiAddActivity : AppCompatActivity() {
    private lateinit var dashboardViewModel: DashboardViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]
            BmiAddAScreen(
                onCreateBmi = { weight,height,age,gender ->
                    dashboardViewModel.insertBmi(BMI(weight = weight, height =  height, age = age, gender = gender, date = LocalDateTime.now()))
                    finish()
                },
                onExit = { finish() }
            )
        }
    }
}