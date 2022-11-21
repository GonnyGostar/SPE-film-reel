package com.nulight.FilmTool.ui.filmPage;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.nulight.FilmTool.MainActivity;
import com.nulight.FilmTool.R;
import com.nulight.FilmTool.film.FilmFormat;
import com.nulight.FilmTool.film.RuntimeInputFilter;

import org.w3c.dom.Text;

public class filmFragment extends Fragment {
    MainActivity main;
    EditText reelValue;
    EditText hourValue;
    EditText minuteValue;
    EditText secValue;
    int hour;
    int min;
    int sec;
    boolean value;

    private formula2ViewModel formula2ViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        formula2ViewModel = ViewModelProviders.of(this).get(formula2ViewModel.class);

        View root = inflater.inflate(R.layout.fragment_film, container, false);

        this.main = (MainActivity) getActivity();


        //false = runtime true= reel length to be calculated
        this.value=false;

        this.reelValue = root.findViewById(R.id.reelValue);
        this.hourValue = root.findViewById(R.id.hourInput);
        this.minuteValue = root.findViewById(R.id.minuteInput);
        this.secValue = root.findViewById(R.id.secondInput);





        this.createReelLength(root);
        this.createFilmFormatDropDown(root);
        this.createFrameRateDropDown(root);
        this.createUnitInputToggle(root);
        this.createToggleCalculatedValue(root);
        this.toggleCalculator(false, root);
        this.createRuntime(root);

        return root;
    }

    private void createToggleCalculatedValue(final View root){
        ToggleButton calcToggle = root.findViewById(R.id.valueToggle);
        calcToggle.setText("Runtime");
        calcToggle.setTextOff("Runtime");
        calcToggle.setTextOn("Reel Length");

        calcToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleCalculator(isChecked, root);
                updateFormula();
            }
        });

    }

    public void toggleCalculator(boolean value, View root){
        this.value = value;
        System.out.println(value);

        ImageView runtimeBG = root.findViewById(R.id.calculated_background);
        TextView runtimeTitle = root.findViewById(R.id.calculated_title);
        TextView runtimeHT = root.findViewById(R.id.runtimeUnit1);
        TextView runtimeMT = root.findViewById(R.id.runtimeUnit2);
        TextView runtimeST = root.findViewById(R.id.runtimeUnit3);
        EditText hourInput = root.findViewById(R.id.hourInput);
        EditText minuteInput = root.findViewById(R.id.minuteInput);
        EditText secondInput = root.findViewById(R.id.secondInput);



        ImageView reelLengthBG = root.findViewById(R.id.input_background);
        TextView reelLengthTitle = root.findViewById(R.id.reelLengthLabel);
        TextView reelLengthUnit = root.findViewById(R.id.reelLengthUnit);
        EditText reelLength = root.findViewById(R.id.reelValue);

        //true calculate reel length
        if(value){
            hourInput.setEnabled(true);
            minuteInput.setEnabled(true);
            secondInput.setEnabled(true);

            hourInput.requestFocus();

            EditText reelLengthInput = root.findViewById(R.id.reelValue);
            reelLengthInput.setEnabled(false);

            // Set colours accordingly
            runtimeBG.setBackgroundColor(getResources().getColor(R.color.backgroundColor));
            runtimeTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
            runtimeHT.setTextColor(getResources().getColor(R.color.colorPrimary));
            runtimeMT.setTextColor(getResources().getColor(R.color.colorPrimary));
            runtimeST.setTextColor(getResources().getColor(R.color.colorPrimary));
            hourInput.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            minuteInput.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            secondInput.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            hourInput.setTextColor(getResources().getColor(R.color.colorPrimary));
            minuteInput.setTextColor(getResources().getColor(R.color.colorPrimary));
            secondInput.setTextColor(getResources().getColor(R.color.colorPrimary));
            hourInput.setHintTextColor(getResources().getColor(R.color.secondaryColor));
            minuteInput.setHintTextColor(getResources().getColor(R.color.secondaryColor));
            secondInput.setHintTextColor(getResources().getColor(R.color.secondaryColor));


            reelLengthBG.setBackgroundColor(getResources().getColor(R.color.secondaryColor));
            reelLengthTitle.setTextColor(getResources().getColor(android.R.color.black));
            reelLengthUnit.setTextColor(getResources().getColor(android.R.color.black));
            reelLength.setTextColor(getResources().getColor(android.R.color.black));
            reelLength.getBackground().mutate().setColorFilter(getResources().getColor(R.color.secondaryColor), PorterDuff.Mode.SRC_ATOP);

        }

        else{

            EditText reelLengthInput = root.findViewById(R.id.reelValue);
            reelLengthInput.setEnabled(true);

            reelLengthInput.requestFocus();

            hourInput.setEnabled(false);
            minuteInput.setEnabled(false);
            secondInput.setEnabled(false);

            // Set colours accordingly
            runtimeBG.setBackgroundColor(getResources().getColor(R.color.secondaryColor));
            runtimeTitle.setTextColor(getResources().getColor(android.R.color.black));
            runtimeHT.setTextColor(getResources().getColor(android.R.color.black));
            runtimeMT.setTextColor(getResources().getColor(android.R.color.black));
            runtimeST.setTextColor(getResources().getColor(android.R.color.black));
            hourInput.getBackground().mutate().setColorFilter(getResources().getColor(R.color.secondaryColor), PorterDuff.Mode.SRC_ATOP);
            minuteInput.getBackground().mutate().setColorFilter(getResources().getColor(R.color.secondaryColor), PorterDuff.Mode.SRC_ATOP);
            secondInput.getBackground().mutate().setColorFilter(getResources().getColor(R.color.secondaryColor), PorterDuff.Mode.SRC_ATOP);
            hourInput.setTextColor(getResources().getColor(android.R.color.black));
            minuteInput.setTextColor(getResources().getColor(android.R.color.black));
            secondInput.setTextColor(getResources().getColor(android.R.color.black));

            reelLengthBG.setBackgroundColor(getResources().getColor(R.color.backgroundColor));
            reelLengthTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
            reelLengthUnit.setTextColor(getResources().getColor(R.color.colorPrimary));
            reelLength.setTextColor(getResources().getColor(R.color.colorPrimary));
            reelLength.setHintTextColor(getResources().getColor(R.color.secondaryColor));
            reelLength.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        }

    }

    private void createUnitInputToggle(final View root){
        ToggleButton unitInput = root.findViewById(R.id.unitInput2);
        unitInput.setText(R.string.metric);
        unitInput.setTextOff("Metric");
        unitInput.setTextOn("Imperial");

        unitInput.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setUnit(isChecked, root);
                updateFormula();
            }
        });
    }

    private void createReelLength(View root){
        final EditText reelLengthInput = root.findViewById(R.id.reelValue);
        Double currentRl = (this.main.formula2Reel.getReelLength());
        if (currentRl > 0) {
            String s = Double.toString(currentRl);
            reelLengthInput.setText(s);
        }
        reelLengthInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (reelLengthInput.hasFocus()) {
                    try {
                        double reelLength = Integer.parseInt(s.toString());
                        setReelLength(reelLength);
                        updateFormula();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void createFilmFormatDropDown(View root){
        Spinner filmFormatSpinner = (Spinner) root.findViewById(R.id.filmFormatInput);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_style, this.main.formula2Reel.getFilmFormatArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filmFormatSpinner.setAdapter(adapter);
        // Remember the previously chosen film format
        FilmFormat currentFf = this.main.formula2Reel.getFilmFormat();
        if (currentFf.getID() != 0) {
            filmFormatSpinner.setSelection(currentFf.getID());
        }
        filmFormatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                int index = parent.getSelectedItemPosition();
                System.out.println(index);
                setFilmFormat(index);
                updateFormula();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void createFrameRateDropDown(View root){
        Spinner frameRateSpinner = (Spinner) root.findViewById(R.id.frameRateInput);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_style, this.main.formula2Reel.getFrameRateArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        frameRateSpinner.setAdapter(adapter);
        // Remember the previously chosen framerate
        int currentFpsID = this.main.formula2Reel.getFrameRateID();
        if (currentFpsID != 0) {
            frameRateSpinner.setSelection(currentFpsID);
        }

        frameRateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                int index = parent.getSelectedItemPosition();
                setFrameRate(index);
                setFrameRateIndex(index);
                updateFormula();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                System.out.println("hello");
            }
        });
    }

    public void setFilmFormat(int fileFormat) {
        this.main.formula2Reel.setFilmFormat(fileFormat);
    }

    public void setFrameRate(int frameRateIndex) {
        this.main.formula2Reel.setFramesPerSecond(frameRateIndex);
    }

    public void setFrameRateIndex(int frameRateIndex) {
        this.main.formula2Reel.setFrameRateID(frameRateIndex);
    }

    public void setReelLength(double reelLength){
        this.main.formula2Reel.setReelLength(reelLength);
    }
    //False = metric true = imperial
    public void setUnit(boolean unit, View root){
        this.main.formula2Reel.setUnit(unit);
        TextView reelUnit = root.findViewById(R.id.reelLengthUnit);
        if (unit){
            reelUnit.setText(R.string.ft);
        }

        else{
            reelUnit.setText(R.string.metres);
        }
    }

    private void createRuntime(View root){
        final EditText hourInput = root.findViewById(R.id.hourInput);
        final EditText minuteInput = root.findViewById(R.id.minuteInput);
        minuteInput.setFilters( new InputFilter[]{ new RuntimeInputFilter("0", "59")});
        final EditText secondInput = root.findViewById(R.id.secondInput);
        secondInput.setFilters( new InputFilter[]{ new RuntimeInputFilter("0", "59")});
        this.hour = (this.main.formula2Reel.getRuntime()/3600);
        this.min = ((this.main.formula2Reel.getRuntime()/60)%60);
        this.sec = (this.main.formula2Reel.getRuntime()%60);
        if (hour > 0) {
            String s = Integer.toString(hour);
            hourInput.setText(s); }
        if (min > 0) {
            String s = Integer.toString(min);
            minuteInput.setText(s); }
        if (sec > 0) {
            String s = Integer.toString(sec);
            secondInput.setText(s); }
        hourInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (hourInput.hasFocus()) {
                    try {
                        if (!s.toString().trim().equals("")) {
                            int hour = Integer.parseInt(s.toString());
                            setHour(hour);
                            updateFormula();
                        } else {
                            setHour(0);
                            updateFormula();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        minuteInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (minuteInput.hasFocus()) {
                    try {

                        if (!s.toString().trim().equals("")) {
                            int min = Integer.parseInt(s.toString());
                            setMinute(min);
                            updateFormula();
                        } else {
                            setMinute(0);
                            updateFormula();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        secondInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (secondInput.hasFocus()){
                    try {
                        if (!s.toString().trim().equals("")) {
                            int sec = Integer.parseInt(s.toString());
                            setSecond(sec);
                            updateFormula();
                        } else {
                            setSecond(0);
                            updateFormula();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void setHour(int hour){
        this.hour = hour;
        this.main.formula2Reel.setRuntime(this.hour*3600 + this.min*60 + this.sec);
    }

    public void setMinute(int min){
        this.min = min;
        this.main.formula2Reel.setRuntime(this.hour*3600 + this.min*60 + this.sec);
    }

    public void setSecond(int sec) {
        this.sec = sec;
        this.main.formula2Reel.setRuntime(this.hour*3600 + this.min*60 + this.sec);
    }



    public void updateFormula(){
        //runtime calculator
        if (!this.value) {
            System.out.println("runtime");
            double totalSeconds = this.main.formula2Reel.calculateRuntime();
            int hours = (int) Math.floor(totalSeconds / 3600);
            int minutes = (int) Math.floor((totalSeconds / 60) % 60);
            int seconds = (int) Math.round(totalSeconds % 60);
            //   String time = String.valueOf();
            this.hourValue.setText(String.valueOf(hours));
            this.minuteValue.setText(String.valueOf(minutes));
            this.secValue.setText(String.valueOf(seconds));

        }

        //reel length calculator
        else{
            System.out.println("reel length");
            String reelLength = String.valueOf(this.main.formula2Reel.calculateReelLengthFromRuntime());
            this.reelValue.setText(reelLength);

        }
        System.out.println("updating");
    }

}