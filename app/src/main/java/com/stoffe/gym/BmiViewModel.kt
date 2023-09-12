package com.stoffe.gym.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stoffe.gym.dashboard.BMIRepository
import com.stoffe.gym.database.entities.*
import kotlinx.coroutines.flow.MutableStateFlow

class BmiViewModel(application: Application) : AndroidViewModel(
    application
) {
    private val bmiRepository = BMIRepository(getDatabase(application))

    val allBMI = bmiRepository.bmi

}