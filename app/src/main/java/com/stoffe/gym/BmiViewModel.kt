package com.stoffe.gym

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.stoffe.gym.dashboard.bmi.BMIRepository
import com.stoffe.gym.database.getDatabase

class BmiViewModel(application: Application) : AndroidViewModel(
    application
) {
    private val bmiRepository = BMIRepository(getDatabase(application))

    val allBMI = bmiRepository.bmi

}