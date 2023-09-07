package com.stoffe.gym.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.stoffe.gym.dashboard.BMIRepository
import com.stoffe.gym.database.entities.*
import kotlinx.coroutines.flow.MutableStateFlow

class DashboardViewModel(application: Application?) : AndroidViewModel(
    application!!
) {
    var bmiRepository =  BMIRepository(application)

    fun insertBmi(bmi: BMI) {
        bmiRepository.insert(bmi)
    }

    val allBMI: LiveData<List<BMI>>
        get() = bmiRepository.bmi

}