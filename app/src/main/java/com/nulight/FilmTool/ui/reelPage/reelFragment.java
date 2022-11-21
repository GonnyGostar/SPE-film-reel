package com.nulight.FilmTool.ui.reelPage;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.nulight.FilmTool.MainActivity;
import com.nulight.FilmTool.R;

public class reelFragment extends Fragment {
    TextView reelLengthText;
    MainActivity main;

    private formula1ViewModel formula1ViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_reel, container, false);
        formula1ViewModel = ViewModelProviders.of(this).get(formula1ViewModel.class);

        this.main = (MainActivity) getActivity();




        //reelLengthTextView object
        this.reelLengthText = root.findViewById(R.id.reelLength);

       //Run functions that add the functionality of the dropdowns and input boxes.
        this.createReelDiameter(root);
        this.createCoreDiameter(root);



        return root;
    }

    private void createReelDiameter(View root){
        final EditText reelDiameterInput = root.findViewById(R.id.reelDiameterInput);
        Double currentRd = (this.main.formula1Reel.getReelDiameter());
        if (currentRd > 0) {
            String s = Double.toString(currentRd);
            reelDiameterInput.setText(s);
        }
        reelDiameterInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    double reelDiameter = Double.parseDouble(s.toString());
                    setReelDiameter(reelDiameter);
                    updateFormula();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        reelDiameterInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reelDiameterInput.getText().clear();
            }
        });
    }

    private void createCoreDiameter(View root){
        final EditText coreDiameterInput = root.findViewById(R.id.coreDiameterInput);
        Double currentCd = this.main.formula1Reel.getCoreDiameter();
        // Remember the previously chosen core diameter
        if (currentCd > 0) {
            String s = Double.toString(currentCd);
            coreDiameterInput.setText(s);
        }
        coreDiameterInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    double coreDiameter = Double.parseDouble(s.toString());
                    setCoreDiameter(coreDiameter);
                    updateFormula();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        coreDiameterInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coreDiameterInput.getText().clear();
            }
        });

    }



    public void setCoreDiameter(double coreDiameter) {
        this.main.formula1Reel.setCoreDiameter(coreDiameter);
    }

    public void setReelDiameter(double reelDiameter) {
        this.main.formula1Reel.setReelDiameter(reelDiameter);
    }

    //False = metric true = imperial
    public void setUnit(boolean unit, View root){
        this.main.formula1Reel.setUnit(unit);
        TextView rdUnit = root.findViewById(R.id.reelDiameterUnit);
        TextView cdUnit = root.findViewById(R.id.reelLengthUnit);
        if (unit){
            cdUnit.setText(R.string.inches);
            rdUnit.setText(R.string.inches);
        }

        else{
            cdUnit.setText(R.string.mm);
            rdUnit.setText(R.string.mm);
        }
    }

    public void updateFormula(){
        String time = String.valueOf(this.main.formula1Reel.calculateReelLength());
        this.reelLengthText.setText(time);
    }
}