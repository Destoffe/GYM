package com.stoffe.gym.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.stoffe.gym.dashboard.BMIRepository
import com.stoffe.gym.database.entities.*
import kotlinx.coroutines.flow.MutableStateFlow
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