package com.stoffe.gym.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import com.google.android.material.textfield.TextInputEditText
import com.stoffe.gym.R

class AddBmiLayout(context: Context?) : LinearLayout(context) {
    var ageEditText: TextInputEditText
    var heightEditText: TextInputEditText
    var weightEditText: TextInputEditText
    var genderGroup: RadioGroup
    var maleRadio: RadioButton
    var femaleRadio: RadioButton

    init {
        LayoutInflater.from(getContext()).inflate(
            R.layout.log_bmi_dialog, this
        )
        ageEditText = findViewById(R.id.age_edit_text)
        heightEditText = findViewById(R.id.height_edit_text)
        weightEditText = findViewById(R.id.weight_edit_text)
        genderGroup = findViewById(R.id.radioGroup)
        maleRadio = findViewById(R.id.radio_button_1)
        femaleRadio = findViewById(R.id.radio_button_2)
    }
}