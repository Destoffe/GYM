package com.stoffe.gym.Adapters;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.stoffe.gym.R;

import androidx.core.content.ContextCompat;

public class AddExerciseLayout extends LinearLayout {
    final NumberPicker startTimePicker;
    final NumberPicker incrementPicker;
    public final TextInputEditText editText;
    private TextInputLayout textInputLayout;

    private boolean picker;
    private boolean showEditText;

    public AddExerciseLayout(Context context,boolean picker,boolean showEditText) {
        super(context);
        this.picker = picker;
        this.showEditText = showEditText;
        this.startTimePicker = new NumberPicker(context);
        this.incrementPicker = new NumberPicker(context);
        textInputLayout = new TextInputLayout(new ContextThemeWrapper(context, R.style.Widget_MaterialComponents_TextInputLayout_OutlinedBox));
        editText = new TextInputEditText (textInputLayout.getContext());
        this.setBackgroundColor(getResources().getColor(R.color.white_background));
        setupView(context);
    }

    private void setupView(Context context){

        this.setOrientation(LinearLayout.VERTICAL);

        LinearLayout pickerLinearLayout = new LinearLayout(context);
        pickerLinearLayout.setBackgroundColor(getResources().getColor(R.color.white_background));
        pickerLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

        textInputLayout.setBoxBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.white));
        textInputLayout.setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_OUTLINE);

        if(showEditText) {
            textInputLayout.addView(editText, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        }
        textInputLayout.setPadding(32,0,32,0);
        textInputLayout.setHintTextAppearance(R.style.Widget_MaterialComponents_TextInputLayout_OutlinedBox);
        if(showEditText) {
            this.addView(textInputLayout);
        }
        this.addView(pickerLinearLayout);
        pickerLinearLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.white));

        startTimePicker.setMaxValue(100);
        startTimePicker.setMinValue(0);

        incrementPicker.setMaxValue(100);
        incrementPicker.setMinValue(0);

        this.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        startTimePicker.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        incrementPicker.setBackgroundColor(ContextCompat.getColor(context, R.color.white));


        LayoutParams numPickerParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        numPickerParams.weight = 1;

        LayoutParams incPickerParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        incPickerParams.weight = 1;

        //pickerLinearLayout.setLayoutParams(params);
        if(picker) {
            pickerLinearLayout.addView(startTimePicker, numPickerParams);
            pickerLinearLayout.addView(incrementPicker, incPickerParams);
        }
    }

    public void setEditText(String string){
        editText.setHint(string);
    }

    public int getStartTimePickerValue() {
        return startTimePicker.getValue();
    }

    public int getIncrementPickerValue() {
        return incrementPicker.getValue();
    }
}
