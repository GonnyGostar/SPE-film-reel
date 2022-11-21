package com.nulight.FilmTool.ui.flowchart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.nulight.FilmTool.MainActivity;
import com.nulight.FilmTool.R;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.Objects;

public class flowchartFragment extends Fragment {

    // TODO: add dropdown menu with each flowchart
    MainActivity main;
    private flowchartViewModel flowchartViewModel;
    Button yes;
    Button no;
    Button home;
    TextView question;
    TextView label;
    ImageView image;
    OnBackPressedCallback callback;

    // Used for signalling that state needs to be updated when back is pressed
    boolean update = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        flowchartViewModel =
                ViewModelProviders.of(this).get(flowchartViewModel.class);
        View root = inflater.inflate(R.layout.fragment_flowchart, container, false);

        this.main = (MainActivity) getActivity();



        // Set up UI
        this.yes = root.findViewById(R.id.option_1);
        this.no = root.findViewById(R.id.option_2);
        this.home = root.findViewById(R.id.home);
        this.question = root.findViewById(R.id.question);
        this.label = root.findViewById(R.id.label);
        this.image = root.findViewById(R.id.flowImage);

        this.callback = new OnBackPressedCallback(false /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                if (main.flowchart.getCurrentQ().getParentNode() != null) {
                    main.flowchart.setCurrentQ(main.flowchart.getCurrentQ().getParentNode());
                    updateState();
                }
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        // Initialise
        updateState();
 //       this.image.setImageDrawable(Drawable.createFromPath((main.flowchart.getCurrentQ().getAttributes().getNamedItem("img").getTextContent())));

        // TODO: work out a way to internally store and reference images
        // TODO: work out the further info scroll down menu thing

        yes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Node curq = main.flowchart.getCurrentQ().getFirstChild();
                boolean checksFinished = false;
                while(!checksFinished) {
                    if (!(curq instanceof Element)) {
                        curq = main.flowchart.getCurrentQ().getFirstChild().getNextSibling();
                        continue;
                    }
                    if (!curq.getAttributes().getNamedItem("option").getTextContent().equals("Yes")) {
                        curq = curq.getNextSibling();
                        continue;
                    }
                    checksFinished = true;
                }
                main.flowchart.setCurrentQ(curq);
                updateState();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Node curq = main.flowchart.getCurrentQ().getLastChild();
                boolean checksFinished = false;
                while(!checksFinished) {
                    if (!(curq instanceof Element)) {
                        curq = main.flowchart.getCurrentQ().getLastChild().getPreviousSibling();
                        continue;
                    }
                    if (!curq.getAttributes().getNamedItem("option").getTextContent().equals("No")) {
                        curq = curq.getPreviousSibling();
                        continue;
                    }
                    checksFinished = true;
                }
                main.flowchart.setCurrentQ(curq);
                updateState();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Node homeNode = main.flowchart.getRootQ();
                main.flowchart.setCurrentQ(homeNode);
                updateState();
            }
        });

        return root;
    }

    private void updateState() {
        Node curq = main.flowchart.getCurrentQ();
        if (curq.getAttributes().getNamedItem("question") == null) {
            yes.setVisibility(View.GONE);
            no.setVisibility(View.GONE);
            question.setEnabled(false);
            this.label.setText(main.flowchart.getCurrentQ().getAttributes().getNamedItem("label").getTextContent());
        }
        else {
            question.setEnabled(true);
            yes.setVisibility(View.VISIBLE);
            no.setVisibility(View.VISIBLE);
            this.question.setText(main.flowchart.getCurrentQ().getAttributes().getNamedItem("question").getTextContent());
            this.label.setText(main.flowchart.getCurrentQ().getAttributes().getNamedItem("label").getTextContent());
        }
        if (checkIfAtRoot()) {
            this.callback.setEnabled(false);
        }
        else {
            this.callback.setEnabled(true);
        }
    }

    // Helper method to see if we're on the root node
    private boolean checkIfAtRoot() {
        return Objects.equals(main.flowchart.getCurrentQ(), main.flowchart.getRootQ());
    }



}