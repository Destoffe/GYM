package com.stoffe.gym.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.stoffe.gym.R;

import androidx.annotation.NonNull;

public class LogDataDialogLayout extends LinearLayout {


    @NonNull
    public TextInputEditText setsEditText;
    @NonNull
    public TextInputEditText repsEditText;
    @NonNull
    public TextInputEditText weightEditText;
    public LogDataDialogLayout(Context context) {
        super(context);
        LayoutInflater.from(getContext()).inflate(
                R.layout.log_exercise_dialog, this);
        setsEditText = this.findViewById(R.id.sets_edit_text);
        repsEditText = this.findViewById(R.id.reps_edit_text);
        weightEditText = this.findViewById(R.id.weight_edit_text);
    }

}
