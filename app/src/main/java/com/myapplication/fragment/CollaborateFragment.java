package com.myapplication.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.myapplication.R;
import com.myapplication.api.model.TravelMode;
import com.myapplication.service.PathwheelService;
import com.myapplication.view.MainActivity;


public class CollaborateFragment extends Fragment {

    private View rootView;


    public CollaborateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_colaborate, container, false);

        MainActivity.getInstance().setPathwheelTitle("Colaborar");

        Button buttonAddSpot = (Button) rootView.findViewById(R.id.button_colaboracao_manual);
        buttonAddSpot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*FragmentTransaction transaction = MainActivity.getInstance().getFragmentManager().beginTransaction();
                //Fragment fragment = new AddSpotFragment();
                Fragment fragment = new OsmAddSpotFragment();
                transaction.replace(R.id.content, fragment);
                transaction.commit();*/
                MainActivity.getInstance().changeFragment(new OsmAddSpotFragment(), MainActivity.COLLABORATE);
            }
        });

        Switch switchColaboracaoOportunista = (Switch) rootView.findViewById(R.id.switch_colaboracao_automatica);
        switchColaboracaoOportunista.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked && !PathwheelService.isRunning(rootView.getContext())) {
                    try {
                        PathwheelService.start(
                                MainActivity.getInstance(),
                                5000,
                                10,
                                15f,
                                2f,
                                215f,
                                TravelMode.WHEELCHAIR);

                    } catch(Exception e) {
                        Toast.makeText(rootView.getContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else if (!isChecked && PathwheelService.isRunning(rootView.getContext())) {
                    PathwheelService.getInstance().stop();
                }

            }
        });

        if (PathwheelService.isRunning(rootView.getContext())) {
            switchColaboracaoOportunista.setChecked(true);
        }

        // Inflate the layout for this fragment
        return rootView;
    }

}
