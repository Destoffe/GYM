package com.stoffe.gym.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.stoffe.gym.R;

import androidx.annotation.NonNull;

public class LogDataDialogLayout extends LinearLayout {

    @NonNull
    public final TextInputEditText setsEditText;
    @NonNull
    public final TextInputEditText repsEditText;
    @NonNull
    public final TextInputEditText weightEditText;
    public LogDataDialogLayout(Context context) {
        super(context);
        LayoutInflater.from(getContext()).inflate(
                R.layout.log_exercise_dialog, this);
        setsEditText = this.findViewById(R.id.age_edit_text);
        repsEditText = this.findViewById(R.id.height_edit_text);
        weightEditText = this.findViewById(R.id.weight_edit_text);
    }

}
