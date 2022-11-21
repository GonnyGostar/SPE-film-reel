package com.nulight.FilmTool.ui.filePage;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.nulight.FilmTool.MainActivity;
import com.nulight.FilmTool.R;
import com.nulight.FilmTool.film.FileFormat;
import com.nulight.FilmTool.film.Resolution;
import com.nulight.FilmTool.film.RuntimeInputFilter;

public class fileFragment extends Fragment {

    private formula3ViewModel formula3ViewModel;
    MainActivity main;
    EditText fileSizeValue;
    EditText hourValue;
    EditText minuteValue;
    EditText secValue;
    boolean value;

    int hour;
    int min;
    int sec;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        formula3ViewModel = ViewModelProviders.of(this).get(formula3ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_file, container, false);

        this.main = (MainActivity) getActivity();



        //decides which value is being calculated file size or runtime
        this.value=false;

        this.fileSizeValue = root.findViewById(R.id.fileSizeInput);
        this.hourValue = root.findViewById(R.id.hourInput);
        this.minuteValue = root.findViewById(R.id.minuteInput);
        this.secValue = root.findViewById(R.id.secondInput);

        this.createRuntime(root);
        this.createResolutionDropDown(root);
        this.createFileFormatDropDown(root);
        this.createFrameRateDropDown(root);
        this.createToggleCalculatedValue(root);
        this.toggleCalculator(false, root);
        this.createFileSize(root);






        return root;
    }

    private void createToggleCalculatedValue(final View root){
        ToggleButton calcToggle = root.findViewById(R.id.valueToggle);
        calcToggle.setText("File Size");
        calcToggle.setTextOff("File Size");
        calcToggle.setTextOn("Runtime");

        calcToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleCalculator(isChecked, root);
                //updateFormula();
            }
        });

    }

    public void toggleCalculator(boolean value, View root){
        this.value = value;
        System.out.println(value);
        //true calculate runtime
        if(!value){
            EditText hourInput = root.findViewById(R.id.hourInput);
            hourInput.setEnabled(true);
            EditText minuteInput = root.findViewById(R.id.minuteInput);
            minuteInput.setEnabled(true);
            EditText secondInput = root.findViewById(R.id.secondInput);
            secondInput.setEnabled(true);

            hourInput.requestFocus();


            EditText fileSizeInput = root.findViewById(R.id.fileSizeInput);
            fileSizeInput.setEnabled(false);


        }

        else{

            EditText fileSizeInput = root.findViewById(R.id.fileSizeInput);
            fileSizeInput.setEnabled(true);

            fileSizeInput.requestFocus();

            EditText hourInput = root.findViewById(R.id.hourInput);
            hourInput.setEnabled(false);
            EditText minuteInput = root.findViewById(R.id.minuteInput);
            minuteInput.setEnabled(false);
            EditText secondInput = root.findViewById(R.id.secondInput);
            secondInput.setEnabled(false);

        }

    }

    private void createRuntime(View root){
        final EditText hourInput = root.findViewById(R.id.hourInput);
        final EditText minuteInput = root.findViewById(R.id.minuteInput);
        minuteInput.setFilters( new InputFilter[]{ new RuntimeInputFilter("0", "59")});
        final EditText secondInput = root.findViewById(R.id.secondInput);
        secondInput.setFilters( new InputFilter[]{ new RuntimeInputFilter("0", "59")});
        this.hour = (this.main.formula3Reel.getRuntime()/3600);
        this.min = ((this.main.formula3Reel.getRuntime()/60)%60);
        this.sec = (this.main.formula3Reel.getRuntime()%60);
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

    private void createFileSize(View root){
        final EditText fileSizeInput = root.findViewById(R.id.fileSizeInput);
        Double currentFs = (this.main.formula3Reel.getFileSize());
        if (currentFs > 0) {
            String s = Double.toString(currentFs);
            fileSizeInput.setText(s);
        }
        fileSizeInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (fileSizeInput.hasFocus()) {
                    try {
                        int fileSize = Integer.parseInt(s.toString());
                        setFileSize(fileSize);
                        updateFormula();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void createFileFormatDropDown(View root){
        Spinner fileFormatSpinner = (Spinner) root.findViewById(R.id.filmFormatInput);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, this.main.formula3Reel.getFileFormatArray());

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fileFormatSpinner.setAdapter(adapter);
        // Remember the previously chosen file format
        FileFormat currentFf = this.main.formula3Reel.getFileFormat();
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, this.main.formula3Reel.getFrameRateArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        frameRateSpinner.setAdapter(adapter);
        // Remember the previously chosen framerate
        int currentFpsID = this.main.formula3Reel.getFrameRateID();
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, this.main.formula3Reel.getResolutionArray());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        resolutionSpinner.setAdapter(adapter);
        // Remember the previously chosen resolution
        Resolution currentRes = this.main.formula3Reel.getResolution();
        if (currentRes.getID() != 0) {
            resolutionSpinner.setSelection(currentRes.getID());
        }

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


    public void setFileSize(int fileSize){
        this.main.formula3Reel.setFileSize(fileSize);
    }


    public void setFileFormat(int fileFormat) {
        this.main.formula3Reel.setFileFormat(fileFormat);
    }

    public void setFrameRate(int frameRateIndex) {
        this.main.formula3Reel.setFramesPerSecond(frameRateIndex);
    }

    public void setFrameRateIndex(int frameRateIndex) {
        this.main.formula3Reel.setFrameRateID(frameRateIndex);
    }

    public void setResolution(int resolutionIndex) {
        this.main.formula3Reel.setResolution(resolutionIndex);
    }

    public void setHour(int hour){
        this.hour = hour;
        this.main.formula3Reel.setRuntime(this.hour*3600 + this.min*60 + this.sec);
    }

    public void setMinute(int min){
        this.min = min;
        this.main.formula3Reel.setRuntime(this.hour*3600 + this.min*60 + this.sec);
    }

    public void setSecond(int sec){
        this.sec = sec;
        this.main.formula3Reel.setRuntime(this.hour*3600 + this.min*60 + this.sec);
    }


    public void updateFormula(){
        System.out.println("updating");
        System.out.println(this.value);
        //runtime calculator
        if (this.value) {

            System.out.println("runtime");
            double totalSeconds = this.main.formula3Reel.calculateRuntimeFromFileSize();
            System.out.println(totalSeconds);
            int hours = (int) Math.floor(totalSeconds / 3600);
            int minutes = (int) Math.floor((totalSeconds / 60) % 60);
            int seconds = (int) Math.round(totalSeconds % 60);
            //   String time = String.valueOf();
            this.hourValue.setText(String.valueOf(hours));
            this.minuteValue.setText(String.valueOf(minutes));
            this.secValue.setText(String.valueOf(seconds));
        }

        //file size calculator
        else{
            String fileSize = String.valueOf(this.main.formula3Reel.calculateFileSizeFromRuntime());
            this.fileSizeValue.setText(fileSize);

        }

    }
}