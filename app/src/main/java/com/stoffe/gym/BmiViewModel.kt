package com.stoffe.gym.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.stoffe.gym.dashboard.bmi.BMIRepository

class BmiViewModel(application: Application) : AndroidViewModel(
    application
) {
    private val bmiRepository = BMIRepository(getDatabase(application))

    val allBMI = bmiRepository.bmi

}