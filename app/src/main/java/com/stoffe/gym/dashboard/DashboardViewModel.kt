package com.stoffe.gym.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.stoffe.gym.database.entities.*
import com.stoffe.gym.database.getDatabase
import kotlinx.coroutines.launch

class DashboardViewModel(application: Application) : AndroidViewModel(
    application
) {
    private val bmiRepository = BMIRepository(getDatabase(application))

    fun insertBmi(bmi: BMI) {
        viewModelScope.launch{
            bmiRepository.insert(bmi)
        }
    }

    val allBMI = bmiRepository.bmi
}