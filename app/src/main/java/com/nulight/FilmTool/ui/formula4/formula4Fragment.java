package com.nulight.FilmTool.ui.formula4;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.nulight.FilmTool.MainActivity;
import com.nulight.FilmTool.R;
import com.nulight.FilmTool.film.FileFormat;
import com.nulight.FilmTool.film.RuntimeInputFilter;

public class formula4Fragment extends Fragment {

    private formula4ViewModel formula4ViewModel;
    MainActivity main;
    TextView fileSizeText;

    int hour;
    int min;
    int sec;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        formula4ViewModel = ViewModelProviders.of(this).get(formula4ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_formula4, container, false);

        this.main = (MainActivity) getActivity();

        this.fileSizeText = root.findViewById(R.id.fileSize);

        this.createRuntime(root);
        this.createResolutionDropDown(root);
        this.createFileFormatDropDown(root);
        this.createFrameRateDropDown(root);





        return root;
    }

    // TODO: Limit the user when typing in minutes/seconds so they don't go over 59
    private void createRuntime(View root){
        EditText hourInput = root.findViewById(R.id.hourInput);
        EditText minuteInput = root.findViewById(R.id.minuteInput);
        minuteInput.setFilters( new InputFilter[]{ new RuntimeInputFilter("0", "59")});
        EditText secondInput = root.findViewById(R.id.secondInput);
        secondInput.setFilters( new InputFilter[]{ new RuntimeInputFilter("0", "59")});
        this.hour = (this.main.formula4Reel.getRuntime()/3600);
        this.min = ((this.main.formula4Reel.getRuntime()/60)%60);
        this.sec = (this.main.formula4Reel.getRuntime()%60);
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
                try {
                    if (!s.toString().trim().equals("")) {
                        int hour = Integer.parseInt(s.toString());
                        setHour(hour);
                        updateFormula();
                    }
                    else {
                        setHour(0);
                        updateFormula();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
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
                try {
                    if (!s.toString().trim().equals("")) {
                        int min = Integer.parseInt(s.toString());
                        setMinute(min);
                        updateFormula();
                    }
                    else {
                        setMinute(0);
                        updateFormula();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
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
                try {
                    if (!s.toString().trim().equals("")) {
                        int sec = Integer.parseInt(s.toString());
                        setSecond(sec);
                        updateFormula();
                    }
                    else {
                        setSecond(0);
                        updateFormula();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void createFileFormatDropDown(View root){
        Spinner fileFormatSpinner = (Spinner) root.findViewById(R.id.filmFormatInput);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, this.main.formula4Reel.getFileFormatArray());

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fileFormatSpinner.setAdapter(adapter);
        // Remember the previously chosen film format
        FileFormat currentFf = this.main.formula4Reel.getFileFormat();
        if (currentFf.getID() != 0) {
            fileFormatSpinner.setSelection(currentFf.getID());
        }
        fileFormatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                int index = parent.getSelectedItemPosition();
                setFileFormat(index);
                updateFormula();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void createFrameRateDropDown(View root){
        Spinner frameRateSpinner = (Spinner) root.findViewById(R.id.frameRateInput);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, this.main.formula4Reel.getFrameRateArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        frameRateSpinner.setAdapter(adapter);
        // Remember the previously chosen framerate
        int currentFpsID = this.main.formula4Reel.getFrameRateID();
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
            public void onNothingSelected(AdapterView<?> parent) {}
            });
        }

    private void createResolutionDropDown(View root){
        Spinner resolutionSpinner = (Spinner) root.findViewById(R.id.resolutionInput);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, this.main.formula4Reel.getResolutionArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        resolutionSpinner.setAdapter(adapter);


        resolutionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                int index = parent.getSelectedItemPosition();
                setResolution(index);
                updateFormula();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
            });
        }


    public void setFileFormat(int fileFormat) {
        this.main.formula4Reel.setFileFormat(fileFormat);
    }

    public void setFrameRate(int frameRateIndex) {
        this.main.formula4Reel.setFramesPerSecond(frameRateIndex);
    }

    public void setFrameRateIndex(int frameRateIndex) {
        this.main.formula4Reel.setFrameRateID(frameRateIndex);
    }

    public void setResolution(int resolutionIndex) {
        this.main.formula4Reel.setResolution(resolutionIndex);
    }

    public void setHour(int hour){
        this.hour = hour;
        this.main.formula4Reel.setRuntime(this.hour*3600 + this.min*60 + this.sec);
    }

    public void setMinute(int min){
        this.min = min;
        this.main.formula4Reel.setRuntime(this.hour*3600 + this.min*60 + this.sec);
    }

    public void setSecond(int sec){
        this.sec = sec;
        this.main.formula4Reel.setRuntime(this.hour*3600 + this.min*60 + this.sec);
    }

    public void updateFormula(){
        String fileSize = String.valueOf(this.main.formula4Reel.calculateFileSizeFromRuntime());
        this.fileSizeText.setText(fileSize);
    }
}